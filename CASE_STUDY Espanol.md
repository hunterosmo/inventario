# Caso de Estudio: Sistema de Gestión de Inventario

> **Documento de portfolio profesional**
> Este caso de estudio describe el contexto, decisiones técnicas y aprendizajes detrás del sistema disponible en este repositorio. Los nombres específicos de la empresa, productos y categorías han sido anonimizados; la arquitectura y decisiones técnicas son reales.

---

## Resumen ejecutivo

Diseñé y construí un sistema web de gestión de inventario para una **distribuidora de productos de consumo** en Estados Unidos. El sistema reemplazó procesos manuales basados en papel y hojas de cálculo informales por una aplicación centralizada con roles de usuario, control de stock por áreas, búsqueda avanzada y exportación de reportes.

**Stack técnico:** Java 8, JSF 2.2, Hibernate 4.3, PrimeFaces 6.0, MySQL, GlassFish 4.1.1.

**Mi rol:** Único desarrollador del sistema. Análisis de requisitos, diseño de base de datos, arquitectura, implementación, despliegue y mantenimiento durante 3+ años.

---

## El problema

Cuando comencé, el control de inventario de la empresa tenía varios problemas crónicos:

- **Sin sistema digital centralizado.** El stock se llevaba en papel y hojas de cálculo personales que no se compartían entre quien estaba en bodega, quien estaba en oficina, y quien tomaba decisiones de compra.
- **Sin trazabilidad.** Nadie podía decir con certeza cuánto producto había de cierto ítem, ni cuándo se había actualizado la última cuenta.
- **Sin control de quién hacía qué.** Cualquiera podía cambiar números sin registro.
- **Sin categorización funcional.** Productos de áreas distintas (líquidos, abarrotes, detergentes) se mezclaban en la práctica, dificultando los conteos por sección.
- **Reportes manuales.** Generar un reporte de inventario requería un día completo de conteo físico y armado en Excel.

Adicionalmente identifiqué **problemas operacionales físicos** que el software por sí solo no podía resolver y que también recomendé:

- **Capacidad de almacenamiento insuficiente.** El espacio principal estaba lleno todo el año. Recomendé y se aprobó la instalación de un **contenedor frío externo** para ampliar la capacidad.
- **Falta de organización física.** No había sistema de estantes que facilitara los conteos. Diseñé y se aprobó una **estructura de estantería** que permitió que los conteos fueran consistentes.

Estos cambios físicos fueron prerrequisito para que el sistema digital funcionara bien — el software puede tener los datos perfectos, pero si la bodega es un caos, el dato no se mantiene preciso.

---

## El proceso

### Análisis y diseño

Antes de escribir código entrevisté a las personas que iban a usar el sistema: quien hace los conteos, quien ingresa datos, quien revisa reportes, quien decide compras. De ahí salieron los requisitos reales:

- **Dos perfiles de usuario distintos.** Quien administra (gestiona usuarios, áreas, configuración) y quien opera el día a día (registra inventario, consulta, exporta reportes).
- **Búsqueda y filtrado por múltiples criterios.** Por producto, por fecha, por área.
- **Cálculos automáticos.** Valor total (cantidad × peso × precio) y peso total agregado. Antes se hacían a mano y había errores frecuentes.
- **Exportación a PDF.** Para los reportes que se entregaban a contabilidad.

### Decisiones de arquitectura

**¿Por qué Java / JSF / Hibernate?**

En ese momento estaba terminando mi carrera de Ingeniería en Sistemas y JSF/Hibernate eran las tecnologías con las que más experiencia tenía y que mejor entendía. Para un sistema interno empresarial, las decisiones que prioricé fueron:

- **Persistencia robusta con Hibernate ORM.** Necesitaba que las relaciones (usuarios → registros, áreas → productos) fueran consistentes y que los cambios se trackearan correctamente. Hibernate me dio mapeo objeto-relacional sin tener que escribir SQL crudo en cada operación.
- **Encriptación de contraseñas con BCrypt.** Aunque era un sistema interno, no quería contraseñas en texto plano. BCrypt con sal es estándar de la industria.
- **Pool de conexiones con C3P0** (max 20, min 5, timeout 1800s). Para evitar problemas de rendimiento con conexiones repetidas a MySQL.
- **Sesiones con timeout de 30 minutos.** Por seguridad, especialmente para el rol admin.
- **Roles diferenciados en la capa de presentación.** Las páginas de admin viven en `/admin/` y las del visitante en `/visitante/`. Esto facilita el control de acceso a nivel de URL.

### Modelo de datos

Tres entidades principales:

- **`Usuarios`** — autenticación, datos de contacto, rol (`admin` / `visitante`)
- **`Area`** — categorías funcionales
- **`Inventarios`** — el registro central, con fecha, producto, área, cantidad, peso, precio, y referencia al usuario que lo registró

Las foreign keys de `Inventarios` apuntan a `Area` y `Usuarios`, lo que me permite tener trazabilidad: quién registró qué y en qué área. Cada registro de inventario tiene su propia fecha, lo que permite reconstruir la historia.

### Implementación (3+ años de iteración)

El sistema no se construyó de una sola vez. Empezó como un MVP simple (CRUD de inventario, autenticación, áreas) y fue evolucionando con uso real. Algunas iteraciones importantes:

- **v1:** CRUD básico, login con un solo rol.
- **v2:** Roles diferenciados, separación de páginas admin/visitante, cambio de contraseña separado del CRUD de usuario.
- **v3:** Búsqueda avanzada con múltiples filtros combinables.
- **v4:** Exportación a PDF con PrimeFaces.
- **v5+:** Refinamientos de UX basados en feedback real ("queremos ver el total mientras filtramos", "queremos buscar por mes", etc.)

---

## Resultados e impacto

- **Tiempo de generación de reportes:** pasó de un día completo de conteo manual y armado en Excel, a minutos (consultar, filtrar, exportar PDF).
- **Centralización del dato:** una sola fuente de verdad. Quien está en bodega ve lo mismo que quien está en oficina.
- **Control de acceso:** el rol de visitante puede consultar y registrar inventario sin tocar configuración crítica.
- **Trazabilidad:** cada registro tiene fecha y usuario, lo que permitió identificar errores y patrones que antes pasaban desapercibidos.
- **Auditoría visual:** las búsquedas filtradas hicieron evidente cuándo había discrepancias entre el conteo físico y el digital.

---

## Lecciones aprendidas

**El software no resuelve solo problemas de software.** Una parte importante del éxito de este proyecto fue identificar los problemas físicos (capacidad de almacenamiento, falta de estantería) y empujar para que se resolvieran al mismo tiempo. Un sistema digital perfecto sobre una bodega desorganizada produce datos poco confiables. Esto me enseñó a mirar el problema completo, no solo la parte que cabe en una pantalla.

**Iteración con usuarios reales es invaluable.** Las versiones tempranas tenían funciones que pensé que serían útiles y no se usaron, y faltaban cosas que solo surgieron cuando la gente lo usó día a día. La diferencia entre "lo que la gente dice que necesita" y "lo que realmente necesita" se descubre con uso, no con entrevistas.

**Decisiones simples y conocidas valen más que decisiones nuevas y vistosas.** Pude haber elegido un stack más moderno (React, Spring Boot, microservicios) pero habría agregado complejidad sin beneficio claro para un sistema interno de este tamaño. JSF + Hibernate + MySQL + GlassFish era un stack maduro, estable, y que yo podía mantener solo. La decisión correcta a veces es la menos llamativa.

**El rol de "desarrollador único" requiere más que código.** Hice análisis, diseño, implementación, despliegue, soporte, y entrenamiento de usuarios. Eso me enseñó disciplina en documentar, pensar en mantenibilidad, y no construir cosas que después no pueda explicar.

---

## Metodología y herramientas

Quiero ser transparente sobre cómo trabajé en este proyecto, porque creo que es relevante para entender qué fue mi aporte real:

- **Análisis del problema y diseño de la solución:** mío. Las entrevistas con usuarios, la identificación de los problemas físicos (capacidad, estantería), el modelo de datos, los roles de usuario, los flujos de trabajo, las decisiones de arquitectura y de stack — todo esto vino del contexto real que viví dentro de la empresa.
- **Implementación del código:** trabajé con apoyo de asistentes de IA como herramienta de desarrollo, lo cual es práctica estándar en la industria actual. Las decisiones de qué construir, cómo estructurarlo, qué librerías usar y cómo integrarlo con el problema de negocio fueron mías. Entiendo el código que está en este repositorio y puedo explicar cualquier parte de él.
- **Iteración y mantenimiento:** mío. Los más de 3 años de uso real, con sus errores, ajustes, y mejoras dirigidas por feedback de usuarios, son la parte más difícil de "fakear" — y es donde más aprendí.

Si tienes interés en este proyecto y quieres ver una demo en vivo o revisar el código en una entrevista técnica, estoy disponible.

---

## Stack técnico (detallado)

| Capa | Tecnología | Versión |
|---|---|---|
| Lenguaje | Java | 8+ |
| Framework web | JSF | 2.2 |
| Componentes UI | PrimeFaces | 6.0 |
| ORM | Hibernate | 4.3.x |
| Encriptación | BCrypt | — |
| Pool de conexiones | C3P0 | — |
| Base de datos | MySQL | 5.7+ |
| Servidor | GlassFish | 4.1.1 |
| IDE | NetBeans | 8.2 |

---

