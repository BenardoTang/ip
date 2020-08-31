import java.util.Scanner;

public class ToDo extends Task{

    protected String by;

    public ToDo(String description) {
        super(description);
    }
    public static ToDo checkToDoError(String in) throws DukeException{
        Scanner query = new Scanner(in);
        if(!query.hasNext()){
            throw new DukeException("Description of a task cannot be empty.");
        }
        return new ToDo(in);
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
