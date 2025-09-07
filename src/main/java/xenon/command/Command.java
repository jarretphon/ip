package xenon.command;

import xenon.exception.XenonException;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;

/**
 * Represents an abstract command to be executed.
 * Subclasses of this class need to provide their specific implementation for the
 * {@link #execute(TaskList, Storage)} method.
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
     * @param storage The storage system responsible for saving and loading tasks
     *                to and from the designated file.
     * @return A formatted response message to be displayed to the user.
     * @throws XenonException If an error specific to Xenon occurs during the execution
     *                        of the command.
     */
    public abstract String execute(TaskList tasks, Storage storage) throws XenonException;
}
