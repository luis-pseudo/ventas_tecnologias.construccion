#!/bin/bash
# Punto de entrada unico: ./scripts/verify-module.sh M13
# Delega en scripts/verificar_mNN.sh del modulo indicado.
MODULO="${1:-}"
[ -n "$MODULO" ] || { echo "Uso: ./scripts/verify-module.sh M13" >&2; exit 1; }
NUMERO="$(echo "$MODULO" | tr 'A-Z' 'a-z')"
SCRIPT="$(dirname "$0")/verificar_${NUMERO}.sh"
[ -f "$SCRIPT" ] || { echo "No existe $SCRIPT (modulo $MODULO sin verificacion)" >&2; exit 1; }
exec bash "$SCRIPT"
