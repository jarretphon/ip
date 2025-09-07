package xenon.command;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;

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
    public String execute(TaskList tasks, Storage storage) {
        //ui.showGoodBye();
        return "Bye. Hope to see you again soon!";
    }
}
