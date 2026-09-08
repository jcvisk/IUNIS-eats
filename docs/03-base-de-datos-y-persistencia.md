# Fase 3: PostgreSQL, JPA, JDBC y migraciones

## Objetivo

Configurar persistencia reproducible y explicar cuándo usar JPA y cuándo JDBC.

## Actividades

1. Añadir PostgreSQL y la configuración de conexión por perfiles.
2. Integrar Flyway y crear migraciones iniciales para usuarios, roles y tablas de auditoría técnica.
3. Configurar Spring Data JPA para entidades y transacciones.
4. Configurar `JdbcTemplate` para futuras consultas agregadas, sin duplicar el uso de JPA.
5. Probar el acceso con una prueba de integración contra PostgreSQL.

## Resultado verificable

Al iniciar, Flyway crea el esquema y la aplicación puede persistir y consultar datos de prueba.

## Decisión técnica

JPA manejará el modelo y sus transacciones. JDBC se reservará para reportes de demanda y consultas agregadas.
