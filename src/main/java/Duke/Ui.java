package Duke;

import common.Messages;
import data.Task;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static common.Messages.DUKE_SAYS_HI;
import static common.Messages.LOGO;

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

}
