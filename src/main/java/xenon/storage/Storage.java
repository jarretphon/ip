package xenon.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import xenon.exception.XenonException;
import xenon.task.Task;

/**
 * The Storage class handles the loading and saving of task data to a file.
 * It provides methods to read and write a list of tasks to a specified
 * file path, ensuring data persistence for the application.
 */
public class Storage {

    private final String FILE_PATH;

    public Storage(String filePath) {
        this.FILE_PATH = filePath;
    }

    /**
     * Loads a list of tasks from a file at the specified file path.
     * If the file does not exist, a new empty file will be created.
     * Each line in the file is interpreted as a task and converted into a Task object.
     * Invalid task data will be skipped, and an error message will be printed to the console.
     *
     * @param filePath The file path from which the tasks will be loaded.
     * @return An ArrayList of Task objects loaded from the file. If the file is empty
     *         or does not exist, an empty list is returned.
     * @throws IOException If an I/O error occurs while accessing or creating the file.
     */
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
                System.out.println("Xenon: Task could not be loaded: " + savedTask + "\n" + e.getMessage());
                System.out.println("----------------------------------------------");
            }
        }
        return tasks;
    }

    /**
     * Writes a list of tasks to a file at the specific file path associated
     * with this storage instance. Each task in the list is converted to its storage
     * string representation before being written to the file.
     *
     * @param tasks The list of Task objects to be saved to the file. Each task's
     *              storage string representation will be written as a new line in the file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void saveData(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(this.FILE_PATH);
        for (Task t : tasks) {
            fw.write(t.toStorageString() + "\n");
        }
        fw.close();
    }
}
