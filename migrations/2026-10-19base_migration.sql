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






--


--
-- TOC entry 4936 (class 2604 OID 24802)
-- Name: notificacion id_notificacion; Type: DEFAULT; Schema: public; Owner: postgres

--


--
-- TOC entry 4925 (class 2604 OID 24638)
-- Name: periodo_servicio id_periodo; Type: DEFAULT; Schema: public; Owner: postgres
--


-- TOC entry 4927 (class 2604 OID 24665)
-- Name: platillo id_platillo; Type: DEFAULT; Schema: public; Owner: postgres
--

--
-- TOC entry 4922 (class 2604 OID 24580)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

--
-- TOC entry 4923 (class 2604 OID 24860)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

-- TOC entry 4963 (class 2606 OID 24688)
-- Name: combo combo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 4969 (class 2606 OID 24736)
-- Name: combo_platillo combo_platillo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 4953 (class 2606 OID 24626)
-- Name: datos_personales datos_personales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

--
-- TOC entry 4955 (class 2606 OID 24628)
-- Name: datos_personales datos_personales_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

--
-- TOC entry 4973 (class 2606 OID 24782)
-- Name: detalle_pedido detalle_pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--



--
-- TOC entry 4977 (class 2606 OID 24841)
-- Name: historial_pedido historial_pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 4967 (class 2606 OID 24718)
-- Name: menu_combo menu_combo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

--
-- TOC entry 4959 (class 2606 OID 24655)
-- Name: menu menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 4965 (class 2606 OID 24698)
-- Name: menu_platillo menu_platillo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

--
-- TOC entry 4975 (class 2606 OID 24815)
-- Name: notificacion notificacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--



--
-- TOC entry 4971 (class 2606 OID 24760)
-- Name: pedido pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--



--
-- TOC entry 4957 (class 2606 OID 24644)
-- Name: periodo_servicio periodo_servicio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 4961 (class 2606 OID 24674)
-- Name: platillo platillo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres


--
-- TOC entry 4943 (class 2606 OID 24586)
-- Name: roles roles_des_key; Type: CONSTRAINT; Schema: public; Owner: postgres

--
-- TOC entry 4945 (class 2606 OID 24584)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 4951 (class 2606 OID 24605)
-- Name: roles_user roles_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- TOC entry 4947 (class 2606 OID 24862)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

--
-- TOC entry 4949 (class 2606 OID 24895)
-- Name: users users_user_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres

--
-- TOC entry 4986 (class 2606 OID 24737)
-- Name: combo_platillo combo_platillo_id_combo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.combo_platillo
    ADD CONSTRAINT combo_platillo_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE CASCADE;


--
-- TOC entry 4987 (class 2606 OID 24742)
-- Name: combo_platillo combo_platillo_id_platillo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.combo_platillo
    ADD CONSTRAINT combo_platillo_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE CASCADE;


--
-- TOC entry 4980 (class 2606 OID 24869)
-- Name: datos_personales datos_personales_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.datos_personales
    ADD CONSTRAINT datos_personales_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4990 (class 2606 OID 24793)
-- Name: detalle_pedido detalle_pedido_id_combo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE SET NULL;


--
-- TOC entry 4991 (class 2606 OID 24783)
-- Name: detalle_pedido detalle_pedido_id_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE;


--
-- TOC entry 4992 (class 2606 OID 24788)
-- Name: detalle_pedido detalle_pedido_id_platillo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE SET NULL;


--
-- TOC entry 4995 (class 2606 OID 24842)
-- Name: historial_pedido historial_pedido_id_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_pedido
    ADD CONSTRAINT historial_pedido_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE;


--
-- TOC entry 4996 (class 2606 OID 24884)
-- Name: historial_pedido historial_pedido_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_pedido
    ADD CONSTRAINT historial_pedido_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4984 (class 2606 OID 24724)
-- Name: menu_combo menu_combo_id_combo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_combo
    ADD CONSTRAINT menu_combo_id_combo_fkey FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON DELETE CASCADE;


--
-- TOC entry 4985 (class 2606 OID 24719)
-- Name: menu_combo menu_combo_id_menu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_combo
    ADD CONSTRAINT menu_combo_id_menu_fkey FOREIGN KEY (id_menu) REFERENCES public.menu(id_menu) ON DELETE CASCADE;


--
-- TOC entry 4981 (class 2606 OID 24656)
-- Name: menu menu_id_periodo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_id_periodo_fkey FOREIGN KEY (id_periodo) REFERENCES public.periodo_servicio(id_periodo) ON DELETE CASCADE;


--
-- TOC entry 4982 (class 2606 OID 24699)
-- Name: menu_platillo menu_platillo_id_menu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_platillo
    ADD CONSTRAINT menu_platillo_id_menu_fkey FOREIGN KEY (id_menu) REFERENCES public.menu(id_menu) ON DELETE CASCADE;


--
-- TOC entry 4983 (class 2606 OID 24704)
-- Name: menu_platillo menu_platillo_id_platillo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_platillo
    ADD CONSTRAINT menu_platillo_id_platillo_fkey FOREIGN KEY (id_platillo) REFERENCES public.platillo(id_platillo) ON DELETE CASCADE;


--
-- TOC entry 4993 (class 2606 OID 24821)
-- Name: notificacion notificacion_id_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT notificacion_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido) ON DELETE CASCADE;


--
-- TOC entry 4994 (class 2606 OID 24879)
-- Name: notificacion notificacion_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT notificacion_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4988 (class 2606 OID 24766)
-- Name: pedido pedido_id_periodo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_id_periodo_fkey FOREIGN KEY (id_periodo) REFERENCES public.periodo_servicio(id_periodo) ON DELETE CASCADE;


--
-- TOC entry 4989 (class 2606 OID 24874)
-- Name: pedido pedido_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4978 (class 2606 OID 24611)
-- Name: roles_user roles_user_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_user
    ADD CONSTRAINT roles_user_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


--
-- TOC entry 4979 (class 2606 OID 24864)
-- Name: roles_user roles_user_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_user
    ADD CONSTRAINT roles_user_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;

