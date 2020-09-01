package Duke;

import java.util.Scanner;

public class Event extends Task{

    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }
    public static Event checkEventError(String in) throws DukeException{
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
        String[] eventSplit = in.split("/at");

        return new Event(eventSplit[0], eventSplit[1]);
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }
}
