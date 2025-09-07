package xenon.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import xenon.exception.XenonException;

/**
 * Represents a task with a specific deadline, which is a specific type of task.
 * A {@code DeadlineTask} corresponds to a task that needs to be completed
 * by a specific date and time.
 */
public class DeadlineTask extends Task {

    private LocalDateTime deadline;
    private final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy");


    /**
     * Creates a new DeadlineTask with the specified description and deadline.
     * A DeadlineTask represents a type of task that must be completed by a specific date and time.
     *
     * @param description the description of the deadline task
     * @param deadline the deadline of the task, specified as a LocalDateTime object
     * @throws XenonException if the provided deadline is before the current date and time
     */
    public DeadlineTask(String description, LocalDateTime deadline) throws XenonException {
        super(description);

        assert deadline != null : "Deadline cannot be null";
        this.deadline = deadline;

        if (this.deadline.isBefore(LocalDateTime.now())) {
            throw new XenonException("Deadline cannot be before today.");
        }
    }

    /**
     * Converts the deadline task into a string representation suitable for storage.
     *
     * @inheritDoc
     */
    @Override
    public String toStorageString() {
        // Date strings are in storage with ISO format yyyy-MM-dd HH:mm
        return "D | " + super.toStorageString() + " | " + this.deadline.toString();
    }

    @Override
    public String toString() {
        // Date strings are displayed to the user with custom format dd MMM yyyy HH:mm
        return "[D]" + super.toString() + " (by: " + this.deadline.format(outputFormatter) + ")";
    }
}
