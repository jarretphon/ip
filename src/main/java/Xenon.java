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
                // Display stored user input texts
                System.out.println("----------------------------------------------");
                System.out.println("Xenon: Here are the tasks in your list ");
                for (int i = 0; i < counter; i++) {
                    System.out.println("\t" + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("----------------------------------------------");
                continue;
            }

            if (command.equals("mark")) {
                String taskId = contents;
                int taskIndex = Integer.parseInt(taskId) - 1;
                tasks[taskIndex].markAsDone();

                System.out.println("----------------------------------------------");
                System.out.println("Xenon: Nice! I've marked this task as done: \n" + "\t" + tasks[taskIndex]);
                System.out.println("----------------------------------------------");
                continue;
            }

            if (command.equals("unmark")) {
                String taskId = contents;
                int taskIndex = Integer.parseInt(taskId) - 1;
                tasks[taskIndex].markAsNotDone();

                System.out.println("----------------------------------------------");
                System.out.println("Xenon: Ok, I've marked this task as not done yet: \n" + "\t" + tasks[taskIndex]);
                System.out.println("----------------------------------------------");
                continue;
            }

            if (command.equals("todo")) {
                // Get the task description without command
                tasks[counter] = new ToDoTask(contents);
            }

            if (command.equals("deadline")) {
                String[] tokens = contents.split("/by");
                String description = tokens[0];
                String deadline = tokens.length > 1 ? tokens[1].trim() : null;
                tasks[counter] = new DeadlineTask(description, deadline);
            }

            if (command.equals("event")) {
                String[] tokens = contents.split("/from|/to");
                String description = tokens[0];
                String startDate = tokens.length > 2 ? tokens[1].trim() : null;
                String endDate = tokens.length > 2 ? tokens[2].trim(): null;
                tasks[counter] = new Event(description, startDate, endDate);
            }

            System.out.println("----------------------------------------------");
            System.out.println("Xenon: added " + tasks[counter]);
            System.out.println("----------------------------------------------");

            counter++;
        }

    }

    public static void exit() {
        System.out.println("----------------------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println();
        System.out.println("----------------------------------------------");
    }
}
