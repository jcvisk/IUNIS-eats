# Plan general: IUNIS Eats

## Propósito

IUNIS Eats será una Progressive Web App (PWA) para que estudiantes consulten el menú de la cafetería universitaria y realicen pedidos antes del receso. El personal de cafetería administrará el menú, los pedidos y la información de demanda. El pago se realizará físicamente al recoger el pedido.

Este documento es la referencia del alcance completo. Las fases se ejecutarán una por una, explicando, configurando y validando cada paso antes de continuar.

## Stack acordado

- Java 21 y Spring Boot con Maven.
- Spring Security con roles `STUDENT` y `CAFETERIA_STAFF`.
- Thymeleaf para las vistas del servidor.
- PostgreSQL como base de datos.
- Spring Data JPA para entidades y operaciones transaccionales.
- Spring JDBC (`JdbcTemplate`) para consultas agregadas de demanda.
- Flyway para versionar el esquema de base de datos.
- PWA instalable con service worker y notificaciones Web Push.
- Almacenamiento S3 compatible para imágenes: MinIO en desarrollo y un proveedor S3 configurable en producción.
- Docker Compose para levantar los servicios de desarrollo.

## Funcionalidad final

- Registro local de estudiantes e inicio de sesión para estudiantes y personal.
- Catálogo de platillos, imágenes, precios, disponibilidad y combos.
- Periodos de servicio configurables con publicación del menú y límite de pedidos.
- Carrito y pedidos; el sistema impide ordenar fuera de horario o productos agotados.
- Estados: `PENDING`, `ACCEPTED`, `REJECTED`, `PREPARING`, `READY` y `DELIVERED`.
- Correcciones o cancelaciones solo por personal, antes de la entrega, con motivo y auditoría.
- Notificaciones persistentes y Web Push por cambios relevantes en pedidos.
- Historial de pedidos y consultas de demanda por platillo y periodo.

## Límites explícitos

No se implementarán pagos electrónicos, entregas, inventario físico automatizado, integración institucional/SSO ni predicción automática de demanda.

## Fases

1. [Preparación del repositorio](01-preparacion-del-repositorio.md)
2. [Bootstrap de Spring Boot](02-bootstrap-spring-boot.md)
3. [PostgreSQL, JPA, JDBC y migraciones](03-base-de-datos-y-persistencia.md)
4. [Seguridad, autenticación y roles](04-seguridad-autenticacion-y-roles.md)
5. [Thymeleaf y base PWA](05-thymeleaf-y-pwa.md)
6. [Menú, combos e imágenes S3](06-menu-combos-e-imagenes.md)
7. [Pedidos, estados y auditoría](07-pedidos-estados-y-auditoria.md)
8. [Notificaciones y analítica de demanda](08-notificaciones-y-demanda.md)
9. [Pruebas, Docker y demostración](09-pruebas-contenedores-y-demostracion.md)
