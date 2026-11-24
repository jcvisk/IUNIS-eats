-- ============================================================================
-- IUNIS-Eats | Semilla de Datos Demostrativos (Perfil: demo)
-- Idempotente y reproducible: se puede ejecutar múltiples veces sin duplicar.
-- Contraseña de todas las cuentas demo: demo
-- ============================================================================

-- 1. ROLES BASE
INSERT INTO public.roles (id, des)
SELECT 1, 'STUDENT'
    WHERE NOT EXISTS (SELECT 1 FROM public.roles WHERE id = 1);

INSERT INTO public.roles (id, des)
SELECT 2, 'CAFETERIA_STAFF'
    WHERE NOT EXISTS (SELECT 1 FROM public.roles WHERE id = 2);

-- 2. USUARIOS DE DEMOSTRACIÓN (password: "demo" en BCrypt)
INSERT INTO public.users (user_name, password, role)
SELECT 'admin', '$2a$10$UrdOHk5Ht6w4eGSPvzhXiuXP4ymbWf3TOtsHQPrz279U0JKTnYdfy', 2
    WHERE NOT EXISTS (SELECT 1 FROM public.users WHERE user_name = 'admin');

INSERT INTO public.users (user_name, password, role)
SELECT 'cafeteria.ana', '$2a$10$UrdOHk5Ht6w4eGSPvzhXiuXP4ymbWf3TOtsHQPrz279U0JKTnYdfy', 2
    WHERE NOT EXISTS (SELECT 1 FROM public.users WHERE user_name = 'cafeteria.ana');

INSERT INTO public.users (user_name, password, role)
SELECT 'alumno.carlos', '$2a$10$UrdOHk5Ht6w4eGSPvzhXiuXP4ymbWf3TOtsHQPrz279U0JKTnYdfy', 1
    WHERE NOT EXISTS (SELECT 1 FROM public.users WHERE user_name = 'alumno.carlos');

INSERT INTO public.users (user_name, password, role)
SELECT 'alumna.sofia', '$2a$10$UrdOHk5Ht6w4eGSPvzhXiuXP4ymbWf3TOtsHQPrz279U0JKTnYdfy', 1
    WHERE NOT EXISTS (SELECT 1 FROM public.users WHERE user_name = 'alumna.sofia');

INSERT INTO public.users (user_name, password, role)
SELECT 'alumno.mateo', '$2a$10$UrdOHk5Ht6w4eGSPvzhXiuXP4ymbWf3TOtsHQPrz279U0JKTnYdfy', 1
    WHERE NOT EXISTS (SELECT 1 FROM public.users WHERE user_name = 'alumno.mateo');

-- Asignación en roles_user
INSERT INTO public.roles_user (user_id, role_id)
SELECT u.id, u.role
FROM public.users u
WHERE u.user_name IN ('admin', 'cafeteria.ana', 'alumno.carlos', 'alumna.sofia', 'alumno.mateo')
  AND NOT EXISTS (
    SELECT 1 FROM public.roles_user ru WHERE ru.user_id = u.id AND ru.role_id = u.role
);

-- 3. DATOS PERSONALES
INSERT INTO public.datos_personales (id, nombre, apellidos, edad, genero, user_id)
SELECT COALESCE((SELECT MAX(id) FROM public.datos_personales), 0) + 1, 'Administrador', 'Cafetería IUNIS', 34, 'No especificado', u.id
FROM public.users u
WHERE u.user_name = 'admin'
  AND NOT EXISTS (SELECT 1 FROM public.datos_personales dp WHERE dp.user_id = u.id);

INSERT INTO public.datos_personales (id, nombre, apellidos, edad, genero, user_id)
SELECT COALESCE((SELECT MAX(id) FROM public.datos_personales), 0) + 1, 'Ana Lucía', 'Méndez Torres', 29, 'Femenino', u.id
FROM public.users u
WHERE u.user_name = 'cafeteria.ana'
  AND NOT EXISTS (SELECT 1 FROM public.datos_personales dp WHERE dp.user_id = u.id);

INSERT INTO public.datos_personales (id, nombre, apellidos, edad, genero, user_id)
SELECT COALESCE((SELECT MAX(id) FROM public.datos_personales), 0) + 1, 'Carlos Eduardo', 'Ramírez López', 20, 'Masculino', u.id
FROM public.users u
WHERE u.user_name = 'alumno.carlos'
  AND NOT EXISTS (SELECT 1 FROM public.datos_personales dp WHERE dp.user_id = u.id);

INSERT INTO public.datos_personales (id, nombre, apellidos, edad, genero, user_id)
SELECT COALESCE((SELECT MAX(id) FROM public.datos_personales), 0) + 1, 'Sofía Valentina', 'Castillo Pérez', 19, 'Femenino', u.id
FROM public.users u
WHERE u.user_name = 'alumna.sofia'
  AND NOT EXISTS (SELECT 1 FROM public.datos_personales dp WHERE dp.user_id = u.id);

INSERT INTO public.datos_personales (id, nombre, apellidos, edad, genero, user_id)
SELECT COALESCE((SELECT MAX(id) FROM public.datos_personales), 0) + 1, 'Mateo', 'Gómez Herrera', 21, 'Masculino', u.id
FROM public.users u
WHERE u.user_name = 'alumno.mateo'
  AND NOT EXISTS (SELECT 1 FROM public.datos_personales dp WHERE dp.user_id = u.id);

-- 4. CATÁLOGO DE PLATILLOS DEMO
INSERT INTO public.platillo (nombre, descripcion, precio, categoria, disponibilidad, fecha_actualizacion)
SELECT 'Chilaquiles Verdes con Pollo', 'Totopos bañados en salsa verde casera con pollo deshebrado, crema, queso y frijoles.', 55.00, 'Desayunos', true, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM public.platillo WHERE nombre = 'Chilaquiles Verdes con Pollo');

INSERT INTO public.platillo (nombre, descripcion, precio, categoria, disponibilidad, fecha_actualizacion)
SELECT 'Molletes Tradicionales (2 pzas)', 'Pan bolillo tostado con frijoles refritos, queso manchego gratinado y pico de gallo.', 38.00, 'Desayunos', true, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM public.platillo WHERE nombre = 'Molletes Tradicionales (2 pzas)');

INSERT INTO public.platillo (nombre, descripcion, precio, categoria, disponibilidad, fecha_actualizacion)
SELECT 'Sándwich Integral de Pechuga', 'Pan integral multigrano con pechuga de pavo, panela, aguacate, lechuga y tomate.', 42.00, 'Comidas', true, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM public.platillo WHERE nombre = 'Sándwich Integral de Pechuga');

INSERT INTO public.platillo (nombre, descripcion, precio, categoria, disponibilidad, fecha_actualizacion)
SELECT 'Enchiladas Suizas (3 pzas)', 'Tortillas rellenas de pollo bañadas en salsa cremosa de tomatillo gratinadas con queso.', 62.00, 'Comidas', true, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM public.platillo WHERE nombre = 'Enchiladas Suizas (3 pzas)');

INSERT INTO public.platillo (nombre, descripcion, precio, categoria, disponibilidad, fecha_actualizacion)
SELECT 'Agua Fresca de Horchata 500ml', 'Bebida natural preparada al día con canela y arroz.', 18.00, 'Bebidas', true, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM public.platillo WHERE nombre = 'Agua Fresca de Horchata 500ml');

INSERT INTO public.platillo (nombre, descripcion, precio, categoria, disponibilidad, fecha_actualizacion)
SELECT 'Licuado de Plátano con Avena 400ml', 'Preparado al momento con leche descremada, plátano fresco y avena.', 25.00, 'Bebidas', true, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM public.platillo WHERE nombre = 'Licuado de Plátano con Avena 400ml');

INSERT INTO public.platillo (nombre, descripcion, precio, categoria, disponibilidad, fecha_actualizacion)
SELECT 'Pay de Queso Artesanal (Agotado Demo)', 'Rebanada individual de pay de queso horneado.', 30.00, 'Postres', false, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM public.platillo WHERE nombre = 'Pay de Queso Artesanal (Agotado Demo)');

-- 5. COMBOS DEMO
INSERT INTO public.combo (id_combo, nombre, descripcion, precio, disponibilidad)
SELECT COALESCE((SELECT MAX(id_combo) FROM public.combo), 0) + 1, 'Combo Desayuno Universitario', 'Chilaquiles Verdes con Pollo + Agua Fresca de Horchata 500ml con precio preferencial.', 65.00, true
    WHERE NOT EXISTS (SELECT 1 FROM public.combo WHERE nombre = 'Combo Desayuno Universitario');

INSERT INTO public.combo (id_combo, nombre, descripcion, precio, disponibilidad)
SELECT COALESCE((SELECT MAX(id_combo) FROM public.combo), 0) + 1, 'Combo Ligero Mañanero', 'Sándwich Integral de Pechuga + Licuado de Plátano con Avena 400ml.', 60.00, true
    WHERE NOT EXISTS (SELECT 1 FROM public.combo WHERE nombre = 'Combo Ligero Mañanero');

-- Relación combo_platillo
INSERT INTO public.combo_platillo (id_combo, id_platillo, cantidad)
SELECT c.id_combo, p.id_platillo, 1
FROM public.combo c, public.platillo p
WHERE c.nombre = 'Combo Desayuno Universitario'
  AND p.nombre IN ('Chilaquiles Verdes con Pollo', 'Agua Fresca de Horchata 500ml')
  AND NOT EXISTS (
    SELECT 1 FROM public.combo_platillo cp WHERE cp.id_combo = c.id_combo AND cp.id_platillo = p.id_platillo
);

INSERT INTO public.combo_platillo (id_combo, id_platillo, cantidad)
SELECT c.id_combo, p.id_platillo, 1
FROM public.combo c, public.platillo p
WHERE c.nombre = 'Combo Ligero Mañanero'
  AND p.nombre IN ('Sándwich Integral de Pechuga', 'Licuado de Plátano con Avena 400ml')
  AND NOT EXISTS (
    SELECT 1 FROM public.combo_platillo cp WHERE cp.id_combo = c.id_combo AND cp.id_platillo = p.id_platillo
);

-- 6. PERIODOS DE SERVICIO Y MENÚ PUBLICADO (Fecha actual para demostración en vivo)
INSERT INTO public.periodo_servicio (id_periodo, fecha, limite_hora, estado_periodo)
SELECT COALESCE((SELECT MAX(id_periodo) FROM public.periodo_servicio), 0) + 1, CURRENT_DATE, TIME '23:59:00', 'ABIERTO'
    WHERE NOT EXISTS (
    SELECT 1 FROM public.periodo_servicio WHERE fecha = CURRENT_DATE AND estado_periodo = 'ABIERTO'
);

INSERT INTO public.periodo_servicio (id_periodo, fecha, limite_hora, estado_periodo)
SELECT COALESCE((SELECT MAX(id_periodo) FROM public.periodo_servicio), 0) + 1, CURRENT_DATE, TIME '07:00:00', 'CERRADO'
    WHERE NOT EXISTS (
    SELECT 1 FROM public.periodo_servicio WHERE fecha = CURRENT_DATE AND estado_periodo = 'CERRADO'
);

INSERT INTO public.menu (id_menu, id_periodo, fecha_publicacion, estado_publicacion)
SELECT COALESCE((SELECT MAX(id_menu) FROM public.menu), 0) + 1, ps.id_periodo, CURRENT_TIMESTAMP, 'PUBLICADO'
FROM public.periodo_servicio ps
WHERE ps.fecha = CURRENT_DATE AND ps.estado_periodo = 'ABIERTO'
  AND NOT EXISTS (
    SELECT 1 FROM public.menu m WHERE m.id_periodo = ps.id_periodo AND m.estado_publicacion = 'PUBLICADO'
);

INSERT INTO public.menu_platillo (id_menu, id_platillo, precio_publicado, disponibilidad)
SELECT m.id_menu, p.id_platillo, p.precio, p.disponibilidad
FROM public.menu m
         JOIN public.periodo_servicio ps ON m.id_periodo = ps.id_periodo
         CROSS JOIN public.platillo p
WHERE ps.fecha = CURRENT_DATE AND ps.estado_periodo = 'ABIERTO'
  AND NOT EXISTS (
    SELECT 1 FROM public.menu_platillo mp WHERE mp.id_menu = m.id_menu AND mp.id_platillo = p.id_platillo
);

INSERT INTO public.menu_combo (id_menu, id_combo, precio_publicado, disponibilidad)
SELECT m.id_menu, c.id_combo, c.precio, c.disponibilidad
FROM public.menu m
         JOIN public.periodo_servicio ps ON m.id_periodo = ps.id_periodo
         CROSS JOIN public.combo c
WHERE ps.fecha = CURRENT_DATE AND ps.estado_periodo = 'ABIERTO'
  AND NOT EXISTS (
    SELECT 1 FROM public.menu_combo mc WHERE mc.id_menu = m.id_menu AND mc.id_combo = c.id_combo
);

-- 7. PEDIDOS DEMO EN DISTINTOS ESTADOS (Para tablero de cafetería, demanda y auditoría)
-- Pedido 1: PENDIENTE (alumno.carlos - 1 Combo Desayuno Universitario = $65.00)
INSERT INTO public.pedido (id_pedido, id_usuario, id_periodo, fecha_hora, total, estado)
SELECT COALESCE((SELECT MAX(id_pedido) FROM public.pedido), 0) + 1, u.id, ps.id_periodo, CURRENT_TIMESTAMP - INTERVAL '15 minutes', 65.00, 'PENDIENTE'
FROM public.users u, public.periodo_servicio ps
WHERE u.user_name = 'alumno.carlos' AND ps.fecha = CURRENT_DATE AND ps.estado_periodo = 'ABIERTO'
  AND NOT EXISTS (
    SELECT 1 FROM public.pedido ped WHERE ped.id_usuario = u.id AND ped.total = 65.00 AND ped.estado = 'PENDIENTE'
    );

INSERT INTO public.detalle_pedido (id_detalle, id_pedido, id_platillo, id_combo, cantidad, precio_aplicado)
SELECT COALESCE((SELECT MAX(id_detalle) FROM public.detalle_pedido), 0) + 1, ped.id_pedido, NULL, c.id_combo, 1, 65.00
FROM public.pedido ped
         JOIN public.users u ON ped.id_usuario = u.id
         CROSS JOIN public.combo c
WHERE u.user_name = 'alumno.carlos' AND ped.estado = 'PENDIENTE' AND c.nombre = 'Combo Desayuno Universitario'
  AND NOT EXISTS (SELECT 1 FROM public.detalle_pedido dp WHERE dp.id_pedido = ped.id_pedido);

-- Pedido 2: PREPARANDO (alumna.sofia - 2 Molletes Tradicionales = $76.00)
INSERT INTO public.pedido (id_pedido, id_usuario, id_periodo, fecha_hora, total, estado)
SELECT COALESCE((SELECT MAX(id_pedido) FROM public.pedido), 0) + 1, u.id, ps.id_periodo, CURRENT_TIMESTAMP - INTERVAL '25 minutes', 76.00, 'PREPARANDO'
FROM public.users u, public.periodo_servicio ps
WHERE u.user_name = 'alumna.sofia' AND ps.fecha = CURRENT_DATE AND ps.estado_periodo = 'ABIERTO'
  AND NOT EXISTS (
    SELECT 1 FROM public.pedido ped WHERE ped.id_usuario = u.id AND ped.total = 76.00 AND ped.estado = 'PREPARANDO'
    );

INSERT INTO public.detalle_pedido (id_detalle, id_pedido, id_platillo, id_combo, cantidad, precio_aplicado)
SELECT COALESCE((SELECT MAX(id_detalle) FROM public.detalle_pedido), 0) + 1, ped.id_pedido, p.id_platillo, NULL, 2, 38.00
FROM public.pedido ped
         JOIN public.users u ON ped.id_usuario = u.id
         CROSS JOIN public.platillo p
WHERE u.user_name = 'alumna.sofia' AND ped.estado = 'PREPARANDO' AND p.nombre = 'Molletes Tradicionales (2 pzas)'
  AND NOT EXISTS (SELECT 1 FROM public.detalle_pedido dp WHERE dp.id_pedido = ped.id_pedido);

-- Pedido 3: LISTO para entrega y pago físico en ventanilla (alumno.mateo - 1 Combo Ligero Mañanero = $60.00)
INSERT INTO public.pedido (id_pedido, id_usuario, id_periodo, fecha_hora, total, estado)
SELECT COALESCE((SELECT MAX(id_pedido) FROM public.pedido), 0) + 1, u.id, ps.id_periodo, CURRENT_TIMESTAMP - INTERVAL '35 minutes', 60.00, 'LISTO'
FROM public.users u, public.periodo_servicio ps
WHERE u.user_name = 'alumno.mateo' AND ps.fecha = CURRENT_DATE AND ps.estado_periodo = 'ABIERTO'
  AND NOT EXISTS (
    SELECT 1 FROM public.pedido ped WHERE ped.id_usuario = u.id AND ped.total = 60.00 AND ped.estado = 'LISTO'
    );

INSERT INTO public.detalle_pedido (id_detalle, id_pedido, id_platillo, id_combo, cantidad, precio_aplicado)
SELECT COALESCE((SELECT MAX(id_detalle) FROM public.detalle_pedido), 0) + 1, ped.id_pedido, NULL, c.id_combo, 1, 60.00
FROM public.pedido ped
         JOIN public.users u ON ped.id_usuario = u.id
         CROSS JOIN public.combo c
WHERE u.user_name = 'alumno.mateo' AND ped.estado = 'LISTO' AND c.nombre = 'Combo Ligero Mañanero'
  AND NOT EXISTS (SELECT 1 FROM public.detalle_pedido dp WHERE dp.id_pedido = ped.id_pedido);

-- Pedido 4: ENTREGADO con pago físico completado (alumno.carlos - 1 Enchiladas Suizas = $62.00)
INSERT INTO public.pedido (id_pedido, id_usuario, id_periodo, fecha_hora, total, estado)
SELECT COALESCE((SELECT MAX(id_pedido) FROM public.pedido), 0) + 1, u.id, ps.id_periodo, CURRENT_TIMESTAMP - INTERVAL '60 minutes', 62.00, 'ENTREGADO'
FROM public.users u, public.periodo_servicio ps
WHERE u.user_name = 'alumno.carlos' AND ps.fecha = CURRENT_DATE AND ps.estado_periodo = 'ABIERTO'
  AND NOT EXISTS (
    SELECT 1 FROM public.pedido ped WHERE ped.id_usuario = u.id AND ped.total = 62.00 AND ped.estado = 'ENTREGADO'
    );

INSERT INTO public.detalle_pedido (id_detalle, id_pedido, id_platillo, id_combo, cantidad, precio_aplicado)
SELECT COALESCE((SELECT MAX(id_detalle) FROM public.detalle_pedido), 0) + 1, ped.id_pedido, p.id_platillo, NULL, 1, 62.00
FROM public.pedido ped
         JOIN public.users u ON ped.id_usuario = u.id
         CROSS JOIN public.platillo p
WHERE u.user_name = 'alumno.carlos' AND ped.estado = 'ENTREGADO' AND p.nombre = 'Enchiladas Suizas (3 pzas)'
  AND NOT EXISTS (SELECT 1 FROM public.detalle_pedido dp WHERE dp.id_pedido = ped.id_pedido);

-- Pedido 5: RECHAZADO con motivo registrado en auditoría (alumna.sofia - Pay agotado = $30.00)
INSERT INTO public.pedido (id_pedido, id_usuario, id_periodo, fecha_hora, total, estado)
SELECT COALESCE((SELECT MAX(id_pedido) FROM public.pedido), 0) + 1, u.id, ps.id_periodo, CURRENT_TIMESTAMP - INTERVAL '45 minutes', 30.00, 'RECHAZADO'
FROM public.users u, public.periodo_servicio ps
WHERE u.user_name = 'alumna.sofia' AND ps.fecha = CURRENT_DATE AND ps.estado_periodo = 'ABIERTO'
  AND NOT EXISTS (
    SELECT 1 FROM public.pedido ped WHERE ped.id_usuario = u.id AND ped.total = 30.00 AND ped.estado = 'RECHAZADO'
    );

INSERT INTO public.detalle_pedido (id_detalle, id_pedido, id_platillo, id_combo, cantidad, precio_aplicado)
SELECT COALESCE((SELECT MAX(id_detalle) FROM public.detalle_pedido), 0) + 1, ped.id_pedido, p.id_platillo, NULL, 1, 30.00
FROM public.pedido ped
         JOIN public.users u ON ped.id_usuario = u.id
         CROSS JOIN public.platillo p
WHERE u.user_name = 'alumna.sofia' AND ped.estado = 'RECHAZADO' AND p.nombre = 'Pay de Queso Artesanal (Agotado Demo)'
  AND NOT EXISTS (SELECT 1 FROM public.detalle_pedido dp WHERE dp.id_pedido = ped.id_pedido);

-- 8. BITÁCORA DE AUDITORÍA (historial_pedido)
INSERT INTO public.historial_pedido (id_historial, id_pedido, id_usuario, estado_anterior, estado_nuevo, tipo_accion, fecha_hora, motivo)
SELECT COALESCE((SELECT MAX(id_historial) FROM public.historial_pedido), 0) + 1, ped.id_pedido, staff.id, 'PENDIENTE', 'PREPARANDO', 'ACEPTAR_PEDIDO', CURRENT_TIMESTAMP - INTERVAL '20 minutes', 'Pedido aceptado en cocina por personal de cafetería.'
FROM public.pedido ped
    JOIN public.users alum ON ped.id_usuario = alum.id
    CROSS JOIN public.users staff
WHERE alum.user_name = 'alumna.sofia' AND ped.estado = 'PREPARANDO' AND staff.user_name = 'cafeteria.ana'
  AND NOT EXISTS (SELECT 1 FROM public.historial_pedido hp WHERE hp.id_pedido = ped.id_pedido AND hp.estado_nuevo = 'PREPARANDO');

INSERT INTO public.historial_pedido (id_historial, id_pedido, id_usuario, estado_anterior, estado_nuevo, tipo_accion, fecha_hora, motivo)
SELECT COALESCE((SELECT MAX(id_historial) FROM public.historial_pedido), 0) + 1, ped.id_pedido, staff.id, 'PREPARANDO', 'LISTO', 'MARCAR_LISTO', CURRENT_TIMESTAMP - INTERVAL '10 minutes', 'Orden lista para recoger y liquidar en efectivo en ventanilla.'
FROM public.pedido ped
    JOIN public.users alum ON ped.id_usuario = alum.id
    CROSS JOIN public.users staff
WHERE alum.user_name = 'alumno.mateo' AND ped.estado = 'LISTO' AND staff.user_name = 'cafeteria.ana'
  AND NOT EXISTS (SELECT 1 FROM public.historial_pedido hp WHERE hp.id_pedido = ped.id_pedido AND hp.estado_nuevo = 'LISTO');

INSERT INTO public.historial_pedido (id_historial, id_pedido, id_usuario, estado_anterior, estado_nuevo, tipo_accion, fecha_hora, motivo)
SELECT COALESCE((SELECT MAX(id_historial) FROM public.historial_pedido), 0) + 1, ped.id_pedido, staff.id, 'LISTO', 'ENTREGADO', 'ENTREGAR_Y_COBRAR', CURRENT_TIMESTAMP - INTERVAL '30 minutes', 'Entregado al estudiante; pago físico de $62.00 recibido en caja.'
FROM public.pedido ped
    JOIN public.users alum ON ped.id_usuario = alum.id
    CROSS JOIN public.users staff
WHERE alum.user_name = 'alumno.carlos' AND ped.estado = 'ENTREGADO' AND staff.user_name = 'admin'
  AND NOT EXISTS (SELECT 1 FROM public.historial_pedido hp WHERE hp.id_pedido = ped.id_pedido AND hp.estado_nuevo = 'ENTREGADO');

INSERT INTO public.historial_pedido (id_historial, id_pedido, id_usuario, estado_anterior, estado_nuevo, tipo_accion, fecha_hora, motivo)
SELECT COALESCE((SELECT MAX(id_historial) FROM public.historial_pedido), 0) + 1, ped.id_pedido, staff.id, 'PENDIENTE', 'RECHAZADO', 'RECHAZAR_PEDIDO', CURRENT_TIMESTAMP - INTERVAL '40 minutes', 'Insumo agotado en mostrador al momento de confirmar.'
FROM public.pedido ped
    JOIN public.users alum ON ped.id_usuario = alum.id
    CROSS JOIN public.users staff
WHERE alum.user_name = 'alumna.sofia' AND ped.estado = 'RECHAZADO' AND staff.user_name = 'cafeteria.ana'
  AND NOT EXISTS (SELECT 1 FROM public.historial_pedido hp WHERE hp.id_pedido = ped.id_pedido AND hp.estado_nuevo = 'RECHAZADO');

-- 9. NOTIFICACIONES DEMO PARA ESTUDIANTES
INSERT INTO public.notificacion (id_notificacion, id_usuario, id_pedido, tipo, mensaje, fecha_hora, leida)
SELECT COALESCE((SELECT MAX(id_notificacion) FROM public.notificacion), 0) + 1, alum.id, ped.id_pedido, 'PEDIDO_LISTO', 'Tu pedido #Combo Ligero Mañanero está LISTO. Pasa a ventanilla de cafetería para recoger y realizar tu pago físico.', CURRENT_TIMESTAMP - INTERVAL '10 minutes', false
FROM public.pedido ped
    JOIN public.users alum ON ped.id_usuario = alum.id
WHERE alum.user_name = 'alumno.mateo' AND ped.estado = 'LISTO'
  AND NOT EXISTS (SELECT 1 FROM public.notificacion n WHERE n.id_pedido = ped.id_pedido AND n.tipo = 'PEDIDO_LISTO');

INSERT INTO public.notificacion (id_notificacion, id_usuario, id_pedido, tipo, mensaje, fecha_hora, leida)
SELECT COALESCE((SELECT MAX(id_notificacion) FROM public.notificacion), 0) + 1, alum.id, ped.id_pedido, 'PEDIDO_RECHAZADO', 'Tu pedido de Pay de Queso Artesanal fue rechazado: Insumo agotado en mostrador.', CURRENT_TIMESTAMP - INTERVAL '40 minutes', false
FROM public.pedido ped
    JOIN public.users alum ON ped.id_usuario = alum.id
WHERE alum.user_name = 'alumna.sofia' AND ped.estado = 'RECHAZADO'
  AND NOT EXISTS (SELECT 1 FROM public.notificacion n WHERE n.id_pedido = ped.id_pedido AND n.tipo = 'PEDIDO_RECHAZADO');