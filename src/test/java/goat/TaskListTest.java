package goat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests task-number parsing against collection boundaries.
 */
public class TaskListTest {
    private final TaskList tasks = new TaskList(List.of(
            new Todo("first"), new Todo("second"), new Todo("third")));

    @Test
    public void parseIndex_validOneBasedNumber_returnsZeroBasedIndex() throws GoatException {
        assertEquals(0, tasks.parseIndex("1", "mark"));
        assertEquals(2, tasks.parseIndex("3", "delete"));
    }

    @Test
    public void parseIndex_missingNumber_throwsUsageGuidance() {
        GoatException exception = assertThrows(GoatException.class,
                () -> tasks.parseIndex("", "mark"));

        assertEquals("Please specify a task number. Try: mark <task number>",
                exception.getMessage());
    }

    @Test
    public void parseIndex_nonNumericNumber_throwsHelpfulException() {
        GoatException exception = assertThrows(GoatException.class,
                () -> tasks.parseIndex("two", "delete"));

        assertEquals("The task number must be a positive whole number.", exception.getMessage());
    }

    @Test
    public void parseIndex_numbersOutsideList_throwsRangeGuidance() {
        GoatException belowRange = assertThrows(GoatException.class,
                () -> tasks.parseIndex("0", "mark"));
        GoatException aboveRange = assertThrows(GoatException.class,
                () -> tasks.parseIndex("4", "mark"));

        assertEquals("Task 0 does not exist. Choose a number from 1 to 3.",
                belowRange.getMessage());
        assertEquals("Task 4 does not exist. Choose a number from 1 to 3.",
                aboveRange.getMessage());
    }

    @Test
    public void parseIndex_emptyTaskList_throwsEmptyListGuidance() {
        TaskList emptyTasks = new TaskList();

        GoatException exception = assertThrows(GoatException.class,
                () -> emptyTasks.parseIndex("1", "mark"));

        assertEquals("There are no tasks in the list yet.", exception.getMessage());
    }
}
