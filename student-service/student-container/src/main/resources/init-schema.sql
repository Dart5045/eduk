DROP SCHEMA IF EXISTS student CASCADE;

CREATE SCHEMA student;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE student.students
(
    id uuid NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (id)
);

DROP MATERIALIZED VIEW IF EXISTS student.confirmation_student_m_view;

CREATE MATERIALIZED VIEW student.confirmation_student_m_view
TABLESPACE pg_default
AS
 SELECT id,
    email,
    first_name,
    last_name
   FROM student.students
WITH DATA;

refresh materialized VIEW student.confirmation_student_m_view;

DROP function IF EXISTS student.refresh_confirmation_student_m_view;

CREATE OR replace function student.refresh_confirmation_student_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW student.confirmation_student_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_confirmation_student_m_view ON student.students;

CREATE trigger refresh_confirmation_student_m_view
after INSERT OR UPDATE OR DELETE OR truncate
ON student.students FOR each statement
EXECUTE PROCEDURE student.refresh_confirmation_student_m_view();