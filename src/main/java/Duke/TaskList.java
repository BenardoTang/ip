package Duke;

import data.Task;

import java.util.ArrayList;

/**
 * This class is a list of {@link Task} objects.
 * <p></p>
 * @see ArrayList
 */
public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {
    }

    public TaskList(ArrayList<Task> taskListFromSave) {
        this.tasks = taskListFromSave;
    }

    public int getTaskCount() {
        return this.tasks.size();
    }

    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

    public boolean getTaskDoneStatus(int taskNumber) {
        return this.tasks.get(taskNumber).getIsDone();
    }

    public void markTaskAsDone(int taskNumber) {
        this.tasks.get(taskNumber).markAsDone();
    }

    public String getTaskDescription(int taskNumber) {
        return this.tasks.get(taskNumber).getDescription();
    }

    public String getTaskStatusIcon(int taskNumber) {
        return this.tasks.get(taskNumber).getStatusIcon();
    }

    public void addTask(Task taskToAdd) {
        this.tasks.add(taskToAdd);
    }

    public Task deleteTask(int taskNumber) {
        return this.tasks.remove(taskNumber);
    }
}
