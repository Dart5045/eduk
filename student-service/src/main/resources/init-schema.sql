DROP SCHEMA IF EXISTS admission CASCADE;

CREATE SCHEMA admission;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE admission.students
(
    id uuid NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (id)
);
CREATE TABLE admission.applications
(
    id uuid NOT NULL,
    student_id uuid NOT NULL,
    CONSTRAINT applications_pkey PRIMARY KEY (id)
);


DROP MATERIALIZED VIEW IF EXISTS admission.confirmation_student_m_view;

CREATE MATERIALIZED VIEW admission.confirmation_student_m_view
TABLESPACE pg_default
AS
 SELECT id,
    username,
    first_name,
    last_name
   FROM admission.students
WITH DATA;

refresh materialized VIEW admission.confirmation_student_m_view;

DROP function IF EXISTS admission.refresh_confirmation_student_m_view;

CREATE OR replace function admission.refresh_confirmation_student_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW admission.confirmation_student_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_confirmation_student_m_view ON customer.customers;

CREATE trigger refresh_confirmation_student_m_view
after INSERT OR UPDATE OR DELETE OR truncate
ON admission.students FOR each statement
EXECUTE PROCEDURE admission.refresh_confirmation_student_m_view();