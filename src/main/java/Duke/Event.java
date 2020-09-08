package Duke;

import java.util.Scanner;

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
    public static Event checkEventError(String in) throws DukeException {
        Scanner query = new Scanner(in);
        if(!query.hasNext()){
            throw new DukeException("Description of an Duke.Event cannot be empty.");
        }
        if(!in.contains("/at")){
            throw new DukeException("Did not detect '/at' from user, please try again.");
        }
        int index = in.indexOf("/at");
        if(in.length() <= (index+3)){
            throw new DukeException("Venue of an Duke.Event cannot be empty.");
        }
        in = in.replace("event","");
        String[] eventSplit = in.split("/at");

        return new Event(eventSplit[0], eventSplit[1]);
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }

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
