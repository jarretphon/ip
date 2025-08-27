package xenon.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import xenon.Operation;
import xenon.exception.XenonException;
import xenon.command.AddCommand;
import xenon.command.Command;
import xenon.command.DeleteCommand;
import xenon.command.ExitCommand;
import xenon.command.HelpCommand;
import xenon.command.ListCommand;
import xenon.command.MarkCommand;
import xenon.command.UnmarkCommand;

public class Parser {

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
