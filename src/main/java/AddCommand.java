import java.io.IOException;
import java.time.LocalDateTime;

public class AddCommand extends Command {

    private String description;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public AddCommand(String description) {
        super(false);
        this.description = description;
    }

    public AddCommand(String description, LocalDateTime deadline) {
        super(false);
        this.description = description;
        this.deadline = deadline;
    }

    public AddCommand(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(false);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XenonException{

        if (this.description.isBlank()) {
            throw new XenonException("Task description cannot be empty");
        }

        Task task;

        if (this.deadline != null) {
            task = new DeadlineTask(this.description, this.deadline);
        } else if (this.startDate != null && this.endDate != null) {
            task = new Event(this.description, this.startDate, this.endDate);
        } else {
            task = new ToDoTask(this.description);
        }

        tasks.add(task);
        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        ui.showResponse("added " + task);
    }
}

