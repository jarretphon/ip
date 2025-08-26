import java.io.IOException;

public class Xenon {

    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    public Xenon(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadData(filePath));
        } catch (IOException e) {
            System.out.println("Unable to load data");
        }
    }

    public void chat() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.takeInput();
                Command c = Parser.parse(input);
                c.execute(this.tasks, this.ui, this.storage);
                isExit = c.isExit();
            } catch (XenonException error) {
                ui.showResponse(error + "\n"
                        + "If you are unsure of how to use me, type 'help' to see a list of available commands");
            }
        }
    }
}
