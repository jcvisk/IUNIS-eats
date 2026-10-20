-- Ejecutar despues de 2026-10-19base_migration.sql.
-- Usuario: admin. Contrasena: demo (BCrypt).
-- Rol 2: CAFETERIA_STAFF, con acceso a la administracion de usuarios.
-- Si admin ya existe, conservar sus credenciales y su rol.
INSERT INTO public.users (user_name, password, role)
SELECT 'admin', '$2a$10$UrdOHk5Ht6w4eGSPvzhXiuXP4ymbWf3TOtsHQPrz279U0JKTnYdfy', 2
WHERE NOT EXISTS (
    SELECT 1 FROM public.users WHERE user_name = 'admin'
);
