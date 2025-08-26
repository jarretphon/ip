import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        if (!this.isDone) {
            this.isDone = true;
        }
    }

    public void markAsNotDone() {
        if (this.isDone) {
            this.isDone = false;
        }
    }

    public String toStorageString() {
        String completionStatus = this.isDone ? "1" : "0";
        return completionStatus + " | " + this.description;
    }

    public static Task fromStorageString(String storageString) throws XenonException{
        // split by "|" and remove leading or trailing white spaces
        String[] tokens = storageString.split("\\s*\\|\\s*");
        String type = tokens[0];
        String completionStatus = tokens[1];
        String contents = tokens[2];

        Task task;

        // Create appropriate task for each task Type
        if (type.equals("T")) {
            task = new ToDoTask(contents);
        } else if (type.equals("D")) {
            LocalDateTime deadline = tokens.length > 3 ? LocalDateTime.parse(tokens[3]) : null;
            task = new DeadlineTask(contents, deadline);
        } else {
            LocalDateTime startDate = tokens.length > 4 ? LocalDateTime.parse(tokens[3]) : null;
            LocalDateTime endDate = tokens.length > 4 ? LocalDateTime.parse(tokens[4]) : null;
            task = new Event(contents, startDate, endDate);
        }

        if (completionStatus.equals("1")) task.markAsDone();

        return task;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
