package xenon.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import xenon.exception.XenonException;

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

    public static Task fromStorageString(String storageString) throws XenonException {

        String[] tokens = storageString.split("\\|");

        // Remove leading and trailing white spaces
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }

        String type = tokens[0];
        String completionStatus = tokens[1];
        String description = tokens[2];

        if (!completionStatus.equals("0") && !completionStatus.equals("1")) {
            throw new XenonException(completionStatus + " is an invalid completion status");
        }

        Task task;

        try {
            // Create appropriate task for each task Type
            if (type.equals("T")) {
                task = new ToDoTask(description);
            } else if (type.equals("D")) {
                LocalDateTime deadline = tokens.length > 3 ? LocalDateTime.parse(tokens[3]) : null;
                task = new DeadlineTask(description, deadline);
            } else if (type.equals("E")) {
                LocalDateTime startDate = tokens.length > 4 ? LocalDateTime.parse(tokens[3]) : null;
                LocalDateTime endDate = tokens.length > 4 ? LocalDateTime.parse(tokens[4]) : null;
                task = new Event(description, startDate, endDate);
            } else {
                throw new XenonException(type + " is an invalid task type");
            }
        } catch (DateTimeParseException e) {
            throw new XenonException(e.getParsedString()
                    + " is an invalid date format. Dates should be given in ISO format");
        }

        if (completionStatus.equals("1")) task.markAsDone();

        return task;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
