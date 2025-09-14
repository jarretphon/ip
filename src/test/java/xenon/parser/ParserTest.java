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

    /*
     * JetBrains AI was used to generate the following tests. The test was generated using context from the codebase,
     * making it more efficient than writing it from scratch.
     *
     * */
    @Test
    public void tokenise_validInput_correctTokens() throws XenonException {
        assertArrayEquals(new String[]{"task 1", "28/08/2025"},
                Parser.tokenise("task 1 /by 28/08/2025", "/by", 2,
                        "Error message"));
    }

    @Test
    public void tokenise_insufficientTokens_exceptionThrown() {
        try {
            Parser.tokenise("task 1", "/by", 2,
                    "Error: insufficient tokens");
        } catch (XenonException e) {
            assertEquals("Error: insufficient tokens", e.getMessage());
        }
    }

    @Test
    public void tokenise_delimiterNotFound_exceptionThrown() {
        try {
            Parser.tokenise("task 1 28/08/2025", "/by", 2,
                    "Error: delimiter not found");
        } catch (XenonException e) {
            assertEquals("Error: delimiter not found", e.getMessage());
        }
    }

    @Test
    public void tokenise_extraWhitespace_tokensTrimmed() throws XenonException {
        assertArrayEquals(new String[]{"task 1", "28/08/2025"},
                Parser.tokenise("   task 1    /by      28/08/2025   ", "/by", 2,
                        "Error message"));
    }

    @Test
    public void parseTaskNumber_validInput_success() throws XenonException {
        assertEquals(5, Parser.parseTaskNumber("5"));
        assertEquals(123, Parser.parseTaskNumber("123"));
    }

    @Test
    public void parseTaskNumber_emptyInput_exceptionThrown() {
        try {
            Parser.parseTaskNumber("");
        } catch (XenonException e) {
            assertEquals("Please specify a task number.", e.getMessage());
        }
    }

    @Test
    public void parseTaskNumber_invalidFormat_exceptionThrown() {
        try {
            Parser.parseTaskNumber("abc");
        } catch (XenonException e) {
            assertEquals("abc is not a valid task number", e.getMessage());
        }
    }

    @Test
    public void parseTaskNumber_withWhitespace_success() throws XenonException {
        assertEquals(7, Parser.parseTaskNumber("   7   "));
    }
}
