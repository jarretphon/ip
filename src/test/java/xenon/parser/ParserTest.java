package xenon.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import xenon.exception.XenonException;

public class ParserTest {

    @Test
    public void parseDeadline_validInput_success() throws XenonException {
        assertArrayEquals(new String[] {"task 1", "28/08/2025"},
                Parser.parseDeadline("task 1 /by 28/08/2025"));
    }

    @Test
    public void parseDeadline_extraWhiteSpace_contentTrimmed() throws XenonException {
        assertArrayEquals(new String[] {"task 1", "28/08/2025"},
                Parser.parseDeadline("  task 1    /by      28/08/2025   "));
    }

    @Test
    public void parseDeadline_missingDeadline_exceptionThrown() {
        try {
            Parser.parseDeadline("task 1");
        } catch (XenonException e) {
            assertEquals("You must specify a due date for a deadline task", e.getMessage());
        }
    }

    @Test
    public void parseDeadline_wrongDelimiter_exceptionThrown() {
        try {
            Parser.parseDeadline("task 1 by 28/08/2025");
        } catch (XenonException e) {
            assertEquals("You must specify a due date for a deadline task", e.getMessage());
        }
    }
}
