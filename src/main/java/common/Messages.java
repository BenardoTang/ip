package common;

import Duke.TaskList;
import data.Task;

public class Messages {
    public static final String MESSAGE_BOUNDARY = "____________________________________________________________";

    public static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    //private static final String FILEPATH = "Duke/duke.txt";

    public static final String ERROR_EMOJI = "(O_o) OOPS!";

    public static final int INDEX_OFFSET = 1;

    public static final String DUKE_SAYS_HI = "Hello! I'm Duke d(^u^)\nWhat can I do for you?";

    public static final String INVALID_TASK_NUMBER = "Task reference number needs to be a valid integer...";

    public static final String TOO_MANY_PARAMETERS = "Too many parameters input for the command.";

    public static final String TOO_LITTLE_PARAMETERS = "Too little parameters input for the command.";

    public static final String NO_DESCRIPTION = "The task must have a description.";

    public static final String TASK_NOT_FOUND = "No such task was found.";

    public static final String NO_INPUT = "No valid input detected, try again...";

    public static final String TASK_LOADED_INTO_DUKE = "Hi user! your previous tasks have been loaded into Duke...";

    public static final String FAILED_TO_LOAD_TEXT_FILE = "Can't load from file.Creating a new file duke.txt in new folder Duke.";

    public static final String ERROR_SAVING_INTO_TEXT_FILE = "Encountered an error trying to save your task into duke.txt";

    public static final String DEADLINE_COMMAND = "deadline";

    public static final String EVENT_COMMAND = "event";

    public static final String TODO_COMMAND = "todo";

    public static final String BYE_COMMAND = "bye";

    public static final String LIST_COMMAND = "list";

    public static final String DONE_COMMAND = "done";

    public static final String DELETE_COMMAND = "delete";

    public static final String SHOW_ALL_USER_TASKS = "Here are the tasks in your list: ";

    public static final String MARKED_TASK_AS_DONE = "Nice, I've marked this task as done: ";

    public static final String REMOVE_TASK_FOR_USER = "Noted! I've deleted this task for you: ";

    public static final String EMPTY_LIST_ERROR = "The task list is empty.";

    public static final String SAY_SAYONARA = "Bye, hope to see you soon! ";

    public static final String INVALID_COMMAND_MESSAGE = "I don't know what you just said, care to try again?";

    public String printResponseWithBorder(String sampleText) {
        return MESSAGE_BOUNDARY + "\n" + sampleText + "\n" + MESSAGE_BOUNDARY;
    }
    public static String getTaskDoneMessage(int queryNumber, TaskList listInput) {
        return MARKED_TASK_AS_DONE  + "\t" + Integer.toString(queryNumber)
                + ".[" + listInput.getTaskStatusIcon(queryNumber - INDEX_OFFSET) + "] "
                + listInput.getTaskDescription(queryNumber - INDEX_OFFSET);
    }

    public static String getTaskAddedMessage(Task newTask, TaskList listInput) {
        String plural = ((listInput.getTaskCount()  > 1) ? "s" : "");
        return "Got it. I've added this task:" + newTask.toString() + "\n"
                + "Now you have " + listInput.getTaskCount() + " task" + plural + "in the list.\n";

    }
    public static String getTaskRemovedMessage(Task removedTask, TaskList listInput) {
        String plural = ((listInput.getTaskCount()  > 1) ? "s" : "");
        return "\tGot it. I've removed this task: \n\t" + removedTask.toString()
                + "\tNow you have " + listInput.getTaskCount() + " task" + plural +"in the list.\n";
    }
}
