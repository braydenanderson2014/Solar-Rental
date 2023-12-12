package assets;

import InstallManager.ProgramController;
import Login.Login;
import Login.SwitchController;
import MainSystem.Main;
import MainSystem.MainMenu;
import UserController.MainSystemUserController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;
import java.util.stream.Collectors;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

public class Notebook {
    public static List<String> currentNote = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);
    public static Properties userNotebooks = new Properties();
    private static String user = SwitchController.focusUser;
    private static String path = ProgramController.userRunPath;
    public static String currentNoteName = null;
    public static String currentNotePath = null;
    public static String notesFolderPath = path + File.separator + "Users" + File.separator + "Notebooks" + File.separator + user + File.separator + "Notebooks";
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
    public static void renameNoteUI(Stage stage) {
        // Dialog for old note name
        TextInputDialog oldNoteNameDialog = new TextInputDialog();
        oldNoteNameDialog.setTitle("Rename Note");
        oldNoteNameDialog.setHeaderText("Enter the name of the note you want to rename:");
        Optional<String> oldNoteNameResult = oldNoteNameDialog.showAndWait();

        if (oldNoteNameResult.isPresent()) {
            String oldNoteName = oldNoteNameResult.get().trim();
            File oldNoteFile = new File(notesFolderPath, oldNoteName + ".txt");

            if (!oldNoteFile.exists()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Note not found. Please try again.");
                alert.showAndWait();
                return;
            }

            // Dialog for new note name
            TextInputDialog newNoteNameDialog = new TextInputDialog();
            newNoteNameDialog.setTitle("Rename Note");
            newNoteNameDialog.setHeaderText("Enter the new name for the note:");
            Optional<String> newNoteNameResult = newNoteNameDialog.showAndWait();

            if (newNoteNameResult.isPresent()) {
                String newNoteName = newNoteNameResult.get().trim();
                File newNoteFile = new File(notesFolderPath, newNoteName + ".txt");
                MessageProcessor.processMessage(1, newNoteName.toString(), false);

                if (newNoteFile.exists()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("A note with this name already exists. Please try again.");
                    alert.showAndWait();
                    return;
                }

                if (oldNoteFile.renameTo(newNoteFile)) {
                    userNotebooks.setProperty(newNoteName, newNoteFile.getPath());
                    userNotebooks.remove(oldNoteName);
                    saveProperties();
                    MessageProcessor.processMessage(1, "Note renamed successfully!", true);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to rename the note. Please try again.");
                    alert.showAndWait();
                }

                notebookMenuUI(stage);
            }
        }
    }

    public static void notebookMenu() {
        Logo.displayLogo();
        user = SwitchController.focusUser;
        System.out.println("Welcome to the User Notes Menu; User: " + MainSystemUserController.GetProperty("AccountName"));
        System.out.println("[Create]: Create a new Note");
        if(userNotebooks.size() == 0 || currentNoteName == null) {
            System.out.println("[Load]: Load an Existing Note");
        }else{
            System.out.println("[Load]: Load an Existing Note; Current Loaded Note: " + currentNoteName);
        }
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
    public static void notebookMenuUI(Stage stage) {
    	Main.reloadUI(stage);
        VBox vbox = new VBox(20); // add your buttons to this vbox
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        Label welcomeLabel = new Label("Welcome to the User Notes Menu; User: " + MainSystemUserController.GetProperty("AccountName"));
        vbox.getChildren().add(welcomeLabel);
        
        Button createButton = new Button("CREATE: Create a new Note");
        
        createButton.setOnAction(e -> {
            createNewNoteUI(stage);
        });
        vbox.getChildren().add(createButton);
        
        String loadButtonText = (userNotebooks.size() == 0 || currentNoteName == null) ?
            "LOAD: Load an Existing Note" : "LOAD: Load an Existing Note; Current Loaded Note: " + currentNoteName;
        Button loadButton = new Button(loadButtonText);
        loadButton.setOnAction(e -> {
            loadNoteUI(stage);
        });
        vbox.getChildren().add(loadButton);
        
        String addButtonText = "ADD: Add to an Existing Note; Must have a Note";
        Button addButton = new Button(addButtonText);
        addButton.setOnAction(e -> {
            addToExistingNoteUI(stage);
        });
        vbox.getChildren().add(addButton);
        
        Button deleteButton = new Button("DELETE: Delete a Note");
        deleteButton.setOnAction(e -> {
            deleteNoteUI(stage);
        });
        vbox.getChildren().add(deleteButton);
        
        Button renameButton = new Button("RENAME: Rename a Note");
        renameButton.setOnAction(e -> {
            renameNoteUI(stage);
        });
        vbox.getChildren().add(renameButton);
        
        Button returnButton = new Button("RETURN: Return to Main Menu");
        returnButton.setOnAction(e -> {
            MainMenu.showMainMenu(stage);
        });
        Platform.runLater(new Runnable() {
			public void run() {
				stage.hide();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				    e.printStackTrace(pw);
				    String stackTrace = sw.toString();

				    MessageProcessor.processMessage(2, stackTrace, true);
				}
				stage.show();
			}
		});


        vbox.getChildren().add(returnButton);
        vbox.setFillWidth(true);

        Scene notebookMenuScene = new Scene(vbox);
        stage.setScene(notebookMenuScene);
        stage.setTitle("Notebook Menu");
        stage.show();
    }

    private static void createNewNote() {
        user = SwitchController.focusUser; // Add this line to update the user
        notesFolderPath = path + File.separator + "Users" + File.separator + "Notebooks" + File.separator + user + File.separator + "Notebooks"; // Add this line to update the notesFolderPath
        File file = new File(notesFolderPath);
        if (!file.exists()) {
            file.mkdirs();
            MessageProcessor.processMessage(1, "Created New Directory at: " + notesFolderPath, false);
        }
        file = new File(notesFolderPath + File.separator + user + ".properties");
        if(!file.exists()) {
        	try {
				file.createNewFile();
			} catch (IOException e) {
				MessageProcessor.processMessage(-2, "Failed to create Properties File!", true);
	    		StringWriter sw = new StringWriter();
			    PrintWriter pw = new PrintWriter(sw);
			    e.printStackTrace(pw);
			    String stackTrace = sw.toString();

			    MessageProcessor.processMessage(2, stackTrace, true);
			}
        }
        boolean creatingNote = true;
        while (creatingNote) {
            System.out.println("Enter the name of the new note:");
            String newNoteName = scan.nextLine();
            File newNote = new File(notesFolderPath + File.separator + newNoteName + ".txt");
            
            if (newNote.exists()) {
                MessageProcessor.processMessage(-1, "Note already exists.", true);
                System.out.println("Do you want to rename the file? Type 'yes' to rename, or any other key to go back to the menu:");
                String userInput = scan.nextLine();
                if (userInput.equalsIgnoreCase("yes")) {
                    continue;
                }
				break;
            }
			userNotebooks.setProperty(newNoteName, newNote.toString());
			try {
			    if (newNote.createNewFile()) {
			        MessageProcessor.processMessage(1, "Note created: " + newNoteName, true);
			    }
			    saveProperties();
			} catch (IOException e) {
			    MessageProcessor.processMessage(-2, "Error creating note: " + e.getMessage(), true);
			    StringWriter sw = new StringWriter();
			    PrintWriter pw = new PrintWriter(sw);
			    e.printStackTrace(pw);
			    String stackTrace = sw.toString();

			    MessageProcessor.processMessage(2, stackTrace, true);
			}
			creatingNote = false;
        }
        notebookMenu();
    }
    
    private static void createNewNoteUI(Stage stage) {
        user = SwitchController.focusUser; 
        notesFolderPath = path + File.separator + "Users" + File.separator + "Notebooks" + File.separator + user + File.separator + "Notebooks"; 
        File file = new File(notesFolderPath);
        if (!file.exists()) {
            file.mkdirs();
            MessageProcessor.processMessage(1, "Created New Directory at: " + notesFolderPath, false);
        }

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        Label instructionLabel = new Label("Enter the name of the new note:");
        TextField noteNameTextField = new TextField();
        Button createButton = new Button("Create Note");
        Label resultLabel = new Label("");
        
        createButton.setOnAction(e -> {
            String newNoteName = noteNameTextField.getText();
            File newNote = new File(notesFolderPath + File.separator + newNoteName + ".txt");
            
            if (newNote.exists()) {
                resultLabel.setText("Note already exists.");
            } else {
                userNotebooks.setProperty(newNoteName, newNote.toString());
                try {
                    if (newNote.createNewFile()) {
                        resultLabel.setText("Note created: " + newNoteName);
                        MessageProcessor.processMessage(1, "Note created: " + newNoteName, true);
                        saveProperties();
                        notebookMenuUI(stage);
                    }
                } catch (IOException ex) {
                    resultLabel.setText("Error creating note: " + ex.getMessage());
                    MessageProcessor.processMessage(-2, "Error creating note: " + ex.getMessage(), true);
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String stackTrace = sw.toString();

                    MessageProcessor.processMessage(2, stackTrace, true);
                }
            }
        });
        GridPane gridPane = new GridPane();
		Scene scene = new Scene(gridPane , 300, 200);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
            	String newNoteName = noteNameTextField.getText();
                File newNote = new File(notesFolderPath + File.separator + newNoteName + ".txt");
                
                if (newNote.exists()) {
                    resultLabel.setText("Note already exists.");
                } else {
                    userNotebooks.setProperty(newNoteName, newNote.toString());
                    try {
                        if (newNote.createNewFile()) {
                            resultLabel.setText("Note created: " + newNoteName);
                            MessageProcessor.processMessage(1, "Note created: " + newNoteName, true);
                            saveProperties();
                            notebookMenuUI(stage);
                        }
                    } catch (IOException ex) {
                        resultLabel.setText("Error creating note: " + ex.getMessage());
                        MessageProcessor.processMessage(-2, "Error creating note: " + ex.getMessage(), true);
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        ex.printStackTrace(pw);
                        String stackTrace = sw.toString();

                        MessageProcessor.processMessage(2, stackTrace, true);
                    }
                }
                event.consume();
            }
        });
        stage.setScene(scene);
        vbox.getChildren().addAll(instructionLabel, noteNameTextField, createButton, resultLabel, gridPane);
        
        Scene createNoteScene = new Scene(vbox, 400, 400);
        stage.setScene(createNoteScene);
        stage.setTitle("Create New Note");
        stage.show();
    }

    public static void loadNote() {
        user = SwitchController.focusUser; // Add this line to update the user
        MessageProcessor.processMessage(2, user + " Loading Note", true);
        notesFolderPath = path + File.separator + "Users" + File.separator + "Notebooks" + File.separator + user + File.separator + "Notebooks"; // Add this line to update the notesFolderPath
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
            MessageProcessor.processMessage(-2, e.toString(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
        }
    
        notebookMenu();
    }
    
    public static void loadNoteUI(Stage stage) {
        user = SwitchController.focusUser; 
        notesFolderPath = path + File.separator + "Users" + File.separator + "Notebooks" + File.separator + user + File.separator + "Notebooks"; 
        loadProperties();

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        Label instructionLabel = new Label("Enter the name of the note you want to load:");
        TextField noteNameTextField = new TextField();
        Button loadButton = new Button("Load Note");
        Label resultLabel = new Label("");
        Button cancelButton = new Button("Cancel");  // New cancel button
        
        // Cancel button action
        cancelButton.setOnAction(e -> {
            notebookMenuUI(stage);
        });

        loadButton.setOnAction(e -> {
            String noteName = noteNameTextField.getText().trim();
            String notePath = userNotebooks.getProperty(noteName);
            currentNotePath = notePath;
            if (notePath == null) {
                resultLabel.setText("Note not found. Please try again.");
                MessageProcessor.processMessage(-1, "Note not found. Please try again.", true);
                return;
            }
        
            File noteFile = new File(notePath);
            if (!noteFile.exists()) {
                resultLabel.setText("Note file not found. Please try again.");
                MessageProcessor.processMessage(-1, "Note file not found. Please try again.", true);
                return;
            }
        
            try {
                List<String> noteLines = Files.readAllLines(noteFile.toPath(), StandardCharsets.UTF_8);
                currentNote.clear();
                currentNote.addAll(noteLines);
                currentNoteName = noteName;
                resultLabel.setText("Note loaded successfully!");
                MessageProcessor.processMessage(1, "Note loaded successfully!", true);
                notebookMenuUI(stage);
            } catch (IOException ex) {
                resultLabel.setText("Failed to load the note. Please try again.");
                MessageProcessor.processMessage(-1, "Failed to load the note. Please try again.", true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
            }
        });
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 300, 200);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
            	String noteName = noteNameTextField.getText().trim();
                String notePath = userNotebooks.getProperty(noteName);
                currentNotePath = notePath;
                if (notePath == null) {
                    resultLabel.setText("Note not found. Please try again.");
                    MessageProcessor.processMessage(-1, "Note not found. Please try again.", true);
                    return;
                }
            
                File noteFile = new File(notePath);
                if (!noteFile.exists()) {
                    resultLabel.setText("Note file not found. Please try again.");
                    MessageProcessor.processMessage(-1, "Note file not found. Please try again.", true);
                    return;
                }
            
                try {
                    List<String> noteLines = Files.readAllLines(noteFile.toPath(), StandardCharsets.UTF_8);
                    currentNote.clear();
                    currentNote.addAll(noteLines);
                    currentNoteName = noteName;
                    resultLabel.setText("Note loaded successfully!");
                    MessageProcessor.processMessage(1, "Note loaded successfully!", true);
                    notebookMenuUI(stage);
                } catch (IOException ex) {
                    resultLabel.setText("Failed to load the note. Please try again.");
                    MessageProcessor.processMessage(-1, "Failed to load the note. Please try again.", true);
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String stackTrace = sw.toString();

                    MessageProcessor.processMessage(2, stackTrace, true);
                }
                event.consume();
            }
        });
        vbox.getChildren().addAll(instructionLabel, noteNameTextField, loadButton, cancelButton, resultLabel, gridPane);
        
        Scene loadNoteScene = new Scene(vbox, 400, 400);
        stage.setScene(loadNoteScene);
        stage.setTitle("Load Note");
        stage.show();
    }
   
    public static void addToExistingNote() {
        Logo.displayLogo();
        try{
            System.out.println("Current note: " + currentNoteName);
            Logo.displayLine();
            currentNote = Files.readAllLines(Paths.get(currentNotePath));
                for (String line : currentNote) {
                    System.out.println(line);
                }
        }catch(IOException e){
            System.out.println("No note loaded");
            MessageProcessor.processMessage(-2, e.toString(), false);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            notebookMenu();
        }catch(NullPointerException e){
            System.out.println("No note loaded");
            MessageProcessor.processMessage(-2, e.toString(), false);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            notebookMenu();
        }
    if (currentNote != null) {
        System.out.println();
        System.out.println("Enter the text you want to add to the note (Type 'done' when finished):");
        System.out.println("Type 'undo' to undo the last line.");
        
        String input = "";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(currentNotePath, true))) {
            while (!(input = CustomScanner.nextLine()).equalsIgnoreCase("done")) {
                if (input.equalsIgnoreCase("undo")) {
                    List<String> lines = Files.lines(Paths.get(currentNotePath))
                            .collect(Collectors.toList());
                    if (!lines.isEmpty()) {
                        lines.remove(lines.size() - 1);
                        Files.write(Paths.get(currentNotePath), lines);
                    }
                } else {
                    bw.write(input + System.lineSeparator());
                    bw.flush();
                }
                Logo.displayLogo();
                System.out.println("Current note: " + currentNoteName);
                Logo.displayLine();                
                currentNote = Files.readAllLines(Paths.get(currentNotePath));
                for (String line : currentNote) {
                    System.out.println(line);
                }
                System.out.println();
                System.out.println("Enter the text you want to add to the note (Type 'done' when finished):");
                System.out.println("Type 'undo' to undo the last line.");
            }
        } catch (IOException e) {
            System.err.println("Error while writing to the note: " + e.getMessage());
            MessageProcessor.processMessage(-2, "Error while writing to the note: " + e.getMessage(), false);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
        }
        MessageProcessor.processMessage(1, "Note successfully updated.", true);
        notebookMenu();
    } else {
        MessageProcessor.processMessage(-1, "No note is currently loaded. Please load a note first.", true);
        notebookMenu();
    }
}
    
    public static void addToExistingNoteUI(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        TextArea currentNoteDisplay = new TextArea();
        currentNoteDisplay.setEditable(false);

        try {
            currentNote = Files.readAllLines(Paths.get(currentNotePath));
            currentNoteDisplay.setText(String.join("\n", currentNote));
        } catch (IOException e) {
            MessageProcessor.processMessage(-2, e.toString(), false);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            notebookMenuUI(stage);
            return;
        } catch (NullPointerException e) {
            MessageProcessor.processMessage(-2, e.toString(), false);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            notebookMenuUI(stage);
            return;
        }

        Label inputLabel = new Label("Enter the text you want to add to the note:");
        TextField inputField = new TextField();
        Button addButton = new Button("Add Line");
        Button undoButton = new Button("Undo Last Line");
        Button doneButton = new Button("Done");

        addButton.setOnAction(e -> {
            String input = inputField.getText();
            try {
                Files.writeString(Paths.get(currentNotePath), input + "\n", StandardOpenOption.APPEND);
                currentNoteDisplay.appendText(input + "\n");
                inputField.clear();
            } catch (IOException ex) {
                MessageProcessor.processMessage(-2, "Error while writing to the note: " + ex.getMessage(), false);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
            }
        });

        undoButton.setOnAction(e -> {
            try {
                List<String> lines = Files.lines(Paths.get(currentNotePath)).collect(Collectors.toList());
                if (!lines.isEmpty()) {
                    lines.remove(lines.size() - 1);
                    Files.write(Paths.get(currentNotePath), lines);
                    currentNoteDisplay.setText(String.join("\n", lines));
                }
            } catch (IOException ex) {
                MessageProcessor.processMessage(-2, "Error while undoing last line: " + ex.getMessage(), false);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
            }
        });

        doneButton.setOnAction(e -> {
            MessageProcessor.processMessage(1, "Note successfully updated.", true);
            notebookMenuUI(stage);
        });

        vbox.getChildren().addAll(currentNoteDisplay, inputLabel, inputField, addButton, undoButton, doneButton);

        Scene addToNoteScene = new Scene(vbox, 600, 400);
        stage.setScene(addToNoteScene);
        stage.setTitle("Add to Note");
        stage.show();
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
            notebookMenu();
        } else {
            MessageProcessor.processMessage(-1 ,"No note found with the given name.", true);
            notebookMenu();
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
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
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
    
    public static void deleteNoteUI(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        Label instructionLabel = new Label("Enter the name of the note you want to delete:");
        TextField inputField = new TextField();
        Button deleteButton = new Button("Delete Note");

        deleteButton.setOnAction(e -> {
            String noteName = inputField.getText();

            if (userNotebooks.containsKey(noteName)) {
                File noteFile = new File(userNotebooks.getProperty(noteName));
                noteFile.delete();
                userNotebooks.remove(noteName);
                saveProperties();
                MessageProcessor.processMessage(1, "Note deleted successfully.", true);
                notebookMenuUI(stage);
            } else {
                MessageProcessor.processMessage(-1 ,"No note found with the given name.", true);
                notebookMenuUI(stage);
            }
        });

        vbox.getChildren().addAll(instructionLabel, inputField, deleteButton);

        Scene deleteNoteScene = new Scene(vbox, 600, 400);
        stage.setScene(deleteNoteScene);
        stage.setTitle("Delete Note");
        stage.show();
    }

    public static void deleteLineUI(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        Label instructionLabel = new Label("Enter the line number to delete:");
        TextField inputField = new TextField();
        Button deleteButton = new Button("Delete Line");

        deleteButton.setOnAction(e -> {
            if (currentNote != null) {
                int lineNumber;
                try {
                    lineNumber = Integer.parseInt(inputField.getText());
                } catch (NumberFormatException ex) {
                    MessageProcessor.processMessage(-2, "Invalid input. Please enter a valid line number.", true);
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String stackTrace = sw.toString();

                    MessageProcessor.processMessage(2, stackTrace, true);
                    return;
                }

                if (lineNumber > 0 && lineNumber <= currentNote.size()) {
                    currentNote.remove(lineNumber - 1);
                    saveCurrentNote();
                    MessageProcessor.processMessage(1, "Line deleted successfully.", true);
                } else {
                    MessageProcessor.processMessage(-1, "Invalid line number. Please enter a valid line number.", true);
                }
                notebookMenuUI(stage);
            } else {
                MessageProcessor.processMessage(-1, "No note is currently loaded. Load a note first.", true);
                notebookMenuUI(stage);
            }
        });

        vbox.getChildren().addAll(instructionLabel, inputField, deleteButton);

        Scene deleteLineScene = new Scene(vbox, 600, 400);
        stage.setScene(deleteLineScene);
        stage.setTitle("Delete Line");
        stage.show();
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
            	StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
                MessageProcessor.processMessage(-2, "Error saving current note: " + e.getMessage(), true);
            }
        } else {
            MessageProcessor.processMessage(-1, "No note is currently loaded. Load a note first.", true);
        }
    }
    
    public static void saveProperties() {
        try {
            File userPropertiesFile = new File(notesFolderPath + File.separator + MainSystemUserController.GetProperty("Username") + ".properties");
            try (FileOutputStream fos = new FileOutputStream(userPropertiesFile)) {
                userNotebooks.store(fos, "User Notebooks");
            }
        } catch (IOException e) {
            MessageProcessor.processMessage(-2, "Error saving properties: " + e.getMessage(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
        }
    }

    public static void loadProperties() {
    	MainSystemUserController.loadUserProperties(SwitchController.focusUser);
    	MessageProcessor.processMessage(2, SwitchController.focusUser, true);
        File userPropertiesFile = new File(notesFolderPath + File.separator + MainSystemUserController.GetProperty("Username") + ".properties");
        if (userPropertiesFile.exists()) {
            try (FileInputStream fis = new FileInputStream(userPropertiesFile)) {
                userNotebooks.load(fis);
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, "Error loading properties: " + e.getMessage(), true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
            }
        } else {
            try {
                userPropertiesFile.createNewFile();
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, "Error creating properties file: " + e.getMessage(), true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
            }
            saveProperties();
            try (FileInputStream fis = new FileInputStream(userPropertiesFile)) {
                userNotebooks.load(fis);
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, "Error loading properties: " + e.getMessage(), true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
            }
        }
    }
}
