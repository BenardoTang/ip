package Duke;

import common.Messages;
import data.*;

import static common.Messages.*;

public class Command {
    private Messages messageContainer = new Messages();
    private String keyword;
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
        Task newTask;
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
            deleteTask(taskListInput, uiInput, queryTaskNumberText);
            break;
        default:
            insertNewTask(taskListInput, uiInput, tokenizedInput);
            break;
        }
    }
    private void insertNewTask(TaskList listInput, Ui uiInput, String[] tokenizedInput) throws
            DukeException{
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
        String taskAddedMessage = messageContainer.getTaskAddedMessage(newTask, listInput);
        System.out.println(taskAddedMessage);
    }
    public void printTaskList(TaskList listInput, Ui uiInput) {
        String taskListPrintOutput = new String();

        //if list empty, inform user and await next command
        if (listInput.getTaskCount() == 0) {
            uiInput.displayErrorMessage(EMPTY_LIST_ERROR);
            return;
        }
        //if list non-empty, print out all existing tasks
        for (int i = 0; i < listInput.getTaskCount(); i++) {
            taskListPrintOutput += "\t" + Integer.toString(i + 1) + "."
                    + listInput.getTaskList().get(i).toString() +"\n";
        }
        System.out.println(taskListPrintOutput);
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
        //handle case where user tries to mark as done an already completed task
       /* boolean isTaskAlreadyDone = listInput.getTaskDoneStatus(queryNumber-1);

        if (isTaskAlreadyDone) {
            uiInput.displayErrorMessage(TASK_ALREADY_COMPLETED_ERROR_MESSAGE);
            return;
        }*/
        listInput.markTaskAsDone(queryNumber-INDEX_OFFSET);

        String taskDoneMessage = messageContainer.getTaskDoneMessage(queryNumber, listInput);
        System.out.println(taskDoneMessage);
    }



    private void deleteTask(TaskList listInput, Ui uiInput, String taskNumberInput) throws DukeException {
        int taskNumberForRemoval;
        //TODO: exceptions - second input out of bounds, not integer, no second input, only whitespaces after firstinput
        try {
            taskNumberForRemoval = Integer.parseInt(taskNumberInput);
        } catch (NumberFormatException e) {
            //throw NumberFieldException if taskNumber is a string eg. "remove foo" OR whitespaces only
            throw new DukeException(INVALID_TASK_NUMBER);
        } /*catch (ArrayIndexOutOfBoundsException e) {
            //throw MissingParameterException if remove cmd given without 2nd input (ie "remove") HANDLED IN PARSER.JAVA
            throw new MissingParameterException(INSUFFICIENT_COMMAND_PARAMETERS_ERROR_MESSAGE);
        }*/

        //throw NumberFieldException if task number out of range
        boolean isOutOfBounds = (taskNumberForRemoval <= 0 || taskNumberForRemoval > listInput.getTaskCount());
        if (isOutOfBounds) {
            throw new DukeException(TASK_NOT_FOUND);
        }
        Task removedTask = listInput.deleteTask(Integer.valueOf(taskNumberForRemoval)
                - INDEX_OFFSET);

        String taskRemovedMessage = messageContainer.getTaskRemovedMessage(removedTask, listInput);
        System.out.println((taskRemovedMessage));
    }
}
