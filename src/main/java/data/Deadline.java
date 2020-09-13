package data;

import java.util.Scanner;

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

    public static Deadline checkDeadlineError(String in) throws DukeException {
        Scanner query = new Scanner(in);
        if(in.contains("deadline /by")){
            throw new DukeException("Description of a deadline cannot be empty.");
        }
        if(!in.contains("/by")){
            throw new DukeException("Did not detect '/by' from user, please try again.");
        }
        int index = in.indexOf("/by");
        if(in.length() <= (index+3)){
            throw new DukeException("End date of a deadline cannot be empty.");
        }
        in = in.replace("deadline","");
        String[] deadlineSplit = in.split("/by");

        return new Deadline(deadlineSplit[0], deadlineSplit[1]);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

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