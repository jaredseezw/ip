/**
 * Represents a task in the task list.
 */
public abstract class Task {
    protected String description;

    /**
     * Creates a task with the given description.
     *
     * @param description description of the task
     */
    public Task(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "[ ] " + description;
    }
}
