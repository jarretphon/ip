import java.io.IOException;

public class UnmarkCommand extends Command {

    private int taskNumber;

    public UnmarkCommand(int taskNumber) {
        super(false);
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XenonException {
        int taskIndex = this.taskNumber - 1;
        Task unmarkedTask = tasks.markAsNotDone(taskIndex);
        ui.showResponse("Ok, I've marked this task as not done yet:\n" + "\t" + unmarkedTask);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }
    }
}

