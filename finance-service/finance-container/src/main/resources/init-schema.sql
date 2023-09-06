DROP SCHEMA IF EXISTS finance CASCADE;

CREATE SCHEMA finance;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS finance.finances CASCADE;

CREATE TABLE finance.finances
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    active boolean NOT NULL,
    CONSTRAINT finances_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS approval_status;

CREATE TYPE approval_status AS ENUM ('APPROVED', 'REJECTED');

DROP TABLE IF EXISTS finance.confirmation_approval CASCADE;

CREATE TABLE finance.confirmation_approval
(
    id uuid NOT NULL,
    finance_id uuid NOT NULL,
    confirmation_id uuid NOT NULL,
    status approval_status NOT NULL,
    CONSTRAINT confirmation_approval_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS finance.products CASCADE;

CREATE TABLE finance.products
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    price numeric(10,2) NOT NULL,
    available boolean NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS finance.finance_products CASCADE;

CREATE TABLE finance.finance_products
(
    id uuid NOT NULL,
    finance_id uuid NOT NULL,
    product_id uuid NOT NULL,
    CONSTRAINT finance_products_pkey PRIMARY KEY (id)
);

ALTER TABLE finance.finance_products
    ADD CONSTRAINT "FK_RESTAURANT_ID" FOREIGN KEY (finance_id)
    REFERENCES finance.finances (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE finance.finance_products
    ADD CONSTRAINT "FK_PRODUCT_ID" FOREIGN KEY (product_id)
    REFERENCES finance.products (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

DROP MATERIALIZED VIEW IF EXISTS finance.confirmation_finance_m_view;

CREATE MATERIALIZED VIEW finance.confirmation_finance_m_view
TABLESPACE pg_default
AS
 SELECT r.id AS finance_id,
    r.name AS finance_name,
    r.active AS finance_active,
    p.id AS product_id,
    p.name AS product_name,
    p.price AS product_price,
    p.available AS product_available
   FROM finance.finances r,
    finance.products p,
    finance.finance_products rp
  WHERE r.id = rp.finance_id AND p.id = rp.product_id
WITH DATA;

refresh materialized VIEW finance.confirmation_finance_m_view;

DROP function IF EXISTS finance.refresh_confirmation_finance_m_view;

CREATE OR replace function finance.refresh_confirmation_finance_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW finance.confirmation_finance_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_confirmation_finance_m_view ON finance.finance_products;

CREATE trigger refresh_confirmation_finance_m_view
after INSERT OR UPDATE OR DELETE OR truncate
ON finance.finance_products FOR each statement
EXECUTE PROCEDURE finance.refresh_confirmation_finance_m_view();