#!/bin/bash
# DESCRIPCIÓN DEL SCRIPT:
# Este script realiza la verificacion automatizada del proyecto.
#
# ACCIONES QUE REALIZA:
# 1. Ejecuta 'mvn clean' para eliminar artefactos y compilaciones previas.
# 2. Ejecuta 'mvn test' para compilar el codigo y correr la suite completa
#    de pruebas unitarias en todos los modulos del proyecto.
# 3. Evalua el resultado: si una prueba falla, aborta la ejecucion notificando
#    el error; si todas pasan, confirma el exito de la verificacion.

echo "----INICIANDO VERIFICACION INTEGRAL DE PRUEBAS------"

# 1. Limpieza previa del proyecto Maven
echo ""
echo "[1/2] Limpiando artefactos de builds anteriores (mvn clean)..."
mvn clean

if [ $? -ne 0 ]; then
    echo "Error al limpiar el proyecto. Interrumpiendo verificacion."
    exit 1
fi

# 2. Ejecucion de la suite completa de pruebas unitarias
echo ""
echo "[2/2] Ejecutando pruebas unitarias de todos los modulos..."
mvn test

if [ $? -eq 0 ]; then
    echo ""
    echo "----VERIFICACION INTEGRAL COMPLETADA CON EXITO-----"
    echo "Todas las pruebas unitarias pasaron correctamente."
else
    echo ""
    echo "----Error: Se detectaron fallas o errores en las pruebas.----"
    exit 1
fi