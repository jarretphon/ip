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
    private final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy");


    public DeadlineTask(String description, LocalDateTime deadline) throws XenonException {
        super(description);
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
        return "[D]" + super.toString() + " (by: " + this.deadline.format(OUTPUT_FORMATTER) + ")";
    }
}
