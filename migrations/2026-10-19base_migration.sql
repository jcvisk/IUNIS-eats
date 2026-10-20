CREATE TABLE public.combo (
    id_combo integer NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text,
    precio numeric(10,2) NOT NULL,
    imagen character varying(255),
    disponibilidad boolean DEFAULT true NOT NULL
);

CREATE TABLE public.combo_platillo (
    id_combo integer NOT NULL,
    id_platillo integer NOT NULL,
    cantidad integer NOT NULL
);

CREATE TABLE public.datos_personales (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    apellidos character varying(100) NOT NULL,
    edad integer,
    genero character varying(20),
    foto character varying(255),
    user_id integer NOT NULL
);

CREATE TABLE public.detalle_pedido (
    id_detalle integer NOT NULL,
    id_pedido integer NOT NULL,
    id_platillo integer,
    id_combo integer,
    cantidad integer NOT NULL,
    precio_aplicado numeric(10,2) NOT NULL,
    CONSTRAINT chk_platillo_o_combo CHECK ((((id_platillo IS NOT NULL) AND (id_combo IS NULL)) OR ((id_platillo IS NULL) AND (id_combo IS NOT NULL))))
);

CREATE TABLE public.historial_pedido (
    id_historial integer NOT NULL,
    id_pedido integer NOT NULL,
    id_usuario integer NOT NULL,
    estado_anterior character varying(50),
    estado_nuevo character varying(50) NOT NULL,
    tipo_accion character varying(100) NOT NULL,
    fecha_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    motivo text
);

CREATE TABLE public.menu (
    id_menu integer NOT NULL,
    id_periodo integer NOT NULL,
    fecha_publicacion timestamp without time zone NOT NULL,
    estado_publicacion character varying(50) NOT NULL
);

CREATE TABLE public.menu_combo (
    id_menu integer NOT NULL,
    id_combo integer NOT NULL,
    precio_publicado numeric(10,2) NOT NULL,
    disponibilidad boolean DEFAULT true NOT NULL
);

CREATE TABLE public.menu_platillo (
    id_menu integer NOT NULL,
    id_platillo integer NOT NULL,
    precio_publicado numeric(10,2) NOT NULL,
    disponibilidad boolean DEFAULT true NOT NULL
);

CREATE TABLE public.notificacion (
    id_notificacion integer NOT NULL,
    id_usuario integer NOT NULL,
    id_pedido integer NOT NULL,
    tipo character varying(50) NOT NULL,
    mensaje text NOT NULL,
    fecha_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    leida boolean DEFAULT false NOT NULL
);

CREATE TABLE public.pedido (
    id_pedido integer NOT NULL,
    id_usuario integer NOT NULL,
    id_periodo integer NOT NULL,
    fecha_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    total numeric(10,2) NOT NULL,
    estado character varying(50) NOT NULL
);

CREATE TABLE public.periodo_servicio (
    id_periodo integer NOT NULL,
    fecha date NOT NULL,
    limite_hora time without time zone NOT NULL,
    estado_periodo character varying(50) NOT NULL
);

CREATE TABLE public.platillo (
    id_platillo integer NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text,
    precio numeric(10,2) NOT NULL,
    imagen character varying(255),
    disponibilidad boolean DEFAULT true NOT NULL
);

CREATE TABLE public.roles (
    id integer NOT NULL,
    des character varying(50) NOT NULL
);


CREATE TABLE public.roles_user (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);

CREATE TABLE public.users (
    id bigint NOT NULL,
    user_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role integer
);


ALTER TABLE ONLY public.combo_platillo
    ADD CONSTRAINT combo_platillo_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE CASCADE;

ALTER TABLE ONLY public.combo_platillo
    ADD CONSTRAINT combo_platillo_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE CASCADE;

ALTER TABLE ONLY public.datos_personales
    ADD CONSTRAINT datos_personales_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE SET NULL;

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE;

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE SET NULL;

ALTER TABLE ONLY public.historial_pedido
    ADD CONSTRAINT historial_pedido_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE;

ALTER TABLE ONLY public.historial_pedido
    ADD CONSTRAINT historial_pedido_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.menu_combo
    ADD CONSTRAINT menu_combo_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE CASCADE;

ALTER TABLE ONLY public.menu_combo
    ADD CONSTRAINT menu_combo_id_menu_fkey FOREIGN KEY (id_menu) REFERENCES public.menu(id_menu) ON DELETE CASCADE;

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_id_periodo_fkey FOREIGN KEY (id_periodo) REFERENCES public.periodo_servicio(id_periodo) ON DELETE CASCADE;

ALTER TABLE ONLY public.menu_platillo
    ADD CONSTRAINT menu_platillo_id_menu_fkey FOREIGN KEY (id_menu) REFERENCES public.menu(id_menu) ON DELETE CASCADE;

ALTER TABLE ONLY public.menu_platillo
    ADD CONSTRAINT menu_platillo_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE CASCADE;

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT notificacion_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE;

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT notificacion_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_id_periodo_fkey FOREIGN KEY (id_periodo) REFERENCES public.periodo_servicio(id_periodo) ON DELETE CASCADE;

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.roles_user
    ADD CONSTRAINT roles_user_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.roles_user
    ADD CONSTRAINT roles_user_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;