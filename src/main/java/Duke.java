import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    //Constants
    private static final String MESSAGE_BOUNDARY = "____________________________________________________________";
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    //class variables
    private List<Task> myTasks;

    //Constructor for class Duke
    public Duke(){
        this.myTasks = new ArrayList<>();
    }

    //Default response template to user
    private void dukeResponse(String sampleText){
        System.out.println(MESSAGE_BOUNDARY);
        System.out.println(sampleText);
        System.out.println(MESSAGE_BOUNDARY);

    }
    //Print out full task list
    private void dukeResponse(String sampleText , List<Task> listOfTasks ){
        System.out.println(MESSAGE_BOUNDARY);
        System.out.println(sampleText);
        int index = 0;
        for(Task t:listOfTasks){
            index +=1;
            System.out.println(String.format("%d",index) +". "+ t);
        }
        System.out.println(MESSAGE_BOUNDARY);
    }
    //Respond when user has done a task
    private void dukeRespondTask(String sampleText , Task userTask ){
        System.out.println(MESSAGE_BOUNDARY);
        System.out.println(sampleText);
        System.out.println(userTask);
        System.out.println(MESSAGE_BOUNDARY);
    }

    //mark task as done
    private Task taskIsDone(Scanner userInput) throws DukeException {
        //Scanner taskExist = new Scanner(userInput);
        //String done = taskExist.next();
        //taskExist.close();

        if(!userInput.hasNextInt()) {
            throw new DukeException("Task reference number needs to be an integer...");
        }

        int index = userInput.nextInt();
        if(userInput.hasNext()) {
            throw new DukeException("Too many arguments input for the 'mark as done' comment.");
        }
        if(index <= this.myTasks.size() && index >0){
            this.myTasks.get(index-1).markAsDone();
            return this.myTasks.get(index-1);
        }

        throw new DukeException("No such task was found");

    }

    //Query from user.Boolean represents whetherDuke should continue the chat.
    public Boolean shouldGiveResponse(String query) {
        boolean shouldContinueChat = true;
        Scanner in = new Scanner(query);
        Task newItem = null;


        //parse user query
        try {
            if(!in.hasNext()){
                throw new DukeException("Type something man...");
            }
            String userCommand = in.next();
            switch (userCommand) {
            case "deadline":
                newItem = Deadline.checkDeadlineError(query);
                break;
            case "todo":
                newItem = new ToDo(query);
                break;
            case "event":
                newItem = Event.checkEventError(query);
                break;
            case "list":
                dukeResponse("Here are the tasks in your list: ", myTasks);
                break;
            case "done":
                Task completedTask = taskIsDone(in);
                dukeRespondTask("Nice! I've marked this task as done:", completedTask);
                break;
            case "bye":
                dukeResponse("Bye, hope to see you soon!");
                shouldContinueChat = false;
                break;
            default:
                throw new DukeException("Kid, i don't know what you just said. Try again...");
            }

        } catch (DukeException ex) {
            dukeResponse("☹ OOPS!!! " + ex.getMessage());
        }
        if (newItem != null) {
            this.myTasks.add(newItem);
            dukeRespondTask("Hey kid, i've added: ", newItem);
            String plural = ((this.myTasks.size() > 1) ? "s" : "");
            dukeResponse("Now you have " + myTasks.size() + " task" + plural + " in the list.");
        }

        return shouldContinueChat;
    }
    //To run Duke/s program
    public void dukeIntro(){
        boolean repeat = true;
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.print(LOGO);
        dukeResponse("Hello! I'm Duke\n What can I do for you?");
        while(repeat){
            String userQuery = scanner.nextLine();
            repeat = shouldGiveResponse(userQuery);

        }
        scanner.close();

    }


    public static void main(String[] args) {
        Duke myObj = new Duke();
        myObj.dukeIntro();

    }
}
