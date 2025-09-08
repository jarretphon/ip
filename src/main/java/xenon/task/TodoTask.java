package xenon.task;

/**
 * Represents a To-Do task, which is a specific type of task.
 * This class extends the functionality of the {@code Task} class.
 */
public class TodoTask extends Task {

    public TodoTask(String description) {
        super(description);
    }

    /**
     * Converts a To-Do task into a string representation suitable for storage.
     *
     * @inheritDoc
     */
    @Override
    public String toStorageString() {
        return "T | " + super.toStorageString();
    }

    /**
     * Converts the To-Do task into its command-line representation.
     *
     * @return a command string representation of the To-Do task
     */
    @Override
    public String toCommandString() {
        return "todo " + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
