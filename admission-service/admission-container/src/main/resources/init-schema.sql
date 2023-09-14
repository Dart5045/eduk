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


DROP TYPE IF EXISTS saga_status;
CREATE TYPE saga_status AS ENUM ('STARTED', 'FAILED', 'SUCCEEDED', 'PROCESSING', 'COMPENSATING', 'COMPENSATED');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'COMPLETED', 'FAILED');

DROP TABLE IF EXISTS "admission".payment_outbox CASCADE;

CREATE TABLE "admission".payment_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    saga_status saga_status NOT NULL,
    confirmation_status confirmation_status NOT NULL,
    version integer NOT NULL,
    CONSTRAINT payment_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "payment_outbox_saga_status"
    ON "admission".payment_outbox
    (type, outbox_status, saga_status);

--CREATE UNIQUE INDEX "payment_outbox_saga_id"
--    ON "confirmation".payment_outbox
--    (type, saga_id, saga_status);

DROP TABLE IF EXISTS "admission".finance_approval_outbox CASCADE;

CREATE TABLE "admission".finance_approval_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    saga_status saga_status NOT NULL,
    confirmation_status confirmation_status NOT NULL,
    version integer NOT NULL,
    CONSTRAINT finance_approval_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "finance_approval_outbox_saga_status"
    ON "admission".finance_approval_outbox
    (type, outbox_status, saga_status);

--CREATE UNIQUE INDEX "finance_approval_outbox_saga_id"
--    ON "confirmation".finance_approval_outbox
--    (type, saga_id, saga_status);



DROP TABLE IF EXISTS "admission".customers CASCADE;
CREATE TABLE admission.students
(
    id uuid NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (id)
);

