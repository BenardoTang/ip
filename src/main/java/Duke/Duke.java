package Duke;

import common.Messages;
import data.DukeException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static common.Messages.ERROR_SAVING_INTO_TEXT_FILE;

public class Duke {
    //Constants
    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private Messages messageContainer = new Messages();


    public Duke(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadFileToMyTasks());
        } catch (FileNotFoundException | DukeException e) {
            //ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        Duke main = new Duke("Duke/duke.txt");
        main.startDuke();
        main.runLoopUntilExit();
        main.byeDuke();
    }

    public void startDuke() {
        ui.sayIntro();
    }

    public void runLoopUntilExit() {
        Parser commandParser = new Parser(tasks);
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

    public void byeDuke() {
        //TODO: save tasklist to storage
        try {
            storage.saveMyTasksToFile(tasks);
        } catch (IOException e) {
            ui.displayErrorMessage(ERROR_SAVING_INTO_TEXT_FILE);
        }

        ui.sayBye();
        System.exit(0);
    }
}
