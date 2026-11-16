#!/bin/bash
# VERIFICACION DEL MODULO M13 (P10 - Cliente Angular que consume la API).
# Uso: ./scripts/verify-module.sh M13      (o directamente ./scripts/verificar_m13.sh)
# Variables opcionales:
#   PORT_API (default 8089)   puerto temporal de la API Spring Boot
#   PORT_WEB (default 4299)   puerto temporal del servidor de desarrollo Angular
#   MIN_TESTS_API (default 40)  minimo de pruebas del backend
#   MIN_TESTS_WEB (default 30)  minimo de pruebas del cliente Angular
#
# ACCIONES QUE REALIZA:
# 1. Comprueba Java >= 11, Maven, curl, Node (>= 20.19 / 22.12) y npm.
# 2. Revisa que domain/application no dependan de Spring ni de JPA/Hibernate.
# 3. Ejecuta 'mvn clean test' (backend) y exige el minimo de pruebas y HexagonalTest.
# 4. Ejecuta en cliente-angular: npm ci (si falta node_modules), build de produccion y pruebas.
# 5. Levanta la API y prueba el contrato que consume el cliente (exito y errores 400/404).
# 6. Levanta el cliente Angular con proxy hacia la API y repite el recorrido a traves de el.

set -uo pipefail
cd "$(dirname "$0")/.." || exit 1
RAIZ="$(pwd)"

PORT_API="${PORT_API:-8089}"
PORT_WEB="${PORT_WEB:-4299}"
MIN_TESTS_API="${MIN_TESTS_API:-40}"
MIN_TESTS_WEB="${MIN_TESTS_WEB:-30}"
LOG_MVN="$(mktemp)"
LOG_WEB="$(mktemp)"
LOG_API="$(mktemp)"
LOG_NG="$(mktemp)"
PROXY_CONF="$(mktemp --suffix=.json)"
API_PID=""
NG_PID=""

fallo() { echo "FALLO M13: $1" >&2; exit 1; }

detener() {
  for pid in "$API_PID" "$NG_PID"; do
    if [ -n "$pid" ] && kill -0 "$pid" 2>/dev/null; then
      kill -- "-$pid" 2>/dev/null || kill "$pid" 2>/dev/null
    fi
  done
  rm -f "$LOG_MVN" "$LOG_WEB" "$LOG_API" "$LOG_NG" "$PROXY_CONF"
}
trap detener EXIT

codigo() { curl -s -o /dev/null -w "%{http_code}" "$@"; }
sin_color() { sed -E 's/\x1b\[[0-9;]*[A-Za-z]//g'; }

# Recorrido de la API: se ejecuta contra la API directa y contra el proxy del cliente.
recorrido_api() {
  local BASE="$1" ETIQUETA="$2" C PRODUCTO_ID VENTA_ID

  C="$(codigo -X POST "$BASE/productos" -H 'Content-Type: application/json' \
        -d '{"nombre":"Teclado","precio":450.00}')"
  [ "$C" = "201" ] || fallo "[$ETIQUETA] POST /productos esperaba 201 y devolvio $C"

  PRODUCTO_ID="$(curl -s -X POST "$BASE/productos" -H 'Content-Type: application/json' \
        -d '{"nombre":"Mouse","precio":199.90}' | sed -n 's/.*"id":"\([^"]*\)".*/\1/p')"
  [ -n "$PRODUCTO_ID" ] || fallo "[$ETIQUETA] la API no devolvio el id del producto"

  C="$(codigo "$BASE/productos")"
  [ "$C" = "200" ] || fallo "[$ETIQUETA] GET /productos esperaba 200 y devolvio $C"

  VENTA_ID="$(curl -s -X POST "$BASE/ventas" -H 'Content-Type: application/json' \
        -d "{\"lineas\":[{\"productoId\":\"$PRODUCTO_ID\",\"cantidad\":2}]}" \
        | sed -n 's/.*"id":"\([^"]*\)".*/\1/p' | head -1)"
  [ -n "$VENTA_ID" ] || fallo "[$ETIQUETA] POST /ventas no devolvio una venta con id"

  C="$(codigo "$BASE/ventas")"
  [ "$C" = "200" ] || fallo "[$ETIQUETA] GET /ventas esperaba 200 y devolvio $C"
  curl -s "$BASE/ventas" | grep -q "$VENTA_ID" \
    || fallo "[$ETIQUETA] GET /ventas no incluye la venta recien registrada"

  # Casos negativos (los que el cliente debe mostrar como error)
  C="$(codigo -X POST "$BASE/ventas" -H 'Content-Type: application/json' \
        -d '{"lineas":[{"productoId":"no-existe","cantidad":1}]}')"
  [ "$C" = "404" ] || fallo "[$ETIQUETA] venta con producto inexistente esperaba 404 y devolvio $C"
  curl -s -X POST "$BASE/ventas" -H 'Content-Type: application/json' \
        -d '{"lineas":[{"productoId":"no-existe","cantidad":1}]}' | grep -q '"code":"NOT_FOUND"' \
    || fallo "[$ETIQUETA] el error 404 no usa el formato consistente"

  C="$(codigo -X POST "$BASE/productos" -H 'Content-Type: application/json' \
        -d '{"nombre":"","precio":-1}')"
  [ "$C" = "400" ] || fallo "[$ETIQUETA] producto invalido esperaba 400 y devolvio $C"

  echo "[$ETIQUETA] OK: 201 producto, 200 listas, 201 venta, venta listada, 404 y 400 con formato consistente"
}

echo "----INICIANDO VERIFICACION DEL MODULO M13------"

echo ""
echo "[1/6] Entorno..."
command -v mvn  >/dev/null || fallo "Maven no esta instalado"
command -v curl >/dev/null || fallo "curl no esta instalado"
command -v node >/dev/null || fallo "Node.js no esta instalado (se requiere >= 20.19 o >= 22.12)"
command -v npm  >/dev/null || fallo "npm no esta instalado"
JAVA_MAYOR="$(java -version 2>&1 | head -1 | sed -E 's/.*"(1\.)?([0-9]+).*/\2/')"
[ "${JAVA_MAYOR:-0}" -ge 11 ] 2>/dev/null || fallo "Se requiere Java 11 o superior y se detecto Java $JAVA_MAYOR"
node -e 'const [a,b]=process.versions.node.split(".").map(Number);process.exit((a===20&&b>=19)||(a===22&&b>=12)||a>=24?0:1)' \
  || fallo "Node $(node -v) no es compatible con Angular 21 (usa 20.19+, 22.12+ o 24+)"
echo "Java $JAVA_MAYOR, Node $(node -v), npm $(npm -v) OK"

echo ""
echo "[2/6] Reglas del nucleo..."
if grep -rn "jakarta.persistence\|javax.persistence\|org.hibernate" src/main/java/mx/uv/tcsw/domain; then
  fallo "El dominio no debe depender de JPA ni Hibernate"
fi
if grep -rn "org.springframework" src/main/java/mx/uv/tcsw/domain src/main/java/mx/uv/tcsw/application; then
  fallo "domain y application no deben depender de Spring"
fi
echo "Nucleo limpio OK"

echo ""
echo "[3/6] Pruebas del backend (mvn clean test)..."
mvn -B clean test > "$LOG_MVN" 2>&1 || { tail -40 "$LOG_MVN"; fallo "mvn clean test fallo"; }
TOTAL_API="$(grep -E 'Tests run:.*Failures' "$LOG_MVN" | tail -1 | sed -E 's/.*Tests run: ([0-9]+),.*/\1/')"
[ -n "$TOTAL_API" ] || fallo "No se pudo leer el total de pruebas del backend"
[ "$TOTAL_API" -ge "$MIN_TESTS_API" ] || fallo "Backend: $TOTAL_API pruebas y el minimo es $MIN_TESTS_API"
grep -q "Running mx.uv.tcsw.architecture.HexagonalTest" "$LOG_MVN" || fallo "HexagonalTest no se ejecuto"
echo "Backend OK ($TOTAL_API pruebas, minimo $MIN_TESTS_API)"

echo ""
echo "[4/6] Cliente Angular (build y pruebas)..."
cd "$RAIZ/cliente-angular" || fallo "No existe la carpeta cliente-angular"
if [ ! -d node_modules ]; then
  npm ci --no-audit --no-fund > "$LOG_WEB" 2>&1 || { tail -30 "$LOG_WEB"; fallo "npm ci fallo"; }
fi
npm run build > "$LOG_WEB" 2>&1 || { tail -40 "$LOG_WEB"; fallo "ng build fallo"; }
echo "Build de produccion OK"
CI=true npm run test:ci > "$LOG_WEB" 2>&1 || { sin_color < "$LOG_WEB" | tail -60; fallo "Las pruebas del cliente fallaron"; }
RESUMEN_WEB="$(sin_color < "$LOG_WEB" | grep -E '^ *Tests +[0-9]+ passed' | tail -1)"
TOTAL_WEB="$(echo "$RESUMEN_WEB" | sed -E 's/.*Tests +([0-9]+) passed.*/\1/')"
[ -n "$TOTAL_WEB" ] || { sin_color < "$LOG_WEB" | tail -20; fallo "No se pudo leer el total de pruebas del cliente"; }
sin_color < "$LOG_WEB" | grep -qE 'Tests .*failed' && fallo "El cliente reporta pruebas fallidas"
[ "$TOTAL_WEB" -ge "$MIN_TESTS_WEB" ] || fallo "Cliente: $TOTAL_WEB pruebas y el minimo es $MIN_TESTS_WEB"
echo "Pruebas del cliente OK ($TOTAL_WEB, minimo $MIN_TESTS_WEB)"
cd "$RAIZ" || exit 1

echo ""
echo "[5/6] Arranque de la API y contrato..."
setsid mvn -B spring-boot:run \
  -Dspring-boot.run.arguments="--server.port=$PORT_API" > "$LOG_API" 2>&1 &
API_PID=$!
LISTA=0
for _ in $(seq 1 90); do
  if curl -s -o /dev/null "http://localhost:$PORT_API/v3/api-docs"; then LISTA=1; break; fi
  kill -0 "$API_PID" 2>/dev/null || { tail -40 "$LOG_API"; fallo "La API termino antes de arrancar"; }
  sleep 1
done
[ "$LISTA" = "1" ] || { tail -40 "$LOG_API"; fallo "La API no respondio en 90 segundos"; }
recorrido_api "http://localhost:$PORT_API/api/v1" "API directa"

echo ""
echo "[6/6] Recorrido integrado a traves del cliente Angular (proxy)..."
cat > "$PROXY_CONF" <<JSON
{ "/api": { "target": "http://localhost:$PORT_API", "secure": false, "changeOrigin": true } }
JSON
cd "$RAIZ/cliente-angular" || exit 1
setsid npx ng serve --port "$PORT_WEB" --proxy-config "$PROXY_CONF" > "$LOG_NG" 2>&1 &
NG_PID=$!
cd "$RAIZ" || exit 1
LISTO=0
for _ in $(seq 1 120); do
  if [ "$(codigo "http://localhost:$PORT_WEB/")" = "200" ]; then LISTO=1; break; fi
  kill -0 "$NG_PID" 2>/dev/null || { tail -40 "$LOG_NG"; fallo "ng serve termino antes de arrancar"; }
  sleep 1
done
[ "$LISTO" = "1" ] || { tail -40 "$LOG_NG"; fallo "El cliente Angular no respondio en 120 segundos"; }
curl -s "http://localhost:$PORT_WEB/" | grep -q "<app-root" || fallo "La pagina del cliente no contiene <app-root>"
echo "Cliente Angular sirviendo en http://localhost:$PORT_WEB"
recorrido_api "http://localhost:$PORT_WEB/api/v1" "Cliente -> proxy -> API"

echo ""
echo "MODULO_M13_VERIFICADO"
