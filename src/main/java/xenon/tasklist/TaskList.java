package xenon.tasklist;

import java.util.ArrayList;

import xenon.exception.XenonException;
import xenon.task.Task;

/**
 * Represents a list of tasks in memory. A {@code TaskList} object
 * manages a collection of {@code Task} objects, allowing for tasks to be
 * added, deleted and marked as done or not done.
 */
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

    /**
     * Adds a new {@code Task} to the task list.
     *
     * @param task The {@code Task} object to be added to the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list at the specified index.
     *
     * @param taskIndex The index of the task to delete.
     *                  This value must be within the valid range of the task list.
     * @return The {@code Task} that was deleted.
     * @throws XenonException If the specified index is out of the valid range
     *                        of the task list.
     */
    public Task delete(int taskIndex) throws XenonException {

        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > tasks.size() - 1) {
            throw new XenonException("Task " + (taskIndex + 1) + " does not exist in your list");
        }

        Task deletedTask = tasks.get(taskIndex);
        tasks.remove(taskIndex);

        return deletedTask;
    }

    /**
     * Marks the task at the specified index in the task list as done.
     *
     * @param taskIndex The index of the task to mark as done.
     *                  This value must be within the valid range of the task list.
     * @return The {@code Task} that has been marked as done.
     * @throws XenonException If the specified index is out of the valid range
     *                        of the task list.
     */
    public Task markAsDone(int taskIndex) throws XenonException {
        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > tasks.size() - 1) {
            throw new XenonException("Task " + (taskIndex + 1) + " does not exist in your list");
        }

        Task task = tasks.get(taskIndex);
        task.markAsDone();
        return task;
    }

    /**
     * Marks the specified task within the task list as not done.
     *
     * @param taskIndex The index of the task to be marked as not done.
     *                  This value must be within the valid range of the task list.
     * @return The {@code Task} that was marked as not done.
     * @throws XenonException If the specified index is out of the valid range
     *                        of the task list.
     */
    public Task markAsNotDone(int taskIndex) throws XenonException {
        // Ensure that the taskIndex is within the range of available tasks
        if (taskIndex < 0 || taskIndex > tasks.size() - 1) {
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
            if (t.containsPhrase(phrase)) {
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
            sb.append("  ").append((i + 1)).append(". ").append(tasks.get(i));
        }
        return sb.toString();
    }
}
