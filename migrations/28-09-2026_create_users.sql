CREATE TABLE public.users
(
    id bigserial NOT NULL,
    user_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role bigint NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.users
    OWNER to admin;