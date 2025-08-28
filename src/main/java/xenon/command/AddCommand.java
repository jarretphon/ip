package xenon.command;

import java.io.IOException;
import java.time.LocalDateTime;
import xenon.storage.Storage;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;
import xenon.exception.XenonException;
import xenon.task.Task;
import xenon.task.TodoTask;
import xenon.task.DeadlineTask;
import xenon.task.Event;

/**
 * Represents a command that creates and adds a new task to the task list.
 * The task can be one of three types: ToDoTask, Event, or DeadlineTask,
 * depending on the input parameters provided during construction.
 */
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

    /**
     * Executes the addition of a new task to the task list. The task type can be ToDoTask, Event,
     * or DeadlineTask, determined based on the provided input parameters. The added task is saved
     * to storage, and a response message is displayed to the user.
     *
     * @inheritDoc
     *
     * @throws XenonException If the task description is empty or invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XenonException {

        if (this.description.isBlank()) {
            throw new XenonException("Task description cannot be empty");
        }

        Task task;

        if (this.deadline != null) {
            task = new DeadlineTask(this.description, this.deadline);
        } else if (this.startDate != null && this.endDate != null) {
            task = new Event(this.description, this.startDate, this.endDate);
        } else {
            task = new TodoTask(this.description);
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

