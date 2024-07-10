DROP TYPE IF EXISTS role_type CASCADE;
CREATE TYPE role_type AS ENUM ('STUDENT', 'TEACHER', 'ADMIN', 'MODERATOR');

DROP TABLE IF EXISTS student_achievements;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS task_results;
DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS task_game_methods;
DROP TABLE IF EXISTS game_method_tags;
DROP TABLE IF EXISTS game_methods;
DROP TABLE IF EXISTS course_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS lesson_tasks;
DROP TABLE IF EXISTS course_tasks;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS course_lessons;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS disciplines;
DROP TABLE IF EXISTS scores;
DROP TABLE IF EXISTS course_teachers;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS course_students;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS blogs;
DROP TABLE IF EXISTS users;





DROP SEQUENCE IF EXISTS users_id_seq;
CREATE SEQUENCE IF NOT EXISTS users_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS users
(
    id bigint DEFAULT nextval('users_id_seq'::regclass) NOT NULL,
    name character varying(20) NOT NULL,
    surname character varying(32) NOT NULL,
    fathers_name character varying(32),
    password character varying(20) NOT NULL,
    email character varying(32) NOT NULL,
    phone_number character(13) NOT NULL,
    role role_type NOT NULL,
    tokens character varying(255)[],
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_phone_number_key UNIQUE (phone_number)
);

ALTER TABLE IF EXISTS users
    OWNER to postgres;


DROP SEQUENCE IF EXISTS blogs_id_seq;
CREATE SEQUENCE IF NOT EXISTS blogs_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS blogs
(
    id bigint DEFAULT nextval('blogs_id_seq'::regclass) NOT NULL,
    title character varying(255) NOT NULL,
    text text NOT NULL,
    CONSTRAINT blogs_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS blogs
    OWNER to postgres;

-- Table: personal_data.students

DROP SEQUENCE IF EXISTS students_id_seq;
CREATE SEQUENCE IF NOT EXISTS students_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS students
(
    id bigint DEFAULT nextval('students_id_seq'::regclass) NOT NULL,
    user_id bigint NOT NULL,
	wallet numeric(15,2) NOT NULL,
	experience integer NOT NULL,
	activity integer NOT NULL,
	blog_id bigint NOT NULL,
	bth_date date NOT NULL,
	age integer,
	image_path text,
    CONSTRAINT students_pkey PRIMARY KEY (id),
    CONSTRAINT users_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT blogs_fkey FOREIGN KEY (blog_id)
        REFERENCES blogs (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS students
    OWNER to postgres;

DROP SEQUENCE IF EXISTS courses_id_seq;
CREATE SEQUENCE IF NOT EXISTS courses_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS courses
(
    id bigint DEFAULT nextval('courses_id_seq'::regclass) NOT NULL,
	title character varying(255) NOT NULL,
	description text NOT NULL,
	creator_uid bigint NOT NULL,
	cost numeric(15,2) NOT NULL,
	rating numeric(1) NOT NULL,
	amt_passed_users integer NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (id),
    CONSTRAINT users_fkey FOREIGN KEY (creator_uid)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS courses
    OWNER to postgres;

DROP SEQUENCE IF EXISTS course_students_id_seq;
CREATE SEQUENCE IF NOT EXISTS course_students_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS course_students
(
    id bigint DEFAULT nextval('course_students_id_seq'::regclass) NOT NULL,
    course_id bigint NOT NULL,
    student_id bigint NOT NULL,
    CONSTRAINT course_students_pkey PRIMARY KEY (id),
    CONSTRAINT courses_fkey FOREIGN KEY (course_id)
        REFERENCES courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT students_fkey FOREIGN KEY (student_id)
        REFERENCES students (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT course_students_course_id_student_id_key UNIQUE (course_id, student_id)
);

ALTER TABLE IF EXISTS course_students
    OWNER to postgres;

DROP SEQUENCE IF EXISTS scores_id_seq;
CREATE SEQUENCE IF NOT EXISTS scores_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS scores
(
    id bigint DEFAULT nextval('scores_id_seq'::regclass) NOT NULL,
	title character varying(255) NOT NULL,
	student_id bigint NOT NULL,
	course_id bigint NOT NULL,
	experience integer NOT NULL,
    CONSTRAINT scores_pkey PRIMARY KEY (id),
    CONSTRAINT students_fkey FOREIGN KEY (student_id)
        REFERENCES students (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT courses_fkey FOREIGN KEY (course_id)
        REFERENCES courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS scores
    OWNER to postgres;

DROP SEQUENCE IF EXISTS disciplines_id_seq;
CREATE SEQUENCE IF NOT EXISTS disciplines_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS disciplines
(
    id integer DEFAULT nextval('disciplines_id_seq'::regclass) NOT NULL,
    title character varying(255) NOT NULL,
    CONSTRAINT disciplines_pkey PRIMARY KEY (id),
    CONSTRAINT disciplines_title_key UNIQUE (title)
);

ALTER TABLE IF EXISTS disciplines
    OWNER to postgres;

DROP SEQUENCE IF EXISTS lessons_id_seq;
CREATE SEQUENCE IF NOT EXISTS lessons_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS lessons
(
    id bigint DEFAULT nextval('lessons_id_seq'::regclass) NOT NULL,
    title character varying(255) NOT NULL,
    description text NOT NULL,
    discipline_id integer NOT NULL,
    experience integer NOT NULL,
    complexity integer NOT NULL,
    CONSTRAINT lessons_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS lessons
    OWNER to postgres;

DROP SEQUENCE IF EXISTS course_lessons_id_seq;
CREATE SEQUENCE IF NOT EXISTS course_lessons_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS course_lessons
(
    id bigint DEFAULT nextval('course_lessons_id_seq'::regclass) NOT NULL,
    course_id bigint NOT NULL,
    lesson_id bigint NOT NULL,
    CONSTRAINT course_lessons_pkey PRIMARY KEY (id),
    CONSTRAINT courses_fkey FOREIGN KEY (course_id)
        REFERENCES courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT lessons_fkey FOREIGN KEY (lesson_id)
        REFERENCES lessons (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT course_lessons_course_id_lesson_id_key UNIQUE (course_id, lesson_id)
);

ALTER TABLE IF EXISTS course_lessons
    OWNER to postgres;

DROP SEQUENCE IF EXISTS tasks_id_seq;
CREATE SEQUENCE IF NOT EXISTS tasks_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS tasks
(
    id bigint DEFAULT nextval('tasks_id_seq'::regclass) NOT NULL,
    title character varying(255) NOT NULL,
    description text NOT NULL,
    discipline_id integer NOT NULL,
    time time NOT NULL,
    experience integer NOT NULL,
    complexity integer NOT NULL,
    answer text NOT NULL,
    CONSTRAINT tasks_pkey PRIMARY KEY (id),
    CONSTRAINT disciplines_fkey FOREIGN KEY (discipline_id)
        REFERENCES disciplines (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS tasks
    OWNER to postgres;

DROP SEQUENCE IF EXISTS course_tasks_id_seq;
CREATE SEQUENCE IF NOT EXISTS course_tasks_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS course_tasks
(
    id bigint DEFAULT nextval('course_tasks_id_seq'::regclass) NOT NULL,
    course_id bigint NOT NULL,
    task_id bigint NOT NULL,
    CONSTRAINT course_tasks_pkey PRIMARY KEY (id),
    CONSTRAINT courses_fkey FOREIGN KEY (course_id)
        REFERENCES courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT tasks_fkey FOREIGN KEY (task_id)
        REFERENCES tasks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT course_tasks_course_id_task_id_key UNIQUE (course_id, task_id)
);

ALTER TABLE IF EXISTS course_tasks
    OWNER to postgres;

DROP SEQUENCE IF EXISTS lesson_tasks_id_seq;
CREATE SEQUENCE IF NOT EXISTS lesson_tasks_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS lesson_tasks
(
    id bigint DEFAULT nextval('lesson_tasks_id_seq'::regclass) NOT NULL,
    lesson_id bigint NOT NULL,
    task_id bigint NOT NULL,
    CONSTRAINT lesson_tasks_pkey PRIMARY KEY (id),
    CONSTRAINT lessons_fkey FOREIGN KEY (lesson_id)
        REFERENCES lessons (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT tasks_fkey FOREIGN KEY (task_id)
        REFERENCES tasks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT lesson_tasks_lesson_id_task_id_key UNIQUE (lesson_id, task_id)
);

ALTER TABLE IF EXISTS lesson_tasks
    OWNER to postgres;

DROP SEQUENCE IF EXISTS tags_id_seq;
CREATE SEQUENCE IF NOT EXISTS tags_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS tags
(
    id integer DEFAULT nextval('tags'::regclass) NOT NULL,
    title character varying(255) NOT NULL,
    CONSTRAINT tags_pkey PRIMARY KEY (id),
    CONSTRAINT tags_title_key UNIQUE (title)
);

ALTER TABLE IF EXISTS tags
    OWNER to postgres;

DROP SEQUENCE IF EXISTS course_tags_id_seq;
CREATE SEQUENCE IF NOT EXISTS course_tags_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS course_tags
(
    id bigint DEFAULT nextval('course_tags_id_seq'::regclass) NOT NULL,
    course_id bigint NOT NULL,
    tag_id integer NOT NULL,
    CONSTRAINT course_tags_pkey PRIMARY KEY (id),
    CONSTRAINT courses_fkey FOREIGN KEY (course_id)
        REFERENCES courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT tags_fkey FOREIGN KEY (tag_id)
        REFERENCES tags (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT course_tags_course_id_tag_id_key UNIQUE (course_id, tag_id)
);

ALTER TABLE IF EXISTS course_tags
    OWNER to postgres;

DROP SEQUENCE IF EXISTS game_methods_id_seq;
CREATE SEQUENCE IF NOT EXISTS game_methods_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS game_methods
(
    id integer DEFAULT nextval('game_methods_id_seq'::regclass) NOT NULL,
    title character varying(255) NOT NULL,
    type character varying(64) NOT NULL,
    CONSTRAINT game_methods_pkey PRIMARY KEY (id),
    CONSTRAINT game_methods_title_key UNIQUE (title)
);

ALTER TABLE IF EXISTS game_methods
    OWNER to postgres;

DROP SEQUENCE IF EXISTS game_method_tags_id_seq;
CREATE SEQUENCE IF NOT EXISTS game_method_tags_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS game_method_tags
(
    id bigint DEFAULT nextval('game_method_tags_id_seq'::regclass) NOT NULL,
    game_method_id bigint NOT NULL,
    tag_id integer NOT NULL,
    CONSTRAINT game_method_tags_pkey PRIMARY KEY (id),
    CONSTRAINT game_methods_fkey FOREIGN KEY (game_method_id)
        REFERENCES game_methods (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT tags_fkey FOREIGN KEY (tag_id)
        REFERENCES tags (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT game_method_tags_game_method_id_tag_id_key UNIQUE (game_method_id, tag_id)
);

ALTER TABLE IF EXISTS game_method_tags
    OWNER to postgres;

DROP SEQUENCE IF EXISTS task_game_methods_id_seq;
CREATE SEQUENCE IF NOT EXISTS task_game_methods_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS task_game_methods
(
    id bigint DEFAULT nextval('task_game_methods_id_seq'::regclass) NOT NULL,
    task_id bigint NOT NULL,
    game_method_id bigint NOT NULL,
    CONSTRAINT task_game_methods_pkey PRIMARY KEY (id),
    CONSTRAINT tasks_fkey FOREIGN KEY (task_id)
        REFERENCES tasks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT game_methods_fkey FOREIGN KEY (game_method_id)
        REFERENCES game_methods (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT task_game_methods_task_id_game_method_id_key UNIQUE (task_id, game_method_id)
);

ALTER TABLE IF EXISTS task_game_methods
    OWNER to postgres;

DROP SEQUENCE IF EXISTS tokens_id_seq;
CREATE SEQUENCE IF NOT EXISTS tokens_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS tokens
(
    id bigint DEFAULT nextval('tokens_id_seq'::regclass) NOT NULL,
    access_token character varying(255) NOT NULL,
    is_logged_out boolean NOT NULL,
    refresh_token character varying(255) NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT tokens_pkey PRIMARY KEY (id),
    CONSTRAINT users_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS tokens
    OWNER to postgres;

DROP SEQUENCE IF EXISTS task_results_id_seq;
CREATE SEQUENCE IF NOT EXISTS task_results_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS task_results
(
    id bigint DEFAULT nextval('task_results_id_seq'::regclass) NOT NULL,
    task_id bigint NOT NULL,
    student_id bigint NOT NULL,
    experience integer NOT NULL,
    time time NOT NULL,
    CONSTRAINT task_results_pkey PRIMARY KEY (id),
    CONSTRAINT tasks_fkey FOREIGN KEY (task_id)
        REFERENCES tasks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT students_fkey FOREIGN KEY (student_id)
        REFERENCES students (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT task_results_task_id_student_id_key UNIQUE (task_id, student_id)
);

ALTER TABLE IF EXISTS task_results
    OWNER to postgres;

DROP SEQUENCE IF EXISTS achievements_id_seq;
CREATE SEQUENCE IF NOT EXISTS achievements_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS achievements
(
    id bigint DEFAULT nextval('achievements_id_seq'::regclass) NOT NULL,
    title character varying(255) NOT NULL,
    required_exp integer NOT NULL,
    CONSTRAINT achievements_pkey PRIMARY KEY (id),
    CONSTRAINT achievements_title_key UNIQUE (title)
);

ALTER TABLE IF EXISTS achievements
    OWNER to postgres;

DROP SEQUENCE IF EXISTS student_achievements_id_seq;
CREATE SEQUENCE IF NOT EXISTS student_achievements_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS student_achievements
(
    id bigint DEFAULT nextval('student_achievements_id_seq'::regclass) NOT NULL,
    student_id bigint NOT NULL,
    achievement_id bigint NOT NULL,
    CONSTRAINT students_achievements_pkey PRIMARY KEY (id),
    CONSTRAINT students_fkey FOREIGN KEY (student_id)
        REFERENCES students (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT achievements_fkey FOREIGN KEY (achievement_id)
        REFERENCES achievements (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT students_achievements_student_id_achievement_id_key UNIQUE (student_id, achievement_id)
);

ALTER TABLE IF EXISTS students_achievements
    OWNER to postgres;

DROP SEQUENCE IF EXISTS teachers_id_seq;
CREATE SEQUENCE IF NOT EXISTS teachers_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS teachers
(
    id bigint DEFAULT nextval('teachers_id_seq'::regclass) NOT NULL,
    user_id bigint NOT NULL,
	wallet numeric(15,2) NOT NULL,
	organisation character varying(255) NOT NULL,
	qualification character varying(255) NOT NULL,
	blog_id bigint NOT NULL,
	bth_date date NOT NULL,
	age integer,
	image_path text,
    CONSTRAINT teachers_pkey PRIMARY KEY (id),
    CONSTRAINT users_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT blogs_fkey FOREIGN KEY (blog_id)
        REFERENCES blogs (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS teachers
    OWNER to postgres;

DROP SEQUENCE IF EXISTS course_teachers_id_seq;
CREATE SEQUENCE IF NOT EXISTS course_teachers_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS course_teachers
(
    id bigint DEFAULT nextval('course_teachers_id_seq'::regclass) NOT NULL,
    course_id bigint NOT NULL,
    teacher_id bigint NOT NULL,
    CONSTRAINT course_teachers_pkey PRIMARY KEY (id),
    CONSTRAINT courses_fkey FOREIGN KEY (course_id)
        REFERENCES courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT teachers_fkey FOREIGN KEY (teacher_id)
        REFERENCES teachers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT course_teachers_course_id_teacher_id_key UNIQUE (course_id, teacher_id)
);

ALTER TABLE IF EXISTS course_teachers
    OWNER to postgres;




CREATE OR REPLACE FUNCTION calculate_student_age()
RETURNS trigger AS '
BEGIN
UPDATE students
SET age = CAST(date_part(''year'', age(NEW.bth_date)) AS integer)
WHERE NEW.id = id;
RETURN NEW;
END; '
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER student_bth_date_updated
AFTER INSERT OR UPDATE OF bth_date
ON students
FOR EACH ROW
EXECUTE PROCEDURE calculate_student_age();

CREATE OR REPLACE FUNCTION calculate_teacher_age()
RETURNS trigger AS '
BEGIN
UPDATE teachers
SET age = CAST(date_part(''year'', age(NEW.bth_date)) AS integer)
WHERE NEW.id = id;
RETURN NEW;
END; '
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER teacher_bth_date_updated
AFTER INSERT OR UPDATE OF bth_date
ON teachers
FOR EACH ROW
EXECUTE PROCEDURE calculate_teacher_age();