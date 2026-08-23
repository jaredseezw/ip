import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runs the Goat console application and manages its in-memory task list.
 */
public class Goat {
    private static final int MAX_TASKS = 100;
    private static final Pattern DEADLINE_ARGUMENTS =
            Pattern.compile("^(.*?)\\s*/by(?:\\s+(.*))?$");
    private static final Pattern EVENT_ARGUMENTS =
            Pattern.compile("^(.*?)\\s*/from(?:\\s+(.*?))?\\s*/to(?:\\s+(.*))?$");

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task task that was added
     * @param taskCount current number of tasks
     */
    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Returns a valid task index parsed from a mark or unmark command.
     *
     * @param argument text after the command word
     * @param taskCount current number of tasks
     * @return zero-based index of the requested task
     * @throws GoatException if the number is missing, non-numeric, or out of range
     */
    private static int parseTaskIndex(String argument, int taskCount) throws GoatException {
        if (argument.isEmpty()) {
            throw new GoatException("Please specify a task number. Try: mark <task number>");
        }

        final int taskNumber;
        try {
            taskNumber = Integer.parseInt(argument);
        } catch (NumberFormatException exception) {
            throw new GoatException("The task number must be a positive whole number.");
        }

        if (taskCount == 0) {
            throw new GoatException("There are no tasks in the list yet.");
        }
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new GoatException("Task " + taskNumber + " does not exist. Choose a number from 1 to "
                    + taskCount + ".");
        }
        return taskNumber - 1;
    }

    /**
     * Adds a task after checking that the fixed-size task list has room.
     *
     * @param tasks task storage
     * @param taskCount current number of tasks
     * @param task task to add
     * @return updated task count
     * @throws GoatException if the task list is full
     */
    private static int addTask(Task[] tasks, int taskCount, Task task) throws GoatException {
        if (taskCount == tasks.length) {
            throw new GoatException("The task list is full. Complete or remove a task before adding another.");
        }
        tasks[taskCount] = task;
        int updatedTaskCount = taskCount + 1;
        printTaskAdded(task, updatedTaskCount);
        return updatedTaskCount;
    }

    /**
     * Starts the command loop for the application.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        String separator = "____________________________________________________________";
        String banner = "  ____             _\n"
                + " / ___| ___   __ _| |_\n"
                + "| |  _ / _ \\ / _` | __|\n"
                + "| |_| | (_) | (_| | |_\n"
                + " \\____|\\___/ \\__,_|\\__|";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Goat.");
        System.out.println("What can I do for you?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            System.out.println(separator);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            }

            String[] commandParts = command.split("\\s+", 2);
            String commandWord = commandParts[0];
            String argument = commandParts.length == 2 ? commandParts[1].trim() : "";

            try {
                if (command.isEmpty()) {
                    throw new GoatException("Please enter a command.");
                } else if (commandWord.equals("list") && argument.isEmpty()) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                } else if (commandWord.equals("mark")) {
                    int taskIndex = parseTaskIndex(argument, taskCount);
                    tasks[taskIndex].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskIndex]);
                } else if (commandWord.equals("unmark")) {
                    int taskIndex = parseTaskIndex(argument, taskCount);
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskIndex]);
                } else if (commandWord.equals("todo")) {
                    if (argument.isEmpty()) {
                        throw new GoatException("A todo needs a description. Try: todo <description>");
                    }
                    taskCount = addTask(tasks, taskCount, new Todo(argument));
                } else if (commandWord.equals("deadline")) {
                    Matcher matcher = DEADLINE_ARGUMENTS.matcher(argument);
                    if (!matcher.matches()) {
                        throw new GoatException("Use this format: deadline <description> /by <date or time>");
                    }
                    String description = matcher.group(1).trim();
                    String by = matcher.group(2) == null ? "" : matcher.group(2).trim();
                    if (description.isEmpty()) {
                        throw new GoatException("A deadline needs a description before /by.");
                    }
                    if (by.isEmpty()) {
                        throw new GoatException("A deadline needs a date or time after /by.");
                    }
                    taskCount = addTask(tasks, taskCount, new Deadline(description, by));
                } else if (commandWord.equals("event")) {
                    Matcher matcher = EVENT_ARGUMENTS.matcher(argument);
                    if (!matcher.matches()) {
                        throw new GoatException(
                                "Use this format: event <description> /from <start> /to <end>");
                    }
                    String description = matcher.group(1).trim();
                    String from = matcher.group(2) == null ? "" : matcher.group(2).trim();
                    String to = matcher.group(3) == null ? "" : matcher.group(3).trim();
                    if (description.isEmpty()) {
                        throw new GoatException("An event needs a description before /from.");
                    }
                    if (from.isEmpty()) {
                        throw new GoatException("An event needs a start time after /from.");
                    }
                    if (to.isEmpty()) {
                        throw new GoatException("An event needs an end time after /to.");
                    }
                    taskCount = addTask(tasks, taskCount, new Event(description, from, to));
                } else {
                    throw new GoatException("I don't recognise that command. Try todo, deadline, event, "
                            + "list, mark, unmark, or bye.");
                }
            } catch (GoatException exception) {
                System.out.println("OOPS!!! " + exception.getMessage());
            }
            System.out.println(separator);
        }
    }
}
