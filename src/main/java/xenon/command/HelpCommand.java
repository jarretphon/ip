package xenon.command;
import xenon.Operation;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;


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
    public String execute(TaskList tasks, Storage storage) {
        return Operation.showUsageGuide();
    }
}
