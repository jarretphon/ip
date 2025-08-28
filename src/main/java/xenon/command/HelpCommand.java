package xenon.command;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;
import xenon.Operation;

/**
 * Represents a command to display the help or usage guide for the application.
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super(false);
    }

    /**
     * Executes the HelpCommand, displaying the application's usage guide to the user.
     *
     * @inheritDoc
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showResponse(Operation.usageGuide());
    }
}
