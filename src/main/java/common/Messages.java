package common;

public class Messages {
    private static final String MESSAGE_BOUNDARY = "____________________________________________________________";

    public static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    //private static final String FILEPATH = "Duke/duke.txt";

    private static final String ERROR_EMOJI = "(O_o) OOPS!";

    public static final String DUKE_SAYS_HI = "Hello! I'm Duke d(^u^)\nWhat can I do for you?";

    private static final String INVALID_TASK_NUMBER = "Task reference number needs to be an integer...";

    private static final String TOO_MANY_PARAMETERS = "Too many parameters input for the command.";

    private static final String TASK_NOT_FOUND = "No such task was found.";

    private static final String NO_INPUT = "No valid input detected, try again...";

    private static final String TASK_LOADED_INTO_DUKE = "Hi user! your previous tasks have been loaded into Duke...";

    private static final String FAILED_TO_LOAD_TEXT_FILE = "Can't load from file.Creating a new file duke.txt in new folder Duke.";

    private static final String ERROR_SAVING_INTO_TEXT_FILE = "Encountered an error trying to save your task into duke.txt";

    private static final String DEADLINE_COMMAND = "deadline";

    private static final String EVENT_COMMAND = "event";

    private static final String TODO_COMMAND = "todo";

    private static final String BYE_COMMAND = "bye";

    private static final String LIST_COMMAND = "list";

    private static final String DONE_COMMAND = "done";

    private static final String DELETE_COMMAND = "delete";

    private static final String SHOW_ALL_USER_TASKS = "Here are the tasks in your list: ";

    private static final String MARKED_TASK_AS_DONE = "Nice, I've marked this task as done: ";

    private static final String REMOVE_TASK_FOR_USER = "Noted! I've deleted this task for you: ";

    private static final String SAY_SAYONARA = "Bye, hope to see you soon! ";

    private static final String INVALID_COMMAND_MESSAGE = "I don't know what you just said, care to try again?";

    public String printResponseWithBorder(String sampleText) {
        return MESSAGE_BOUNDARY + "\n" + sampleText + "\n" + MESSAGE_BOUNDARY;
    }

}
