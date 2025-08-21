public enum Command {
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

    Command(String keyword, String usage, String description) {
        this.keyword = keyword;
        this.usage = usage;
        this.description = description;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public static Command fromInput(String input) {
        for (Command cmd : Command.values()) {
            if (cmd.keyword.equals(input)) return cmd;
        }
        return null;
    }

    public static String usageGuide() {
        StringBuilder helpText = new StringBuilder("Here are the commands you can use:\n");
        for (Command c : Command.values()) {
            helpText.append(String.format("- %-60s - %s%n", c.usage, c.description));
        }
        return helpText.toString();
    }
}
