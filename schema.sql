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
