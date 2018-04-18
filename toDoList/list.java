
import java.io.IOException;
import java.sql.*;
import java.util.*;
import com.budhash.cliche.Command;
import com.budhash.cliche.ShellFactory;
import com.budhash.cliche.example.HelloWorld;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class list {
	
	/**
	 * View currently active tasks - lists the task IDs, labels, create dates, and due
	 * dates (if assigned).
	 */
	@Command
	public void active() {
		System.out.println("Active");
	}
	
	/**
	 * Add new tasks to the list.
	 * @param task
	 * @param taskID
	 */
	@Command
	public void add(String task, int taskID) {
		System.out.println("Add " + task + " " + taskID);
	}
	
	/**
	 * Associate due dates with tasks.
	 * @param taskID
	 * @param dueDate
	 */
	@Command
	public void due(int taskID, String dueDate) {
		System.out.println("Due " + taskID + " " + dueDate);
	}
	
	/**
	 * Associate tags with tasks.
	 * @param taskID
	 * @param tag
	 */
	@Command
	public void tag(int taskID, String tag) {
		System.out.println("Tag " + taskID + " " + tag);
	}
	
	/**
	 * Mark tasks as completed.
	 * @param taskID
	 */
	@Command
	public void finish(int taskID) {
		System.out.println("Finish " + taskID);
	}
	
	/**
	 * Mark tasks as cancelled.
	 * @param taskID
	 */
	@Command
	public void cancel(int taskID) {
		System.out.println("Cancel " + taskID);
	}
	
	/**
	 * Show details for a certain task.
	 * @param taskID
	 */
	@Command
	public void show(int taskID) {
		System.out.println("Show " + taskID);
	}
	
	/**
	 * Show active tasks that are associated with a certain tag.
	 * @param label
	 */
	@Command
	public void active(String label) {
		System.out.println("Active " + label);
	}
	
	/**
	 * Show completed tasks that are associated with a certain tag.
	 * @param label
	 */
	@Command
	public void completed (String label) {
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
	 * @param taskID
	 * @param newName
	 */
	@Command
	public void rename(int taskID, String newName) {
		System.out.println("Rename " + taskID + " " + newName);
	}
	
	/**
	 * Search for tasks by keyword (e.g. search for tasks having the word "project"
	 * in their label.
	 * @param keyword
	 */
	@Command
	public void search(String keyword) {
		System.out.println("Search " + keyword);
	}


	private static Session doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
	{
		/*This is one of the available choices to connect to mysql
		 * If you think you know another way, you can go ahead*/

		final JSch jsch = new JSch();
		java.util.Properties configuration = new java.util.Properties();
		configuration.put("StrictHostKeyChecking", "no");

		Session session = jsch.getSession( strSshUser, strSshHost, 22 );
		session.setPassword( strSshPassword );

		session.setConfig(configuration);
		session.connect();
		session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
		return session;
	}

	public static void main (String[] args) throws ClassNotFoundException, JSchException, SQLException, IOException {

		if (args.length<5){
			System.out.println("Usage DBConnectTest <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>");
		}
		else{

			//Move connection into list class
			Connection con = null;
			Session session = null;
			Statement stmt = null, stmt2 = null;
			try
			{
				String strSshUser = args[0]; // SSH loging username
				String strSshPassword = args[1]; // SSH login password
				String strSshHost = "onyx.boisestate.edu"; // hostname or ip or SSH server
				int nSshPort = 22; // remote SSH host port number
				String strRemoteHost = "localhost"; // hostname or ip of your database server
				int nLocalPort = 3367;  // local port number use to bind SSH tunnel

				String strDbUser = args[2];                    // database loging username
				String strDbPassword = args[3];                    // database login password
				int nRemotePort = Integer.parseInt(args[4]); // remote port number of your database

				/*
				 * STEP 0
				 * CREATE a SSH session to ONYX
				 *
				 * */
				session = doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);


				/*
				 * STEP 1 and 2
				 * LOAD the Database DRIVER and obtain a CONNECTION
				 *
				 * */
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort, strDbUser, strDbPassword);


				ShellFactory.createConsoleShell("TO-DO", null, new list())
						.commandLoop();

				/*
				 * STEP 3
				 * EXECUTE STATEMENTS (by using Transactions)
				 *
				 * */

				con.setAutoCommit(false);//transaction block starts


				stmt = con.createStatement();

//				String[] data = {"boise", "nampa"};
//				stmt2 = insertLocations(con,data);
//				ResultSet resultSet = stmt.executeQuery("select * from `charity`.`fund`");

				/*TO INSERT INTO TABLES
				 * You can also read from a file and store in a data structure of your choice*/

				con.commit(); //transaction block ends

				System.out.println("Transaction done!");

				/*
				 * STEP 4
				 * Use result sets (tables) to navigate through the results
				 *
				 * */

//				ResultSetMetaData rsmd = resultSet.getMetaData();
//
//				int columnsNumber = rsmd.getColumnCount();
//				while (resultSet.next()) {
//					for (int i = 1; i <= columnsNumber; i++) {
//						if (i > 1) System.out.print(",  ");
//						String columnValue = resultSet.getString(i);
//						System.out.print(columnValue + " " + rsmd.getColumnName(i));
//					}
//					System.out.println(" ");
//				}
			}
			catch( SQLException e )
			{
				System.out.println(e.getMessage());
				con.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction
			}

			finally{

				/*
				 * STEP 5
				 * CLOSE CONNECTION AND SSH SESSION
				 *
				 * */

				if(stmt!=null)
					stmt.close();

				if(stmt2!=null)
					stmt.close();

				con.setAutoCommit(true); // restore dafault mode
				con.close();
				session.disconnect();
			}
		}
	}
}
