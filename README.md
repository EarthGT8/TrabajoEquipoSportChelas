#  Punto de Venta - Papelería "Vero"

Proyecto de la materia de **Paradigmas de Programación** desarrollado por el equipo **SportChelas**. Sistema de Punto de Venta (POS), gestión de inventario y administración de proveedores construido con **Java y Spring Boot**.

---

## Integrantes y Ramas de Trabajo (Equipo SportChelas)

Cada integrante desarrolló su módulo de forma independiente en su rama dedicada antes de integrarlo en `main`:

| Integrante | Usuario GitHub | Rama | Módulo Asignado |
| :--- | :--- | :--- | :--- |
| **Ángel Sánchez** | [@EarthGT8](https://github.com/EarthGT8) | [`feature/punto-venta-caja`](../../tree/feature/punto-venta-caja) | Caja Registradora, Ventas y Tickets (`/pos`) |
| **Gustavo Romero** | [@gssttvv5](https://github.com/gssttvv5) | [`feature/inventario-productos`](../../tree/feature/inventario-productos) | Inventario, Productos y Categorías (`/inventario`) |
| **Emmanuel Sánchez** | [@emmanuel471419](https://github.com/emmanuel471419) | [`feature/usuarios-proveedores`](../../tree/feature/usuarios-proveedores) | Usuarios y Proveedores (`/proveedores`) |

---

##  Diagramas de Casos de Uso (PlantUML)
Los **20 diagramas de casos de uso (`.puml`)** se encuentran organizados en:
 [`/docs/casos-de-uso`](./docs/casos-de-uso)

---

##  Tecnologías y Paradigmas Aplicados
* **Backend:** Java 17 + Spring Boot 4 (Spring Web, Spring Data JPA, Lombok)
* **Frontend:** Thymeleaf + Bootstrap 5
* **Base de Datos:** H2 Database embebida en archivo local (`./data/papeleria_vero_db`)
* **Arquitectura:** Patrón MVC por Capas (`model`, `repository`, `service`, `controller`)

---

##  Cómo ejecutar el proyecto

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/EarthGT8/TrabajoEquipoSportChelas.git](https://github.com/EarthGT8/TrabajoEquipoSportChelas.git)
   cd TrabajoEquipoSportChelas

2. Ejecutar con Maven Wrapper:

   En Windows:
   PowerShell
   .\mvnw.cmd spring-boot:run

   En Linux/Mac:
   Bash
   ./mvnw spring-boot:run
    
3. Abrir en el navegador (puerto 8081):

   Punto de Venta (Caja): http://localhost:8081/pos

   Inventario: http://localhost:8081/inventario

   Proveedores: http://localhost:8081/proveedores

   Consola de Base de Datos H2: http://localhost:8081/h2-console

   JDBC URL: jdbc:h2:file:./data/papeleria_vero_db

   User Name: sa

   Password: (vacío)