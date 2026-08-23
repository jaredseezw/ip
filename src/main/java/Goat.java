import java.util.Scanner;

/**
 * Runs the Goat console application.
 */
public class Goat {
    /**
     * Starts the application, greets the user, and waits for the exit command.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
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
            if (command.equals("bye")) {
                System.out.println(separator);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            }
            System.out.println(separator);
            System.out.println(command);
            System.out.println(separator);
        }
    }
}
