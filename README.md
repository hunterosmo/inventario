# 📦 Sistema de Gestión de Inventario

Sistema web de gestión de inventario con roles, búsqueda avanzada y reportes en PDF.
Construido con **Java 8 · JSF 2.2 · Hibernate 4.3 · MySQL · GlassFish**, con más de 3 años de iteración basada en uso real.

> 📖 **Lee el caso de estudio completo:** [Español](https://github.com/hunterosmo/inventario/blob/main/CASE_STUDY%20Espanol.md) · [English]([CASE_STUDY_Ingles.md](https://github.com/hunterosmo/inventario/blob/main/CASE_STUDY_Ingles.md)
> Contiene el contexto del problema, las decisiones técnicas y las lecciones aprendidas.

---

## 🚀 Características Principales

### 🔐 Gestión de Usuarios
- Autenticación con contraseñas encriptadas (BCrypt con sal)
- Roles diferenciados: **Administrador** y **Visitante**
- CRUD completo de usuarios
- Cambio de contraseña separado de la información personal

### 📦 Gestión de Inventario
- Registro de productos por áreas/categorías
- Control de cantidades, pesos y precios
- Cálculo automático de valor total y peso total
- Búsqueda y filtrado avanzado (por producto, fecha, área)
- Exportación de reportes a PDF

### 🏢 Gestión de Áreas
- Categorización funcional de productos (abarrote, detergentes, líquidos, etc.)
- CRUD completo

### 📊 Dashboard y Reportes
- Vista general del inventario en tiempo real
- Totales consolidados por valor y peso
- Interfaces diferenciadas según rol del usuario

---

## 🛠️ Stack Técnico

| Capa | Tecnología |
|---|---|
| **Lenguaje** | Java 8+ |
| **Framework web** | JSF 2.2 |
| **Componentes UI** | PrimeFaces 6.0 |
| **ORM** | Hibernate 4.3.x |
| **Seguridad** | BCrypt (hashing de contraseñas) |
| **Pool de conexiones** | C3P0 |
| **Base de datos** | MySQL 5.7+ |
| **Servidor de aplicaciones** | GlassFish 4.1.1 |
| **IDE recomendado** | NetBeans 8.2 |

---

## 📋 Prerrequisitos

- Java JDK 8+
- MySQL Server 5.7+
- GlassFish Server 4.1.1 o Apache Tomcat 8+
- NetBeans 8.2 o IDE compatible con proyectos JSF

---

## ⚙️ Configuración

### Pool de Conexiones C3P0

```xml
<property name="hibernate.c3p0.max_size">20</property>
<property name="hibernate.c3p0.min_size">5</property>
<property name="hibernate.c3p0.timeout">1800</property>
```

### Sesiones

- Timeout: 30 minutos
- Estado: Guardado en servidor
- Vistas en sesión: 15

---

## 🔑 Credenciales de Demo

> ⚠️ **Solo para evaluación local.** Estas credenciales existen únicamente para que cualquiera pueda probar el sistema rápidamente. En un despliegue real, el primer paso es cambiar las contraseñas por defecto y crear usuarios reales. Las contraseñas se almacenan hasheadas con BCrypt.

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `123` | Gestión completa: usuarios, áreas, inventario |
| `visitante` | `123` | Solo visualización y registro de inventario |

---

## 🖥️ Uso del Sistema

### Página de Login
Acceso con validación de credenciales y redirección automática según el rol.

### Panel de Administración
- **Usuarios:** crear, editar, eliminar, cambiar contraseñas
- **Áreas:** gestionar categorías
- **Inventario:** control completo

### Panel de Visitante
- **Inventario:** visualizar y registrar productos
- **Búsqueda:** filtrar por producto, fecha o área
- **Reportes:** exportar a PDF

---

## 📁 Estructura del Proyecto

```
inventario/
├── Web Pages/
│   ├── admin/                # Páginas del administrador
│   ├── visitante/            # Páginas del visitante
│   ├── plantillas/           # Templates JSF
│   ├── resources/css/        # Estilos
│   └── index.xhtml           # Login
├── Source Packages/
│   ├── controlador/          # Managed Beans (controllers)
│   ├── modelo.dao/           # Data Access Objects
│   ├── modelo.entidad/       # Entidades JPA/Hibernate
│   ├── modelo.util/          # Utilidades y configuración
│   └── auxiliar/             # Clases auxiliares
└── Configuration Files/
    ├── hibernate.cfg.xml     # Configuración de Hibernate
    └── web.xml               # Configuración web
```

---

## 🔄 Evolución del proyecto

Este repositorio contiene la **primera generación** del sistema. Posteriormente diseñé una segunda generación con mejoras significativas basadas en años de uso real:

- ✅ Roles más granulares (admin, proveedores con vista limitada a sus productos, áreas/rooms con vista limitada a su sección)
- ✅ **Sistema de semáforo de stock** (crítico / bajo / OK) con umbrales configurables y reglas distintas según el tipo de área
- ✅ **Versionado de datos por fecha**: editar con una fecha nueva crea una versión histórica, no sobrescribe
- ✅ **Auditoría completa** con razón obligatoria para cada cambio
- ✅ **Filtros temporales** por semana, mes y día específico
- ✅ Dashboard con gráficos interactivos
- ✅ Notificaciones automáticas de stock bajo
- ✅ Historial completo de movimientos
- ✅ Interfaz bilingüe (español / inglés)

Puedes leer más detalles de la evolución en el [caso de estudio](CASE_STUDY_ES.md).

---

## 📚 Documentación

- **[Caso de estudio (Español)](CASE_STUDY_ES.md)** — Contexto, problema, decisiones técnicas, resultados y aprendizajes
- **[Case Study (English)](CASE_STUDY_EN.md)** — Same content, in English

---

## 👤 Autor

**Joel** — Ingeniero en Sistemas
Sistema diseñado y construido como parte de la experiencia profesional en gestión de inventarios para una distribuidora en USA.
Si quieres una demo en vivo o conversar sobre las decisiones técnicas detrás del proyecto, contáctame por LinkedIn.

---

## 📝 Licencia

Proyecto de portfolio profesional. Ver detalles en el caso de estudio.
