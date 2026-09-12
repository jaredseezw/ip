package goat;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests internal assumptions enforced by task assertions.
 */
public class TaskTest {
    @Test
    public void constructor_blankDescription_assertionError() {
        assertThrows(AssertionError.class, () -> new Todo(" "));
    }
}
