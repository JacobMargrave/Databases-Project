
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.budhash.cliche.Command;
import com.budhash.cliche.Param;
import com.budhash.cliche.Shell;
import com.budhash.cliche.ShellFactory;
import com.budhash.cliche.example.HelloWorld;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class List {

	// Move connection into list class
	private Connection con = null;
	private Session session = null;
	private Statement stmt = null;

	/**
	 * View currently active tasks - lists the task IDs, labels, create dates, and
	 * due dates (if assigned).
	 */
	@Command
	public void active() throws SQLException {
		boolean existActiveTasks = true;
		
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			String showActiveTasks = "SELECT * FROM ToDoList.task, ToDoList.task_status WHERE ToDoList.task.task_ID = ToDoList.task_status.task_id AND status_state = 'active'";
			ResultSet resultSet = stmt.executeQuery(showActiveTasks);
			
			while(resultSet.next()) {
				
				existActiveTasks = false;
				int task_id = resultSet.getInt("task_id");
				String task_label= resultSet.getString("task_label");
				String due_date = resultSet.getString("task_due_date");
				String task_create_date = resultSet.getString("task_create_date");
				System.out.println("Task ID: " + task_id + ", Label: " + task_label + 
						", Due Date: " + due_date + ", Task Create Date: " + task_create_date);
			}
			if(existActiveTasks) {
				System.out.println("There are currently no active tasks.");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			// con.setAutoCommit(true);
			con.rollback();
		}
		//System.out.println("Active");
	}

	/**
	 * Add new tasks to the list.
	 * 
	 * @param taskLabel
	 * @throws SQLException
	 */
	@Command
	public void add(@Param(name = "taskLabel", description = "task label") String... taskLabel) throws SQLException {

		int id = -1;
		String task = "";

		for (String s : taskLabel) {
			task += s;
			task += " ";
		}
		task.trim();

		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			String insertTask = "INSERT INTO ToDoList.task (task_label) VALUES ('" + task + "')";

			String taskID = "SELECT LAST_INSERT_ID()";

			stmt.executeUpdate(insertTask);

			ResultSet resultSet = stmt.executeQuery(taskID);

			if (resultSet.next()) {
				id = resultSet.getInt(1);

			}

			String taskStatus = "INSERT INTO ToDoList.task_status (task_id, status_value, status_state) VALUES ('" + id
					+ "','incomplete', 'active')";
			stmt.executeUpdate(taskStatus);

			System.out.println("Task ID: " + id);

			con.commit();
			// con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			// con.setAutoCommit(true);
			con.rollback();
		}
	}

	/**
	 * Associate due dates with tasks.
	 * 
	 * @param taskID
	 * @param dueDate
	 */
	@Command
	public void due(int taskID, String dueDate) throws SQLException {

		if (checkDateFormat(dueDate)) {

			try {
				con.setAutoCommit(false);

				stmt = con.createStatement();

				String updateDueDate = "UPDATE ToDoList.task SET task_due_date = '" + dueDate + "' WHERE task_id = "
						+ taskID;

				stmt.executeUpdate(updateDueDate);

				con.commit();
				System.out.println("Due " + taskID + " " + dueDate);
				// con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				// con.setAutoCommit(true);
				con.rollback();
			}
		} else {
			System.out.println("Check date format (yyyy-mm-dd");
		}
	}

	/**
	 * Associate tags with tasks.
	 * 
	 * @param taskID
	 * @param tag
	 */
	@Command
	public void tag(int taskID, @Param(name = "tag", description = "task tag") String... tag) throws SQLException {

		String taskTag = "";

		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();

			for (String str : tag) {
				String insertTag = "INSERT INTO ToDoList.tag (task_id, label) VALUES ('" + taskID + "','" + str + "')";
				stmt.executeUpdate(insertTag);
				taskTag += str;
				taskTag += " ";
			}

			con.commit();
			// con.setAutoCommit(true);
			System.out.println("Tag " + taskID + " " + taskTag.trim());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			// con.setAutoCommit(true);
			con.rollback();
		}
	}

	/**
	 * Mark tasks as completed.
	 * 
	 * @param taskID
	 */
	@Command
	public void finish(int taskID) throws SQLException {
//		System.out.println("Finish " + taskID);

		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();

			String updateDueDate = "UPDATE ToDoList.task_status SET status_value = 'completed' WHERE task_id = " + taskID;

			stmt.executeUpdate(updateDueDate);

			con.commit();
			System.out.println("Task " + taskID + " is completed");
//			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
//			con.setAutoCommit(true);
			con.rollback();
		}
	}

	/**
	 * Mark tasks as cancelled.
	 * 
	 * @param taskID
	 */
	@Command
	public void cancel(int taskID) throws SQLException{
		System.out.println("Cancel " + taskID);

		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();

			String updateDueDate = "UPDATE ToDoList.task_status SET status_value = 'cancel', status_state = 'inactive' WHERE task_id = " + taskID;

			stmt.executeUpdate(updateDueDate);

			con.commit();
			System.out.println("Task " + taskID + " is cancel");
//			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
//			con.setAutoCommit(true);
			con.rollback();
		}
	}

	/**
	 * Show details for a certain task.
	 * 
	 * @param taskID
	 */
	@Command
	public void show(int taskID) throws SQLException{
		System.out.println("Show " + taskID);
		boolean resultExist = true;
		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();

			String searchTask = "SELECT * FROM ToDoList.task, ToDoList.task_status WHERE task.task_id = " + taskID + "AND" ;

			ResultSet resultSet = stmt.executeQuery(searchTask);

			while (resultSet.next()){
				resultExist = false;
				int id = resultSet.getInt("task_id");
				String label = resultSet.getString("task_label");
				String date = resultSet.getString("task_due_date");
				String timeStamp = resultSet.getString("task_create_date");

				System.out.println("ID: " + id + ", Label: " + label + ", Due Date: " + date + ", TimeStamp " + timeStamp);
			}

			if (resultExist){
				System.out.println("No task with the ID " + taskID);
			}

			con.commit();
//			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
//			con.setAutoCommit(true);
			con.rollback();
		}
	}

	/**
	 * Show active tasks that are associated with a certain tag.
	 * 
	 * @param label
	 */
	@Command
	public void active(String label) {
		System.out.println("Active " + label);
	}

	/**
	 * Show completed tasks that are associated with a certain tag.
	 * 
	 * @param label
	 */
	@Command
	public void completed(String label) {
		System.out.println("Completed " + label);
	}

	/**
	 * Show tasks that are overdue.
	 */
	@Command
	public void overdue() {
		System.out.println("Overdue");
	}

	/**
	 * Shows tasks that are due today, or due in the next three days
	 */
	@Command
	public void due() {
		System.out.println("Due");
	}

	/**
	 * Change the label of a task
	 * 
	 * @param taskID
	 * @param newName
	 */
	@Command
	public void rename(int taskID, @Param(name = "newName", description = "task name") String... newName)
			throws SQLException {
		// System.out.println("Rename " + taskID + " " + newName);

		String taskName = "";
		for (String s : newName) {
			taskName += s;
			taskName += " ";
		}
		taskName.trim();

		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();

			String updateDueDate = "UPDATE ToDoList.task SET task_label = '" + taskName + "' WHERE task_id = " + taskID;

			stmt.executeUpdate(updateDueDate);

			con.commit();
			System.out.println("Task rename " + taskID + " " + taskName);
			// con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			// con.setAutoCommit(true);
			con.rollback();
		}
	}

	/**
	 * Search for tasks by keyword (e.g. search for tasks having the word "project"
	 * in their label.
	 * 
	 * @param keyword
	 */
	@Command
	public void search(String keyword) throws SQLException{
		boolean resultExist = true;

//		System.out.println("Search " + keyword);
		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();

			String searchTask = "SELECT * FROM ToDoList.task WHERE task_label LIKE '%" + keyword + "%'";

			ResultSet resultSet = stmt.executeQuery(searchTask);

			while (resultSet.next()){
				resultExist = false;
				int id = resultSet.getInt("task_id");
				String label = resultSet.getString("task_label");
				String date = resultSet.getString("task_due_date");
				String timeStamp = resultSet.getString("task_create_date");

				System.out.println("ID: " + id + ", Label: " + label + ", Due Date: " + date + ", TimeStamp " + timeStamp);
			}

			if (resultExist){
				System.out.println("No task with the keyword " + keyword);
			}

			con.commit();
//			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
//			con.setAutoCommit(true);
			con.rollback();
		}
	}

	@Command
	public void help() {
		System.out.println("Help");
	}

	public void setupDatabaseConnection(String sshUser, String sshPassword, String dbUser, String dbPassword,
			String remotePort) {

		String strSshUser = sshUser; // SSH loging username
		String strSshPassword = sshPassword; // SSH login password
		String strSshHost = "onyx.boisestate.edu"; // hostname or ip or SSH server
		int nSshPort = 22; // remote SSH host port number
		String strRemoteHost = "localhost"; // hostname or ip of your database server
		int nLocalPort = 3367; // local port number use to bind SSH tunnel

		String strDbUser = dbUser; // database loging username
		String strDbPassword = dbPassword; // database login password
		int nRemotePort = Integer.parseInt(remotePort); // remote port number of your database

		try {
			/*
			 * STEP 0 CREATE a SSH session to ONYX
			 *
			 */
			session = doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort,
					nRemotePort);

			/*
			 * STEP 1 and 2 LOAD the Database DRIVER and obtain a CONNECTION
			 *
			 */
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:" + nLocalPort, strDbUser, strDbPassword);

		} catch (JSchException e) {
			e.printStackTrace();
			error();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			error();
		} catch (SQLException e) {
			e.printStackTrace();
			error();
		}
	}

	public void createTables() throws SQLException {
		try {
			con.setAutoCommit(false);

			stmt = con.createStatement();

			String createDatabase = "CREATE DATABASE IF NOT EXISTS ToDoList";

			String useDatabase = "USE ToDoList";

			String taskTable = "CREATE TABLE task (task_id INTEGER PRIMARY KEY AUTO_INCREMENT,"
					+ "task_label TEXT NOT NULL," + "task_due_date DATE," + "task_create_date TIMESTAMP" + ");";

			String tagTable = "CREATE TABLE tag (tag_id INTEGER PRIMARY KEY AUTO_INCREMENT,"
					+ "task_id INTEGER NOT NULL," + "label VARCHAR(200) NOT NULL,"
					+ "FOREIGN KEY (task_id) REFERENCES task (task_id)," + "INDEX (task_id)" + ");";

			String task_statusTable = "CREATE TABLE task_status (status_id INTEGER PRIMARY KEY AUTO_INCREMENT,"
					+ "task_id INTEGER NOT NULL REFERENCES task," + "status_value VARCHAR(200) NOT NULL,"
					+ "status_state VARCHAR(200) NOT NULL," + "FOREIGN KEY (task_id) REFERENCES task (task_id),"
					+ "INDEX (task_id)" + ");";

			if (!dbExist()) {
				System.out.println("Creating Database---\n");
				stmt.executeUpdate(createDatabase);
				stmt.executeUpdate(useDatabase);
				stmt.executeUpdate(taskTable);
				stmt.executeUpdate(task_statusTable);
				stmt.executeUpdate(tagTable);
			}

			con.commit();
			// con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			// con.setAutoCommit(true);
			con.rollback();
		}
	}

	private boolean dbExist() {
		if (con != null) {

			try {
				ResultSet resultSet = con.getMetaData().getCatalogs();

				while (resultSet.next()) {
					String catalogs = resultSet.getString(1);

					if ("ToDoList".equals(catalogs)) {
						// System.out.println("The database exist");
						return true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// System.out.println("The database does not exist");
		return false;
	}

	private boolean checkDateFormat(String dueDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			dateFormat.parse(dueDate);
			return true;
		} catch (ParseException e) {
			// e.printStackTrace();
			return false;
		}
	}

	public void closeDBconnectionAndSShsession() {

		try {

			if (con != null) {
				con.setAutoCommit(true);
				con.close();
			}

			if (session != null) {
				session.disconnect();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Session doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort,
			String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
		/*
		 * This is one of the available choices to connect to mysql If you think you
		 * know another way, you can go ahead
		 */

		final JSch jsch = new JSch();
		java.util.Properties configuration = new java.util.Properties();
		configuration.put("StrictHostKeyChecking", "no");

		Session session = jsch.getSession(strSshUser, strSshHost, 22);
		session.setPassword(strSshPassword);

		session.setConfig(configuration);
		session.connect();
		session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
		return session;
	}

	private void error() {
		closeDBconnectionAndSShsession();
		System.out.println("Check Connection Arguments or external jars");
		System.exit(1);
	}

	public static void main(String[] args) throws SQLException {

		if (args.length < 5) {
			System.out.println(
					"Usage DBConnectTest <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>");
		} else {

			String strSshUser = args[0]; // SSH loging username
			String strSshPassword = args[1]; // SSH login password
			String strDbUser = args[2]; // database loging username
			String strDbPassword = args[3]; // database login password
			String nRemotePort = args[4]; // remote port number of your database

			List list = new List();

			list.setupDatabaseConnection(strSshUser, strSshPassword, strDbUser, strDbPassword, nRemotePort);

			list.createTables();

			try {
				Shell shell = ShellFactory.createConsoleShell("TO-DO",
						"TO-DO List...\n" + "Enter ?list to list available commands.\n", list);
				shell.commandLoop();
			} catch (IOException e) {
				// System.out.println("Not a command");
				e.printStackTrace();
			}

			list.closeDBconnectionAndSShsession();

		}
	}
}
