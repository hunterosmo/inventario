# 📦 Sistema de Gestión de Inventario

Sistema web de gestión de inventario con roles, búsqueda avanzada y reportes en PDF.
Construido con **Java 8 · JSF 2.2 · Hibernate 4.3 · MySQL · GlassFish**, con más de 3 años de iteración basada en uso real.

> 📖 **Lee el caso de estudio completo:** [Español](https://github.com/hunterosmo/inventario/blob/main/CASE_STUDY%20Espanol.md) · [English](https://github.com/hunterosmo/inventario/blob/main/CASE_STUDY_Ingles.md)
> Contiene el contexto del problema, las decisiones técnicas y las lecciones aprendidas.

> 🔒 **Nota sobre los datos:** Este repositorio contiene una versión sanitizada del sistema. Los nombres reales de la empresa, productos y categorías han sido reemplazados por equivalentes genéricos (ej. *abarrote*, *detergentes*, *líquidos*). La arquitectura, decisiones técnicas y el código son los del sistema real en producción.

---

## 🚀 Características Principales

### 🔐 Gestión de Usuarios
- Autenticación con contraseñas encriptadas (BCrypt con sal)
- Roles diferenciados: **Administrador** y **Visitante**
- CRUD completo de usuarios
- Cambio de contraseña separado de la información personal

### 📦 Gestión de Inventario
- Registro de productos por áreas/categorías
- Control de cantidades, pesos y precios unitarios
- Cálculo automático de valor total y peso total
- Búsqueda y filtrado avanzado (por producto, fecha, área)
- Exportación de reportes a PDF

### 🏢 Gestión de Áreas
- Categorización funcional de productos
- CRUD completo de áreas
- Foreign keys que vinculan cada producto con su área correspondiente

### 📊 Dashboard y Reportes
- Vista general del inventario en tiempo real
- Totales consolidados por valor y peso
- Interfaces diferenciadas según rol del usuario

---

## 🛠️ Stack Técnico

### Backend

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 8+ | Lenguaje principal |
| JSF | 2.2 | Framework web MVC |
| Hibernate | 4.3.x | ORM para persistencia |
| BCrypt | — | Hashing de contraseñas |
| C3P0 | — | Pool de conexiones |

### Frontend

| Tecnología | Versión | Propósito |
|---|---|---|
| PrimeFaces | 6.0 | Componentes UI |
| XHTML | — | Templates y vistas JSF |
| CSS3 | — | Estilos personalizados |
| Bootstrap | — | Framework CSS (parcial) |

### Base de Datos y Servidor

| Tecnología | Versión |
|---|---|
| MySQL | 5.7+ |
| GlassFish | 4.1.1 |
| NetBeans (IDE recomendado) | 8.2 |

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

> ⚠️ **Solo para evaluación local.** Estas credenciales existen únicamente para que cualquiera pueda probar el sistema rápidamente. En un despliegue real, el primer paso es cambiar las contraseñas por defecto y crear usuarios reales. Las contraseñas se almacenan hasheadas con BCrypt en la base de datos.

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `123` | Gestión completa: usuarios, áreas, inventario |
| `visitante` | `123` | Visualización y registro de inventario |

---

## 🖥️ Uso del Sistema

### Página de Login
Acceso con validación de credenciales y redirección automática según el rol del usuario.

### Panel de Administración (Admin)
- **Usuarios:** crear, editar, eliminar, cambiar contraseñas
- **Áreas:** gestionar categorías
- **Inventario:** control completo (CRUD)

### Panel de Visitante
- **Inventario:** visualizar y registrar productos
- **Búsqueda:** filtrar por producto, fecha o área
- **Reportes:** exportar a PDF

### Funcionalidades clave
- ✅ Búsqueda avanzada con múltiples filtros combinables
- ✅ Cálculos automáticos de totales por valor y peso
- ✅ Exportación a PDF de reportes filtrados
- ✅ Interfaz responsive con PrimeFaces
- ✅ Gestión de sesiones con timeout de seguridad

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

## 🚀 Roadmap

Funcionalidades en consideración para futuras versiones:

- Dashboard con gráficos interactivos
- Notificaciones automáticas de stock bajo
- Historial detallado de movimientos de inventario
- API REST para integración con otros sistemas
- Backup automático de datos

---

## 📚 Documentación

- **[Caso de estudio (Español)](https://github.com/hunterosmo/inventario/blob/main/CASE_STUDY%20Espanol.md)** — Contexto, problema, decisiones técnicas, resultados y aprendizajes
- **[Case Study (English)](https://github.com/hunterosmo/inventario/blob/main/CASE_STUDY_Ingles.md)** — Same content, in English

---

## 👤 Autor

**Joel** — Ingeniero en Sistemas
Sistema diseñado, construido y mantenido durante 3+ años para una distribuidora en USA.
Si quieres una demo en vivo o conversar sobre las decisiones técnicas detrás del proyecto, contáctame por LinkedIn.

---

## 📝 Licencia

Proyecto de portfolio profesional.
