# Trazabilidad Individual - Integrante 3

* *Rol:* Autor
* *Issue atendido:* [https://github.com/luis-pseudo/ventas_tecnologias.construccion/issues/3]
* *Pull Request:* [https://github.com/luis-pseudo/ventas_tecnologias.construccion/pull/5]
* *Contribución verificable:* Implementé el adaptador InMemoryProductoRepository utilizando un HashMap. En la revisión de código se detectó que la lista retornada exponía las referencias internas. Respondí corrigiendo el código con Collections.unmodifiableList() para proteger la inmutabilidad de la colección y ejecuté pruebas locales con éxito para garantizar que no existieran regresiones.
