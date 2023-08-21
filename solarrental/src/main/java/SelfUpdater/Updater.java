package SelfUpdater;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;

import messageHandler.MessageProcessor;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Updater {

    private static final Path REPO_PATH = Paths.get("/Users/braydenanderson/Documents/GitHub/Solar-Rental-Mac_Edition");
    private static final String REPO_URL = "https://github.com/braydenanderson2014/Solar-Rental-Mac_Edition.git"; // URL
    private static final Path COMPILED_FOLDER = Path.of("compiled");

    @SuppressWarnings({ "resource", "static-method" })
	public void updateFromGitHub() throws IOException, GitAPIException {
        MessageProcessor.processMessage(2, "Checking for Updates", true);
        File folder = REPO_PATH.toFile();
        MessageProcessor.processMessage(2, folder.toString(), true);
        if (folder.exists()) {
            MessageProcessor.processMessage(2, "Pulling from GitHub", true);
            try (Git git = Git.open(folder)) {
                git.pull().call();
            }
        } else {
            MessageProcessor.processMessage(2, "Cloning from GitHub", true);
            Git.cloneRepository()
                    .setURI(REPO_URL)
                    .setDirectory(folder)
                    .call();
        }
    }

    @SuppressWarnings("static-method")
	public void compileSourceCode() throws IOException {
        MessageProcessor.processMessage(2, "Compiling Code...", true);
                                                                                   JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            MessageProcessor.processMessage(-1,
                    "Cannot find system Java compiler. Ensure JDK is installed and properly set up.", true);
            throw new RuntimeException(
                    "Cannot find system Java compiler. Ensure JDK is installed and properly set up.");
        }

        List<String> sourceFiles = new ArrayList<>();
        Files.walk(REPO_PATH)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> sourceFiles.add(path.toString()));

        int result = compiler.run(null, null, null, sourceFiles.toArray(new String[0]));

        if (result != 0) {
            MessageProcessor.processMessage(-1, "Failed to compile source code.", true);
            throw new RuntimeException("Failed to compile source code.");
        }

        // You would need to create a JAR file from these .class files
        // This step is omitted here for simplicity
    }

    @SuppressWarnings("static-method")
	public void deleteSourceCode() throws IOException {
        MessageProcessor.processMessage(2, "Deleting Source Code...", true);
        Files.walk(REPO_PATH)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                    	StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        String stackTrace = sw.toString();

                        MessageProcessor.processMessage(2, stackTrace, true);
                        throw new RuntimeException(e);
                    }
                });
    }

    @SuppressWarnings("static-method")
	public void compileWithMaven() throws IOException {
        MessageProcessor.processMessage(2, "Compiling with Maven...", true);
        ProcessBuilder pb = new ProcessBuilder("mvn", "clean", "install");
        pb.directory(REPO_PATH.toFile()); // Set the working directory to your project directory
        Process p = pb.start(); // Start the process

        // If you want to read the output
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            MessageProcessor.processMessage(-2, e.toString(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            e.printStackTrace();
        }

        // Wait for the process to finish and check the exit value
        try {
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Maven compile failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            MessageProcessor.processMessage(-2, e.toString(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            throw new RuntimeException("Maven compile process was interrupted", e);
        }
    }

    public void startUpdate() throws IOException, InterruptedException {
        MessageProcessor.processMessage(1, "Starting Update", true);
        try {
            updateFromGitHub();
        } catch (GitAPIException e) {
            MessageProcessor.processMessage(-2, e.toString(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            e.printStackTrace();
        }
        compileSourceCode();

        compileWithMaven();
        deleteSourceCode();
        //SetupController.finishStart();
    }

    @SuppressWarnings("static-method")
	public boolean isUpdateAvailable() throws IOException, InterruptedException, NoHeadException, GitAPIException {
        MessageProcessor.processMessage(2, "Checking if update is available...", true);
        System.out.println("Checking if update is available...");
        File folder = REPO_PATH.toFile();
        if (!folder.exists()) {
            MessageProcessor.processMessage(-1, folder + " not found", true);
            System.out.println(folder + " not found");
            return true;
        }

        try (Git git = Git.open(folder)) {
            String currentCommit = git.log().setMaxCount(1).call().iterator().next().getName();
            MessageProcessor.processMessage(2, "Current commit: " + currentCommit, true);
            System.out.println("Current commit: " + currentCommit);
            git.fetch().call();
            String latestCommit = git.log().setMaxCount(1).call().iterator().next().getName();
            MessageProcessor.processMessage(2, "Latest commit: " + latestCommit, true);
            System.out.println("Latest commit: " + latestCommit);
            boolean updateAvailable = !currentCommit.equals(latestCommit);
            MessageProcessor.processMessage(2, "Update available: " + updateAvailable, true);
            System.out.println("Update available: " + updateAvailable);
            return updateAvailable;
        }
    }

}
