public class Main {
    public static void main(String[] args) {
        Xenon.greet();
        Xenon chatbot = new Xenon("./data.txt");
        chatbot.chat();
        Xenon.exit();
    }
}
