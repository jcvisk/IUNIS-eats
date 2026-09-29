CREATE TABLE public.personal_data
(
    id bigserial NOT NULL,
    names character varying NOT NULL,
    last_names character varying,
    age integer,
    gender "char",
    user_id bigint,
    CONSTRAINT personal_data_pk PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.personal_data
    OWNER to admin;

ALTER TABLE IF EXISTS public.personal_data
    ADD CONSTRAINT personal_data_user_id_fk FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;