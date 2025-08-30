package xenon.ui;

import java.util.Scanner;

import xenon.Operation;

/**
 * The {@code Ui} class handles all interactions with the user through the console.
 * It is responsible for printing prompts, messages, and feedback,
 * as well as capturing user inputs.
 */
public class Ui {

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a divider on the console.
     */
    public void showLine() {
        System.out.println("_".repeat(100));
    }

    /**
     * Displays the welcome message for the chatbot, together with a usage guide
     * of the available commands.
     */
    public void showWelcome() {
        this.showLine();
        System.out.println("Hello! I'm Xenon\n" + "What can I do for you?\n\n" + Operation.showUsageGuide());
        System.out.println();
        this.showLine();
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodBye() {
        this.showLine();
        System.out.println("Xenon: Bye. Hope to see you again soon!");
        System.out.println();
        this.showLine();
    }

    /**
     * Displays a formatted response message to the user.
     * The response is prefixed with {@code Xenon: }
     * for an intuitive UI.
     *
     * @param response The response message to be displayed to the user.
     */
    public void showResponse(String response) {
        this.showLine();
        System.out.println("Xenon: " + response);
        this.showLine();
    }

    /**
     * Prompts the user for input and captures their response from the console.
     *
     * @return the user input as a string.
     */
    public String takeInput() {
        System.out.print("\t\t\t\t\tYou: ");
        return scanner.nextLine();
    }
}
