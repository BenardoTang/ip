package Duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    private final List<Task> myTasks;

    //Constructor for class Duke.Duke
    public Duke() {
        this.myTasks = new ArrayList<>();
    }

    //Default response template to user
    private void dukeResponse(String sampleText) {
        System.out.println(MESSAGE_BOUNDARY);
        System.out.println(sampleText);
        System.out.println(MESSAGE_BOUNDARY);

    }
    //Print out full task list
    private void dukeResponse(String sampleText , List<Task> listOfTasks) {
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
    private void dukeRespondTask(String sampleText , Task userTask) {
        System.out.println(MESSAGE_BOUNDARY);
        System.out.println(sampleText);
        System.out.println(userTask);
        System.out.println(MESSAGE_BOUNDARY);
    }

    //mark task as done
    private Task taskIsDone(Scanner userInput) throws DukeException {
        if(!userInput.hasNextInt()) {
            throw new DukeException("Duke.Task reference number needs to be an integer...");
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
    private static void saveMyTasksToFile(String filePath, List<Task> myTasks) throws IOException {
        FileWriter fw;
        try{
            fw = new FileWriter(filePath); //overwrite existing file contents when called
        } catch (IOException e) {
            throw new IOException();
        }
        //convert newTaskData to string format for storing in duke.txt
        String newTaskString;
        //boolean hasDescription = false;

        for (int i=0; i < myTasks.size(); i++) {
            Task newTaskData = myTasks.get(i);
            if (newTaskData instanceof ToDo) {
                //Format of todo is different with 3 fields, while Deadline/Event has 4
                newTaskString = newTaskData.getTaskData()[0] + " | " + newTaskData.getTaskData()[1] + " | "
                        + newTaskData.getTaskData()[2] + System.lineSeparator();
            } else {
                newTaskString = newTaskData.getTaskData()[0] + " | " + newTaskData.getTaskData()[1] + " | "
                        + newTaskData.getTaskData()[2] + " | " + newTaskData.getTaskData()[3] + System.lineSeparator();
            }
            fw.write(newTaskString);

        }
        fw.close();
    }
    private static void loadFileToMyTasks(String filePath, List<Task> myTasks) throws FileNotFoundException, DukeException {
        File loadingFile = new File(filePath);
        if (!loadingFile.exists()) {
            throw new FileNotFoundException();
        }
        Scanner fileContent = new Scanner(loadingFile);
        while (fileContent.hasNext()) {
            //add task (each line) to ArrayList taskList
            //1. process each line first, construct new Todo/Event/Deadline object
            String taskString = fileContent.nextLine();
            String[] splitTaskString = taskString.trim().split(" \\| ");
            Task loadTaskToList;
            switch(splitTaskString[0].toUpperCase()) {
            case ("T"):
                loadTaskToList = new ToDo(splitTaskString[2]);
                break;
            case ("E"):
                loadTaskToList = new Event(splitTaskString[2], splitTaskString[3]);
                break;
            case ("D"):
                loadTaskToList = new Deadline(splitTaskString[2], splitTaskString[3]);
                break;
            default:
                throw new DukeException("There seems to be an error loading a task, sorry...");
            }
            //if task was previously marked done already, make sure to mark it as done when loading to taskList
            if (splitTaskString[1].equals("1")) {
                loadTaskToList.markAsDone();
            }
            myTasks.add(loadTaskToList);
        }
        System.out.println("Hi user! your previous tasks have been loaded into Duke...");
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
            switch (userCommand){
            case "deadline":
                newItem = Deadline.checkDeadlineError(query);
                try {
                    saveMyTasksToFile("src/main/java/Duke/duke.txt", myTasks);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "todo":
                newItem = ToDo.checkToDoError(query);
                try {
                    saveMyTasksToFile("src/main/java/Duke/duke.txt", myTasks);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "event":
                newItem = Event.checkEventError(query);
                try {
                    saveMyTasksToFile("src/main/java/Duke/duke.txt", myTasks);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "list":
                dukeResponse("Here are the tasks in your list: ", myTasks);
                break;
            case "done":
                Task completedTask = taskIsDone(in);
                dukeRespondTask("Nice! I've marked this task as done:", completedTask);
                try {
                    saveMyTasksToFile("src/main/java/Duke/duke.txt", myTasks);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "bye":
                dukeResponse("Bye, hope to see you soon!");
                try {
                    saveMyTasksToFile("src/main/java/Duke/duke.txt", myTasks);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                shouldContinueChat = false;
                break;
            default:
                throw new DukeException("Kid, i don't know what you just said. Try again...");
            }

        } catch (DukeException ex) {
            dukeResponse("☹ OOPS!!! " + ex.getMessage());
        }
        if (newItem != null){
            this.myTasks.add(newItem);
            dukeRespondTask("Hey kid, i've added: ", newItem);
            String plural = ((this.myTasks.size() > 1) ? "s" : "");
            dukeResponse("Now you have " + myTasks.size() + " task" + plural + " in the list.");
        }

        return shouldContinueChat;
    }
    //To run Duke's program
    public void dukeIntro() {
        boolean repeat = true;
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        try {
            loadFileToMyTasks("src/main/java/Duke/duke.txt", myTasks);
        } catch (FileNotFoundException | DukeException e) {
            System.out.println("Can't seem to load from file...Creating a new file duke.txt");
            //e.printStackTrace();
        }
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
