# Fase 4: Seguridad, autenticación y roles

## Objetivo

Proteger la aplicación y establecer los dos tipos de usuario del MVP.

## Actividades

1. Integrar Spring Security y contraseñas con BCrypt.
2. Implementar registro local exclusivamente para estudiantes.
3. Crear inicio/cierre de sesión y cuentas semilla para personal de cafetería.
4. Definir roles `STUDENT` y `CAFETERIA_STAFF` y restringir rutas y acciones por autorización.
5. Mantener CSRF habilitado en formularios Thymeleaf.

## Resultado verificable

Un estudiante se registra e inicia sesión; el personal accede a rutas operativas que el estudiante no puede abrir.
