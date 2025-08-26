package xenon.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import xenon.exception.XenonException;
import xenon.task.Task;

public class Storage {

    private final String FILE_PATH;

    public Storage(String filePath) {
        this.FILE_PATH = filePath;
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

    public void saveData(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(this.FILE_PATH);
        for (Task t : tasks) {
            fw.write(t.toStorageString() + "\n");
        }
        fw.close();
    }
}
