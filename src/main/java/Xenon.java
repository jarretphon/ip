import java.util.Scanner;

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

            // Exit chatbot
            if (input.equals("bye")) {
                break;
            }

            if (input.equals("list")) {
                // Display stored user input texts
                System.out.println("----------------------------------------------");
                for (int i = 0; i < counter; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println("----------------------------------------------");
                continue;
            }

            if (input.split("\\s+")[0].equals("mark")) {
                String taskId = input.split("\\s+")[1];
                int taskIndex = Integer.parseInt(taskId) - 1;
                tasks[taskIndex].markAsDone();

                System.out.println("----------------------------------------------");
                System.out.println("Xenon: Nice! I've marked this task as done: \n" + "\t" + tasks[taskIndex]);
                System.out.println("----------------------------------------------");
                continue;
            }

            if (input.split("\\s+")[0].equals("unmark")) {
                String taskId = input.split("\\s+")[1];
                int taskIndex = Integer.parseInt(taskId) - 1;
                tasks[taskIndex].markAsNotDone();

                System.out.println("----------------------------------------------");
                System.out.println("Xenon: Ok, I've marked this task as not done yet: \n" + "\t" + tasks[taskIndex]);
                System.out.println("----------------------------------------------");
                continue;
            }

            // Store user input text
            tasks[counter] = new Task(input);
            counter++;

            System.out.println("----------------------------------------------");
            System.out.println("Xenon: added " + input);
            System.out.println("----------------------------------------------");
        }

    }

    public static void exit() {
        System.out.println("----------------------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println();
        System.out.println("----------------------------------------------");
    }
}
