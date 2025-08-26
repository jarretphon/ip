import java.io.IOException;

public class MarkCommand extends Command {

    private int taskNumber;

    public MarkCommand(int taskNumber) {
        super(false);
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XenonException {
        int taskIndex = this.taskNumber - 1;
        Task markedTask = tasks.markAsDone(taskIndex);
        ui.showResponse("Nice! I've marked this task as done:\n" + "\t" + markedTask);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }
    }
}
