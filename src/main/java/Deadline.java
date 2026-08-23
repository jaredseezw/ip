/**
 * Represents a task that must be completed by a specified date or time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates an incomplete deadline with the given description and due date.
     *
     * @param description description of the deadline
     * @param by date or time by which the task should be completed
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline with its type, completion status, and due date.
     *
     * @return formatted deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
