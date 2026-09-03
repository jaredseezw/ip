package goat;

import java.util.Scanner;

/**
 * Reads commands and displays all console messages.
 */
public class Ui {
    private static final String SEPARATOR =
            "____________________________________________________________";
    private static final String BANNER = "  ____             _\n"
            + " / ___| ___   __ _| |_\n"
            + "| |  _ / _ \\ / _` | __|\n"
            + "| |_| | (_) | (_| | |_\n"
            + " \\____|\\___/ \\__,_|\\__|";

    private final Scanner scanner = new Scanner(System.in);
    private String pendingLoadWarning;

    /**
     * Creates a console UI that reads from standard input.
     */
    public Ui() {
    }

    /**
     * Records a load warning to show after the welcome message.
     *
     * @param message storage error message
     */
    public void showLoadWarning(String message) {
        pendingLoadWarning = "I couldn't load saved tasks: " + message
                + " Starting with an empty task list.";
    }

    /**
     * Displays the banner, greeting, and any startup warning.
     */
    public void showWelcome() {
        showLine();
        System.out.println(BANNER);
        System.out.println("Hello! I'm Goat.");
        System.out.println("What can I do for you?");
        showLine();
        if (pendingLoadWarning != null) {
            showLine();
            showError(pendingLoadWarning);
            showLine();
        }
    }

    /**
     * Returns the greeting and any warning raised while loading saved tasks.
     *
     * @return startup message suitable for the GUI
     */
    public String getWelcomeMessage() {
        String message = "Hello! I'm Goat.\nWhat can I do for you?";
        if (pendingLoadWarning != null) {
            message += "\n\nOOPS!!! " + pendingLoadWarning;
        }
        return message;
    }

    /**
     * Returns whether another command is available.
     *
     * @return {@code true} if another line can be read
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads one command line.
     *
     * @return next command line
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the standard response separator.
     */
    public void showLine() {
        System.out.println(SEPARATOR);
    }

    /**
     * Displays an input or storage error.
     *
     * @param message error details
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /**
     * Displays one complete response.
     *
     * @param response response to display
     */
    public void showResponse(String response) {
        System.out.println(response);
    }

    /**
     * Formats an input or storage error.
     *
     * @param message error details
     * @return formatted error
     */
    public String formatError(String message) {
        return "OOPS!!! " + message;
    }

    /**
     * Formats all tasks with one-based numbering.
     *
     * @param tasks tasks to format
     * @return formatted task list
     */
    public String formatTaskList(TaskList tasks) {
        StringBuilder result = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            result.append("\n").append(i + 1).append(".").append(tasks.get(i));
        }
        return result.toString();
    }

    /**
     * Formats tasks that matched a search keyword.
     *
     * @param tasks matching tasks
     * @return formatted matching tasks
     */
    public String formatMatchingTasks(TaskList tasks) {
        StringBuilder result = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            result.append("\n").append(i + 1).append(".").append(tasks.get(i));
        }
        return result.toString();
    }

    /**
     * Formats confirmation that a task was added.
     *
     * @param task added task
     * @param taskCount resulting task count
     * @return formatted confirmation
     */
    public String formatTaskAdded(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Formats confirmation that a task was deleted.
     *
     * @param task deleted task
     * @param taskCount resulting task count
     * @return formatted confirmation
     */
    public String formatTaskDeleted(Task task, int taskCount) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Formats confirmation that a task's completion state changed.
     *
     * @param task changed task
     * @param isDone new completion state
     * @return formatted confirmation
     */
    public String formatCompletionChanged(Task task, boolean isDone) {
        if (isDone) {
            return "Nice! I've marked this task as done:\n  " + task;
        }
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns the exit message.
     *
     * @return exit message
     */
    public String getGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays all tasks with one-based numbering.
     *
     * @param tasks tasks to display
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays tasks that matched a search keyword.
     *
     * @param tasks matching tasks to display
     */
    public void showMatchingTasks(TaskList tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task added task
     * @param taskCount resulting task count
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task was deleted.
     *
     * @param task deleted task
     * @param taskCount resulting task count
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task's completion state changed.
     *
     * @param task changed task
     * @param isDone new completion state
     */
    public void showCompletionChanged(Task task, boolean isDone) {
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + task);
    }

    /**
     * Displays the exit message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
