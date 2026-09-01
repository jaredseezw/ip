package goat;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Coordinates user interaction, command parsing, task management, and storage.
 */
public class Goat {
    private static final Path DATA_FILE_PATH = Path.of("data", "goat.txt");

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates an application that stores tasks at the given path.
     *
     * @param filePath path of the task data file
     */
    public Goat(Path filePath) {
        storage = new Storage(filePath);
        ui = new Ui();

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (IOException | GoatException exception) {
            loadedTasks = new TaskList();
            ui.showLoadWarning(exception.getMessage());
        }
        tasks = loadedTasks;
    }

    /**
     * Runs the application until the input ends or the user exits.
     */
    public void run() {
        ui.showWelcome();
        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            ui.showLine();
            try {
                ParsedCommand command = Parser.parse(input);
                if (execute(command)) {
                    ui.showGoodbye();
                    ui.showLine();
                    return;
                }
            } catch (GoatException exception) {
                ui.showError(exception.getMessage());
            } catch (IOException exception) {
                ui.showError("I couldn't save your tasks: " + exception.getMessage()
                        + " Your task list was not changed.");
            }
            ui.showLine();
        }
    }

    /**
     * Executes one parsed command.
     */
    private boolean execute(ParsedCommand command) throws GoatException, IOException {
        switch (command.type()) {
            case BYE:
                Parser.requireNoArgument(command);
                return true;
            case LIST:
                Parser.requireNoArgument(command);
                ui.showTaskList(tasks);
                break;
            case MARK:
                updateCompletion(command.argument(), command.commandWord(), true);
                break;
            case UNMARK:
                updateCompletion(command.argument(), command.commandWord(), false);
                break;
            case DELETE:
                deleteTask(command.argument(), command.commandWord());
                break;
            case TODO:
                addTask(Parser.parseTodo(command.argument()));
                break;
            case DEADLINE:
                addTask(Parser.parseDeadline(command.argument()));
                break;
            case EVENT:
                addTask(Parser.parseEvent(command.argument()));
                break;
            case UNKNOWN:
                throw Parser.unknownCommandException();
            default:
                throw Parser.unknownCommandException();
        }
        return false;
    }

    /**
     * Adds and saves a task, rolling back if saving fails.
     */
    private void addTask(Task task) throws IOException {
        tasks.add(task);
        try {
            storage.save(tasks.asList());
        } catch (IOException exception) {
            tasks.delete(tasks.size() - 1);
            throw exception;
        }
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Changes and saves a task's completion state, rolling back if saving fails.
     */
    private void updateCompletion(String argument, String commandWord, boolean isDone)
            throws GoatException, IOException {
        int index = tasks.parseIndex(argument, commandWord);
        Task task = tasks.get(index);
        boolean previousState = task.isDone;
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        try {
            storage.save(tasks.asList());
        } catch (IOException exception) {
            if (previousState) {
                task.markAsDone();
            } else {
                task.markAsNotDone();
            }
            throw exception;
        }
        ui.showCompletionChanged(task, isDone);
    }

    /**
     * Deletes and saves a task, restoring it if saving fails.
     */
    private void deleteTask(String argument, String commandWord)
            throws GoatException, IOException {
        int index = tasks.parseIndex(argument, commandWord);
        Task deletedTask = tasks.delete(index);
        try {
            storage.save(tasks.asList());
        } catch (IOException exception) {
            tasks.add(index, deletedTask);
            throw exception;
        }
        ui.showTaskDeleted(deletedTask, tasks.size());
    }

    /**
     * Starts Goat using its default relative data-file path.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        new Goat(DATA_FILE_PATH).run();
    }
}
