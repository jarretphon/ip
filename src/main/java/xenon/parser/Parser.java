package xenon.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import xenon.Operation;
import xenon.command.AddCommand;
import xenon.command.Command;
import xenon.command.DeleteCommand;
import xenon.command.EditCommand;
import xenon.command.ExitCommand;
import xenon.command.FindCommand;
import xenon.command.HelpCommand;
import xenon.command.ListCommand;
import xenon.command.SetCompletionCommand;
import xenon.exception.XenonException;

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
            return new SetCompletionCommand(parseTaskNumber(contents), SetCompletionCommand.Action.MARK);
        case UNMARK:
            return new SetCompletionCommand(parseTaskNumber(contents), SetCompletionCommand.Action.UNMARK);
        case EDIT:
            return new EditCommand(parseTaskNumber(contents));
        case DELETE:
            return new DeleteCommand(parseTaskNumber(contents));
        default:
            throw new XenonException("Unable to parse input");
        }
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
        return tokenise(taskContents, "/by", 2,
                "You must specify a due date for a deadline task");
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
        return tokenise(taskContents, "/from|/to", 3,
                "Invalid event input. Please specify both a start and end date for an event.");
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

        assert taskNumber != null : "Task number cannot be null";

        taskNumber = taskNumber.trim();

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
    public static LocalDateTime parseDateTime(String dateTimeInput) throws XenonException {

        assert dateTimeInput != null : "Date-time input cannot be null";

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

    /**
     * Tokenizes the given command body string based on a specified delimiter.
     * Each token is trimmed of leading and trailing whitespace.
     * If the number of tokens is lower than expected, a XenonException is thrown.
     *
     * @param commandBody The input string to be tokenised.
     * @param splitBy The regex delimiter used to split the input string.
     * @param expectedTokens The expected number of tokens to be obtained after splitting.
     * @param errorMessage The error message to include in the exception if the token count is lower than expected.
     * @return An array of strings containing the tokens after splitting and trimming.
     * @throws XenonException If the number of tokens is lower than the expected count.
     */
    public static String[] tokenise(String commandBody, String splitBy, int expectedTokens,
            String errorMessage) throws XenonException {

        assert commandBody != null : "command body cannot be null";

        String[] tokens = commandBody.split(splitBy, expectedTokens);

        if (tokens.length < expectedTokens) {
            throw new XenonException(errorMessage);
        }

        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }

        return tokens;
    }
}
