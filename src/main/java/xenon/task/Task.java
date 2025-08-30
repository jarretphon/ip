package xenon.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xenon.exception.XenonException;

/**
 * Represents a general task. A {@code Task} corresponds to
 * an item with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks this task as completed. If the task is not already marked as done,
     * it updates the completion status to indicate that the task is done.
     */
    public void markAsDone() {
        if (!this.isDone) {
            this.isDone = true;
        }
    }

    /**
     * Marks the task as not done by updating its completion status to false.
     * This method should only modify the state if the task is currently marked as done.
     */
    public void markAsNotDone() {
        if (this.isDone) {
            this.isDone = false;
        }
    }

    /**
     * Checks if the given phrase is contained within the task's description.
     * The matching is case-insensitive.
     *
     * @param phrase the phrase to search for within the task description
     * @return true if the phrase is found in the description, false otherwise
     */
    public boolean containsPhrase(String phrase) {
        Pattern pattern = Pattern.compile(phrase.trim(), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.description);
        return matcher.find();
    }

    /**
     * Converts the task into a string representation suitable for storage.
     *
     * @return a formatted string representing the task for storage purposes.
     */
    public String toStorageString() {
        String completionStatus = this.isDone ? "1" : "0";
        return completionStatus + " | " + this.description;
    }

    /**
     * Constructs a Task object from its string representation used for storage.
     * <p>
     * The storage string must be formatted according to the expected structure:
     * type | completion_status | description | date(s), where type is one of T (ToDoTask),
     * D (DeadlineTask), or E (Event). Completion status is either 0 (not done) or 1 (done),
     * and date(s) should be provided in ISO format.
     *
     * @param storageString the string representation of the task from storage
     * @return a <code>Task</code> reconstructed from the storage string
     * @throws XenonException if the storage string is malformed or contains invalid data,
     *                        such as unsupported task type, invalid completion status,
     *                        or improperly formatted date.
     */
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
            // Create the appropriate task for each task Type
            if (type.equals("T")) {
                task = new TodoTask(description);
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

        if (completionStatus.equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
