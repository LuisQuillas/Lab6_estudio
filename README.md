# Lab 6 - El Juego de la Cacatúa Sulfúrea (GTICS)

Proyecto Spring Boot + Thymeleaf que resuelve el caso **STFGAMES** usando
**Fragmentos Thymeleaf** y **DTOs**, los dos temas que evalúa el Laboratorio 6.

## Cómo se cubre cada punto de la rúbrica

| Puntos | Requisito | Dónde está resuelto |
| --- | --- | --- |
| 2.0 | Conexión y mapeo de entidades | `model/Celda.java`, `model/EstadoJuego.java` (+ tablas de respaldo) mapeadas con JPA a `cacatua_sulfurea.sql` |
| 2.0 | Persistencia de avance del juego | Todo el estado (tablero y posición) se guarda en BD en cada jugada (`JuegoService.procesarMovimiento`), nunca en variables en memoria |
| 2.0 | DTOs definidos a su gusto | `dto/CeldaDto.java` y `dto/EstadoJuegoDto.java`: separan la entidad de BD de lo que la vista necesita |
| 2.5 | Fragmentos de Celda, Tablero y Panel | `templates/fragments/celda.html`, `tablero.html`, `panel.html` |
| 2.0 | Validar que no salga del tronco | `JuegoService.procesarMovimiento` valida límites antes de tocar la BD |
| 2.5 | Mover/picotear según botón | Misma lógica: si la celda destino tiene madera, pica (nivel-1); si está libre, se mueve |
| 2.0 | Actualizar niveles por picotazo | `celdaDestino.setNivel(nivel - 1)` persistido con cada picotazo |
| 2.0 | Validar derrota/victoria | `evaluarFinDePartida(...)` |
| 1.0 | Botón Reiniciar | `JuegoService.reiniciar()` restaura desde `tablero_inicial` / `estado_inicial` |
| 2.0 | Diseño parecido a la imagen | `static/css/estilos.css` + `index.html` |

## Cómo ejecutar

### Opción A: con MySQL (como en el enunciado real)
1. Ejecutar `cacatua_sulfurea.sql` en MySQL Workbench o consola (crea la BD y la carga).
2. Revisar usuario/clave en `src/main/resources/application.properties`.
3. `mvn spring-boot:run`
4. Abrir `http://localhost:8080/cacatua_sulfurea`

### Opción B: demo rápida sin MySQL (perfil H2 en memoria)
```
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```
Usa `schema-h2.sql` / `data-h2.sql` (mismos datos que el script oficial).

## Estructura

```
lab-estudio/
├── cacatua_sulfurea.sql          # script oficial (MySQL)
├── pom.xml
└── src/main/
    ├── java/pe/edu/pucp/cacatua/
    │   ├── CacatuaSulfureaApplication.java
    │   ├── model/        (entidades JPA: tablero actual + respaldos)
    │   ├── repository/   (Spring Data JPA)
    │   ├── dto/           (CeldaDto, EstadoJuegoDto)
    │   ├── service/       (JuegoService: toda la lógica del juego)
    │   └── controller/    (ruta única /cacatua_sulfurea)
    └── resources/
        ├── application.properties / application-h2.properties
        ├── schema-h2.sql, data-h2.sql
        ├── templates/ (index.html + fragments/celda,tablero,panel)
        └── static/css/estilos.css
```

**Nota sobre el ambiente de desarrollo:** este código se escribió y revisó
manualmente (sintaxis Java/Spring/Thymeleaf), pero no pudo compilarse con
Maven en este entorno porque el acceso a Maven Central está bloqueado por la
política de red de este sandbox. Antes de entregarlo, ejecútalo tú con
`mvn spring-boot:run -Dspring-boot.run.profiles=h2` para confirmar que
compila y corre en tu máquina, y revisa los mensajes de consola si algo falla.
