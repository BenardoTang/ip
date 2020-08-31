import java.util.Scanner;

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }
    public static Deadline checkDeadlineError(String in) throws DukeException{
        Scanner query = new Scanner(in);
        if(!query.hasNext()){
            throw new DukeException("Description of a deadline cannot be empty.");
        }
        if(!in.contains("/by")){
            throw new DukeException("Did not detect '/by' from user, please try again.");
        }
        int index = in.indexOf("/by");
        if(in.length() <= (index+3)){
            throw new DukeException("End date of a deadline cannot be empty.");
        }
        String[] deadlineSplit = in.split("/by");

        return new Deadline(deadlineSplit[0], deadlineSplit[1]);
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}