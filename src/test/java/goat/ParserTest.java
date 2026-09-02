package goat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests command parsing and validation.
 */
public class ParserTest {
    @Test
    public void parse_commandWithArgument_separatesCommandWordAndArgument() throws GoatException {
        ParsedCommand command = Parser.parse("  deadline read book /by 2026-09-30  ");

        assertEquals(CommandType.DEADLINE, command.type());
        assertEquals("deadline", command.commandWord());
        assertEquals("read book /by 2026-09-30", command.argument());
    }

    @Test
    public void parse_emptyInput_throwsHelpfulException() {
        GoatException exception = assertThrows(GoatException.class, () -> Parser.parse("   "));

        assertEquals("Please enter a command.", exception.getMessage());
    }

    @Test
    public void parseDeadline_validInput_createsFormattedDeadline() throws GoatException {
        Deadline deadline = Parser.parseDeadline("submit report /by 2026-10-15");

        assertEquals("[D][ ] submit report (by: Oct 15 2026)", deadline.toString());
        assertEquals("D | 0 | submit report | 2026-10-15", deadline.toFileString());
    }

    @Test
    public void parseDeadline_impossibleDate_throwsHelpfulException() {
        GoatException exception = assertThrows(
                GoatException.class, () -> Parser.parseDeadline("submit report /by 2026-02-30"));

        assertEquals("The deadline date must be a valid date in yyyy-MM-dd format.",
                exception.getMessage());
    }

    @Test
    public void parseEvent_missingEndDate_throwsHelpfulException() {
        GoatException exception = assertThrows(
                GoatException.class, () -> Parser.parseEvent("meeting /from 2026-09-01 /to"));

        assertEquals("An event needs an end date after /to.", exception.getMessage());
    }
}
