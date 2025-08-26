import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy");

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

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
