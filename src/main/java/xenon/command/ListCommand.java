package xenon.command;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;

/**
 * Represents the command to list all tasks in the task list.
 */
public class ListCommand extends Command {

    public ListCommand() {
        super(false);
    }

    /**
     * Lists all the tasks present in the task list and displays them to the user via the user interface.
     *
     * @inheritDoc
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        return "Here are the tasks in your list\n" + tasks;
    }
}
