package xenon.command;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;

/**
 * Represents the command that exits the application.
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        super(true);
    }

    /**
     * Executes the ExitCommand, which terminates the application by displaying
     * a goodbye message to the user via the user interface.
     *
     * @inheritDoc
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodBye();
    }
}
