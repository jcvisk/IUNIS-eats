# IUNIS-eats

Sistema web PWA para la gestión y pedidos de la cafetería universitaria IUNIS Eats, desarrollado con Java 21, Spring Boot, Thymeleaf y PostgreSQL.

## Requisitos previos
* **Java 21** (JDK 21)
* **Maven 3.9+** (o usar el wrapper incluido `./mvnw` / `mvnw.cmd`)
* **PostgreSQL 15+** (instalado localmente o mediante Docker)

### Levantar PostgreSQL con Docker (Opcional)
Si no tienes PostgreSQL instalado localmente, puedes iniciar un contenedor limpio con:
bash
docker run --name iunis-postgres -e POSTGRES_DB=iuniseatsdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=tu_password -p 5432:5432 -d postgres:15

## Configuración de entorno (Variables externalizadas)
1. Copia el archivo de ejemplo `example.env` y créalo como `.env` en la raíz del proyecto (nunca borres ni subas credenciales reales):
   bash
   cp example.env .env

2. Rellena tus valores locales en `.env` (`SPRING_PROFILES_ACTIVE`, `SERVER_PORT`, `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`). El archivo `.env` está protegido por `.gitignore`.

## Perfiles disponibles
* `dev` (por defecto): Desarrollo local con recarga de plantillas Thymeleaf y actualización automática de esquema (`ddl-auto: update`).
* `test`: Ejecución de pruebas automatizadas (`ddl-auto: create-drop`).
* `prod`: Entorno productivo con caché habilitada y validación estricta de esquema (`ddl-auto: validate`).

Para arrancar con un perfil específico:
bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev


## Comandos de verificación, arranque y pruebas
* **Verificar compilación y empaquetado:**
* bash
  ./mvnw clean verify -DskipTests

* **Arrancar la aplicación:**
* bash
  ./mvnw spring-boot:run

* **Ejecutar pruebas automatizadas:**
* bash
  ./mvnw test

## Endpoint de salud (Actuator)
Con la aplicación iniciada, comprueba el estado del servicio desde un entorno limpio en:
* `GET http://localhost:8080/actuator/health` (Respuesta esperada: `{"status":"UP"}`).

## Convenciones de ramas y configuración
* `main`: Rama principal estable; prohibido hacer commits directos.
* `feature/<descripcion>-<numero-issue>`: Nuevas funcionalidades o configuraciones (ej. `feature/salud-y-documentacion-7`).
* `fix/<descripcion>-<numero-issue>`: Corrección de errores.
* Nunca versionar archivos `.env` ni escribir contraseñas en `application.yml`.