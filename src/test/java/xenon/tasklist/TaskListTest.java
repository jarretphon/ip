package xenon.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import xenon.task.Task;

/*
* JetBrains AI was used to generate this test. The test was generated using context from the codebase,
* making it more efficient than writing it from scratch.
*
* */

public class TaskListTest {

    @Test
    public void testFindTasksContaining_phraseExistsInTasks_returnsCorrectTasks() {
        Task task1 = new Task("Read a book");
        Task task2 = new Task("Write a book");
        Task task3 = new Task("Go to the gym");
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        TaskList taskList = new TaskList(tasks);

        TaskList results = taskList.findTasksContaining("book");

        assertEquals(2, results.size());
        assertTrue(results.getAll().contains(task1));
        assertTrue(results.getAll().contains(task2));
        assertFalse(results.getAll().contains(task3));
    }

    @Test
    public void testFindTasksContaining_phraseDoesNotExistInTasks_returnsEmptyTaskList() {
        Task task1 = new Task("Read a book");
        Task task2 = new Task("Write a book");
        Task task3 = new Task("Go to the gym");
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        TaskList taskList = new TaskList(tasks);

        TaskList results = taskList.findTasksContaining("swim");

        assertEquals(0, results.size());
    }
}
