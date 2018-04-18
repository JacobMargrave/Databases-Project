
import java.util.*;
import com.budhash.cliche.Command;

public class list {
	
	/**
	 * View currently active tasks - lists the task IDs, labels, create dates, and due
	 * dates (if assigned).
	 */
	@Command
	public void viewActivetasks() {
		
	}
	
	/**
	 * Add new tasks to the list.
	 * @param task
	 * @param taskID
	 */
	@Command
	public void addNewTask(String task, int taskID) {
		
	}
	
	/**
	 * Associate due dates with tasks.
	 * @param taskID
	 * @param dueDate
	 */
	@Command
	public void addDueDate(int taskID, String dueDate) {
		
	}
	
	/**
	 * Associate tags with tasks.
	 * @param taskID
	 * @param tag
	 * @param tagList
	 */
	@Command
	public void addTag(int taskID, String tag, String[] tagList) {
		
	}
	
	/**
	 * Mark tasks as completed.
	 * @param taskID
	 */
	@Command
	public void finishTask(int taskID) {
		
	}
	
	/**
	 * Mark tasks as cancelled.
	 * @param taskID
	 */
	@Command
	public void cancelTask(int taskID) {
		
	}
	
	/**
	 * Show details for a certain task.
	 * @param taskID
	 */
	@Command
	public void showTaskDetails(int taskID) {
		
	}
	
	/**
	 * Show active tasks that are associated with a certain tag.
	 * @param label
	 */
	@Command
	public void showActiveTag(String label) {
		
	}
	
	/**
	 * Show completed tasks that are associated with a certain tag.
	 * @param label
	 */
	@Command
	public void showCompletedTag (String label) {
		
	}
	
	/**
	 * Show tasks that are overdue.
	 */
	@Command
	public void viewOverdueTasks() {
		
	}
	
	/**
	 * Shows tasks that are due today, or due in the next three days
	 */
	@Command
	public void isItDue() {
		
	}
	
	/**
	 * Change the label of a task
	 * @param taskID
	 * @param newName
	 */
	@Command
	public void renameTask(int taskID, String newName) {
		
	}
	
	/**
	 * Search for tasks by keyword (e.g. search for tasks having the word "project"
	 * in their label.
	 * @param keyword
	 */
	@Command
	public void searchByKeyword(String keyword) {
		
	}
	
	@Command
	public static void main (String[] args) {
		
	}
}
