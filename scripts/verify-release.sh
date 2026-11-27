#!/bin/bash
set -euo pipefail

cd "$(dirname "$0")/.."
RAIZ="$(pwd)"
DIST="$RAIZ/dist/v1.0-rc"

echo "== Verificando candidato v1.0-rc =="

mvn clean test
cd "$RAIZ/cliente-angular"
npm ci --no-audit --no-fund
npm run test:ci
npm run test:cov
npm run build
cd "$RAIZ"

./scripts/verify-module.sh M13

mvn -B package -DskipTests

mkdir -p "$DIST"
cp target/tcsw-ventas-*.jar "$DIST/" || { echo "ERROR: no se encontro el JAR en target/"; exit 1; }
tar -czf "$DIST/cliente-angular.tar.gz" -C cliente-angular dist

echo "Candidato generado en $DIST"
echo "MODULO_R05_VERIFICADO"
