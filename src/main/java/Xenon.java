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

    public Xenon(String filePath) {
        storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadData(filePath));
        } catch (IOException e) {
            System.out.println("Unable to load data");
        }
    }

    public static void greet() {
        System.out.println("----------------------------------------------");
        System.out.println("Hello! I'm Xenon\n" + "What can I do for you?\n\n" + Command.usageGuide());
        System.out.println();
        System.out.println("----------------------------------------------");
    }

    public static void exit() {
        System.out.println("----------------------------------------------");
        System.out.println("Xenon: Bye. Hope to see you again soon!");
        System.out.println();
        System.out.println("----------------------------------------------");
    }

    public void chat() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while(true) {
            System.out.print("\t\t\tYou: ");
            input = scanner.nextLine();

            try {
                String[] tokens = Parser.parse(input);
                String command = tokens[0];
                String contents = tokens[1];

                Command cmd = Command.fromInput(command);

                if (cmd == null) {
                    System.out.println("----------------------------------------------");
                    System.out.println(
                            "Xenon: I'm sorry, I do not recognise your command: "
                            + input + "\n\n"
                            + Command.usageGuide()
                    );
                    System.out.println("----------------------------------------------");
                    continue;
                }

                switch (cmd) {
                case BYE:
                    return;
                case HELP:
                    System.out.println("----------------------------------------------");
                    System.out.println("Xenon: " + Command.usageGuide());
                    System.out.println("----------------------------------------------");
                    break;
                case LIST:
                    displayTasks();
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
                System.out.println("----------------------------------------------");
                System.out.println(error);
                System.out.println("If you are unsure of how to use me, type 'help' to see a list of available commands");
                System.out.println("----------------------------------------------");
            }
        }
    }

    public void displayTasks() {
        System.out.println("----------------------------------------------");
        System.out.println("Xenon: Here are the tasks in your list\n" + tasks);
        System.out.println("----------------------------------------------");
    }

    public void toggleComplete(String command, String taskId) throws XenonException{

        int taskIndex = Parser.parseTaskNumber(taskId) - 1;

        System.out.println("----------------------------------------------");
        if (command.equals("mark")) {
            Task markedTask = tasks.markAsDone(taskIndex);
            System.out.println("Xenon: Nice! I've marked this task as done:\n" + "\t" + markedTask);
        } else if (command.equals("unmark")) {
            Task unmarkedTask = tasks.markAsNotDone(taskIndex);
            System.out.println("Xenon: Ok, I've marked this task as not done yet:\n" + "\t" + unmarkedTask);
        }
        System.out.println("----------------------------------------------");

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }
    }

    public void createTask(String command, String contents) throws XenonException {
        if (contents.isBlank()) {
            throw new XenonException("Xenon: Task description cannot be empty");
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
            throw new XenonException("Xenon: Task description cannot be empty.");
        }

        tasks.add(task);
        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        System.out.println("----------------------------------------------");
        System.out.println("Xenon: added " + task);
        System.out.println("----------------------------------------------");
    }

    public void deleteTask(String taskId) throws XenonException {
        int taskIndex = Parser.parseTaskNumber(taskId) - 1;

        Task deletedTask = tasks.delete(taskIndex);

        try {
            storage.saveData(tasks.getAll());
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        System.out.println("----------------------------------------------");
        System.out.println("Xenon: Noted. I've removed this task\n" + "\t" + deletedTask);
        System.out.println("----------------------------------------------");
    }
}
