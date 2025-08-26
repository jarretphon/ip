package xenon.command;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;

public class ExitCommand extends Command {

    public ExitCommand() {
        super(true);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodBye();
    }
}
