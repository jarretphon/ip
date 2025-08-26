import java.io.IOException;

public class DeleteCommand extends Command{

    private int taskNumber;

    public DeleteCommand(int taskNumber) {
        super(false);
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XenonException{
        int taskIndex = this.taskNumber - 1;
        Task deletedTask = tasks.delete(taskIndex);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        ui.showResponse("Noted. I've removed this task\n" + "\t" + deletedTask);
    }
}
