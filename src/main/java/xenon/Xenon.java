package xenon;

import java.io.IOException;

import xenon.command.Command;
import xenon.exception.XenonException;
import xenon.parser.Parser;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;

/**
 * Represents a chatbot in the application.
 */
public class Xenon {

    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    /**
     * Constructs a new instance of the Xenon chatbot.
     * Initialises the user interface, storage system, and loads any existing tasks
     * from the specified file path.
     *
     * @param filePath The file path where the task data is stored. This file
     *                 will be used to load existing tasks and persist new ones.
     */
    public Xenon(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadData(filePath));
        } catch (IOException e) {
            System.out.println("Unable to load data");
        }
    }

    /**
     * Initiates a chatbot session, providing an interactive loop for users to input commands
     * and receive appropriate responses.
     * <p>
     * The session continues until an {@code ExitCommand} is executed.
     *
     */
    public void chat() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.takeInput();
                Command c = Parser.parse(input);
                c.execute(this.tasks, this.ui, this.storage);
                isExit = c.isExit();
            } catch (XenonException error) {
                ui.showResponse(error + "\n"
                        + "If you are unsure of how to use me, type 'help' to see a list of available commands");
            }
        }
    }
}
