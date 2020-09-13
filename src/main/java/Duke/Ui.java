package Duke;

import common.Messages;
import data.Task;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static common.Messages.*;

public class Ui {
    private final Scanner in;
    private final PrintStream out;
    private final Messages messageContainer = new Messages();

    public Ui() {
        this(System.in, System.out);
    }
    public Ui(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }
    public void sayIntro(){
        System.out.print(LOGO);
        System.out.println(messageContainer.printResponseWithBorder(DUKE_SAYS_HI));
    }
    public void sayBye(){
        System.out.println(messageContainer.printResponseWithBorder(SAY_SAYONARA));
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
    public void displayErrorMessage(String errorMessage) {
        System.out.println(ERROR_EMOJI + errorMessage);
    }
    public String getUserCommand() {
        String userInput = in.nextLine();

        //silently consume all empty/whitespace lines
        while (isInputEmpty(userInput)) {
            displayErrorMessage(NO_INPUT);
            userInput = in.nextLine();
        }

        return userInput;
    }
    //Respond when user has done a task
    private void dukeRespondTask(String sampleText , Task userTask) {
        System.out.println(MESSAGE_BOUNDARY);
        System.out.println(sampleText);
        System.out.println(userTask);
        System.out.println(MESSAGE_BOUNDARY);
    }
    private boolean isInputEmpty(String rawInput) {
        return rawInput.trim().isEmpty();
    }
}
