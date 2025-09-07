package xenon.command;

import java.io.IOException;

import xenon.exception.XenonException;
import xenon.storage.Storage;
import xenon.task.Task;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;

/**
 * Represents a command to mark a specified task as not done.
 */
public class UnmarkCommand extends Command {

    private int taskNumber;

    /**
     * Constructs an UnmarkCommand object with the specified task number to be marked as not done.
     *
     * @param taskNumber The 1-based index of the task to be marked as not done.
     */
    public UnmarkCommand(int taskNumber) {
        super(false);
        this.taskNumber = taskNumber;
    }

    /**
     * Marks a task with the specified task number as not done.
     * Updates the task list, displays the success message to the user, and saves the updated data to storage.
     *
     * @inheritDoc
     * @throws XenonException if the task index is invalid or does not exist in the task list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws XenonException {
        int taskIndex = this.taskNumber - 1;
        Task unmarkedTask = tasks.markAsNotDone(taskIndex);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        return "Ok, I've marked this task as not done yet:\n" + "\t" + unmarkedTask;
    }
}

