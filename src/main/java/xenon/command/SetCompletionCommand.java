package xenon.command;

import java.io.IOException;

import xenon.exception.XenonException;
import xenon.storage.Storage;
import xenon.task.Task;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;


/**
 * Represents a command to mark a specific task as done in the task list.
 */
public class SetCompletionCommand extends Command {

    /**
     * Represents the possible actions that can be applied to a task's completion status.
     * - MARK: Indicates that the task should be marked as completed.
     * - UNMARK: Indicates that the task should be marked as not completed.
     */
    public enum Action {
        MARK, UNMARK
    }

    private int taskNumber;
    private Action action;

    /**
     * Constructs a MarkCommand object with the specified task number to be marked as done.
     *
     * @param taskNumber The 1-based index of the task to be marked as done.
     */
    public SetCompletionCommand(int taskNumber, Action action) {
        super(false);
        this.taskNumber = taskNumber;
        this.action = action;
    }


    /**
     * Executes the command to mark a task as done or not done in the given task list.
     *
     * @inheritDoc
     * @throws XenonException if the task index is invalid or does not exist in the task list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws XenonException {
        int taskIndex = this.taskNumber - 1;

        Task targetTask = this.action == Action.MARK
                ? tasks.markAsDone(taskIndex)
                : tasks.markAsNotDone(taskIndex);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        return this.action == Action.MARK
                ? "Nice! I've marked this task as done:\n" + "\t" + targetTask
                : "Ok, I've marked this task as not done yet:\n" + "\t" + targetTask;
    }
}

