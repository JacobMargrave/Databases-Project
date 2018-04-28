CREATE DATABASE IF NOT EXISTS ToDoList;
USE ToDoList;

CREATE TABLE task (
  task_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  task_label TEXT NOT NULL,
  task_due_date DATE,
  task_create_date TIMESTAMP
);

CREATE TABLE tag (
  tag_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  task_id INTEGER NOT NULL,
  label VARCHAR(200) NOT NULL,

  FOREIGN KEY (task_id) REFERENCES task (task_id),
  INDEX (task_id)
);

CREATE TABLE task_status (
  status_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  task_id INTEGER NOT NULL REFERENCES task,
  status_value VARCHAR(200) NOT NULL,
  status_state VARCHAR(200) NOT NULL,

  FOREIGN KEY (task_id) REFERENCES task (task_id),
  INDEX (task_id)
);

-- BEGIN TABLE task
insert into task (task_label, task_due_data) values ('CS401 Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS410 Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS455 Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS401 Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS401 Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS401 Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS401 Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS401 Final Project', '2018-01-05');
insert into task (task_label, task_due_data) values ('CS401 Final Project', '2018-01-05');

-- BEGIN TABLE tag
insert into tag (task_id, label) values (1, 'School Project');
insert into tag (task_id, label) values (2, 'Group Project');
insert into tag (task_id, label) values (3, 'Team Project');
insert into tag (task_id, label) values (4, 'Final Assignment');
insert into tag (task_id, label) values (5, 'Homework1');
insert into tag (task_id, label) values (6, 'Homework2');
insert into tag (task_id, label) values (7, 'Project1');
insert into tag (task_id, label) values (8, 'Project2');
insert into tag (task_id, label) values (9, 'Project Meeting');
insert into tag (task_id, label) values (10, 'Homework Discussion');

-- BEGIN TABLE tast_status
insert into task_status (task_id, status_value, status_state) values (1, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (2, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (3, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (4, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (5, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (6, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (7, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (8, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (9, 'incomplete', 'active');
insert into task_status (task_id, status_value, status_state) values (10, 'incomplete', 'active');

