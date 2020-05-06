/*==============================================================*/
/* Added data: values for presentation                          */
/*==============================================================*/

INSERT INTO roles(role_id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO programming_languages(programming_language_id, name, default_comparison_sensitivity)
VALUES (1, 'java19', 9),
       (2, 'python3', 12),
       (3, 'c/c++', 12),
       (4, 'c#-1.2', 8),
       (5, 'char', 10),
       (6, 'text', 5),
       (7, 'scheme', 13),
       (8, 'java17', 9),
       (9, 'java15', 8),
       (10, 'java12', 9),
       (11, 'java11', 9);

INSERT INTO users(user_id, email, first_name, last_name, password, role_id)
VALUES (1, 'max@gmail.com', 'Max', 'Svynarchuk', '123456', 1);

INSERT INTO courses(course_id, name, user_id, creation_date)
VALUES (1, 'WEB', 1, '2020-05-01');

INSERT INTO groups(group_id, name, course_id, creation_date)
VALUES (1, 'GROUP-1', 1, '2020-05-01'),
       (2, 'GROUP-2', 1, '2020-05-01'),
       (3, 'GROUP-3', 1, '2020-05-01');

INSERT INTO students(student_id, full_name, user_id)
VALUES (1, 'STUDENT-1', 1),
       (2, 'STUDENT-2', 1),
       (3, 'STUDENT-3', 1),
       (4, 'STUDENT-4', 1),
       (5, 'STUDENT-5', 1),
       (6, 'STUDENT-6', 1),
       (7, 'STUDENT-7', 1),
       (8, 'STUDENT-8', 1);

INSERT INTO student_group(group_id, student_id, course_id, vcs_repository_url)
VALUES (1, 1, 1,'https://github.com/Muguvara/test_github_api'),
       (1, 2, 1,'https://bitbucket.org/Muguvara/test_api_oauth'),
       (1, 3, 1,'https://github.com/Muguvara/test_github_api'),
       (2, 4, 1,'https://bitbucket.org/Muguvara/test_api_oauth'),
       (2, 5, 1,'https://github.com/Muguvara/test_github_api'),
       (3, 6, 1,'https://github.com/Muguvara/test_github_api'),
       (3, 7, 1,'https://github.com/Muguvara/test_github_api'),
       (3, 8, 1,'https://github.com/Muguvara/test_github_api');

INSERT INTO tasks(task_id, name, repository_prefix_path, course_id)
VALUES (1, 'TAKS-1', 'common/exceptions/', 1),
       (2, 'TAKS-2', 'common/exceptions/security/', 1);

INSERT INTO plagiarism_detection_settings(plagiarism_detection_setting_id, base_code_path, comparison_sensitivity,
                                          data_path, minimum_similarity_percent, result_path, type_detection,
                                          programming_language_id)
VALUES (1, null, 1, 'target/classes/data/repositories/Max/Course-1/course/task-1/', 1, 'target/classes/static/jplag-results/Max/Course-1/course/task-1/', 'COURSE', 1),
       (2, null, 1, 'target/classes/data/repositories/Max/Course-1/group-1/task-1/', 1, 'target/classes/static/jplag-results/Max/Course-1/group-1/task-1/', 'GROUP', 2),
       (3, null, 1, 'target/classes/data/repositories/Max/Course-1/group-1/task-2/', 1, 'target/classes/static/jplag-results/Max/Course-1/group-1/task-2/', 'GROUP', 3);

INSERT INTO task_group(group_id, task_id, creation_date, expiry_date, plag_detection_status,
                       plagiarism_detection_result_id, plagiarism_detection_setting_id)
VALUES (1, 1, '2020-04-20 10:16:30', '2020-04-22 03:00:00', 'PENDING', null, 1),
       (3, 1, '2020-04-20 10:16:30', '2020-04-22 02:00:00', 'PENDING', null, 2),
       (2, 2, '2020-04-20 10:16:30', '2020-04-22 03:35:00', 'PENDING', null, 3);

INSERT INTO access_tokens(token_id, access_token_string, authorization_provider, refresh_token, scope, token_type,
                          user_id)
VALUES (1, '5a230d773f7e938296541ff96e35ea292db01eb3', 'GITHUB', null, 'repo', 'Bearer', 1),
       (2,
        'ZdMr67WWZqAFScheWrMzJCG0be2_SWpKH0Wt8sRCvBdzNVEavKTRS0CqC3-VdUOHO-DzxvRXX06RIOreH7ELchcu0KXidYAkirPx74UvOzANLw4WIvNNwswlF3wUbcYrhEneO_1G4HLj0yE-qBF9',
        'BITBUCKET', 'xhXDcMgBdQ9dBpskT5', 'repository', 'Bearer', 1);