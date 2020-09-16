package Duke;

import data.DukeException;

import static common.Messages.BYE_COMMAND;
import static common.Messages.DEADLINE_COMMAND;
import static common.Messages.DELETE_COMMAND;
import static common.Messages.DONE_COMMAND;
import static common.Messages.EVENT_COMMAND;
import static common.Messages.FIND_COMMAND;
import static common.Messages.INVALID_COMMAND_MESSAGE;
import static common.Messages.LIST_COMMAND;
import static common.Messages.NO_DESCRIPTION;
import static common.Messages.TODO_COMMAND;
import static common.Messages.TOO_LITTLE_PARAMETERS;
import static common.Messages.TOO_MANY_PARAMETERS;

public class Parser {

    public static final int MAX_SUBSTRING_FIELDS = 2;
    public boolean shouldEndChat = false;

    //command to validate task
    public Command parseCommand(String userInput) throws DukeException {

        Command newCommand;

        String[] tokenizedInput = userInput.split(" ");
        String commandKeyword = tokenizedInput[0];
        String[] resolveInputIntoDescriptions;
        switch (commandKeyword.toLowerCase()) {
        case (BYE_COMMAND):
        case (LIST_COMMAND):
            if (commandKeyword.equals(BYE_COMMAND)) {
                shouldEndChat = true;
            }
            newCommand = new Command(commandKeyword);
            break;
        case(DONE_COMMAND):
        case(FIND_COMMAND):
        case(DELETE_COMMAND):
            try {
                newCommand = new Command(commandKeyword, tokenizedInput[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(TOO_MANY_PARAMETERS);
            }
            break;
        case (TODO_COMMAND):
            resolveInputIntoDescriptions = splitToDoInput(userInput);
            newCommand = new Command(commandKeyword, tokenizedInput, resolveInputIntoDescriptions);
            break;
        case (EVENT_COMMAND):
        case (DEADLINE_COMMAND):
            resolveInputIntoDescriptions = splitDeadlineOrEventInput(userInput);
            newCommand = new Command(commandKeyword, tokenizedInput, resolveInputIntoDescriptions);
            break;
        default:
            throw new DukeException(INVALID_COMMAND_MESSAGE);
        }
        return newCommand;
    }

    private String[] splitDeadlineOrEventInput(String userInput) throws DukeException{
        String[] returnValue = new String[MAX_SUBSTRING_FIELDS];
        String[] separatedSections;
        if (userInput.contains(" /")){
            separatedSections = userInput.split(" /");
            String commandWord = separatedSections[0].split(" ", 2)[0];

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
        }
        return returnValue;
    }

    public String[] splitToDoInput(String originalInput) throws DukeException{
        String[] returnValue = new String[MAX_SUBSTRING_FIELDS];
        String[] separatedSections;
        // get description part of userInput without the command word
        separatedSections = originalInput.split(" ");
        String commandWord = separatedSections[0].split(" ", 2)[0];

        //only the keyword input
        if (Integer.valueOf(separatedSections.length).equals(1)) {
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

    public boolean userWantsToLeave(){
        return this.shouldEndChat;
    }
}
