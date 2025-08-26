import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static String[] parse(String input) throws XenonException {
        if (input.isBlank()) {
            throw new XenonException("Xenon: Input cannot be empty");
        }

        String[] tokens = input.split("\\s+", 2);

        String command = tokens[0];
        String contents = tokens.length < 2 ? "" : tokens[1];

        return new String[] {command, contents};
    }

    public static String[] parseDeadline(String taskContents) throws XenonException {
        String[] tokens = taskContents.split("\\s*/by\\s*", 2);

        // User did not specify deadline
        if (tokens.length < 2) {
            throw new XenonException("Xenon: You must specify a due date for a deadline task");
        }

        return tokens;
    }

    public static String[] parseEvent(String taskContents) throws XenonException {
        String[] tokens = taskContents.split("\\s*/from\\s*|\\s*/to\\s*", 3);

        // User did not specify start or end date
        if (tokens.length < 3) {
            throw new XenonException("Xenon: You must specify both the start and end date for an event");
        }

        return tokens;
    }

    public static int parseTaskNumber(String taskNumber) throws XenonException {
        if (taskNumber.isBlank()) {
            throw new XenonException("Xenon: Please specify a task number.");
        }

        // Ensure that the user provided a numerical taskId
        try {
            return Integer.parseInt(taskNumber);
        } catch (NumberFormatException e) {
            throw new XenonException("Xenon: " + taskNumber + " is not a valid task number");
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeInput) throws XenonException{

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            return LocalDateTime.parse(dateTimeInput, inputFormatter);
        } catch (DateTimeParseException e) {
            throw new XenonException(
                    "Xenon: Invalid date input. Please specify a date with the following format: dd/MM/yyyy HH:mm "
                            + "E.g. 27/08/2025 09:30"
            );
        }
    }
}
