import java.util.Scanner;
import java.util.Arrays;

public class Xenon {

    private Task[] tasks = new Task[100];
    private int counter = 0;

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
            System.out.print("\t\t\tYou: " );
            input = scanner.nextLine();

            // transform user input into an array of strings
            String[] inputTokens = input.split("\\s+", 2);
            String command = inputTokens[0];
            String contents = inputTokens.length > 1 ? inputTokens[1]: null;

            // Exit chatbot
            if (inputTokens.length == 1 && command.equals("bye")) {
                break;
            }

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

    public  void toggleComplete(String command, String taskId) {
        if (taskId == null) return;

        int taskIndex = Integer.parseInt(taskId) - 1;

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

    public void createTask(String command, String contents) {
        if (contents == null) return;

        Task task;

        if (command.equals("todo")) {
            task = new ToDoTask(contents);
        } else if (command.equals("deadline")) {
            String[] tokens = contents.split("/by", 2);
            String description = tokens[0];
            String deadline = tokens.length > 1 ? tokens[1].trim() : null;
            task = new DeadlineTask(description, deadline);
        } else {
            String[] tokens = contents.split("/from|/to", 3);
            String description = tokens[0];
            String startDate = tokens.length > 2 ? tokens[1].trim() : null;
            String endDate = tokens.length > 2 ? tokens[2].trim() : null;
            task = new Event(description, startDate, endDate);
        }

        tasks[counter] = task;

        System.out.println("----------------------------------------------");
        System.out.println("Xenon: added " + task);
        System.out.println("----------------------------------------------");
    }
}
