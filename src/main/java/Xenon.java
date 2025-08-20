import java.util.Scanner;
import java.util.Arrays;

public class Xenon {

    private Task[] tasks = new Task[100];
    private int counter = 0;

    private final static String HELP_TEXT =
        "Here are the commands you can use:\n"
        + "- todo <description>                                         -Add a todo task\n"
        + "- deadline <description> /by <due date>                      -Add a deadline task\n"
        + "- event <description> /from <start date> /to <end date>      -Add an event\n"
        + "- list                                                       -Display created tasks\n"
        + "- mark <task number>                                         -Mark a task as done\n"
        + "- unmark <task number>                                       -Mark a task as undone\n"
        + "- bye                                                        -Exit chatbot";

    public static void greet() {
        System.out.println("----------------------------------------------");
        System.out.println("Hello! I'm Xenon");
        System.out.println("What can I do for you?");
        System.out.println();
        System.out.println("----------------------------------------------");
    }

    public static void exit() {
        System.out.println("----------------------------------------------");
        System.out.println("Xenon: Bye. Hope to see you again soon!");
        System.out.println();
        System.out.println("----------------------------------------------");
    }

    public void chat() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while(true) {
            System.out.print("\t\t\tYou: ");
            input = scanner.nextLine();

            // transform user input into an array of strings
            String[] inputTokens = input.split("\\s+", 2);
            String command = inputTokens[0];
            String contents = inputTokens.length > 1 ? inputTokens[1] : "";

            // Exit chatbot
            if (inputTokens.length == 1 && command.equals("bye")) {
                break;
            }

            try {

                if (inputTokens.length == 1 && command.equals("list")) {
                    displayTasks();
                    continue;
                }

                if (command.equals("mark") || command.equals("unmark")) {
                    toggleComplete(command, contents);
                    continue;
                }

                if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                    createTask(command, contents);
                    counter++;
                    continue;
                }

                // Unrecognised command words prompt help text
                System.out.println("----------------------------------------------");
                System.out.println(
                        "Xenon: I'm sorry, I do not recognise your command: " + command + "\n\n" + HELP_TEXT
                );
                System.out.println("----------------------------------------------");

            } catch (XenonException error) {
                System.out.println("----------------------------------------------");
                System.out.println(error);
                System.out.println("----------------------------------------------");
            }
        }
    }

    public void displayTasks() {
        System.out.println("----------------------------------------------");
        System.out.println("Xenon: Here are the tasks in your list");
        for (int i = 0; i < counter; i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks[i]);
        }
        System.out.println("----------------------------------------------");
    }

    public void toggleComplete(String command, String taskId) throws XenonException{
        if (taskId.isBlank()) {
            throw new XenonException("Xenon: Please specify the task number to mark/unmark");
        }

        int taskIndex;

        // Ensure that the user provided a numerical taskId
        try {
            taskIndex = Integer.parseInt(taskId) - 1;
        } catch (NumberFormatException e) {
            throw new XenonException("Xenon: " + taskId + " is not a valid task number");
        }

        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > counter - 1) {;
            throw new XenonException("Xenon: Task " + (taskIndex + 1) + " does not exist in your list");
        }

        System.out.println("----------------------------------------------");
        if (command.equals("mark")) {
            tasks[taskIndex].markAsDone();
            System.out.println("Xenon: Nice! I've marked this task as done:\n" + "\t" + tasks[taskIndex]);
        } else if (command.equals("unmark")) {
            tasks[taskIndex].markAsNotDone();
            System.out.println("Xenon: Ok, I've marked this task as not done yet:\n" + "\t" + tasks[taskIndex]);
        }
        System.out.println("----------------------------------------------");
    }

    public void createTask(String command, String contents) throws XenonException {
        if (contents.isBlank()) {
            throw new XenonException("Xenon: Task description cannot be empty");
        };

        String description;
        Task task;

        if (command.equals("todo")) {
            description = contents;
            task = new ToDoTask(contents);
        } else if (command.equals("deadline")) {
            String[] tokens = contents.split("/by", 2);
            description = tokens[0];
            String deadline = tokens.length > 1 ? tokens[1].trim() : "";

            if (deadline.isBlank())
                throw new XenonException("Xenon: You must specify a due date for a deadline task");

            task = new DeadlineTask(description, deadline);
        } else {
            String[] tokens = contents.split("/from|/to", 3);
            description = tokens[0];
            String startDate = tokens.length > 2 ? tokens[1].trim() : "";
            String endDate = tokens.length > 2 ? tokens[2].trim() : "";

            if (startDate.isBlank() || endDate.isBlank())
                throw new XenonException("Xenon: You must specify both the start and end date for an event");

            task = new Event(description, startDate, endDate);
        }

        if (description.isBlank()) {
            throw new XenonException("Xenon: Task description cannot be empty.");
        }

        tasks[counter] = task;

        System.out.println("----------------------------------------------");
        System.out.println("Xenon: added " + task);
        System.out.println("----------------------------------------------");
    }
}
