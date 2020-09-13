package Duke;

import common.Messages;
import data.DukeException;

import static common.Messages.*;

public class Parser {

    public static final int MAX_SUBSTRING_FIELDS = 2;
    public boolean shouldEndChat = false;
    private TaskList taskList;
    private Messages messageContainer = new Messages();

    public Parser(TaskList taskListInput) {
        this.taskList = taskListInput;
    }

    //command to validate task
    public Command parseCommand(String userInput) throws DukeException {

        Command newCommand;

        String[] tokenizedInput = userInput.split(" ");
        String commandKeyword = tokenizedInput[0];
        switch (commandKeyword.toLowerCase()) {
        case (BYE_COMMAND):
        case (LIST_COMMAND):
            if (commandKeyword.equals(BYE_COMMAND)) {
                shouldEndChat = true;
            }
            newCommand = new Command(commandKeyword);
            break;
        case(DONE_COMMAND):
        case(DELETE_COMMAND):
            try {
                newCommand = new Command(commandKeyword, tokenizedInput[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(TOO_MANY_PARAMETERS);
            }
            break;
        case (TODO_COMMAND):
        case (EVENT_COMMAND):
        case (DEADLINE_COMMAND):
            String[] separatedDescriptionRemarksInput = splitUserInput(userInput);
            newCommand = new Command(commandKeyword, tokenizedInput, separatedDescriptionRemarksInput);
            break;
        default:
            throw new DukeException(INVALID_COMMAND_MESSAGE);
        }
        return newCommand;
    }
    public String[] splitUserInput(String originalInput) throws DukeException{
        String[] returnValue = new String[MAX_SUBSTRING_FIELDS];
        if (originalInput.contains(" /")){
            String[] separatedSections = originalInput.split(" /");
            String commandWord = separatedSections[0].split(" ", 2)[0];
            //todo should not have a remark section
            if (commandWord.toLowerCase().equals(TODO_COMMAND)) {
                throw new DukeException(TOO_MANY_PARAMETERS);
            }

            try {
                String testForMissingRemarks = separatedSections[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(TOO_LITTLE_PARAMETERS);
            }

            // get description part of userInput without the command word
            try{
                returnValue[0] = separatedSections[0].split(" ", 2)[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(TOO_LITTLE_PARAMETERS);
            }

            if (returnValue[0].trim().length() == 0) {
                throw new DukeException(NO_DESCRIPTION);
            }
            // get additional remark part of userInput
            returnValue[1] = separatedSections[1];
            boolean isRemarksEmpty = ((commandWord.toLowerCase().equals(EVENT_COMMAND)
                    || commandWord.toLowerCase().equals(DEADLINE_COMMAND))  && returnValue[1].trim().length() == 0);
            if (isRemarksEmpty){
                throw new DukeException(NO_DESCRIPTION);
            }
            return returnValue;
        } else {
            // get description part of userInput without the command word
            String[] separatedSections = originalInput.split(" ");
            String commandWord = separatedSections[0].split(" ", 2)[0];
            //only the keyword input
            if (Integer.valueOf(separatedSections.length) == Integer.valueOf(1)) {
                throw new DukeException(TOO_LITTLE_PARAMETERS);
            }
            //similar to above, event and deadline should not be missing a description section
            if (commandWord.toLowerCase().equals(EVENT_COMMAND) || commandWord.toLowerCase().equals(DEADLINE_COMMAND)) {
                throw new DukeException(NO_DESCRIPTION);
            }
            returnValue[0] = originalInput.trim().split(" ", 2)[1];

            // remark column is an empty string
            returnValue[1] = "";
            return returnValue;
        }
    }
    public boolean userWantsToLeave(){
        return this.shouldEndChat;
    }
}
