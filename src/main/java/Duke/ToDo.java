package Duke;

import java.util.Scanner;

public class ToDo extends Task {

    protected String by;

    public ToDo(String description) {
        super(description);
    }
    public static ToDo checkToDoError(String in) throws DukeException{
        Scanner query = new Scanner(in);
        if(in.trim().matches("todo")){
            throw new DukeException("Description of a task cannot be empty.");
        }
        in = in.replace("todo","");
        return new ToDo(in);
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
