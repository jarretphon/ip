package xenon.tasklist;

import java.util.ArrayList;

import xenon.exception.XenonException;
import xenon.task.Task;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return this.tasks.size();
    }

    public ArrayList<Task> getAll() {
        return this.tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int taskIndex) throws XenonException {

        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > tasks.size() - 1) {;
            throw new XenonException("Task " + (taskIndex + 1) + " does not exist in your list");
        }

        Task deletedTask = tasks.get(taskIndex);
        tasks.remove(taskIndex);

        return deletedTask;
    }

    public Task markAsDone(int taskIndex) throws XenonException {
        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > tasks.size() - 1) {;
            throw new XenonException("Task " + (taskIndex + 1) + " does not exist in your list");
        }

        Task task = tasks.get(taskIndex);
        task.markAsDone();
        return task;
    }

    public Task markAsNotDone(int taskIndex) throws XenonException {
        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > tasks.size() - 1) {;
            throw new XenonException("Task " + (taskIndex + 1) + " does not exist in your list");
        }

        Task task = tasks.get(taskIndex);
        task.markAsNotDone();
        return task;
    }

    /**
     * Returns a list of tasks whose descriptions contain the given phrase.
     * The search is case-insensitive.
     *
     * @param phrase the phrase to search for within the task descriptions
     * @return a new TaskList containing tasks whose descriptions include the phrase
     */
    public TaskList findTasksContaining(String phrase) {

        TaskList results = new TaskList(new ArrayList<Task>());

        for (int i = 0; i < this.tasks.size(); i++) {
            Task t = this.tasks.get(i);
            if(t.containsPhrase(phrase)) {
                results.add(t);
            }
        }

        return results;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {

            if (i > 0) {
                sb.append("\n");
            }
            sb.append("\t").append((i + 1)).append(". ").append(tasks.get(i));
        }
        return sb.toString();
    }
}
