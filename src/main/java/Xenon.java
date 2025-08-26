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

    private ArrayList<Task> tasks;
    private final String FILE_PATH = "./data.txt";

    public Xenon() {
        try {
            this.tasks = loadData(FILE_PATH);
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

            // transform user input into an array of strings
            String[] inputTokens = input.split("\\s+", 2);
            String command = inputTokens[0];
            String contents = inputTokens.length > 1 ? inputTokens[1] : "";

            try {
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
        System.out.println("Xenon: Here are the tasks in your list");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        System.out.println("----------------------------------------------");
    }

    public void toggleComplete(String command, String taskId) throws XenonException{
        if (taskId.isBlank()) {
            throw new XenonException("Xenon: Please specify the task number to mark/unmark");
        }

        int taskIndex;

        // Ensure that the user provided a numerical taskId
        try {
            taskIndex = Integer.parseInt(taskId) - 1;
        } catch (NumberFormatException e) {
            throw new XenonException("Xenon: " + taskId + " is not a valid task number");
        }

        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > tasks.size() - 1) {;
            throw new XenonException("Xenon: Task " + (taskIndex + 1) + " does not exist in your list");
        }

        Task task = tasks.get(taskIndex);

        System.out.println("----------------------------------------------");
        if (command.equals("mark")) {
            task.markAsDone();
            System.out.println("Xenon: Nice! I've marked this task as done:\n" + "\t" + task);
        } else if (command.equals("unmark")) {
            task.markAsNotDone();
            System.out.println("Xenon: Ok, I've marked this task as not done yet:\n" + "\t" + task);
        }
        System.out.println("----------------------------------------------");

        try {
            saveData(tasks, FILE_PATH);
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
            String[] tokens = contents.split("/by", 2);
            description = tokens[0];
            LocalDateTime deadline = tokens.length > 1 ? parseDateTime(tokens[1].trim()) : null;

            if (deadline == null)
                throw new XenonException("Xenon: You must specify a due date for a deadline task");

            task = new DeadlineTask(description, deadline);
        } else {
            String[] tokens = contents.split("/from|/to", 3);
            description = tokens[0];
            LocalDateTime startDate = tokens.length > 2 ? parseDateTime(tokens[1].trim()) : null;
            LocalDateTime endDate = tokens.length > 2 ? parseDateTime(tokens[2].trim()) : null;

            if (startDate == null || endDate == null)
                throw new XenonException("Xenon: You must specify both the start and end date for an event");

            task = new Event(description, startDate, endDate);
        }

        if (description.isBlank()) {
            throw new XenonException("Xenon: Task description cannot be empty.");
        }

        tasks.add(task);
        try {
            saveData(tasks, FILE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        System.out.println("----------------------------------------------");
        System.out.println("Xenon: added " + task);
        System.out.println("----------------------------------------------");
    }

    public void deleteTask(String taskId) throws XenonException {
        if (taskId.isBlank()) {
            throw new XenonException("Xenon: Please specify the task number to delete");
        }

        int taskIndex;

        // Ensure that the user provided a numerical taskId
        try {
            taskIndex = Integer.parseInt(taskId) - 1;
        } catch (NumberFormatException e) {
            throw new XenonException("Xenon: " + taskId + " is not a valid task number");
        }

        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > tasks.size() - 1) {;
            throw new XenonException("Xenon: Task " + (taskIndex + 1) + " does not exist in your list");
        }

        Task deletedTask = tasks.get(taskIndex);
        tasks.remove(taskIndex);

        try {
            saveData(tasks, FILE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to save data");
        }

        System.out.println("----------------------------------------------");
        System.out.println("Xenon: Noted. I've removed this task\n" + "\t" + deletedTask);
        System.out.println("----------------------------------------------");
    }

    public ArrayList<Task> loadData(String filePath) throws IOException {
        File file = new File(filePath);
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner s;

        try {
            s = new Scanner(file);
        } catch (FileNotFoundException e1) {
            file.createNewFile();
            return tasks;
        }

        // Read the data from file into memory
        while (s.hasNext()) {
            String savedTask = s.nextLine();
            try {
                Task task = Task.fromStorageString(savedTask);
                tasks.add(task);
            } catch (XenonException e) {
                System.out.println("Xenon: Task could not be loaded: " + savedTask);
                System.out.println("----------------------------------------------");
            }
        }

        return tasks;
    }

    public void saveData(ArrayList<Task> tasks, String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task t : tasks) {
            fw.write(t.toStorageString() + "\n");
        }
        fw.close();
    }

    public LocalDateTime parseDateTime(String dateTimeInput) throws XenonException{
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, inputFormatter);
            return dateTime;
        } catch (DateTimeParseException e) {
            throw new XenonException(
                    "Xenon: Invalid date input. Please specify a date with the following format: dd/MM/yyyy HH:mm "
                    + "E.g. 27/08/2025 09:30"
            );
        }
    }
}
