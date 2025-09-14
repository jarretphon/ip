package xenon.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import xenon.exception.XenonException;

public class EventTest {

    @Test
    public void testToCommandString_validDates() throws XenonException {
        LocalDateTime start = LocalDateTime.of(2025, 9, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 9, 15, 12, 0);
        Event event = new Event("Meeting", start, end);
        String expected = "event Meeting /from 15/09/2025 10:00 /to 15/09/2025 12:00";
        assertEquals(expected, event.toCommandString());
    }
}
