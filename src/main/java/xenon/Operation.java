package xenon;

/**
 * Represents the operations that can be performed by the chatbot.
 * Each operation is associated with a keyword, a usage format, and a description.
 */
public enum Operation {
    TODO("todo", "todo <description>", "Add a todo task"),
    DEADLINE("deadline", "deadline <description> /by <due date>", "Add a deadline task"),
    EVENT("event", "event <description> /from <start date> /to <end date>", "Add an event"),
    LIST("list", "list", "Display created tasks"),
    MARK("mark", "mark <task number>", "Mark a task as done"),
    UNMARK("unmark", "unmark <task number>", "Mark a task as undone"),
    DELETE("delete", "delete <task number>", "Delete a task"),
    HELP("help", "help", "Displays a list of available commands"),
    BYE("bye", "bye", "Exit chatbot");

    private final String keyword;
    private final String usage;
    private final String description;

    Operation(String keyword, String usage, String description) {
        this.keyword = keyword;
        this.usage = usage;
        this.description = description;
    }

    public String getKeyword() {
        return this.keyword;
    }

    /**
     * Returns the corresponding {@code Operation} matching the input.
     * If a match is not found, null is returned.
     *
     * @param input The input string to match with an operation's keyword.
     * @return {@code Operation} if a match is found, otherwise null.
     */
    public static Operation fromInput(String input) {
        for (Operation cmd : Operation.values()) {
            if (cmd.keyword.equals(input)) return cmd;
        }
        return null;
    }

    /**
     * Returns a usage guide for all available operations.
     * The guide includes the format and description for each operation.
     *
     * @return A string containing the usage guide.
     */
    public static String showUsageGuide() {
        StringBuilder helpText = new StringBuilder("Here are the commands you can use:\n");
        for (Operation c : Operation.values()) {
            helpText.append(String.format("- %-60s - %s%n", c.usage, c.description));
        }
        return helpText.toString();
    }
}
