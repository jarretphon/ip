package xenon.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import xenon.Operation;
import xenon.command.FindCommand;
import xenon.exception.XenonException;
import xenon.command.AddCommand;
import xenon.command.Command;
import xenon.command.DeleteCommand;
import xenon.command.ExitCommand;
import xenon.command.HelpCommand;
import xenon.command.ListCommand;
import xenon.command.MarkCommand;
import xenon.command.UnmarkCommand;

/**
 * Represents an object responsible for interpreting user input and converting it
 * into appropriate Command objects for execution.
 */
public class Parser {

    /**
     * Parses the given input string and returns the corresponding Command object based on the parsed operation.
     *
     * @param input The input string representing the user command.
     * @return A {@code Command} object representing the parsed command.
     * @throws XenonException If the input string is blank, if the operation is not recognized,
     *                        or if the input format is invalid for the specified command.
     */
    public static Command parse(String input) throws XenonException {
        if (input.isBlank()) {
            throw new XenonException("Input cannot be empty");
        }

        String[] tokens = input.split("\\s+", 2);

        String command = tokens[0];
        String contents = tokens.length < 2 ? "" : tokens[1];

        Operation op = Operation.fromInput(command);

        if (op == null) {
            throw new XenonException("I'm sorry, I do not recognise your command: " + input
                    + "\n\n" + Operation.showUsageGuide());
        }

        switch (op) {
        case BYE:
           return new ExitCommand();
        case HELP:
            return new HelpCommand();
        case LIST:
            return new ListCommand();
        case FIND:
            return new FindCommand(contents);
        case TODO:
            return new AddCommand(contents);
        case DEADLINE:
            String[] taskArgs = parseDeadline(contents);
            String taskDescription = taskArgs[0];
            LocalDateTime deadline = parseDateTime(taskArgs[1]);
            return new AddCommand(taskDescription, deadline);
        case EVENT:
            String[] eventArgs = parseEvent(contents);
            String eventDescription = eventArgs[0];
            LocalDateTime startDate = parseDateTime(eventArgs[1]);
            LocalDateTime endDate = parseDateTime(eventArgs[2]);
            return new AddCommand(eventDescription, startDate, endDate);
        case MARK:
            return new MarkCommand(parseTaskNumber(contents));
        case UNMARK:
            return new UnmarkCommand(parseTaskNumber(contents));
        case DELETE:
            return new DeleteCommand(parseTaskNumber(contents));
        }

        throw new XenonException("Unable to parse input");
    }

    /**
     * Parses the deadline task details from the given task content string into an array of strings.
     * The input string is split with the delimiter {@code /by} into two parts:
     * the event description and the deadline.
     *
     * @param taskContents The string containing the event details in the format:
     *                     {@code <description>} /by {@code <deadline>}.
     * @return A string array where the first element is the event description
     *         and the second element is the deadline,
     * @throws XenonException If the input does not contain a deadline.
     */
    public static String[] parseDeadline(String taskContents) throws XenonException {
        String[] tokens = taskContents.split("/by", 2);

        // User did not specify deadline
        if (tokens.length < 2) {
            throw new XenonException("You must specify a due date for a deadline task");
        }

        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }

        return tokens;
    }

    /**
     * Parses the event details from the given task content string into an array of strings.
     * The input string is split by the delimiters {@code /from} and {@code /to} into three parts:
     * the event description, the start and end date.
     *
     * @param taskContents The string containing the event details in the format:
     *                     {@code <description>} /from {@code <start date>} /to {@code <end date>}.
     * @return A string array where the first element is the event description,
     *         the second element is the start date, and the third element is the end date.
     * @throws XenonException If the input does not contain both a start and end date.
     */
    public static String[] parseEvent(String taskContents) throws XenonException {
        String[] tokens = taskContents.split("/from|/to", 3);

        // User did not specify start or end date
        if (tokens.length < 3) {
            throw new XenonException("You must specify both the start and end date for an event");
        }

        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }

        return tokens;
    }

    /**
     * Parses the given task number from a string input into an integer.
     * The input string should represent a valid int.
     *
     * @param taskNumber The string representing the task number to be parsed.
     * @return The parsed task number as an int.
     * @throws XenonException If the input string is blank or does not represent a valid integer.
     */
    public static int parseTaskNumber(String taskNumber) throws XenonException {
        if (taskNumber.isBlank()) {
            throw new XenonException("Please specify a task number.");
        }

        // Ensure that the user provided a numerical taskId
        try {
            return Integer.parseInt(taskNumber);
        } catch (NumberFormatException e) {
            throw new XenonException(taskNumber + " is not a valid task number");
        }
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     * The input string must be in the format <code>dd/MM/yyyy HH:mm</code>.
     *
     * @param dateTimeInput The date-time string to be parsed, in the format <code>dd/MM/yyyy HH:mm</code>.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws XenonException If the input string does not match the expected format.
     */
    public static LocalDateTime parseDateTime(String dateTimeInput) throws XenonException{

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            return LocalDateTime.parse(dateTimeInput, inputFormatter);
        } catch (DateTimeParseException e) {
            throw new XenonException(
                    "Invalid date input. Please specify a date with the following format: dd/MM/yyyy HH:mm "
                            + "E.g. 27/08/2025 09:30"
            );
        }
    }
}
