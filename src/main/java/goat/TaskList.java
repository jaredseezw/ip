package goat;

import java.util.ArrayList;
import java.util.List;

/**
 * Owns the application's task collection and task-index operations.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Creates a task list containing the supplied tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Returns the current number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at a zero-based index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the end of the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Inserts a task at a zero-based index.
     */
    public void add(int index, Task task) {
        tasks.add(index, task);
    }

    /**
     * Removes and returns a task at a zero-based index.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns an immutable snapshot for display or storage.
     */
    public List<Task> asList() {
        return List.copyOf(tasks);
    }

    /**
     * Parses and validates a one-based task number entered by the user.
     */
    public int parseIndex(String argument, String commandWord) throws GoatException {
        if (argument.isEmpty()) {
            throw new GoatException("Please specify a task number. Try: " + commandWord
                    + " <task number>");
        }

        final int taskNumber;
        try {
            taskNumber = Integer.parseInt(argument);
        } catch (NumberFormatException exception) {
            throw new GoatException("The task number must be a positive whole number.");
        }

        if (tasks.isEmpty()) {
            throw new GoatException("There are no tasks in the list yet.");
        }
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new GoatException("Task " + taskNumber + " does not exist. Choose a number from 1 to "
                    + tasks.size() + ".");
        }
        return taskNumber - 1;
    }
}
