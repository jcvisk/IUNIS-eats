CREATE TABLE public.combo (
    id_combo BIGSERIAL PRIMARY KEY,
    nombre character varying(100) NOT NULL,
    descripcion text,
    precio numeric(10,2) NOT NULL,
    imagen character varying(255),
    disponibilidad boolean DEFAULT true NOT NULL
);

CREATE TABLE public.platillo (
    id_platillo BIGSERIAL PRIMARY KEY,
    nombre character varying(100) NOT NULL,
    descripcion text,
    precio numeric(10,2) NOT NULL,
    imagen character varying(255),
    disponibilidad boolean DEFAULT true NOT NULL
);

CREATE TABLE public.roles (
    id BIGSERIAL PRIMARY KEY,
    des character varying(50) NOT NULL
);

CREATE TABLE public.users (
    id BIGSERIAL PRIMARY KEY,
    user_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role integer
);

CREATE TABLE public.periodo_servicio (
    id_periodo BIGSERIAL PRIMARY KEY,
    fecha date NOT NULL,
    limite_hora time without time zone NOT NULL,
    estado_periodo character varying(50) NOT NULL
);

CREATE TABLE public.combo_platillo (
    id_combo BIGINT NOT NULL,
    id_platillo BIGINT NOT NULL,
    cantidad integer NOT NULL,
    CONSTRAINT combo_platillo_pkey PRIMARY KEY (id_combo, id_platillo),
    CONSTRAINT combo_platillo_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE CASCADE,
    CONSTRAINT combo_platillo_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE CASCADE
);

CREATE TABLE public.datos_personales (
    id BIGSERIAL PRIMARY KEY,
    nombre character varying(100) NOT NULL,
    apellidos character varying(100) NOT NULL,
    edad integer,
    genero character varying(20),
    foto character varying(255),
    user_id BIGINT NOT NULL,
    CONSTRAINT datos_personales_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE public.pedido (
    id_pedido BIGSERIAL PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_periodo BIGINT NOT NULL,
    fecha_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    total numeric(10,2) NOT NULL,
    estado character varying(50) NOT NULL,
    CONSTRAINT pedido_id_periodo_fkey FOREIGN KEY (id_periodo) REFERENCES public.periodo_servicio(id_periodo) ON DELETE CASCADE,
    CONSTRAINT pedido_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE public.detalle_pedido (
    id_detalle BIGSERIAL PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_platillo BIGINT,
    id_combo BIGINT,
    cantidad integer NOT NULL,
    precio_aplicado numeric(10,2) NOT NULL,
    CONSTRAINT chk_platillo_o_combo CHECK ((((id_platillo IS NOT NULL) AND (id_combo IS NULL)) OR ((id_platillo IS NULL) AND (id_combo IS NOT NULL)))),
    CONSTRAINT detalle_pedido_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE SET NULL,
    CONSTRAINT detalle_pedido_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE,
    CONSTRAINT detalle_pedido_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE SET NULL
);

CREATE TABLE public.historial_pedido (
    id_historial BIGSERIAL PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    estado_anterior character varying(50),
    estado_nuevo character varying(50) NOT NULL,
    tipo_accion character varying(100) NOT NULL,
    fecha_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    motivo text,
    CONSTRAINT historial_pedido_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE,
    CONSTRAINT historial_pedido_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE public.menu (
    id_menu BIGSERIAL PRIMARY KEY,
    id_periodo BIGINT NOT NULL,
    fecha_publicacion timestamp without time zone NOT NULL,
    estado_publicacion character varying(50) NOT NULL,
    CONSTRAINT menu_id_periodo_fkey FOREIGN KEY (id_periodo) REFERENCES public.periodo_servicio(id_periodo) ON DELETE CASCADE
);

CREATE TABLE public.menu_combo (
    id_menu BIGINT NOT NULL,
    id_combo BIGINT NOT NULL,
    precio_publicado numeric(10,2) NOT NULL,
    disponibilidad boolean DEFAULT true NOT NULL,
    CONSTRAINT menu_combo_pkey PRIMARY KEY (id_menu, id_combo),
    CONSTRAINT menu_combo_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE CASCADE,
    CONSTRAINT menu_combo_id_menu_fkey FOREIGN KEY (id_menu) REFERENCES public.menu(id_menu) ON DELETE CASCADE
);

CREATE TABLE public.menu_platillo (
    id_menu BIGINT NOT NULL,
    id_platillo BIGINT NOT NULL,
    precio_publicado numeric(10,2) NOT NULL,
    disponibilidad boolean DEFAULT true NOT NULL,
    CONSTRAINT menu_platillo_pkey PRIMARY KEY (id_menu, id_platillo),
    CONSTRAINT menu_platillo_id_menu_fkey FOREIGN KEY (id_menu) REFERENCES public.menu(id_menu) ON DELETE CASCADE,
    CONSTRAINT menu_platillo_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE CASCADE
);

CREATE TABLE public.notificacion (
    id_notificacion BIGSERIAL PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_pedido BIGINT NOT NULL,
    tipo character varying(50) NOT NULL,
    mensaje text NOT NULL,
    fecha_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    leida boolean DEFAULT false NOT NULL,
    CONSTRAINT notificacion_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE,
    CONSTRAINT notificacion_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE public.roles_user (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT roles_user_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT roles_user_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE,
    CONSTRAINT roles_user_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);
