package xenon.command;

import java.io.IOException;
import java.time.LocalDateTime;

import xenon.exception.XenonException;
import xenon.storage.Storage;
import xenon.task.DeadlineTask;
import xenon.task.Event;
import xenon.task.Task;
import xenon.task.TodoTask;
import xenon.tasklist.TaskList;
import xenon.ui.Ui;

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

    /**
     * Constructs an AddCommand object with a specified task description.
     * This variant of the AddCommand constructor is used to create a ToDoTask.
     *
     * @param description The description of the task to be added.
     */
    public AddCommand(String description) {
        super(false);
        this.description = description;
    }

    /**
     * Constructs an AddCommand with a specified task description and deadline.
     * This variant of the AddCommand constructor is used to create a DeadlineTask.
     *
     * @param description The description of the task to be added.
     * @param deadline The deadline by which the task must be completed.
     */
    public AddCommand(String description, LocalDateTime deadline) {
        super(false);
        this.description = description;
        this.deadline = deadline;
    }

    /**
     * Constructs an AddCommand with a specified description, start date,
     * and end date to the task list. This variant of the AddCommand constructor
     * is used to create an Event.
     *
     * @param description The description of the task to be added.
     * @param startDate The start date and time of the Event.
     * @param endDate The end date and time of the Event.
     */
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
    public String execute(TaskList tasks, Ui ui, Storage storage) throws XenonException {

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

        //ui.showResponse("added " + task);
        return "added " + task;
    }
}

