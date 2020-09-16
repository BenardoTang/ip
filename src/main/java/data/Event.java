package data;

import java.util.ArrayList;

/**
 * This class represents the EVENT division of Tasks that can be stored in Duke.
 * It contains a description and a venue.
 */
public class Event extends Task {

    private static final int NUMBER_OF_FIELDS_EVENT_FORMAT = 4;// format: E | 0 | project meeting | Aug 6th 2-4pm
    private static final String EVENT_CHARACTER = "E";
    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    public String getAt() {
        return this.at;
    }

    @Override
    public void tasksWithMagicWord(ArrayList<Task> searchResults, String magicKeyword){
        if(this.getDescription().contains(magicKeyword)||this.getAt().contains(magicKeyword)){
            searchResults.add(this);
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }

    /**
     * This method converts the data from a EVENT object into a String array.
     * @return a String array consisting of the Task, its completion status and the description
     */
    @Override
    public String[] getTaskData(){
        String[] taskValues = new String[NUMBER_OF_FIELDS_EVENT_FORMAT];
        taskValues[0] = EVENT_CHARACTER;
        if (this.isDone) {
            taskValues[1] = TASK_DONE_NOTATION;
        } else {
            taskValues[1] = TASK_NOT_DONE_NOTATION;
        }
        taskValues[2] = this.getDescription();
        taskValues[3] = this.getAt();
        return taskValues;
    }
}
