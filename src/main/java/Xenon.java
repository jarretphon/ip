import java.util.Scanner;

public class Xenon {

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

            if (input.equals("bye")) {
                break;
            }

            System.out.println("----------------------------------------------");
            System.out.println("Xenon: " + input);
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
