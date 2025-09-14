package xenon.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeadlineTaskTest {

    @Test
    public void toCommandString_validDeadline_success() throws Exception {
        LocalDateTime deadline = LocalDateTime.of(2025, 12, 31, 23, 59);
        DeadlineTask task = new DeadlineTask("Finish report", deadline);
        String commandString = task.toCommandString();

        assertEquals("deadline Finish report /by 31/12/2025 23:59", commandString);
    }
}
