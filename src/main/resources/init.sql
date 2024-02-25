DROP SCHEMA mydb CASCADE;
CREATE SCHEMA IF NOT EXISTS mydb AUTHORIZATION admin;

CREATE TABLE IF NOT EXISTS mydb.courses
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    course_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS mydb.courses
    OWNER to admin;



CREATE TABLE IF NOT EXISTS mydb.teachers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    teacher_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT teachers_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS mydb.teachers
    OWNER to admin;


CREATE TABLE IF NOT EXISTS mydb.students
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    student_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    course_id bigint,
    CONSTRAINT students_pkey PRIMARY KEY (id),
    CONSTRAINT courses_fkey FOREIGN KEY (course_id)
        REFERENCES mydb.courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS mydb.students
    OWNER to admin;


CREATE TABLE IF NOT EXISTS mydb.courses_teachers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    course_id bigint NOT NULL,
    teacher_id bigint NOT NULL,
    CONSTRAINT teachers_courses_pkey PRIMARY KEY (id),
    CONSTRAINT course_fkey FOREIGN KEY (course_id)
        REFERENCES mydb.courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT teacher_fkey FOREIGN KEY (teacher_id)
        REFERENCES mydb.teachers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS mydb.courses_teachers
    OWNER to admin;

ALTER TABLE IF EXISTS mydb.teachers_courses
    OWNER to admin;


INSERT INTO mydb.courses (course_name) VALUES ('Java');
INSERT INTO mydb.courses (course_name) VALUES ('Python');
INSERT INTO mydb.courses (course_name) VALUES ('JavaScript');
INSERT INTO mydb.courses (course_name) VALUES ('PHP');
INSERT INTO mydb.courses (course_name) VALUES ('SQL');

INSERT INTO mydb.teachers (teacher_name) VALUES ('Демидов Дмитрий');
INSERT INTO mydb.teachers (teacher_name) VALUES ('Чайкина Ольга');
INSERT INTO mydb.teachers (teacher_name) VALUES ('Дудин Виктор');

INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('2', '1');
INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('4', '1');
INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('3', '2');
INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('5', '2');
INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('1', '3');
INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('2', '3');
INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('3', '3');
INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('4', '3');
INSERT INTO mydb.courses_teachers (course_id, teacher_id) VALUES ('5', '3');

INSERT INTO mydb.students (student_name, course_id) VALUES ('Alexey', '1');
INSERT INTO mydb.students (student_name, course_id) VALUES ('Maxim', '2');