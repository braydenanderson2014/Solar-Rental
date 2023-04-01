package assets;

import InstallManager.ProgramController;
import Login.Login;
import MainSystem.MainMenu;
import UserController.MainSystemUserController;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.ArrayList;

import java.util.List;

import java.util.Properties;
import java.util.Scanner;

public class Notebook {
    private static ArrayList<String> currentNote = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);
    public static Properties userNotebooks = new Properties();
    private static String user = Login.getCurrentUser();
    private static String path = ProgramController.userRunPath;
    private static String currentNoteName = null;
    private static String currentNotePath = null;
    private static String notesFolderPath = path + File.separator + "Users\\Notebooks";
    public static void renameNote() {
        ConsoleHandler.getConsole();
        System.out.println("Enter the name of the note you want to rename:");
        String oldNoteName = scan.nextLine().trim();
        File oldNoteFile = new File(notesFolderPath, oldNoteName + ".txt");

        if (!oldNoteFile.exists()) {
            MessageProcessor.processMessage(-1, "Note not found. Please try again.", true);
            renameNote();
            return;
        }

        System.out.println("Enter the new name for the note:");
        String newNoteName = scan.nextLine().trim();
        File newNoteFile = new File(notesFolderPath, newNoteName + ".txt");
        MessageProcessor.processMessage(1, newNoteName.toString(), false);
        if (newNoteFile.exists()) {
            MessageProcessor.processMessage(-1, "A note with this name already exists. Please try again.", true);
            renameNote();
            return;
        }

        if (oldNoteFile.renameTo(newNoteFile)) {
            userNotebooks.setProperty(newNoteName, newNoteFile.getPath());
            userNotebooks.remove(oldNoteName);
            saveProperties();
            MessageProcessor.processMessage(1, "Note renamed successfully!", true);
        } else {
            MessageProcessor.processMessage(-1, "Failed to rename the note. Please try again.", true);
        }

        notebookMenu();
    }
    public static void notebookMenu() {
        Logo.displayLogo();
        System.out.println("Welcome to the User Notes Menu; User: " + MainSystemUserController.GetProperty("AccountName"));
        System.out.println("[Create]: Create a new Note");
        System.out.println("[Load]: Load a Notebook");
        System.out.println("[Add]: Add to an Existing Note; Must have a Note");
        System.out.println("[Delete]: Delete a Note");
        System.out.println("[Rename]: Rename a Note");
        System.out.println("[Return]: Return to Main Menu");
        ConsoleHandler.getConsole();
        String option = scan.nextLine().toLowerCase();
        switch (option) {
            case "create":
                createNewNote();
                break;
            case "load":
                loadNote();
                break;
            case "add":
                addToExistingNote();
                break;
            case "delete":
                deleteNote();
                break;
            case "rename":
                renameNote();
                break;
            case "return":
                MainMenu.mainMenu();
                break;
            default:
                MessageProcessor.processMessage(-1, "Invalid option, try again", true);
                notebookMenu();
                break;
        }
    }
    
    private static void createNewNote() {
        System.out.println("Enter the name of the new note:");
        String newNoteName = scan.nextLine();
        File newNote = new File(notesFolderPath + File.separator + newNoteName + ".txt");
        MessageProcessor.processMessage(1, newNote.toString(), false);
        MessageProcessor.processMessage(1, notesFolderPath + File.separator + newNoteName + ".txt", true);
        userNotebooks.setProperty(newNoteName, newNote.toString());
        try {
            if (newNote.createNewFile()) {
                MessageProcessor.processMessage(1, "Note created: " + newNoteName, true);
            } else {
                MessageProcessor.processMessage(-1, "Note already exists.", true);
            }
            saveProperties();
        } catch (IOException e) {
            MessageProcessor.processMessage(-2, "Error creating note: " + e.getMessage(), true);
        }
    
        notebookMenu();
    }
    
    
    public static void loadNote() {
        loadProperties();
        System.out.println("Enter the name of the note you want to load or type 'exit' to go back to the menu:");
        String noteName = scan.nextLine().trim();
    
        if (noteName.equalsIgnoreCase("exit")) {
            notebookMenu();
            return;
        }
    
        String notePath = userNotebooks.getProperty(noteName);
        currentNotePath = notePath;
        if (notePath == null) {
            MessageProcessor.processMessage(-1, "Note not found. Please try again.", true);
            loadNote();
            return;
        }
    
        File noteFile = new File(notePath);
        if (!noteFile.exists()) {
            MessageProcessor.processMessage(-1, "Note file not found. Please try again.", true);
            loadNote();
            return;
        }
    
        try {
            List<String> noteLines = Files.readAllLines(noteFile.toPath(), StandardCharsets.UTF_8);
            currentNote.clear();
            currentNote.addAll(noteLines);
            currentNoteName = noteName;
            MessageProcessor.processMessage(1, "Note loaded successfully!", true);
        } catch (IOException e) {
            MessageProcessor.processMessage(-1, "Failed to load the note. Please try again.", true);
        }
    
        notebookMenu();
    }
    
    
    
    
    public static void addToExistingNote() {
        if (currentNote != null) {
            System.out.println("Enter the text you want to add to the note (Type 'done' when finished):");
            String input = "";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(currentNotePath, true))) {
                while (!(input = CustomScanner.nextLine()).equalsIgnoreCase("done")) {
                    bw.write(input + System.lineSeparator());
                }
            } catch (IOException e) {
                System.err.println("Error while writing to the note: " + e.getMessage());
                MessageProcessor.processMessage(-2, "Error while writing to the note: " + e.getMessage(), false);
            }
            MessageProcessor.processMessage(1, "Note successfully updated.", true);
            notebookMenu();
        } else {
            MessageProcessor.processMessage(-1, "No note is currently loaded. Please load a note first.", true);
            notebookMenu();
        }
    }
    
    public static void deleteNote() {
        System.out.println("Enter the name of the note you want to delete:");
        String noteName = CustomScanner.nextLine();
    
        if (userNotebooks.containsKey(noteName)) {
            File noteFile = new File(userNotebooks.getProperty(noteName));
            noteFile.delete();
            userNotebooks.remove(noteName);
            saveProperties();
            MessageProcessor.processMessage(1, "Note deleted successfully.", true);
        } else {
            MessageProcessor.processMessage(-1 ,"No note found with the given name.", true);
        }
    }
    
    public static void deleteLine() {
        if (currentNote != null) {
            System.out.println("Enter the line number to delete:");
            int lineNumber;
            try {
                lineNumber = Integer.parseInt(CustomScanner.nextLine());
            } catch (NumberFormatException e) {
                MessageProcessor.processMessage(-2, "Invalid input. Please enter a valid line number.", true);
                return;
            }
    
            if (lineNumber > 0 && lineNumber <= currentNote.size()) {
                currentNote.remove(lineNumber - 1);
                saveCurrentNote();
                MessageProcessor.processMessage(1, "Line deleted successfully.", true);
            } else {
                MessageProcessor.processMessage(-1, "Invalid line number. Please enter a valid line number.", true);
            }
            notebookMenu();
        } else {
            MessageProcessor.processMessage(-1, "No note is currently loaded. Load a note first.", true);
            notebookMenu();
        }
    }
    public static void saveCurrentNote() {
        if (currentNote != null && currentNoteName != null) {
            File noteFile = new File(notesFolderPath, currentNoteName + ".txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(noteFile))) {
                for (String line : currentNote) {
                    bw.write(line);
                    bw.newLine();
                }
                MessageProcessor.processMessage(1, "Current note saved successfully!", true);
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, "Error saving current note: " + e.getMessage(), true);
            }
        } else {
            MessageProcessor.processMessage(-1, "No note is currently loaded. Load a note first.", true);
        }
    }
    
    
    public static void saveProperties() {
        try {
            File userPropertiesFile = new File(notesFolderPath + File.separator + user + ".properties");
            try (FileOutputStream fos = new FileOutputStream(userPropertiesFile)) {
                userNotebooks.store(fos, "User Notebooks");
            }
        } catch (IOException e) {
            MessageProcessor.processMessage(-2, "Error saving properties: " + e.getMessage(), true);
        }
    }
    
    public static void loadProperties() {
        File userPropertiesFile = new File(notesFolderPath + File.separator + user + ".properties");
        if (userPropertiesFile.exists()) {
            try (FileInputStream fis = new FileInputStream(userPropertiesFile)) {
                userNotebooks.load(fis);
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, "Error loading properties: " + e.getMessage(), true);
            }
        }
    }
    
    
    
}
