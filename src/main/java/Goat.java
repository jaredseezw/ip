import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runs the Goat console application and manages its in-memory task list.
 */
public class Goat {
    private static final Path DATA_FILE_PATH = Path.of("data", "goat.txt");
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
     * Returns a valid task index parsed from a task-number command.
     *
     * @param argument text after the command word
     * @param taskCount current number of tasks
     * @param commandWord command whose usage should appear in error guidance
     * @return zero-based index of the requested task
     * @throws GoatException if the number is missing, non-numeric, or out of range
     */
    private static int parseTaskIndex(String argument, int taskCount, String commandWord)
            throws GoatException {
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
     * Adds a task to the task list and prints its confirmation.
     *
     * @param tasks task storage
     * @param task task to add
     */
    private static void addTask(List<Task> tasks, Task task, Storage storage) throws IOException {
        tasks.add(task);
        try {
            storage.save(tasks);
        } catch (IOException exception) {
            tasks.remove(tasks.size() - 1);
            throw exception;
        }
        printTaskAdded(task, tasks.size());
    }

    /**
     * Prints a storage warning within the same separator style as command responses.
     *
     * @param message warning to display
     * @param separator line surrounding the warning
     */
    private static void printStorageWarning(String message, String separator) {
        System.out.println(separator);
        System.out.println("OOPS!!! " + message);
        System.out.println(separator);
    }

    /**
     * Creates the guidance shown for an unsupported command.
     *
     * @return exception containing the supported command words
     */
    private static GoatException unknownCommandException() {
        return new GoatException("I don't recognise that command. Try todo, deadline, event, "
                + "list, mark, unmark, delete, or bye.");
    }

    /**
     * Starts the command loop for the application.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        Storage storage = new Storage(DATA_FILE_PATH);
        List<Task> tasks;
        String loadWarning = null;
        try {
            tasks = storage.load();
        } catch (IOException | GoatException exception) {
            tasks = new ArrayList<>();
            loadWarning = "I couldn't load saved tasks: " + exception.getMessage()
                    + " Starting with an empty task list.";
        }
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
        if (loadWarning != null) {
            printStorageWarning(loadWarning, separator);
        }

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            System.out.println(separator);

            String[] commandParts = command.split("\\s+", 2);
            String commandWord = commandParts[0];
            String argument = commandParts.length == 2 ? commandParts[1].trim() : "";

            try {
                if (command.isEmpty()) {
                    throw new GoatException("Please enter a command.");
                }

                CommandType commandType = CommandType.from(commandWord);
                switch (commandType) {
                case BYE:
                    if (!argument.isEmpty()) {
                        throw unknownCommandException();
                    }
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(separator);
                    return;
                case LIST:
                    if (!argument.isEmpty()) {
                        throw unknownCommandException();
                    }
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    break;
                case MARK:
                    int markIndex = parseTaskIndex(argument, tasks.size(), commandWord);
                    boolean wasDoneBeforeMark = tasks.get(markIndex).isDone;
                    tasks.get(markIndex).markAsDone();
                    try {
                        storage.save(tasks);
                    } catch (IOException exception) {
                        if (!wasDoneBeforeMark) {
                            tasks.get(markIndex).markAsNotDone();
                        }
                        throw exception;
                    }
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(markIndex));
                    break;
                case UNMARK:
                    int unmarkIndex = parseTaskIndex(argument, tasks.size(), commandWord);
                    boolean wasDoneBeforeUnmark = tasks.get(unmarkIndex).isDone;
                    tasks.get(unmarkIndex).markAsNotDone();
                    try {
                        storage.save(tasks);
                    } catch (IOException exception) {
                        if (wasDoneBeforeUnmark) {
                            tasks.get(unmarkIndex).markAsDone();
                        }
                        throw exception;
                    }
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(unmarkIndex));
                    break;
                case DELETE:
                    int deleteIndex = parseTaskIndex(argument, tasks.size(), commandWord);
                    Task deletedTask = tasks.remove(deleteIndex);
                    try {
                        storage.save(tasks);
                    } catch (IOException exception) {
                        tasks.add(deleteIndex, deletedTask);
                        throw exception;
                    }
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + deletedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    break;
                case TODO:
                    if (argument.isEmpty()) {
                        throw new GoatException("A todo needs a description. Try: todo <description>");
                    }
                    addTask(tasks, new Todo(argument), storage);
                    break;
                case DEADLINE:
                    Matcher deadlineMatcher = DEADLINE_ARGUMENTS.matcher(argument);
                    if (!deadlineMatcher.matches()) {
                        throw new GoatException(
                                "Use this format: deadline <description> /by <yyyy-MM-dd>");
                    }
                    String description = deadlineMatcher.group(1).trim();
                    String by = deadlineMatcher.group(2) == null ? "" : deadlineMatcher.group(2).trim();
                    if (description.isEmpty()) {
                        throw new GoatException("A deadline needs a description before /by.");
                    }
                    if (by.isEmpty()) {
                        throw new GoatException("A deadline needs a date after /by.");
                    }
                    addTask(tasks, new Deadline(description,
                            TaskDate.parse(by, "The deadline date")), storage);
                    break;
                case EVENT:
                    Matcher eventMatcher = EVENT_ARGUMENTS.matcher(argument);
                    if (!eventMatcher.matches()) {
                        throw new GoatException(
                                "Use this format: event <description> /from <start date> /to <end date>");
                    }
                    String eventDescription = eventMatcher.group(1).trim();
                    String from = eventMatcher.group(2) == null ? "" : eventMatcher.group(2).trim();
                    String to = eventMatcher.group(3) == null ? "" : eventMatcher.group(3).trim();
                    if (eventDescription.isEmpty()) {
                        throw new GoatException("An event needs a description before /from.");
                    }
                    if (from.isEmpty()) {
                        throw new GoatException("An event needs a start date after /from.");
                    }
                    if (to.isEmpty()) {
                        throw new GoatException("An event needs an end date after /to.");
                    }
                    addTask(tasks, new Event(eventDescription,
                            TaskDate.parse(from, "The event start date"),
                            TaskDate.parse(to, "The event end date")), storage);
                    break;
                case UNKNOWN:
                    throw unknownCommandException();
                default:
                    throw unknownCommandException();
                }
            } catch (GoatException exception) {
                System.out.println("OOPS!!! " + exception.getMessage());
            } catch (IOException exception) {
                System.out.println("OOPS!!! I couldn't save your tasks: " + exception.getMessage()
                        + " Your task list was not changed.");
            }
            System.out.println(separator);
        }
    }
}
