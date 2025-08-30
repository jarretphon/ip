package xenon.command;

import java.io.IOException;

import xenon.exception.XenonException;
import xenon.storage.Storage;
import xenon.task.Task;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;

/**
 * Represents a delete command to remove a specific task from the task list.
 * The command identifies the task to delete by its position in the list.
 */
public class DeleteCommand extends Command {

    private int taskNumber;

    /**
     * Constructs a DeleteCommand with the specified task number.
     * This command is used to remove a task identified by its position in the task list.
     *
     * @param taskNumber The 1-based index of the task to be removed from the task list.
     */
    public DeleteCommand(int taskNumber) {
        super(false);
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the delete command which removes a specific task from the task list,
     * updates the storage, and displays a response to the user.
     *
     * @inheritDoc
     *
     * @throws XenonException If the task number is invalid or does not exist in the task list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XenonException {
        int taskIndex = this.taskNumber - 1;
        Task deletedTask = tasks.delete(taskIndex);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        ui.showResponse("Noted. I've removed this task\n" + "\t" + deletedTask);
    }
}
