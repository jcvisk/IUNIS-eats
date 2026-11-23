# Fase 9: Pruebas, Docker y demostración

## Objetivo

Garantizar que el sistema se ejecute de forma reproducible y pueda demostrarse de principio a fin.

## Actividades

1. Completar pruebas unitarias, MVC e integración con PostgreSQL/Testcontainers.
2. Validar seguridad, límite horario, agotados, combos, transiciones inválidas, auditoría y reportes.
3. Crear Dockerfile y Docker Compose para aplicación, PostgreSQL y MinIO.
4. Añadir datos demostrativos y una guía de ejecución limpia.
5. Realizar una demostración guiada de estudiante y personal.

## Resultado verificable

El proyecto levanta con Docker Compose y supera los escenarios funcionales acordados.



...............................
## Suite de pruebas automatizadas (Issue #24)

Las pruebas unitarias y MVC se ejecutan de manera aislada utilizando base de datos en memoria **H2** 
(perfil `test` en `src/test/resources/application-test.yml`) y **MockMvc**, sin depender de PostgreSQL local.

### Comando de ejecución

```bash
./mvnw test


(Pruebas unitarias y MVC (Issue #24): Tests run: 19, Failures: 0, Errors: 0, Skipped: 0 — BUILD SUCCESS ejecutando ./mvnw test con H2 en memoria.)