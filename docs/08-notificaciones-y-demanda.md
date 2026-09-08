# Fase 8: Notificaciones y analítica de demanda

## Objetivo

Informar al estudiante de cambios de pedido y proporcionar datos útiles al personal.

## Actividades

1. Crear notificaciones persistentes para creación, aceptación, rechazo, preparación, listo, entrega y corrección/cancelación.
2. Configurar claves VAPID, suscripciones desde el service worker y envío de Web Push.
3. Mantener un centro de notificaciones en la PWA como respaldo cuando no exista permiso push.
4. Implementar con `JdbcTemplate` reportes de cantidades solicitadas por platillo, periodo, fecha y estado.
5. Crear vistas para consultar demanda actual e historial.

## Resultado verificable

Un cambio de pedido genera registro visible y, si existe permiso, notificación push; el personal consulta demanda acumulada.
