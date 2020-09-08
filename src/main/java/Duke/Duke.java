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
    private static final String FILEPATH = "src/main/java/Duke/duke.txt";

    //class variables
    private final List<Task> myTasks;

    //Constructor for class Duke
    public Duke() {
        this.myTasks = new ArrayList<>();
    }

    //Default response template to user
    private void dukePrintTaskList(String sampleText) {
        System.out.println(MESSAGE_BOUNDARY);
        System.out.println(sampleText);
        System.out.println(MESSAGE_BOUNDARY);
    }

    //Print out full task list
    private void dukePrintTaskList(String sampleText , List<Task> listOfTasks) {
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
            //For each new line, add task to List myTasks
            //Process each line first, and construct new Todo/Event/Deadline object
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
            //if task was previously marked done already, make sure to mark it as done when loading to myTasks
            if (splitTaskString[1].equals("1")) {
                loadTaskToList.markAsDone();
            }
            myTasks.add(loadTaskToList);
        }
        System.out.println("Hi user! your previous tasks have been loaded into Duke...");
    }

    private static void autoSaveIntoFile(List<Task> myTasks){
        try {
            saveMyTasksToFile(FILEPATH, myTasks);
        } catch (IOException e) {
            System.out.println("Encountered an error trying to save your task into duke.txt");
        }
    }

    private Task deleteTask(Scanner userInput) throws DukeException {
        if(!userInput.hasNextInt()) {
            throw new DukeException("Task reference number needs to be an integer...");
        }

        int index = userInput.nextInt();
        if(userInput.hasNext()) {
            throw new DukeException("Too many arguments input for the 'delete' command.");
        }
        if(index <= this.myTasks.size() && index >0){
            Task taskRef = this.myTasks.get(index-1);
            this.myTasks.remove(index-1);
            return taskRef;
        }
        throw new DukeException("No such task was found");
    }

    //Query from user.Boolean represents whether Duke should continue the chat.
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
                autoSaveIntoFile(myTasks);
                break;
            case "todo":
                newItem = ToDo.checkToDoError(query);
                autoSaveIntoFile(myTasks);
                break;
            case "event":
                newItem = Event.checkEventError(query);
                autoSaveIntoFile(myTasks);
                break;
            case "list":
                dukePrintTaskList("Here are the tasks in your list: ", myTasks);
                break;
            case "done":
                Task completedTask = taskIsDone(in);
                dukeRespondTask("Nice! I've marked this task as done:", completedTask);
                autoSaveIntoFile(myTasks);
                break;
            case "delete":
                Task deletedTask = deleteTask(in);
                dukeRespondTask("Noted! I've removed this task for you:", deletedTask);
                autoSaveIntoFile(myTasks);
                break;
            case "bye":
                dukePrintTaskList("Bye, hope to see you soon!");
                autoSaveIntoFile(myTasks);
                shouldContinueChat = false;
                break;
            default:
                throw new DukeException("Kid, i don't know what you just said. Try again...");
            }

        } catch (DukeException ex) {
            dukePrintTaskList("☹ OOPS!!! " + ex.getMessage());
        }
        if (newItem != null){
            this.myTasks.add(newItem);
            dukeRespondTask("Hey kid, i've added: ", newItem);
            String plural = ((this.myTasks.size() > 1) ? "s" : "");
            dukePrintTaskList("Now you have " + myTasks.size() + " task" + plural + " in the list.");
        }
        return shouldContinueChat;
    }

    //To run Duke's program
    public void dukeIntro() {
        boolean repeat = true;
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        try {
            loadFileToMyTasks(FILEPATH, myTasks);
        } catch (FileNotFoundException | DukeException e) {
            System.out.println("Can't seem to load from file...Creating a new file duke.txt");
        }
        System.out.print(LOGO);
        dukePrintTaskList("Hello! I'm Duke\nWhat can I do for you?");
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
