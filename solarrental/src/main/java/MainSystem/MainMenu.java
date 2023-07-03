package MainSystem;

import java.io.File;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;
import com.solarrental.assets.Notebook;

import InstallManager.ProgramController;
import Login.SwitchController;
import PointofSale.POSMenu;
import UserController.MainSystemUserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import messageHandler.AllMessages;
import messageHandler.ConsoleHandler;
import messageHandler.LogDump;
import messageHandler.MessageProcessor;

public class MainMenu{
    public static void mainMenu(){
        MainSystemUserController.loadUserProperties(SwitchController.focusUser);
        if(MainSystemUserController.GetProperty("AccountName").equals("Blank")){
            //UserController.updateUserProfile(1);
        }
        Logo.displayLogo();
        System.out.println("Welcome to Solar! User: " + MainSystemUserController.GetProperty("AccountName"));
        System.out.println("[POS]:  Point of Sale");
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
            System.out.println("[ADMIN]: Administrative Functions");
        }
        System.out.println("[NOTE]: Notebook ");
        System.out.println("[SWI]:  Switch User");
        System.out.println("[SET]:  Settings");
        System.out.println("[HELP]: Display Help Messages");
        System.out.println("[SET TEST]: Set a Test Message");
        System.out.println("[OFF]:  Log Off");
        System.out.println("[EXIT]: Exit the Program");
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        switch(option){
            case "pos":
                POSMenu.PointofSaleMenu();
                break;
            case "set test":
            System.out.println("Test Message: ");
            String newMessage = CustomScanner.nextLine();
            String newMessageT = newMessage;
            AllMessages.allMessages.add(newMessage);
            AllMessages.allMessagesT.add(newMessageT);
            AllMessages.visibleToConsole.add(true); // or false, depending on the visibility you want
            mainMenu();
            break;
            case "admin":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    AdministrativeFunctions.AdministrativeMenu();
                }else{
                    MessageProcessor.processMessage(-1, SwitchController.focusUser + " does not have the proper permissions to use this function", true);
                    mainMenu();
                }
                break;
            case "note":
                Notebook.notesFolderPath = ProgramController.userRunPath + File.separator + "Users" + File.separator + "Notebooks" + File.separator + SwitchController.focusUser + File.separator + "Notebooks";
                Notebook.currentNote.clear();
                Notebook.currentNoteName = null;
                Notebook.currentNotePath = null;
                MainSystemUserController.loadUserProperties(SwitchController.focusUser);
                Notebook.notebookMenu();
                break;
            case "swi": 
                SwitchController.switchMenu(2);
                break;
            case "set":
                try{
                    Settings.settingsMenu();
                }catch(NumberFormatException e){
                    MessageProcessor.processMessage(-2, "Failed to access Settings Menu", true);
                    mainMenu();
                }
                break;
            case "help":
                MessageProcessor.processMessage(-2, "This option is not yet available! Check back on the next update!", true);
                mainMenu();
                break;
            case "off":
                SwitchController.removeCurrentUser(MainSystemUserController.GetProperty("Username"));
                //Login.Login.loginScreen();
                break;
            case "exit":
                LogDump.DumpLog("all");
                System.exit(1);
                break;
            default:
                MessageProcessor.processMessage(-1, "Invalid Option, try again!", true);
                mainMenu();
                break;
        }
    }

	public static void showMainMenu(Stage currentStage) {
		VBox vbox = new VBox(20); // add your buttons to this vbox
        vbox.setAlignment(Pos.CENTER);
        Button posButton = new Button("POS: Point of Sale");
        posButton.setOnAction(e -> {
        	POSMenu.PointofSaleMenu();
        });
        Button adminButton = new Button("ADMIN: Administrative Functions");
        adminButton.setOnAction(e -> {
            // Call method to handle admin functions
        });
        Button noteButton = new Button("NOTE: Notebook ");
        Button swiButton = new Button("SWI: Switch User");
        Button setButton = new Button("SET: Settings");
        Button helpButton = new Button("HELP: Display Help Messages");
        Button offButton = new Button("OFF: Log Off");
        Button exitButton = new Button("EXIT: Exit the Program");

        // Add the buttons to the VBox
        vbox.getChildren().addAll(posButton, adminButton, noteButton, swiButton, setButton, helpButton, offButton, exitButton);

        // Create a new Scene for your main menu.
        Scene mainMenuScene = new Scene(vbox, 300, 200);
        // Create buttons and add them to the VBox...
        // ...

        

        // Set the new Scene to the stage.
        currentStage.setScene(mainMenuScene);
        currentStage.setTitle("Main Menu");

        // Make stage full screen
        currentStage.setFullScreen(true);
     // Set the fullscreen exit key combination to no combination, so user cannot accidentally exit fullscreen
        currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        // Show the stage
        currentStage.show();

		
	}
}