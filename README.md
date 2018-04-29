****************
* Final Project
* CS 410: Databases
* 04/29/2018
* Emmanuel Massaquoi and Jacob Margrave
**************** 

# Databases-Project

## Overview
This program implements a database-driven to-do list manager in Java that uses a command-line application to create, make changes and/or view certain characteristics of a to-do list where information about each created task, including labels, due dates, etc., are stored in a database that the program automatically creates upon the initial running.  

## Compiling and Running

## Program Design
Due to the complex nature of creating a connection to MYSQL as well as creating shell command templates with Java code written from scratch, we decided to take the easy route and use external executable libraries provided to us in the project description. These libraries are:
* cliche-shell-0.9.9-SNAPSHOT.jar
* jsch-0.1.53.jar
* mysql-connector-java-5.1.28.jar

Together, these libraries enable the easy implementation of the various program commands through the interactive and aid in the connection to the MYSQL database server. 

### The Commands
One of the main goals for this program was to modify information and obtain certain information quickly by automatically executing SQL code. The commands that are able to be executed within the program to manage the to-do list database, as well as each of their functions, are listed below:
* *active* - View currently active tasks - lists the task IDs, labels, create dates, and due dates (if assigned).
* *add(taskLabel)* - Add new tasks to the list with a given label specified by the user.
* *due(taskID, dueDate)* - Assigns a due date to a certain task with its ID specified by the user.
* *tag(taskID, tag)* - Assigns a tag to a certain task with its specified ID. Think of it as assigning the task to a certain "category" of task.
* *finish(taskID)* - Marks a certain task as completed.
* *cancel(taskID)* - Marks a certain task as cancelled.
* *show(taskID)* - Shows details for a certain task.
* *active(label)* - Shows all tasks that are associated with a certain tag, or category.
* *completed(label)* - Shows all completed tasks that are associated with a certain tag, or category.
* *overdue* - Shows all incomplete tasks with due dates that are overdue.
* *due(due)* - Shows all imcomplete tasks that are due on the current date, or in the next three days. The user can specify by either typing *due today* or *due soon*.
* *rename(taskID, newName)* - Changes the name of a certain task.
* *search(keyword)* - Used for searching for tasks with a certain keyword (e.g. To search for tasks having the word "project" in their label, the user would type *search project*.)

## Testing
Testing this program involved plenty of interaction between MYSQL and the command line. For example, for the add command, we would run it within the terminal window and then view the database in MYSQL to see if an changes were made to one of our tables. Once we saw a task added to the table, it was a sure sign that we had implemented the command correctly.

Of course, with writing each of the commands for our program, it involved writing concise and correct SQL queries to make sure we received the correct information. In doing so, we were able to obtain information much quicker with our commands then by by manually typing in each query from scratch each time, as well as hone our SQL coding skills in the process. 

## Discussion

