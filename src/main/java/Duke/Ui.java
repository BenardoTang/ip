package Duke;

import common.Messages;

import java.io.InputStream;
import java.util.Scanner;

import static common.Messages.DUKE_SAYS_HI;
import static common.Messages.ERROR_EMOJI;
import static common.Messages.LOGO;
import static common.Messages.NO_INPUT;
import static common.Messages.SAY_SAYONARA;

/**
 * This class represents the user interaction with Duke.
 * It contains methods to take in user input and display messages.
 */
public class Ui {
    private final Scanner in;
    private final Messages messageContainer = new Messages();

    public Ui() {
        this(System.in);
    }
    public Ui(InputStream in) {
        this.in = new Scanner(in);
    }

    /**
     * This method greets the user on startup.
     */
    public void sayIntro(){
        System.out.print(LOGO);
        System.out.println(messageContainer.printResponseWithBorder(DUKE_SAYS_HI));
    }

    /**
     * This method says goodbye to the user on program termination.
     */
    public void sayBye(){
        System.out.println(messageContainer.printResponseWithBorder(SAY_SAYONARA));
    }

    /**
     * This methods prints out the message input.
     * @param errorMessage the message to be printed
     */
    public void displayErrorMessage(String errorMessage) {
        System.out.println(ERROR_EMOJI + errorMessage);
    }

    /**
     * This method collects input from the user.
     * <p></p>
     * It runs a loop that continues if the user provides an empty input.
     * <p></p>
     * It only breaks when the user provides a non-empty command
     * @return the command given as a String.
     */
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
