package data;

/**
 * This class is the superclass of all tasks that can be stored in Duke.
 */
public abstract class Task {

    protected String description;
    protected boolean isDone;
    protected final String TASK_DONE_NOTATION = ":)";
    protected final String TASK_NOT_DONE_NOTATION = ":(";

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription(){
    return this.description;
    }

    public String getStatusIcon() {
        return (isDone ? "(^u^)" : "('n')"); //return tick or X symbols
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public boolean getIsDone(){
        return this.isDone;
    }

    public String toString() {
        return String.format("[%s]%s", getStatusIcon(), this.description);
    }

    /**
     * This abstract method is overridden in the subclasses of Task.
     * It converts the data for Task objects into a String array.
     * <p></p>
     * This data is used by the Storage class when saving the task into the local save file.
     * <p></p>
     * @return the subclass will return its respective task information
     * @see ToDo
     * @see Event
     * @see Deadline
     */
    public abstract String[] getTaskData();

}
