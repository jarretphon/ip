import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.sql.SQLOutput;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Xenon {

    //private ArrayList<Task> tasks;
    private TaskList tasks;
    private Storage storage;
    private UI ui;

    public Xenon(String filePath) {
        ui = new UI();
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

        while(!isExit) {
            try {
                String input = ui.takeInput();

                String[] tokens = Parser.parse(input);
                String command = tokens[0];
                String contents = tokens[1];

                Command cmd = Command.fromInput(command);

                if (cmd == null) {
                    ui.showResponse("I'm sorry, I do not recognise your command: "
                            + input + "\n\n" + Command.usageGuide());
                    continue;
                }

                switch (cmd) {
                case BYE:
                    ui.showGoodBye();
                    isExit = true;
                    break;
                case HELP:
                    ui.showResponse(Command.usageGuide());
                    break;
                case LIST:
                    ui.showResponse("Here are the tasks in your list\n" + tasks);
                    break;
                case TODO: case DEADLINE: case EVENT:
                    createTask(cmd.getKeyword(), contents);
                    break;
                case MARK: case UNMARK:
                    toggleComplete(cmd.getKeyword(), contents);
                    break;
                case DELETE:
                    deleteTask(contents);
                    break;
                }
            } catch (XenonException error) {
                ui.showResponse(error
                        + "\n If you are unsure of how to use me, type 'help' to see a list of available commands");
            }
        }
    }

    public void toggleComplete(String command, String taskId) throws XenonException{

        int taskIndex = Parser.parseTaskNumber(taskId) - 1;

        if (command.equals("mark")) {
            Task markedTask = tasks.markAsDone(taskIndex);
            ui.showResponse("Nice! I've marked this task as done:\n" + "\t" + markedTask);
        } else if (command.equals("unmark")) {
            Task unmarkedTask = tasks.markAsNotDone(taskIndex);
            ui.showResponse("Ok, I've marked this task as not done yet:\n" + "\t" + unmarkedTask);
        }

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }
    }

    public void createTask(String command, String contents) throws XenonException {
        if (contents.isBlank()) {
            throw new XenonException("Task description cannot be empty");
        };

        String description;
        Task task;

        if (command.equals("todo")) {
            description = contents;
            task = new ToDoTask(contents);
        } else if (command.equals("deadline")) {
            String[] tokens = Parser.parseDeadline(contents);
            description = tokens[0];
            LocalDateTime deadline = Parser.parseDateTime(tokens[1]);
            task = new DeadlineTask(description, deadline);
        } else {
            String[] tokens = Parser.parseEvent(contents);
            description = tokens[0];
            LocalDateTime startDate = Parser.parseDateTime(tokens[1]);
            LocalDateTime endDate = Parser.parseDateTime(tokens[2]);
            task = new Event(description, startDate, endDate);
        }

        if (description.isBlank()) {
            throw new XenonException("Task description cannot be empty.");
        }

        tasks.add(task);
        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        ui.showResponse("added " + task);
    }

    public void deleteTask(String taskId) throws XenonException {
        int taskIndex = Parser.parseTaskNumber(taskId) - 1;

        Task deletedTask = tasks.delete(taskIndex);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        ui.showResponse("Noted. I've removed this task\n" + "\t" + deletedTask);
    }
}
