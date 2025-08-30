package xenon.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import xenon.exception.XenonException;

public class TaskTest {

    @Test
    public void containsPhrase_withMatchingWord_returnsTrue() {
        String description = "Project meeting";
        Task t = new Task(description);
        assertTrue(t.containsPhrase("Project"));
    }

    @Test
    public void containsPhrase_withMatchingPhrase_returnTrue() {
        String description = "Project meeting for CS2103T and CS2101";
        Task t = new Task(description);
        assertTrue(t.containsPhrase("CS2103T and CS2101"));
    }

    @Test
    public void containsPhrase_withDifferentCase_returnsTrue() {
        String description = "PROJECT meeting for CS2103T and CS2101";
        Task t = new Task(description);
        assertTrue(t.containsPhrase("project meeting"));
        assertTrue(t.containsPhrase("project MEETING"));
        assertTrue(t.containsPhrase("Project Meeting"));
    }

    @Test
    public void containsPhrase_partialWordMatch_returnsTrue() {
        String description = "project meeting for CS2103T and CS2101";
        Task t = new Task(description);
        assertTrue(t.containsPhrase("proj"));
    }

    @Test
    public void containsPhrase_specialCharacters_returnsTrue() {
        String description = "project meeting for CS2103T and CS2101 @UTown";
        Task t = new Task(description);
        assertTrue(t.containsPhrase("@UTown"));
    }

    @Test
    public void containsPhrase_noMatchingPhrase_returnsFalse() {
        String description = "PROJECT meeting for CS2103T and CS2101";
        Task t = new Task(description);
        assertFalse(t.containsPhrase("book"));
    }

    @Test
    public void containsPhrase_extraWhiteSpace_returnsTrue() {
        String description = "PROJECT meeting for CS2103T and CS2101";
        Task t = new Task(description);
        assertTrue(t.containsPhrase("  CS2103T  "));
    }

    @Test
    public void fromStorageString_validTodoString_success() throws XenonException {
        Task t = Task.fromStorageString("T | 0 | task 1");
        assertInstanceOf(TodoTask.class, t);
        assertFalse(t.isDone);
        assertEquals("task 1", t.description);
    }

    @Test
    public void fromStorageString_validDeadlineString_success() throws XenonException {
        Task t = Task.fromStorageString("D | 1 | task 1 | 2026-08-28T09:30");
        assertInstanceOf(DeadlineTask.class, t);
        assertTrue(t.isDone);
        assertEquals("task 1", t.description);
    }

    @Test
    public void fromStorageString_validEventString_success() throws XenonException {
        Task t = Task.fromStorageString("E | 0 | task 1 | 2026-08-28T09:30 | 2026-08-30T08:30");
        assertInstanceOf(Event.class, t);
        assertFalse(t.isDone);
        assertEquals("task 1", t.description);
    }

    @Test
    public void fromStorageString_extraWhiteSpaces_success() throws XenonException {
        Task t = Task.fromStorageString("  E | 0  |  task 1 | 2026-08-28T09:30    | 2026-08-30T08:30");
        assertInstanceOf(Event.class, t);
        assertFalse(t.isDone);
        assertEquals("task 1", t.description);
    }

    @Test
    public void fromStorageString_invalidTaskType_exceptionThrown() {
        try {
            Task t = Task.fromStorageString("X | 0 | task 1 | 2025-08-28T09:30 | 2025-08-30T08:30");
        } catch (XenonException e) {
            assertEquals("X is an invalid task type", e.getMessage());
        }
    }

    @Test
    public void fromStorageString_invalidCompletionStatus_exceptionThrown() {
        try {
            Task t = Task.fromStorageString("E | 2 | task 1 | 2025-08-28T09:30 | 2025-08-30T08:30");
        } catch (XenonException e) {
            assertEquals("2 is an invalid completion status", e.getMessage());
        }
    }

    @Test
    public void fromStorageString_invalidDateFormat_exceptionThrown() {
        try {
            Task t = Task.fromStorageString("E | 0 | task 1 | 2025/08/28T09:30 | 2025-08-30T08:30");
        } catch (XenonException e) {
            assertEquals("2025/08/28T09:30 is an invalid date format. Dates should be given in ISO format",
                    e.getMessage());
        }
    }

}
