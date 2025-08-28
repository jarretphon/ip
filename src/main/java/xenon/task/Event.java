package xenon.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import xenon.exception.XenonException;

/**
 * Represents an event with a start date and an end date.
 * A {@code Event} corresponds to a task that takes place between
 * a specific start and end date.
 */
public class Event extends Task {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy");

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) throws XenonException {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;

        if (this.startDate.isBefore(LocalDateTime.now()) || this.endDate.isBefore(LocalDateTime.now())) {
            throw new XenonException("Start and end dates cannot be set before today");
        }

        if (this.endDate.isBefore(this.startDate)) {
            throw new XenonException("Start date must be after end date");
        }

    }

    /**
     * Converts the event into a string representation suitable for storage.
     *
     * @inheritDoc
     */
    public String toStorageString() {
        // Date strings are in storage with ISO format yyyy-MM-dd HH:mm
        return "E | " + super.toStorageString() + " | " + this.startDate.toString() + " | " + this.endDate.toString();
    }

    @Override
    public String toString() {
        // Date strings are displayed to the user with custom format dd MMM yyyy HH:mm
        return "[E]" + super.toString() + " (from: "
                + this.startDate.format(OUTPUT_FORMATTER) + " to: " + this.endDate.format(OUTPUT_FORMATTER) + ")";
    }
}
