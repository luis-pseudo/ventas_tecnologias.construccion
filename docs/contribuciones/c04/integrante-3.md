# Contribución C04 - Integrante 3

**Nombre:** Jonathan Daniel Paz Sanchez  
**Rol:** Sonar, calidad y tratamiento de hallazgos  
**Issue:** #[COMPLETAR]  
**Rama:** `feature/c04-sonar`  
**PR:** #[COMPLETAR]  
**Revisó el PR:** Integrante 4  
**Revisé el PR de:** Integrante 2

## Cambios realizados

- Se agregó `sonar-project.properties` para analizar el backend Java y el cliente Angular.
- Se configuraron los reportes de cobertura JaCoCo (XML) y LCOV.
- Se documentó el procedimiento reproducible para generar los reportes y ejecutar el análisis.

## Análisis reproducible

Requisitos: Java 11, Maven, Node.js y npm. También se necesita acceso a un servidor SonarQube y un token con permisos de análisis. Define `SONAR_HOST_URL` y `SONAR_TOKEN` en el entorno antes de ejecutar los comandos; no guardes el token en el repositorio.

Desde la raíz del repositorio:

```bash
mvn -B clean verify
cd cliente-angular
npm ci --no-audit --no-fund
npm run test:cov
cd ..
mvn -B sonar:sonar \
  -Dsonar.projectKey=tcsw-ventas \
  -Dsonar.host.url="$SONAR_HOST_URL" \
  -Dsonar.token="$SONAR_TOKEN"
```

El análisis toma la cobertura Java de `target/site/jacoco/jacoco.xml` y la cobertura Angular de `cliente-angular/coverage/cliente-angular/lcov.info`.

## Hallazgos tratados

El análisis SonarQube no se ejecutó en este cambio porque no se proporcionaron servidor ni token. No se reportan hallazgos ni correcciones sin evidencia. Después de ejecutar el procedimiento anterior, registrar aquí cada hallazgo y la acción tomada:

| Archivo | Hallazgo | Acción | Evidencia |
|---|---|---|---|

## Limitación conocida

En esta rama, `npm run test:cov` no llega a generar LCOV porque el compilador Angular no encuentra imports de modelos, servicios e interceptor usados por el catálogo. Esos errores están fuera de los archivos de esta contribución; deben resolverse antes de poder verificar la cobertura frontend.

## Evidencia pendiente

- URL o identificador del análisis SonarQube.
- Captura del Quality Gate.
- Reporte JaCoCo generado; reporte LCOV pendiente de que pase la compilación Angular.
- Hallazgos y correcciones respaldados por el análisis.
