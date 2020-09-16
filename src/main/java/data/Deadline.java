package data;

import java.util.ArrayList;

/**
 * This class represents the DEADLINE division of Tasks that can be stored in Duke.
 * It contains a description and a deadline.
 */
public class Deadline extends Task {

    private static final int NUMBER_OF_FIELDS_DEADLINE_FORMAT = 4; // format: D | 0 | return book | June 6th
    private static final String DEADLINE_CHARACTER = "D";
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return this.by;
    }

    @Override
    public void tasksWithMagicWord(ArrayList<Task> searchResults, String magicKeyword){
        if(this.getDescription().contains(magicKeyword)||this.getBy().contains(magicKeyword)){
            searchResults.add(this);
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (" + by + ")";
    }

    /**
     * This method converts the data from a DEADLINE object into a String array.
     * @return a String array consisting of the Task, its completion status and the description
     */
    @Override
    public String[] getTaskData(){
        String[] taskValues = new String[NUMBER_OF_FIELDS_DEADLINE_FORMAT];
        taskValues[0] = DEADLINE_CHARACTER;
        if (this.isDone) {
            taskValues[1] = TASK_DONE_NOTATION;
        } else {
            taskValues[1] = TASK_NOT_DONE_NOTATION;
        }
        taskValues[2] = this.getDescription();
        taskValues[3] = this.getBy();
        return taskValues;
    }
}