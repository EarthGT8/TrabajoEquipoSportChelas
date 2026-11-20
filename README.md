# 🛒 Punto de Venta — Papelería "Vero"

Sistema web de Punto de Venta (POS), gestión de inventario, proveedores, usuarios, reportes ejecutivos y emisión de tickets desarrollado para la unidad de aprendizaje **Paradigmas de Programación I (UAEMéx)** por el equipo **SportChelas**.

---

## Integrantes y Ramas de Trabajo (Equipo SportChelas)

El desarrollo colaborativo se realizó mediante ramas independientes integradas finalmente en `main`:

| Integrante | Usuario GitHub | Rama de Desarrollo | Módulo Principal |
| :--- | :--- | :--- | :--- |
| **Ángel Sánchez** | [@EarthGT8](https://github.com/EarthGT8) | [`feature/punto-venta-caja`](../../tree/feature/punto-venta-caja) | Caja Registradora, Tickets, Seguridad y Reportes (`/pos`, `/dashboard`, `/reportes`) |
| **Gustavo Romero** | [@gssttvv5](https://github.com/gssttvv5) | [`feature/inventario-productos`](../../tree/feature/inventario-productos) | Inventario, Productos y Categorías (`/inventario`) |
| **Emmanuel Sánchez** | [@emmanuel471419](https://github.com/emmanuel471419) | [`feature/usuarios-proveedores`](../../tree/feature/usuarios-proveedores) | Gestión de Usuarios y Proveedores (`/usuarios`, `/proveedores`) |

---

## Funcionalidades Principales

* **Seguridad y Control de Acceso:** Inicio de sesión con contraseñas cifradas mediante **SHA-256** (`PasswordUtil`), sesiones HTTP y bloqueo de URLs directas sin autenticación (`AuthInterceptor`) con permisos diferenciados por rol (`ADMIN` y `CAJERO`).
* **Dashboard Ejecutivo (`/dashboard`):** Resumen en tiempo real de ingresos, tickets cobrados, catálogo, proveedores/usuarios y alertas de productos con stock bajo.
* **Caja Registradora (`/pos`) y Tickets (`/pos/ticket/{id}`):** Buscador en vivo por nombre o código de barras, carrito dinámico, validación obligatoria de monto recibido, cálculo de cambio, descuento automático de stock transaccional (`@Transactional`), corte de caja y comprobante imprimible.
* **Operaciones CRUD Completas:** Alta, consulta, edición y baja en **Inventario** (productos y categorías), **Proveedores** y **Usuarios**.
* **Validación y Manejo de Errores:** Bloques `try - catch - finally` con alertas visuales en pantalla y registros de auditoría en consola.
* **Reportes (`/reportes`):** Desglose de ventas por artículo, valoración del inventario y lista de reabastecimiento con formato listo para imprimir/exportar a PDF.
* **Diseño Responsivo:** Interfaz construida con **Bootstrap 5** adaptable a escritorio y dispositivos móviles (menú colapsable y tablas responsivas).

---

## Documentación, Base de Datos y Diagramas

* **10 Diagramas UML (PlantUML editables):** [`/docs/uml`](./docs/uml) *(3 Casos de Uso generales, 5 de Clases y 2 de Secuencia)*
* **Diagramas de Casos de Uso Detallados:** [`/docs/casos-de-uso`](./docs/casos-de-uso)
* **10 Mockups de Interfaz (PNG alta resolución):** [`/docs/mockups`](./docs/mockups)
* **Script de Base de Datos (estructura + datos de prueba):** [`papeleria_vero_db.sql`](./papeleria_vero_db.sql)

---

## Stack Tecnológico

* **Backend:** Java + Spring Boot (Spring Web MVC, Spring Data JPA, Lombok)
* **Frontend:** Thymeleaf + HTML5 + Bootstrap 5 + JavaScript (Fetch API)
* **Base de Datos:** H2 Database persistente en archivo local (`./data/papeleria_vero_db`)
* **Arquitectura:** Patrón MVC por Capas (`config`, `model`, `repository`, `service`, `controller`)

---

## Cómo Ejecutar el Proyecto

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/EarthGT8/TrabajoEquipoSportChelas.git](https://github.com/EarthGT8/TrabajoEquipoSportChelas.git)
   cd TrabajoEquipoSportChelas
2. Levantar el servidor con Maven Wrapper:Windows:PowerShell. \mvnw.cmd spring-boot:run
   Linux / macOS:Bash./mvnw spring-boot:run
   Abrir en el navegador (puerto 8081):Acceso principal (Login): http://localhost:8081/loginConsola H2: http://localhost:8081/h2-consoleJDBC URL: jdbc:h2:file:./data/papeleria_vero_dbUser Name: saPassword: (vacío)
   
## Credenciales de Prueba Precargadas
   Al iniciar la aplicación por primera vez (UsuarioInitializer), se crean automáticamente los siguientes accesos con contraseñas cifradas en SHA-256:RolUsuarioContraseñaRutas PermitidasAdministradoradminadmin123/dashboard, /pos, /pos/ticket/{id}, /inventario, /proveedores, /usuarios, /reportesCajerocajerocajero123/pos y /pos/ticket/{id} (cualquier otra ruta administrativa es bloqueada)   
   
   