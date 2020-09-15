package Duke;

import common.Messages;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static common.Messages.DUKE_SAYS_HI;
import static common.Messages.ERROR_EMOJI;
import static common.Messages.LOGO;
import static common.Messages.NO_INPUT;
import static common.Messages.SAY_SAYONARA;

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

    public void displayErrorMessage(String errorMessage) {
        System.out.println(ERROR_EMOJI + errorMessage);
    }
    public String getUserCommand() {
        String userInput = in.nextLine();

        //Take out all empty/whitespace lines
        while (isInputEmpty(userInput)) {
            displayErrorMessage(NO_INPUT);
            userInput = in.nextLine();
        }

        return userInput;
    }

    private boolean isInputEmpty(String rawInput) {
        return rawInput.trim().isEmpty();
    }
}
