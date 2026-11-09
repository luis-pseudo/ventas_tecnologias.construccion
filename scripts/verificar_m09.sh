#!/bin/bash
# VERIFICACION DEL MODULO M09 (P09 - API basica con Spring Boot).
# Uso: ./scripts/verificar_m09.sh
# Variables opcionales: PORT (default 8089), MIN_TESTS (default 34)
#
# ACCIONES QUE REALIZA:
# 1. Comprueba Java 11, Maven y curl.
# 2. Revisa que domain/application no dependan de Spring ni de JPA/Hibernate.
# 3. Ejecuta 'mvn clean test' y exige el minimo de pruebas y HexagonalTest.
# 4. Levanta la API y hace una prueba de humo (exito y errores 400 y 404).

set -uo pipefail
cd "$(dirname "$0")/.." || exit 1

PORT="${PORT:-8089}"
MIN_TESTS="${MIN_TESTS:-34}"
LOG_MVN="$(mktemp)"
LOG_APP="$(mktemp)"
APP_PID=""

fallo() { echo "FALLO M09: $1" >&2; exit 1; }

limpiar() {
  if [ -n "$APP_PID" ] && kill -0 "$APP_PID" 2>/dev/null; then
    kill -- "-$APP_PID" 2>/dev/null || kill "$APP_PID" 2>/dev/null
  fi
  rm -f "$LOG_MVN" "$LOG_APP"
}
trap limpiar EXIT

echo "----INICIANDO VERIFICACION DEL MODULO M09------"

echo ""
echo "[1/5] Entorno..."
command -v mvn >/dev/null  || fallo "Maven no esta instalado"
command -v curl >/dev/null || fallo "curl no esta instalado"
JAVA_MAYOR="$(java -version 2>&1 | head -1 | sed -E 's/.*"(1\.)?([0-9]+).*/\2/')"
[ "$JAVA_MAYOR" = "11" ] || fallo "Se requiere Java 11 y se detecto Java $JAVA_MAYOR (usa: sdk use java <version 11>)"
echo "Java $JAVA_MAYOR OK"

echo ""
echo "[2/5] Reglas del nucleo..."
if grep -rn "jakarta.persistence\|javax.persistence\|org.hibernate" src/main/java/mx/uv/tcsw/domain; then
  fallo "El dominio no debe depender de JPA ni Hibernate"
fi
if grep -rn "org.springframework" src/main/java/mx/uv/tcsw/domain src/main/java/mx/uv/tcsw/application; then
  fallo "domain y application no deben depender de Spring"
fi
echo "Nucleo limpio OK"

echo ""
echo "[3/5] Pruebas automatizadas (mvn clean test)..."
mvn -B clean test > "$LOG_MVN" 2>&1 || { tail -40 "$LOG_MVN"; fallo "mvn clean test fallo"; }
TOTAL="$(grep -E 'Tests run:.*Failures' "$LOG_MVN" | tail -1 | sed -E 's/.*Tests run: ([0-9]+),.*/\1/')"
[ -n "$TOTAL" ] || fallo "No se pudo leer el total de pruebas"
[ "$TOTAL" -ge "$MIN_TESTS" ] || fallo "Se ejecutaron $TOTAL pruebas y el minimo es $MIN_TESTS (falta junit-vintage-engine?)"
grep -q "Running mx.uv.tcsw.architecture.HexagonalTest" "$LOG_MVN" || fallo "HexagonalTest no se ejecuto"
echo "Pruebas OK ($TOTAL ejecutadas, minimo $MIN_TESTS)"

echo ""
echo "[4/5] Arranque de la API..."
if [ ! -f src/main/java/mx/uv/tcsw/adapter/web/ProductoRestController.java ]; then
  echo "Controladores aun no integrados: se omite la prueba de humo."
else
  setsid mvn -B spring-boot:run \
    -Dspring-boot.run.arguments="--server.port=$PORT" > "$LOG_APP" 2>&1 &
  APP_PID=$!
  LISTA=0
  for _ in $(seq 1 90); do
    if curl -s -o /dev/null "http://localhost:$PORT/v3/api-docs"; then LISTA=1; break; fi
    kill -0 "$APP_PID" 2>/dev/null || { tail -40 "$LOG_APP"; fallo "La aplicacion termino antes de arrancar"; }
    sleep 1
  done
  [ "$LISTA" = "1" ] || { tail -40 "$LOG_APP"; fallo "La API no respondio en 90 segundos"; }

  echo ""
  echo "[5/5] Prueba de humo (exito y errores)..."
  BASE="http://localhost:$PORT/api/v1"
  codigo() { curl -s -o /dev/null -w "%{http_code}" "$@"; }

  C="$(codigo -X POST "$BASE/productos" -H 'Content-Type: application/json' \
        -d '{"nombre":"Teclado","precio":450.00}')"
  [ "$C" = "201" ] || fallo "POST /productos esperaba 201 y devolvio $C"

  C="$(codigo "$BASE/productos")"
  [ "$C" = "200" ] || fallo "GET /productos esperaba 200 y devolvio $C"

  C="$(codigo -X POST "$BASE/productos" -H 'Content-Type: application/json' \
        -d '{"nombre":"","precio":-1}')"
  [ "$C" = "400" ] || fallo "POST invalido esperaba 400 y devolvio $C"

  C="$(codigo "$BASE/productos/no-existe")"
  [ "$C" = "404" ] || fallo "GET inexistente esperaba 404 y devolvio $C"

  curl -s "$BASE/productos/no-existe" | grep -q '"code":"NOT_FOUND"' \
    || fallo "El error 404 no usa el formato consistente"
  echo "API OK (201, 200, 400, 404 con formato consistente)"
fi

echo ""
echo "MODULO_M09_VERIFICADO"