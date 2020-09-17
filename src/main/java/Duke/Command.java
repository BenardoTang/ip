package Duke;

import data.Deadline;
import data.DukeException;
import data.Event;
import data.Task;
import data.ToDo;

import java.util.ArrayList;

import static common.Messages.ALL_USER_TASKS;
import static common.Messages.BYE_COMMAND;
import static common.Messages.DEADLINE_COMMAND;
import static common.Messages.DELETE_COMMAND;
import static common.Messages.DONE_COMMAND;
import static common.Messages.EMPTY_LIST_ERROR;
import static common.Messages.EVENT_COMMAND;
import static common.Messages.FIND_COMMAND;
import static common.Messages.INDEX_OFFSET;
import static common.Messages.INVALID_COMMAND_MESSAGE;
import static common.Messages.INVALID_TASK_NUMBER;
import static common.Messages.LIST_COMMAND;
import static common.Messages.MATCHES_MESSAGE;
import static common.Messages.MESSAGE_BOUNDARY;
import static common.Messages.NO_MATCHES_MESSAGE;
import static common.Messages.TASK_NOT_FOUND;
import static common.Messages.TODO_COMMAND;
import static common.Messages.getTaskAddedMessage;
import static common.Messages.getTaskDoneMessage;
import static common.Messages.getTaskRemovedMessage;

/**
 * This class deals with commands. The call for command execution is done through here. It also executes the relevant
 * operations on the {@link TaskList} containing the Tasks.
 * @see TaskList
 */
public class Command {
    private final String keyword;
    private String[] tokenizedInput;
    private String[] taskDescriptionRemarksFieldsInput;
    private String query;

    /**
     * Constructor for LIST and BYE command.
     * @param keyword indicates the operation to be executed
     */
    public Command(String keyword) {
        this.keyword = keyword;
    }


    /**
     * Constructor for DONE, DELETE and FIND command.
     * @param keyword indicates the operation to be executed
     * @param queryInput this input is a number index for DONE and DELETE command
     * and search keyword for FIND command.
     */

    public Command(String keyword, String queryInput) {
        this.keyword = keyword;
        this.query = queryInput;
    }

    /**
     * Constructor for TODO, EVENT or DEADLINE command.
     * @param keyword indicates the operation to be executed
     * @param tokenizedInput a string array of the original user input string
     * @param processedUserInput a string array of the description and remarks fields of the task.
     */
    public Command(String keyword, String[] tokenizedInput, String[] processedUserInput) {
        this.keyword = keyword;
        this.tokenizedInput = tokenizedInput;
        this.taskDescriptionRemarksFieldsInput = processedUserInput;
    }

    /**
     * This method parses the keyword attribute of the {@link Command} object, and carries out the operation corresponding to the keyword on a {@link TaskList} list.
     * <p></p>
     * If any exception is encountered during the operation, they will be thrown and caught by the exception handler
     * in the main class ({@link Duke})
     * <p></p></p>
     * @param taskListInput the list of tasks
     * @param uiInput for displaying Ui elements
     * @see TaskList
     * @see Ui
     */
    public void runCommand(TaskList taskListInput, Ui uiInput) throws DukeException {
        switch (keyword.toLowerCase()) {
        case (BYE_COMMAND):
            break;
        case (LIST_COMMAND):
            printTaskList(taskListInput, uiInput);
            break;
        case (DONE_COMMAND):
            taskIsDone(taskListInput, uiInput, query);
            break;
        case (DELETE_COMMAND):
            deleteTask(taskListInput, query);
            break;
        case (FIND_COMMAND):
            findTasksByKeyword(taskListInput, uiInput, query);
            break;
        default:
            insertNewTask(taskListInput, uiInput, tokenizedInput);
            break;
        }
    }

    /**
     * This method constructs a new {@link Task} from the attributes of the {@link Command} object,
     * and inserts it into a given {@link TaskList} list.
     * <p></p>
     * @param listInput         the list of Tasks
     * @param uiInput           for displaying Ui elements
     * @param tokenizedInput    a string array of the original user input string
     * @see TaskList
     * @see Ui
     */
    private void insertNewTask(TaskList listInput, Ui uiInput, String[] tokenizedInput) {
        Task newTask = null;
        switch (tokenizedInput[0]) {
        case (TODO_COMMAND):
            newTask = new ToDo(taskDescriptionRemarksFieldsInput[0], taskDescriptionRemarksFieldsInput[1]);
            break;
        case (DEADLINE_COMMAND):
            newTask = new Deadline(taskDescriptionRemarksFieldsInput[0], taskDescriptionRemarksFieldsInput[1]);
            break;
        case (EVENT_COMMAND):
            newTask = new Event(taskDescriptionRemarksFieldsInput[0], taskDescriptionRemarksFieldsInput[1]);
            break;
        default:
            uiInput.displayErrorMessage(INVALID_COMMAND_MESSAGE);
        }

        listInput.addTask(newTask);
        String taskAddedMessage = getTaskAddedMessage(newTask, listInput);
        System.out.println(taskAddedMessage + MESSAGE_BOUNDARY);
    }

    /**
     * This method prints out the {@link Task} objects that are currently in the given {@link TaskList} list.
     * <p></p>
     * <p>
     * The list includes the type ({@link ToDo}, {@link Event}, {@link Deadline}) of each Task and the status
     * of each task. If there are no tasks in the TaskList, an empty list message is printed instead.
     * </p>
     * @param listInput the list of Tasks
     * @param uiInput   for displaying Ui elements
     * @see TaskList
     * @see Ui
     * @see ToDo
     * @see Event
     * @see Deadline
     */
    public void printTaskList(TaskList listInput, Ui uiInput) {
        String taskListPrintOutput = "";

        //if list empty, inform user and await next command
        if (listInput.getTaskCount() == 0) {
            uiInput.displayErrorMessage(EMPTY_LIST_ERROR);
            return;
        }
        //if list non-empty, print out all existing tasks
        for (int i = 0; i < listInput.getTaskCount(); i++) {
            taskListPrintOutput +=  + (i + 1) + "."
                    + listInput.getTaskList().get(i).toString() +"\n";
        }

        System.out.println(MESSAGE_BOUNDARY+"\n"+ALL_USER_TASKS+"\n"+taskListPrintOutput+MESSAGE_BOUNDARY);
    }

    /**
     * This method marks a {@link Task} object (denoted by task number) in the {@link TaskList} list as "done".
     * <p></p>
     * If the task number given is not a valid number or falls outside the range of existing tasks,
     * an error message will be shown stating that the number chosen is out of range
     * <p></p>
     *
     * @param listInput         the list of tasks
     * @param taskNumberInput   the task number of the task to be marked done
     * @param uiInput           for displaying Ui elements
     * @see TaskList
     * @see Ui
     */
    public void taskIsDone(TaskList listInput, Ui uiInput, String taskNumberInput) throws DukeException {
        int queryNumber;
        try {
            queryNumber = Integer.parseInt(taskNumberInput);
        } catch (NumberFormatException e) {
            //throw error if taskNumber is a string eg. "remove foo" OR whitespaces only
            throw new DukeException(INVALID_TASK_NUMBER);
        }
        boolean isOutOfRange = queryNumber < 1 || queryNumber > listInput.getTaskCount();

        //handle case where user inputs non-existing task number to mark as done
        if (isOutOfRange){
            uiInput.displayErrorMessage(TASK_NOT_FOUND);
            return;
        }
        listInput.markTaskAsDone(queryNumber-INDEX_OFFSET);

        String taskDoneMessage = getTaskDoneMessage(queryNumber, listInput);
        System.out.println(taskDoneMessage +"\n"+ MESSAGE_BOUNDARY);
    }

    /**
     * This method constructs a new {@link Task} from the attributes of the {@link Command} object,
     * and inserts it into a given {@link TaskList} list.
     * <p></p>
     * If the task number given is not a valid number or falls outside the range of existing tasks,
     * an error message will be shown stating that the number chosen is out of range
     * <p></p>
     * @param listInput         the list of Tasks
     * @param taskNumberInput   the task number of the task to be deleted
     * @see TaskList
     * @see Ui
     */
    private void deleteTask(TaskList listInput, String taskNumberInput) throws DukeException {
        int taskNumberForRemoval;
        try {
            taskNumberForRemoval = Integer.parseInt(taskNumberInput);
        } catch (NumberFormatException e) {
            throw new DukeException(INVALID_TASK_NUMBER);
        }
        boolean isOutOfBounds = (taskNumberForRemoval <= 0 || taskNumberForRemoval > listInput.getTaskCount());
        if (isOutOfBounds) {
            throw new DukeException(TASK_NOT_FOUND);
        }
        Task removedTask = listInput.deleteTask(taskNumberForRemoval
                - INDEX_OFFSET);

        String taskRemovedMessage = getTaskRemovedMessage(removedTask, listInput);
        System.out.println(taskRemovedMessage+MESSAGE_BOUNDARY);
    }

    /**
     * This method searches the Tasks in the {@link TaskList} object input for a keyword. It filters out
     * Tasks containing the search keyword and prints them.
     * @param listInput the TaskList to be searched
     * @param uiInput for displaying Ui elements
     * @param searchQuery the keyword to be searched for in Tasks
     */
    private void findTasksByKeyword(TaskList listInput, Ui uiInput, String searchQuery) {
        int resultNumber = 1;
        ArrayList<Task> searchResults = listInput.searchTaskList(listInput.getTaskList(), searchQuery);

        if (Integer.valueOf(searchResults.size()).equals(0)) {
            uiInput.displayErrorMessage(NO_MATCHES_MESSAGE);
        } else {
            String searchOutput = MATCHES_MESSAGE + "\n";
            for (Task result : searchResults) {
                searchOutput += resultNumber + "." + result.toString() + "\n";
                resultNumber++;
            }
            System.out.println(MESSAGE_BOUNDARY +"\n"+ searchOutput + MESSAGE_BOUNDARY);
        }
    }
}
