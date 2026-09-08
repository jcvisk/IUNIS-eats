# Fase 7: Pedidos, estados y auditoría

## Objetivo

Implementar el flujo operativo completo del pedido, sin pagos en línea.

## Actividades

1. Crear carrito, validación de cantidades, confirmación y total persistido del pedido.
2. Bloquear pedidos fuera del límite horario, de artículos agotados y con datos alterados desde el navegador.
3. Implementar las transiciones: `PENDING → ACCEPTED | REJECTED`, `ACCEPTED → PREPARING`, `PREPARING → READY`, `READY → DELIVERED`.
4. Crear tablero de personal para revisar pedidos, cambiar estados y registrar entrega/pago físico.
5. Permitir al personal corregir o cancelar pedidos no entregados, siempre con motivo y una bitácora inmutable de actor, fecha y cambios.
6. Añadir historial y detalle de pedido para estudiantes.

## Resultado verificable

Se puede seguir un pedido real desde su creación hasta la entrega, y cada excepción queda trazable.
