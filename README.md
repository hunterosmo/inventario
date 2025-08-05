📦 Sistema de Gestión de Inventario
Un sistema web completo para la gestión de inventarios desarrollado con Java, JSF, PrimeFaces e Hibernate.
🚀 Características Principales
🔐 Gestión de Usuarios

Autenticación segura con contraseñas encriptadas (BCrypt)
Roles diferenciados: Admin y Visitante
CRUD completo de usuarios
Cambio de contraseña separado de información personal

📦 Gestión de Inventario

Registro de productos por áreas/categorías
Control de cantidades, pesos y precios
Cálculo automático de valores totales
Búsqueda y filtrado avanzado (por producto, fecha, área)
Exportación de reportes a PDF

🏢 Gestión de Áreas

Categorización de productos por departamentos
CRUD completo de áreas (abarrote, detergentes, líquidos, etc.)

📊 Dashboard y Reportes

Vista general del inventario en tiempo real
Totales consolidados por valor y peso
Interfaces diferenciadas según rol de usuario

🛠️ Tecnologías Utilizadas
Backend

Java 8+ - Lenguaje principal
JSF 2.2 - Framework web MVC
Hibernate 4.3.x - ORM para persistencia
BCrypt - Encriptación de contraseñas
C3P0 - Pool de conexiones

Frontend

PrimeFaces 6.0 - Componentes UI ricos
XHTML - Templates y vistas
CSS3 - Estilos personalizados
Bootstrap - Framework CSS (parcial)

Base de Datos

MySQL 5.7+ - Base de datos relacional
Hibernate Mapping - Mapeo objeto-relacional

Servidor

GlassFish 4.1.1 - Servidor de aplicaciones

📋 Prerrequisitos

Java JDK 8+
MySQL Server 5.7+
GlassFish Server 4.1.1 o Apache Tomcat 8+
NetBeans 8.2 (recomendado) o cualquier IDE compatible

🔑 Credenciales de Acceso
Usuario Administrador

Usuario: admin
Contraseña: 123
Permisos: Gestión completa de usuarios, áreas e inventario

Usuario Visitante

Usuario: visitante
Contraseña: 123
Permisos: Solo visualización y gestión de inventario

🖥️ Uso del Sistema
Página de Login

Acceso seguro con validación de credenciales
Redirección automática según rol del usuario

Panel de Administración (Solo Admin)

Usuarios: Crear, editar, eliminar usuarios y cambiar contraseñas
Áreas: Gestionar categorías de productos
Inventario: Control completo de productos

Panel de Visitante

Inventario: Visualizar y gestionar productos
Búsqueda: Filtrar por producto, fecha o área
Reportes: Exportar información a PDF

Funcionalidades Principales

✅ Búsqueda avanzada con múltiples filtros
✅ Cálculos automáticos de totales por valor y peso
✅ Exportación a PDF de reportes
✅ Interfaz responsive con PrimeFaces
✅ Gestión de sesiones con timeout de seguridad

📁 Estructura del Proyecto
inventario/
├── Web Pages/
│   ├── admin/                    # Páginas del administrador
│   ├── visitante/                # Páginas del visitante
│   ├── plantillas/               # Templates JSF
│   ├── resources/css/            # Estilos CSS
│   └── index.xhtml              # Página de login
├── Source Packages/
│   ├── controlador/             # Managed Beans (Controllers)
│   ├── modelo.dao/              # Data Access Objects
│   ├── modelo.entidad/          # Entidades JPA/Hibernate
│   ├── modelo.util/             # Utilidades y configuración
│   └── auxiliar/                # Clases auxiliares
└── Configuration Files/
    ├── hibernate.cfg.xml        # Configuración Hibernate
    └── web.xml                  # Configuración web
🔧 Configuración Avanzada
Pool de Conexiones C3P0
xml<property name="hibernate.c3p0.max_size">20</property>
<property name="hibernate.c3p0.min_size">5</property>
<property name="hibernate.c3p0.timeout">1800</property>
Configuración de Sesiones

Timeout: 30 minutos
Estado: Guardado en servidor
Vistas en sesión: 15

🚀 Funcionalidades Futuras

 Dashboard con gráficos interactivos
 Notificaciones de stock bajo
 Historial de movimientos de inventario
 API REST para integración
 Aplicación móvil
 Backup automático de datos

🤝 Contribuir

Fork del proyecto
Crear una rama para tu feature (git checkout -b feature/NuevaFuncionalidad)
Commit de tus cambios (git commit -am 'Agregar nueva funcionalidad')
Push a la rama (git push origin feature/NuevaFuncionalidad)
Crear un Pull Request
