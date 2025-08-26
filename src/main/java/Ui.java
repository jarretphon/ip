import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showLine() {
        System.out.println("_".repeat(100));
    }

    public void showWelcome() {
        this.showLine();
        System.out.println("Hello! I'm Xenon\n" + "What can I do for you?\n\n" + Operation.usageGuide());
        System.out.println();
        this.showLine();
    }

    public void showGoodBye() {
        this.showLine();
        System.out.println("Xenon: Bye. Hope to see you again soon!");
        System.out.println();
        this.showLine();
    }

    public void showResponse(String response) {
        this.showLine();
        System.out.println("Xenon: " + response);
        this.showLine();
    }

    public String takeInput() {
        System.out.print("\t\t\t\t\tYou: ");
        return scanner.nextLine();
    }
}
