package assets;

import InstallManager.ProgramController;
import Login.Login;
import MainSystem.MainMenu;
import UserController.MainSystemUserController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Notebook {
    private static Scanner scan = new Scanner(System.in);
    private static String user = Login.getCurrentUser();
    private static String notesFolderPath = ProgramController.userRunPath + File.separator + user + "_notes";

    public static boolean newNote(){
        String note = "Null";
        System.out.println("Type Done When Done Typing Notes");
        while (!note.equals("done") || !note.equals("Done")) {
            note = scan.nextLine();
            addNote(note);
        }
        return true;
    }
    public static void NotebookMenu(){
        Logo.displayLogo();
        System.out.println("Welcome to the User Notes Menu; User: " + MainSystemUserController.GetProperty("AccountName"));
        System.out.println("[Create]: Create a new Note");
        System.out.println("[LOAD]: Load a Notebook");
        System.out.println("[ADD]: Add to an Existing Note; Must have a Note");
        System.out.println("[Delete]: Delete a Note");
        System.out.println("[Return]: Return to Main Menu");

    }
    public static boolean addNote(String note){
        //userNotes.add(note);
        return true;
    }

    public static boolean deleteNote(){
        return true;
    }

    public static boolean submitNotesToFile(){
        return true;
    }

    public static String viewNotes(){
        return "NOTES";
    }

    public static String resetNotes(){
        return "NOTES";
    }

    public static String loadUserNotes(){
        return "NOTES";
    }
    private static void renameNote() {
        System.out.println("Enter the name of the note you want to rename:");
        String currentNoteName = scan.nextLine();
        File currentNote = new File(notesFolderPath + File.separator + currentNoteName + ".txt");
    
        if (!currentNote.exists()) {
            System.out.println("Note not found. Returning to notebook menu.");
            notebookMenu();
            return;
        }
    
        System.out.println("Enter the new name for the note:");
        String newNoteName = scan.nextLine();
        File newNote = new File(notesFolderPath + File.separator + newNoteName + ".txt");
    
        if (currentNote.renameTo(newNote)) {
            System.out.println("Note renamed successfully.");
        } else {
            System.out.println("Failed to rename the note.");
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
                System.out.println("Invalid option, try again");
                notebookMenu();
                break;
        }
    }
    
    private static void createNewNote() {
        // Implementation of createNewNote
    }
    
    private static void loadNote() {
        // Implementation of loadNote
    }
    
    private static void addToExistingNote() {
        // Implementation of addToExistingNote
    }
    
    
    
    
}
