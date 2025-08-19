import java.util.Scanner;

public class Xenon {

    private String[] tasks = new String[100];
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
            } else {

                // Store user input text
                tasks[counter] = input;
                counter++;

                System.out.println("----------------------------------------------");
                System.out.println("Xenon: added " + input);
                System.out.println("----------------------------------------------");
            }
        }

    }

    public static void exit() {
        System.out.println("----------------------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println();
        System.out.println("----------------------------------------------");
    }
}
