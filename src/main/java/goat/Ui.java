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
     * Records a load warning to show after the welcome message.
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
     * Returns whether another command is available.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads one command line.
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
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /**
     * Displays all tasks with one-based numbering.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays confirmation that a task was added.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task was deleted.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task's completion state changed.
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
