package xenon.command;

import java.io.IOException;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;
import xenon.exception.XenonException;
import xenon.task.Task;

/**
 * Represents a command to mark a specific task as done in the task list.
 */
public class MarkCommand extends Command {

    private int taskNumber;

    public MarkCommand(int taskNumber) {
        super(false);
        this.taskNumber = taskNumber;
    }

    /**
     * Marks a task with the specified task number as done.
     * Updates the task list, displays the success message to the user, and saves the updated data to storage.
     *
     * @inheritDoc
     * @throws XenonException if the task index is invalid or does not exist in the task list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XenonException {
        int taskIndex = this.taskNumber - 1;
        Task markedTask = tasks.markAsDone(taskIndex);
        ui.showResponse("Nice! I've marked this task as done:\n" + "\t" + markedTask);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }
    }
}
