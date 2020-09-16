package data;
import java.util.ArrayList;

/**
 * This class represents the TODO division of Tasks that can be stored in Duke.
 * It contains a description
 */
public class ToDo extends Task {

    protected String by;
    public static final int NUMBER_OF_FIELDS_TODO_FORMAT = 3;
    public static final String TODO_CHARACTER = "T";
    public ToDo(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public void tasksWithMagicWord(ArrayList<Task> searchResults, String magicKeyword){
        if(this.getDescription().contains(magicKeyword)){
            searchResults.add(this);
        }
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * This method converts the data from a TODO object into a String array.
     * @return a String array consisting of the Task, its completion status and the description
     */
    @Override
    public String[] getTaskData(){
        String[] taskValues = new String[NUMBER_OF_FIELDS_TODO_FORMAT];
        taskValues[0] = TODO_CHARACTER;
        if (this.isDone) {
            taskValues[1] = TASK_DONE_NOTATION;
        } else {
            taskValues[1] = TASK_NOT_DONE_NOTATION;
        }
        taskValues[2] = this.getDescription();
        return taskValues;
    }
}
