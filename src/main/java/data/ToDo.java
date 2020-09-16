package data;

import java.util.ArrayList;

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
