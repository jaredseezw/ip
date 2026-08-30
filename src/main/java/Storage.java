import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves the current task list to a text file.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates storage that writes to the given file path.
     *
     * @param filePath path of the task data file
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Replaces the data file with a serialized copy of the current task list.
     * Creates the parent directory when it does not exist yet.
     *
     * @param tasks tasks to save
     * @throws IOException if the directory or file cannot be written
     */
    public void save(List<Task> tasks) throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        List<String> taskData = new ArrayList<>();
        for (Task task : tasks) {
            taskData.add(task.toFileString());
        }
        Files.write(filePath, taskData, StandardCharsets.UTF_8);
    }
}
