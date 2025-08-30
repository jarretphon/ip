package xenon;

/**
 * The Main class serves as the entry point for the chatbot application.
 */
public class Main {
    public static void main(String[] args) {
        Xenon chatbot = new Xenon("./data.txt");
        chatbot.chat();
    }
}
