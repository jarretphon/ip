import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task{

    private LocalDateTime deadline;
    private final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy ");


    public DeadlineTask(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

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
