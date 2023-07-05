package Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URI;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;

import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import MainSystem.AdministrativeFunctions;
import MainSystem.MainMenu;

import MainSystem.SettingsController;
import UserController.LoginUserController;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;


public class Login {
    private static String currentUser = "Null";
    private static TextField usernameField;
    private static PasswordField passwordField;
    static String username;
    protected static String password;

    public static void showLoginScreen(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        //Label Console = new Label("Console: ");
        //gridPane.add(Console, 0, 4);
        TextFlow consoleOutput = MessageProcessor.getUIConsole(stage);


        // ...

        // Add consoleOutput to the grid
        gridPane.add(consoleOutput, 0, 4, 2, 1);
        GridPane.setVgrow(consoleOutput, Priority.ALWAYS);
        GridPane.setHgrow(consoleOutput, Priority.ALWAYS);

        // ...
    
        Label usernameLabel = new Label("Username:");
        gridPane.add(usernameLabel, 0, 1);
        usernameField = new TextField();
        gridPane.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 2);
        passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> handleLogin());
        gridPane.add(loginBtn, 1, 3);

        Scene scene = new Scene(gridPane, 300, 200);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
                event.consume();
            }
        });
        stage.setScene(scene);
        stage.setTitle("Login Screen");
        stage.show();
    }
    public static void showLoginScreen(Stage stage, String User) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        TextFlow consoleOutput = MessageProcessor.getUIConsole(stage);
     // Add consoleOutput to the grid
        gridPane.add(consoleOutput, 0, 4, 2, 1);
        GridPane.setVgrow(consoleOutput, Priority.ALWAYS);
        GridPane.setHgrow(consoleOutput, Priority.ALWAYS);
        
        Label usernameLabel = new Label("Username:");
        gridPane.add(usernameLabel, 0, 1);
        usernameField = new TextField(User);
        gridPane.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 2);
        passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> handleLogin());
        gridPane.add(loginBtn, 1, 3);

        Scene scene = new Scene(gridPane, 300, 200);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
                event.consume();
            }
        });
        stage.setScene(scene);
        stage.setTitle("Login Screen");
        stage.show();
    }
    private synchronized static void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (LoginUserController.checkPassword(username, password)) {
            SwitchController.updateCurrentUser(username);
            
            // Get the current stage and show main menu
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            MainMenu.showMainMenu(currentStage);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }
    public static void loginScreen(){
        Logo.displayLogo();
        System.out.println("LOGIN SCREEN: ");
        Logo.displayLine();
        ConsoleHandler.getConsole();
        System.out.println("Username: ");
        username = CustomScanner.nextLine();
        if(username.equals("command") || username.equals("Command")){
            command(0);
        }else if (username.equals("alloff")){
            SwitchController.forceAllLogoff();
            loginScreen();   
        }else{
            System.out.println("Password: ");
            password = CustomScanner.nextLine();
            if(password.equals("off")){
                SwitchController.forceLogoff(username);
                loginScreen();
            }else{
                if(LoginUserController.checkPassword(username, password)){
                    SwitchController.updateCurrentUser(username);
                    MainMenu.mainMenu();
                }else if(!LoginUserController.checkPassword(username, password)){
                    loginScreen();
                }
            }
        }
    }

    public static void loginScreen(String user){
        Logo.displayLogo();
        System.out.println("LOGIN SCREEN: ");
        Logo.displayLine();
        MessageProcessor.processMessage(1, "AutoFilling Username with " + user, true);
        ConsoleHandler.getConsole();
        System.out.println("Username: " + user);
        System.out.println("Password: ");
        password = CustomScanner.nextLine();
        if(password.equals("off")){
            SwitchController.forceLogoff(user);
            loginScreen();
        }else{
            if(LoginUserController.checkPassword(user, password)){
                SwitchController.updateCurrentUser(user);
                MainMenu.mainMenu();
            }else if(!LoginUserController.checkPassword(user, password)){
                loginScreen(user);
            }
        }
    }

    private static void command(int mode) {
        Logo.displayLogo();
        ConsoleHandler.getConsole();
        if(mode == 1) {
        	System.out.println("[SWI,SWITCH]: \"Swi\" or \"Switch\" will bring you to the Switch Control Menu if 2 or more users are logged in.");
            System.out.println("[HELP, LIST]: \"HELP\" or \"LIST\" Lists all the possible commands in the Login screen. You should know this command as you typed it to show this statement anyway");
            System.out.println("[RESTART]: \"Restart\" Restarts the program...(Thought that might be obvious)");
            System.out.println("[FIRST TIME ON]: \"First Time On\" Activates the First Time Setup... Program will restart into First Time Mode");
            System.out.println("[BACK]: \"Back\" takes you back to the Login Screen");
            System.out.println("[CREATE]: \"Create\" puts in a request for an account... The Admin has to follow up on the request");
            System.out.println("[_RESETADMIN]: \"_ResetAdmin\" re-enables the admin account after it is disabled. Automatically sets the passflag so you will have to change your password.");
            System.out.println("[FORCEOFF]: \"ForceOff\" Forces all users to logoff");
            System.out.println("[DUMP]: Dump Logs To File [ALL]");
            System.out.println("[_EXIT]: \"_Exit\" Quits the program.");
        }
        System.out.println("Command: ");
        String command = CustomScanner.nextLine().toLowerCase();
        if(command.equals("swi") || command.equals("switch")){
            SwitchController.switchMenu(1);
        }else if(command.equals("forceoff")){
            SwitchController.forceAllLogoff();
            command(0);
        }else if(command.equals("help") || command.equals("list")){
            command(1);
        }else if(command.equals("restart")){
            ProgramController.Start();
        }else if(command.equals("first time on")){
            FirstTimeManager.updateFirstTime();
            ProgramController.Start();
        }else if(command.equals("back")){
            loginScreen();
        }else if(command.equals("create")){
            System.out.println("New Username: ");
            String user = CustomScanner.nextLine();
            AdministrativeFunctions.newRequest("Guest", "new Account", "Blank Account", user);
            AdministrativeFunctions.accountRequestNamePool.add(user);
            loginScreen();
        }else if(command.equals("_resetadmin")){
            if(AdministrativeFunctions.enableAdminAccount()){
                MessageProcessor.processMessage(2, "Admin Account Re-Enabled", true);
            }else{
                MessageProcessor.processMessage(-1, "Failed to Re-Enable Admin Account", true);
            }
            loginScreen();
        }else if(command.equals("rab")){
            try {
                URI uri= new URI(SettingsController.getSetting("debugSite"));
                java.awt.Desktop.getDesktop().browse(uri);
                MessageProcessor.processMessage(1, "Webpage opened in your default Browser...", true);
                MessageProcessor.processMessage(2, "WebPage: " + SettingsController.getSetting("debugSite"), true);
            } catch (Exception e) {
                MessageProcessor.processMessage(-1, "Unable to Launch Webpage, [" + e.toString() + "]", true);
            }
            loginScreen();
        }else if(command.equals("_exit")){
            System.exit(1);
        }else{
            command(0);
        }
    }

   

    public static boolean validateAdmin(){
        Logo.displayLogo();
        System.out.println("ADMIN PASSWORD: ");
        String password = CustomScanner.nextLine();
        return (LoginUserController.checkPassword("Admin", password));
    }

    public static String getCurrentUser() {
        return SwitchController.focusUser;
    }

}