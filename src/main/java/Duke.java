import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    private static final String MESSAGE_BOUNDARY = "____________________________________________________________";
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    //List of tasks/class variables
    private List<Task> myTasks;

    public Duke(){
        this.myTasks = new ArrayList<Task>();
    }

    //Default responses
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
    //Runs when user types done + number
    private void dukeRespondTask(String sampleText , Task userTask ){
        System.out.println(MESSAGE_BOUNDARY);
        System.out.println(sampleText);
        System.out.println(userTask);
        System.out.println(MESSAGE_BOUNDARY);
    }

    private Boolean continueChat(String input){
        return !input.equals("bye"); // returns FALSE if user inputs "bye"
    }
    private Task taskIsDone(String userInput){
        Scanner taskExist = new Scanner(userInput);
        String done = taskExist.next();
        int index = taskExist.nextInt();
        taskExist.close();

        if(index <= this.myTasks.size() && index >0){
            this.myTasks.get(index-1).markAsDone();
            return this.myTasks.get(index-1);
        }

        return null;
    }

    public Boolean giveResponse(String query) {
        if (!continueChat(query)) { // if user said bye
            dukeResponse("Bye, hope to see you soon!");
            return false;
        }
        else if (query.equals("list")) { // if user said list
            dukeResponse("Here are the tasks in your list: " , myTasks);
        }
        else if (query.startsWith("done")) { // if user has completed a task
            Task completedTask = taskIsDone(query);
            if(completedTask==null){
                dukeResponse("Task does not exist sir");
            }
            else{
                dukeRespondTask("Nice! I've marked this task as done:" , completedTask);
            }
        }
        else { // add a task to the list
            Task newItem = new Task(query);
            this.myTasks.add(newItem);
            dukeRespondTask("added: ", newItem);
        }
        return true;
    }


    public void dukeIntro(){
        boolean repeat = true;
        Scanner myscanner = new Scanner(System.in);  // Create a Scanner object
        System.out.print(LOGO);
        dukeResponse("Hello! I'm Duke\n What can I do for you?");
        while(repeat){
            String userQuery = myscanner.nextLine();
            repeat = giveResponse(userQuery);

        }
        myscanner.close();

    }


    public static void main(String[] args) {

        Duke myobj = new Duke();
        myobj.dukeIntro();

    }
}
