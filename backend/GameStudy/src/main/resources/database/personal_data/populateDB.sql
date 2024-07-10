INSERT INTO users (
    name,
	surname,
	fathers_name,
    phone_number,
    email,
    password,
    role) VALUES
('Name1', 'Surname1', 'FathersName1', '88005553531', 'example1@example.com', '123451', 'STUDENT'),
('Name2', 'Surname2', 'FathersName2', '88005553532', 'example2@example.com', '123452', 'STUDENT'),
('Name3', 'Surname3', 'FathersName3', '88005553533', 'example3@example.com', '123453', 'STUDENT'),
('Name4', 'Surname4', 'FathersName4', '88005553534', 'example4@example.com', '123454', 'STUDENT'),
('Name5', 'Surname5', 'FathersName5', '88005553535', 'example5@example.com', '123455', 'STUDENT'),
('Name6', 'Surname6', 'FathersName6', '88005553536', 'example6@example.com', '123456', 'TEACHER'),
('Name7', 'Surname7', 'FathersName7', '88005553530', 'example0@example.com', '123450', 'TEACHER');

INSERT INTO blogs (id,
	title,
	text) VALUES
(1, 'About User1', 'Text'),
(2, 'About User1', 'Text'),
(3, 'About User3', 'Text'),
(4, 'About User4', 'Text'),
(5, 'About User0', 'Text');

INSERT INTO students (
	user_id,
	wallet,
	experience,
	activity,
	blog_id,
	bth_date,
	age) VALUES
(5, 50.00, 340, 70, 1, to_date('7.05.2007', 'DD.MM.YYYY'), NULL),
(3, 100.00, 200, 80, 2, to_date('13.04.2007', 'DD.MM.YYYY'), NULL),
(4, 250.00, 500, 85, 3, to_date('21.01.2006', 'DD.MM.YYYY'), NULL),
(2, 500.00, 1000, 99, 4, to_date('14.07.2007', 'DD.MM.YYYY'), NULL),
(1, 0.00, 20, 15, 5, to_date('25.04.2006', 'DD.MM.YYYY'), NULL);

INSERT INTO courses (id,
	title,
	description,
	creator_uid,
	cost,
	rating,
	amt_passed_users) VALUES
(2, 'Course0', 'Description', 5, 50.00, 8.4, 0),
(1, 'Course1', 'Description', 6, 20.00, 6.7, 2);

INSERT INTO course_students(
    student_id,
    course_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(3, 2),
(4, 1),
(4, 2),
(5, 1),
(5, 2);

INSERT INTO scores (id,
					title,
					student_id,
					course_id,
					experience) VALUES
(10, 'Title0', 5, 2, 20),
(1, 'Title1', 1, 2, 150),
(2, 'Title2', 1, 1, 190),
(3, 'Title3', 2, 2, 200),
(4, 'Title4', 3, 2, 250),
(5, 'Title5', 3, 1, 250),
(6, 'Title6', 4, 2, 250),
(7, 'Title7', 4, 2, 250),
(8, 'Title8', 4, 1, 250),
(9, 'Title9', 4, 1, 250);

INSERT INTO disciplines (id,
	title) VALUES
(2, 'Discipline0'),
(1, 'Discipline1');

INSERT INTO lessons (id,
	title,
	description,
	discipline_id,
	experience,
	complexity) VALUES
(4, 'Lesson0', 'Description', 2, 250, 1),
(1, 'Lesson1', 'Description', 2, 250, 4),
(2, 'Lesson2', 'Description', 1, 270, 2),
(3, 'Lesson3', 'Description', 1, 280, 5);

INSERT INTO course_lessons (id,
	course_id,
	lesson_id) VALUES
(4, 2, 4),
(1, 2, 1),
(2, 1, 2),
(3, 1, 3);

INSERT INTO tasks (id,
	title,
	description,
	discipline_id,
	time,
	experience,
	complexity,
	answer) VALUES
(4, 'Task0', 'Description', 2, '10:00:00', 250, 1, 'Answer'),
(1, 'Task1', 'Description', 2, '20:00:00', 250, 4, 'Answer'),
(2, 'Task2', 'Description', 1, '10:00:00', 270, 2, 'Answer'),
(3, 'Task3', 'Description', 1, '20:00:00', 280, 5, 'Answer');

INSERT INTO course_tasks (
	course_id,
	task_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4);

INSERT INTO lesson_tasks (id,
	lesson_id,
	task_id) VALUES
(4, 4, 4),
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

INSERT INTO tags (id, title) VALUES
(4, 'Tag0'),
(1, 'Tag1'),
(2, 'Tag2'),
(3, 'Tag3');

INSERT INTO course_tags (id,
	course_id,
	tag_id) VALUES
(4, 2, 4),
(1, 2, 1),
(2, 1, 2),
(3, 1, 3);

INSERT INTO game_methods (id, title, type) VALUES
(4, 'Game Method 0', 'Type0'),
(1, 'Game Method 1', 'Type1'),
(2, 'Game Method 2', 'Type2'),
(3, 'Game Method 3', 'Type3');

INSERT INTO game_method_tags (id,
	game_method_id,
	tag_id) VALUES
(8, 4, 4),
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 1),
(5, 4, 2),
(6, 1, 4),
(7, 3, 2);

INSERT INTO task_game_methods (id,
    task_id,
	game_method_id) VALUES
(6, 4, 4),
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 1),
(5, 4, 2);

INSERT INTO task_results (
    task_id,
    student_id,
    experience,
    time) VALUES
(1, 1, 160, '00:05:00'),
(1, 2, 250, '00:15:00'),
(1, 3, 230, '00:16:00'),
(1, 4, 200, '00:13:00'),
(2, 2, 250, '00:10:00'),
(2, 3, 230, '00:11:00'),
(3, 1, 160, '00:17:00');

INSERT INTO achievements (
    title,
    required_exp) VALUES
('Title 1', 100),
('Title 2', 250),
('Title 3', 500),
('Title 4', 1000);

INSERT INTO student_achievements (
    student_id,
	achievement_id) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3);

INSERT INTO teachers (
	user_id,
	wallet,
	organisation,
	qualification,
	blog_id,
	bth_date,
	age) VALUES
(6, 50.00, 'Organisation 1', 'Qualification 1', 1, to_date('7.05.1975', 'DD.MM.YYYY'), NULL),
(7, 100.00, 'Organisation 2', 'Qualification 2', 2, to_date('13.04.1986', 'DD.MM.YYYY'), NULL);

INSERT INTO course_teachers (
    course_id,
	teacher_id) VALUES
(1, 1),
(1, 2),
(2, 1);
