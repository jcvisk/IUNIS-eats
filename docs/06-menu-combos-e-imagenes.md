# Fase 6: Menú, combos e imágenes S3

## Objetivo

Permitir al personal publicar la oferta de alimentos que verán los estudiantes.

## Actividades

1. Modelar platillos, menú/periodo de servicio, disponibilidad y combos.
2. Implementar CRUD protegido para personal, incluyendo precio, imagen y estado agotado/disponible.
3. Configurar cliente S3 compatible; usar MinIO en desarrollo mediante Docker Compose.
4. Crear catálogo público autenticado para estudiantes con menú, imágenes, precios y combos vigentes.
5. Aplicar ventanas configurables para publicar el menú y cerrar pedidos.

## Resultado verificable

El personal publica un menú y un estudiante solo puede visualizar y seleccionar artículos disponibles durante el periodo vigente.
