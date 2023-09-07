DROP SCHEMA IF EXISTS "admission" CASCADE;

CREATE SCHEMA "admission";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS confirmation_status;
CREATE TYPE confirmation_status AS ENUM ('PENDING', 'PAID', 'APPROVED', 'CANCELLED', 'CANCELLING');

CREATE TABLE "admission".terms
(
    id uuid NOT NULL,
    year integer NOT NULL,
    period character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT term_pkey PRIMARY KEY (id)
);