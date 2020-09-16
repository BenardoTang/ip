package Duke;

import data.Deadline;
import data.DukeException;
import data.Event;
import data.Task;
import data.ToDo;

import static common.Messages.ALL_USER_TASKS;
import static common.Messages.BYE_COMMAND;
import static common.Messages.DEADLINE_COMMAND;
import static common.Messages.DELETE_COMMAND;
import static common.Messages.DONE_COMMAND;
import static common.Messages.EMPTY_LIST_ERROR;
import static common.Messages.EVENT_COMMAND;
import static common.Messages.INDEX_OFFSET;
import static common.Messages.INVALID_COMMAND_MESSAGE;
import static common.Messages.INVALID_TASK_NUMBER;
import static common.Messages.LIST_COMMAND;
import static common.Messages.MESSAGE_BOUNDARY;
import static common.Messages.TASK_NOT_FOUND;
import static common.Messages.TODO_COMMAND;
import static common.Messages.getTaskAddedMessage;
import static common.Messages.getTaskDoneMessage;
import static common.Messages.getTaskRemovedMessage;

public class Command {
    private final String keyword;
    private String[] tokenizedInput;
    private String[] taskDescriptionRemarksFieldsInput;
    private String queryTaskNumberText;

    public Command(String keyword) {
        this.keyword = keyword;
    }

    public Command(String keyword, String queryTaskNumberTextInput) {
        this.keyword = keyword;
        this.queryTaskNumberText = queryTaskNumberTextInput;
    }

    public Command(String keyword, String[] tokenizedInput, String[] processedUserInput) {
        this.keyword = keyword;
        this.tokenizedInput = tokenizedInput;
        this.taskDescriptionRemarksFieldsInput = processedUserInput;
    }

    public void runCommand(TaskList taskListInput, Ui uiInput) throws DukeException {
        switch (keyword.toLowerCase()) {
        case (BYE_COMMAND):
            break;
        case (LIST_COMMAND):
            printTaskList(taskListInput, uiInput);
            break;
        case (DONE_COMMAND):
            taskIsDone(taskListInput, uiInput, queryTaskNumberText);
            break;
        case (DELETE_COMMAND):
            deleteTask(taskListInput, queryTaskNumberText);
            break;
        default:
            insertNewTask(taskListInput, uiInput, tokenizedInput);
            break;
        }
    }
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
            //break;
        }

        listInput.addTask(newTask);
        String taskAddedMessage = getTaskAddedMessage(newTask, listInput);
        System.out.println(taskAddedMessage + MESSAGE_BOUNDARY);
    }
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
        return;
    }

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
        Task removedTask = listInput.deleteTask(Integer.valueOf(taskNumberForRemoval)
                - INDEX_OFFSET);

        String taskRemovedMessage = getTaskRemovedMessage(removedTask, listInput);
        System.out.println(taskRemovedMessage+MESSAGE_BOUNDARY);
    }
}
