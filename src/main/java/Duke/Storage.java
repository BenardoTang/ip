package Duke;

import data.Deadline;
import data.DukeException;
import data.Event;
import data.Task;
import data.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Storage {

    private static final String FILEPATH = "Duke/duke.txt";

    public static void saveMyTasksToFile(String filePath, List<Task> myTasks) throws IOException {
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

    public static void loadFileToMyTasks(String filePath, List<Task> myTasks) throws FileNotFoundException, DukeException {
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
            if (splitTaskString[1].equals(":)")) {
                loadTaskToList.markAsDone();
            }
            myTasks.add(loadTaskToList);
        }
        System.out.println("Hi user! your previous tasks have been loaded into Duke...");
    }

    public static void autoSaveIntoFile(List<Task> myTasks){
        try {
            saveMyTasksToFile(FILEPATH, myTasks);
        } catch (IOException e) {
            System.out.println("Encountered an error trying to save your task into duke.txt");
        }
    }
}
