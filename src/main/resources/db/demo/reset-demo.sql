-- ============================================================================
-- IUNIS-Eats | Reinicio limpio de datos de demostración
-- Elimina únicamente los registros creados por el entorno demo y vuelve a
-- ejecutar data-demo.sql para dejar el ambiente listo para una nueva sesión.
-- ============================================================================

DELETE FROM public.notificacion
WHERE id_usuario IN (
    SELECT id FROM public.users WHERE user_name IN ('cafeteria.ana', 'alumno.carlos', 'alumna.sofia', 'alumno.mateo')
);

DELETE FROM public.historial_pedido
WHERE id_usuario IN (
    SELECT id FROM public.users WHERE user_name IN ('admin', 'cafeteria.ana', 'alumno.carlos', 'alumna.sofia', 'alumno.mateo')
);

DELETE FROM public.pedido
WHERE id_usuario IN (
    SELECT id FROM public.users WHERE user_name IN ('alumno.carlos', 'alumna.sofia', 'alumno.mateo')
);

DELETE FROM public.menu_combo
WHERE id_combo IN (
    SELECT id_combo FROM public.combo WHERE nombre IN ('Combo Desayuno Universitario', 'Combo Ligero Mañanero')
);

DELETE FROM public.menu_platillo
WHERE id_platillo IN (
    SELECT id_platillo FROM public.platillo WHERE nombre IN (
                                                             'Chilaquiles Verdes con Pollo',
                                                             'Molletes Tradicionales (2 pzas)',
                                                             'Sándwich Integral de Pechuga',
                                                             'Enchiladas Suizas (3 pzas)',
                                                             'Agua Fresca de Horchata 500ml',
                                                             'Licuado de Plátano con Avena 400ml',
                                                             'Pay de Queso Artesanal (Agotado Demo)'
        )
);

DELETE FROM public.combo
WHERE nombre IN ('Combo Desayuno Universitario', 'Combo Ligero Mañanero');

DELETE FROM public.platillo
WHERE nombre IN (
                 'Chilaquiles Verdes con Pollo',
                 'Molletes Tradicionales (2 pzas)',
                 'Sándwich Integral de Pechuga',
                 'Enchiladas Suizas (3 pzas)',
                 'Agua Fresca de Horchata 500ml',
                 'Licuado de Plátano con Avena 400ml',
                 'Pay de Queso Artesanal (Agotado Demo)'
    );

DELETE FROM public.datos_personales
WHERE user_id IN (
    SELECT id FROM public.users WHERE user_name IN ('cafeteria.ana', 'alumno.carlos', 'alumna.sofia', 'alumno.mateo')
);

DELETE FROM public.roles_user
WHERE user_id IN (
    SELECT id FROM public.users WHERE user_name IN ('cafeteria.ana', 'alumno.carlos', 'alumna.sofia', 'alumno.mateo')
);

DELETE FROM public.users
WHERE user_name IN ('cafeteria.ana', 'alumno.carlos', 'alumna.sofia', 'alumno.mateo');