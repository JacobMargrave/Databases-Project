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
1. Export CLASSPATH: in toDoList directory
	export CLASSPATH=.:../lib/cliche-shell-0.9.9-SNAPSHOT.jar:../lib/jsch-0.1.53.jar:../lib/mysql-connector-java-5.1.28.jar:$CLASSPATH

2. COMPILE and RUN:
	* javac List.java 
	* java List &ltBroncoUserid&gt <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>

### Run using JAR
1. RUN in Database_Project_jar directory
	* java -jar Databases-Project2.jar folowed with the same arguments as above.

## Program Design
Due to the complex nature of creating a connection to MYSQL as well as creating shell command templates with Java code written from scratch, we decided to take the easy route and use external executable libraries provided to us in the project description. These libraries are:
* cliche-shell-0.9.9-SNAPSHOT.jar
* jsch-0.1.53.jar
* mysql-connector-java-5.1.28.jar

Together, these libraries enable the easy implementation of the various program commands through the interactive and aid in the connection to the MYSQL database server. 

### The Commands
One of the main goals for this program was to modify information and obtain certain information quickly by automatically executing various SQL operations and queries. This was accomplished with the use of executable commands. The commands that are used within this program, along with each of their functions, are listed below:
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
Testing this program involved plenty of interaction between MYSQL and the command line. Of course, with writing each of the commands for our program, it involved writing concise and correct SQL operations and queries to make sure we received the correct information. In doing so, we were able to obtain information much quicker with our commands then by by manually typing in each query from scratch each time, as well as hone our SQL coding skills in the process. Once we were receiving results that matched our expected results, we double-checked to ensure that we were doing the right thing. 

For example, for the add command, we would run it within the terminal window and then view the database in MYSQL to see if an changes were made to one of our tables. Once we saw a task added to the table, it was a sure sign that we had implemented the command correctly. We followed this practice of testing for each of our commands until we were satisfied with our results.

## Discussion
All in all, this project was a great exercise in writing SQL and executing SQL with the quick entering of commands in a shell program. The hardest part for us, in our opinion, was implementing the initial connection to the MYSQL server, as we weren't too sure at first on how to use the provided libraries as well understand the provided examples. But once we actually looked over the connection examples and understand their functionality, we were able to use these concepts in creating our own connection.

The only other real issues we encountered with this program were minute things associated with making sure that each command works the way it was intended. For example, we encountered a bug within the due method that would cause the program to list overdue tasks as tasks that were "due soon" as the result of there being no lower bound in the SQL query's WHERE clause (which calculated a task's condition of being due soon). Luckily, this was an easy problem to fix.

In the end, this project was not as problematic as it could have been, given that we were familar with SQL and we understood what the program needed to do. It was just a matter of putting it into Java syntax.
