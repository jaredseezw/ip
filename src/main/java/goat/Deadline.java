package goat;

import java.time.LocalDate;

/**
 * Represents a task that must be completed by a specified date.
 */
public class Deadline extends Task {
    /** Due date of this deadline. */
    protected LocalDate by;

    /**
     * Creates an incomplete deadline with the given description and due date.
     *
     * @param description description of the deadline
     * @param by date by which the task should be completed
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns this deadline in the data-file format.
     *
     * @return serialized deadline data
     */
    @Override
    public String toFileString() {
        return formatFileData("D", by.toString());
    }

    /**
     * Returns the deadline with its type, completion status, and due date.
     *
     * @return formatted deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + TaskDate.format(by) + ")";
    }
}
