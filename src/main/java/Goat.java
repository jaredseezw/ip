import java.util.Scanner;

/**
 * Runs the Goat console application and manages its in-memory task list.
 */
public class Goat {
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
     * Starts the command loop for the application.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
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
            String command = scanner.nextLine();
            System.out.println(separator);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            }

            if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (command.startsWith("todo ")) {
                tasks[taskCount] = new Todo(command.substring(5).trim());
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            } else if (command.startsWith("deadline ")) {
                String details = command.substring(9).trim();
                int byIndex = details.indexOf(" /by ");
                String description = details.substring(0, byIndex).trim();
                String by = details.substring(byIndex + 5).trim();
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            } else if (command.startsWith("event ")) {
                String details = command.substring(6).trim();
                int fromIndex = details.indexOf(" /from ");
                int toIndex = details.indexOf(" /to ", fromIndex + 7);
                String description = details.substring(0, fromIndex).trim();
                String from = details.substring(fromIndex + 7, toIndex).trim();
                String to = details.substring(toIndex + 5).trim();
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            } else {
                System.out.println(command);
            }
            System.out.println(separator);
        }
    }
}
