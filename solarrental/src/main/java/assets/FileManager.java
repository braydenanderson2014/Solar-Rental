package assets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileManager {

    /**
     * Creates a new file.
     *
     * @param filePath the path of the file to create
     * @return true if the file was created successfully, false otherwise
     */
    public boolean createFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a file exists.
     *
     * @param filePath the path of the file to check
     * @return true if the file exists, false otherwise
     */
    public boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Reads content from a file.
     *
     * @param filePath the path of the file to read from
     * @return the content of the file as a List of Strings
     */
    public List<String> readFile(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Writes content to a file.
     *
     * @param filePath the path of the file to write to
     * @param content the content to write to the file
     * @return true if the write operation was successful, false otherwise
     */
    public boolean writeFile(String filePath, List<String> content) {
        try {
            Files.write(Paths.get(filePath), content, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
}
