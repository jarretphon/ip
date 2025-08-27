package xenon.command;

import xenon.storage.Storage;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;
import xenon.exception.XenonException;

/**
 * Represents an abstract command to be executed.
 * Subclasses of this class need to provide their specific implementation for the
 * {@link #execute(TaskList, Ui, Storage)} method.
 * Commands can determine if they should terminate the application after execution.
 */
public abstract class Command {

    private boolean isExit;

    public Command(boolean isExit) {
        this.isExit = isExit;
    }

    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Executes the command, performing the intended operation on the task list
     * and interacting with the user interface and storage system as required.
     *
     * @param tasks The TaskList containing the current tasks.
     * @param ui The user interface responsible for displaying messages
     *           and interacting with the user.
     * @param storage The storage system responsible for saving and loading tasks
     *                to and from the designated file.
     * @throws XenonException If an error specific to Xenon occurs during the execution
     *                        of the command.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws XenonException;
}
