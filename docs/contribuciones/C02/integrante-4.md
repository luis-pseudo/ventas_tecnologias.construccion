# Reporte de Actividades - Integrante 4
Rol: Pruebas Unitarias, Cobertura y SonarQube

## Actividades Realizadas
- **Pruebas de Transacciones:** Se implementaron pruebas unitarias en RegistrarVentaServiceTest.java utilizando Mockito para aislar el adaptador VentaRepositoryHibernate.
- **Verificación de Rollback:** Se validó que el sistema invoque transaction.rollback() ante fallos de validación, asegurando la consistencia transaccional.
- **Análisis de Calidad:** Se ejecutó mvn verify contra el proyecto integrado.