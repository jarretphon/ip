package xenon.command;

import xenon.CommandTracker;
import xenon.exception.XenonException;
import xenon.storage.Storage;
import xenon.task.Task;
import xenon.tasklist.TaskList;


/**
 * Represents a command to edit a specific task in the task list.
 * This command allows a user to modify an existing task identified
 * by its 1-based index.
 */
public class EditCommand extends Command {

    private int taskNumber;

    /**
     * Constructs an EditCommand object with a specified task number.
     * This command is used to edit a specific task in the task list.
     *
     * @param taskNumber The 1-based index of the task to be marked as done.
     */
    public EditCommand(int taskNumber) {
        super(false);
        this.taskNumber = taskNumber;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) throws XenonException {

        int taskIndex = this.taskNumber - 1;

        CommandTracker.setEditing(true);
        CommandTracker.setLastModifiedIndex(taskIndex);

        Task task = tasks.get(taskIndex);
        return task.toCommandString();
    }
}
