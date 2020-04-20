/*==============================================================*/
/* Added data: values for presentation                          */
/*==============================================================*/

INSERT INTO roles(role_id, name)
VALUES (1, 'user'),
       (2, 'admin');

INSERT INTO programming_languages(programming_language_id, name)
VALUES (1, 'java19'),
       (2, 'python3'),
       (3, 'c/c++'),
       (4, 'c#-1.2'),
       (5, 'char'),
       (6, 'text'),
       (7, 'scheme'),
       (8, 'java17'),
       (9, 'java15'),
       (10, 'java12'),
       (11, 'java11');

INSERT INTO users(user_id, date_of_birth, email, first_name, last_name, gender, password, role_id)
VALUES (1, '1998-11-27', 'max@gmail.com', 'Max', 'Svynarchuk', 'MALE', '123456', 1);

INSERT INTO courses(course_id, name, user_id)
VALUES (1, 'WEB', 1);

INSERT INTO groups(group_id, name, course_id)
VALUES (1, 'GROUP-1', 1),
       (2, 'GROUP-2', 1),
       (3, 'GROUP-3', 1);

INSERT INTO students(student_id, full_name, vcs_repository_link, user_id)
VALUES (1, 'STUDENT-1', 'https://github.com', 1),
       (2, 'STUDENT-2', 'https://github.com', 1),
       (3, 'STUDENT-3', 'https://github.com', 1),
       (4, 'STUDENT-4', 'https://github.com', 1),
       (5, 'STUDENT-5', 'https://github.com', 1),
       (6, 'STUDENT-6', 'https://github.com', 1),
       (7, 'STUDENT-7', 'https://github.com', 1),
       (8, 'STUDENT-8', 'https://github.com', 1);

INSERT INTO student_group(group_id, student_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (3, 6),
       (3, 7),
       (3, 8),
       (3, 1);

INSERT INTO tasks(task_id, name, repository_prefix_path, course_id)
VALUES (1, 'TAKS-1', '', 1),
       (2, 'TAKS-2', '', 1);

INSERT INTO plagiarism_detection_settings(plagiarism_detection_setting_id, base_code_path, comparison_sensitivity, data_path, minimum_similarity_percent, result_path, type_detection, programming_language_id)
VALUES (1, null, 1, '\', 1, '\', 'GROUP', 1),
       (2, null, 1, '\', 1, '\', 'GROUP', 2),
       (3, null, 1, '\', 1, '\', 'GROUP', 3);

INSERT INTO task_group(group_id, task_id, creation_date, expiry_date, plag_detection_status, plagiarism_detection_result_id, plagiarism_detection_setting_id)
VALUES (1, 1, '2020-04-20 17-16-30',  '2020-04-21 17-16-30', 'PENDING', null, 1),
       (3, 1, '2020-04-20 17-16-30',  '2020-04-21 17-16-30', 'PENDING', null, 2),
       (2, 2, '2020-04-20 17-16-30',  '2020-04-21 17-16-30', 'PENDING', null, 3);