package assets;
import java.util.ArrayList;
import java.util.Scanner;

import InstallManager.ProgramController;
import Login.Login;
public class Notebook {
    private static final String NOTES = "Notes";
	private static ArrayList<String>userNotes = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);
    private static String user = Login.getCurrentUser();
    private static String path = ProgramController.userRunPath;
    private static String FullPath;
    public static boolean newNote(){
        String note = "Null";
        System.out.println("Type Done When Done Typing Notes");
        while (!note.equals("done") || !note.equals("Done")) {
            note = scan.nextLine();
            addNote(note);
        }
        return true;
    }

    public static boolean addNote(String note){
        userNotes.add(note);
        return true;
    }

    public static boolean deleteNote(){
        return true;
    }

    public static boolean submitNotesToFile(){
        return true;
    }

    public static String viewNotes(){
        return NOTES;
    }

    public static String resetNotes(){
        return NOTES;
    }

    public static String loadUserNotes(){
        return NOTES;
    }
}