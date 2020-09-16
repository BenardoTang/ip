package Duke;

import common.Messages;
import data.DukeException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static common.Messages.ERROR_SAVING_INTO_TEXT_FILE;
import static common.Messages.TASKLIST_SAVE_DIRECTORY;

/**
 * This is the main class that runs the entire Duke program.
 */
public class Duke {

    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;
    private final Messages messageContainer = new Messages();

    /**
     * This constructor initializes the other classes to be used in the Duke program.
     *<p></p>
     * The constructor will also attempt to to load any existing local TaskList saved
     * at the location specified in the filePath.
     *<p></p>
     * @param filePath the location of the file with the local TaskList save.
     * @see Ui
     * @see TaskList
     * @see Storage
     * @see Storage#loadFileToMyTasks
     */

    public Duke(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadFileToMyTasks());
        } catch (FileNotFoundException | DukeException e) {
            this.tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        Duke main = new Duke(TASKLIST_SAVE_DIRECTORY);
        main.startDuke();
        main.runLoopUntilExit();
        main.byeDuke();
    }

    /**
     * Displays the startup greeting to user.
     *<p></p>
     * @see Ui#sayIntro
     */

    public void startDuke() {
        ui.sayIntro();
    }

    /**
     * This method runs the main loop where where Duke obtains command from user and converts
     * it into a {@link Command} object that executes the corresponding operation.
     *<p></p>
     * This loop continues till the user enters the "bye" command. It will then exit the loop.</p>
     * @see Ui#getUserCommand`
     * @see Parser#parseCommand
     * @see Command#runCommand
     */

    public void runLoopUntilExit() {
        Parser commandParser = new Parser();
        while (!commandParser.userWantsToLeave()) {
            try {
                String userInputText = ui.getUserCommand();
                Command nextCommand = commandParser.parseCommand(userInputText);
                nextCommand.runCommand(tasks, ui);
            } catch (DukeException e) {
                ui.displayErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * This method is used to exit Duke.
     * <p></p>
     * Occurs when the "bye" command is entered. It saves the current task list before saying goodbye.
     * @see Storage#saveMyTasksToFile
     * @see Ui
     * @see Ui#sayBye()
     */

    public void byeDuke() {
        try {
            storage.saveMyTasksToFile(tasks);
        } catch (IOException e) {
            ui.displayErrorMessage(ERROR_SAVING_INTO_TEXT_FILE);
        }
        ui.sayBye();
        System.exit(0);
    }
}
