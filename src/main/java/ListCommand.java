public class ListCommand extends Command {

    public ListCommand() {
        super(false);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showResponse("Here are the tasks in your list\n" + tasks);
    }
}
