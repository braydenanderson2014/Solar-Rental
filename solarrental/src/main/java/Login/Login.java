package Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URI;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;

import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import MainSystem.AdministrativeFunctions;
import MainSystem.Main;
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
	public static GridPane gridPane = new GridPane();
    public static Scene scene = new Scene(gridPane, 500, 400);
    public static Stage stage = new Stage();
    public static VBox vbox = new VBox();

//	public static void showLoginScreen() {
//		stage.close();
//		stage = new Stage();
//	    // Create a new stage for the login screen
//	    stage.initStyle(StageStyle.TRANSPARENT);  // Make the stage transparent
//
//	    vbox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);" +  // Semi-transparent white
//	                  "-fx-background-radius: 10;" +  // Rounded corners
//	                  "-fx-padding: 20;" +  // Padding for visual comfort
//	                  "-fx-alignment: center;");  // Content alignment
//
//	    gridPane.setAlignment(Pos.CENTER);
//	    gridPane.setHgap(10);
//	    gridPane.setVgap(10);
//	    gridPane.setPadding(new Insets(25, 25, 25, 25));
//
//	    TextFlow consoleOutput = MessageProcessor.getUIConsole(stage);
//	    gridPane.add(consoleOutput, 0, 4, 2, 1);
//	    GridPane.setVgrow(consoleOutput, Priority.ALWAYS);
//	    GridPane.setHgrow(consoleOutput, Priority.ALWAYS);
//
//	    Label usernameLabel = new Label("Username:");
//	    gridPane.add(usernameLabel, 0, 1);
//	    usernameField = new TextField();  // removed the TextField
//	    gridPane.add(usernameField, 1, 1);
//
//	    Label passwordLabel = new Label("Password:");
//	    gridPane.add(passwordLabel, 0, 2);
//	    passwordField = new PasswordField();  // removed the PasswordField
//	    gridPane.add(passwordField, 1, 2);
//
//	    Button loginBtn = new Button("Login");
//	    loginBtn.setOnAction(e -> {
//	        stage.close();
//	        handleLogin(stage);
//	    });
//	    gridPane.add(loginBtn, 1, 3);
//
//	    Button CommandBtn = new Button("Commands");
//	    CommandBtn.setOnAction(e -> commandInterface());
//	    gridPane.add(CommandBtn, 0, 3);
//
//	    //vbox.getChildren().add(gridPane); // Add the gridPane to the root VBox
//
//	    scene.setFill(null);  // Make scene's background transparent
//
//	    stage.setScene(scene);
//
//	    scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
//	        if (event.getCode() == KeyCode.ENTER) {
//	            stage.close();
//	            handleLogin(stage);
//	            event.consume();
//	        }
//	    });
//
//	    stage.sizeToScene();
//	    stage.centerOnScreen();
//	    stage.setTitle("Login Screen");
//	    stage.show();
//	}

    public static void showLoginScreen() {
    	stage.hide();
    	stage.close();
    	stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);

        VBox root = new VBox(10); // Vertical spacing between elements
        root.setPadding(new Insets(20)); // Add padding to the VBox
        Scene scene = new Scene(root, 400, 300);
        scene.setFill(Color.TRANSPARENT); // Make the background transparent for rounded edges
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.setResizable(false);

        // Set the rounded window edges

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleLogin(stage);
            }
        });

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleLogin(stage);
            }
        });

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(stage));

        Button commandsButton = new Button("Commands");
        commandsButton.setOnAction(e -> {
        	commandInterface();
        });

        TextFlow console = MessageProcessor.getUIConsole(stage);
        root.getChildren().addAll(usernameField, passwordField, loginButton, commandsButton, console);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    




	public static void showLoginScreen(String User) {
		stage.close();
    	stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);

        VBox root = new VBox(10); // Vertical spacing between elements
        root.setPadding(new Insets(20)); // Add padding to the VBox
        Scene scene = new Scene(root, 400, 300);
        stage.hide();
        scene.setFill(Color.TRANSPARENT); // Make the background transparent for rounded edges
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.setResizable(false);

        // Set the rounded window edges

        usernameField = new TextField(User);
        usernameField.setPromptText("Username");
        usernameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleLogin(Login.stage);
            }
        });

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleLogin(Login.stage);
            }
        });

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(Login.stage));

        Button commandsButton = new Button("Commands");

        TextFlow console = MessageProcessor.getUIConsole(stage);
        root.getChildren().addAll(usernameField, passwordField, loginButton, commandsButton, console);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
	}

	private synchronized static void handleLogin(Stage stage) {
	    String username = usernameField.getText();
	    String password = passwordField.getText();

	    if (LoginUserController.checkPassword(username, password)) {
	        SwitchController.updateCurrentUser(username);
	        
	        //Scene mainMenuScene = MainMenu.getMainMenuScene();  // getMainMenuScene() is a new method you should create in MainMenu
	        //stage.setScene(mainMenuScene);
	        stage.close();
	        MainMenu.showMainMenu(new Stage());
	    } else {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Login Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("Invalid username or password. Please try again.");
	        alert.showAndWait();
	    }
	}



	public static void loginScreen() {
		Logo.displayLogo();
		System.out.println("LOGIN SCREEN: ");
		Logo.displayLine();
		ConsoleHandler.getConsole();
		System.out.println("Username: ");
		username = CustomScanner.nextLine();
		if (username.equals("command") || username.equals("Command")) {
			command(0);
		} else if (username.equals("alloff")) {
			SwitchController.forceAllLogoff();
			loginScreen();
		} else {
			System.out.println("Password: ");
			password = CustomScanner.nextLine();
			if (password.equals("off")) {
				SwitchController.forceLogoff(username);
				loginScreen();
			} else {
				if (LoginUserController.checkPassword(username, password)) {
					SwitchController.updateCurrentUser(username);
					MainMenu.mainMenu();
				} else if (!LoginUserController.checkPassword(username, password)) {
					loginScreen();
				}
			}
		}
	}

	public static void loginScreen(String user) {
		Logo.displayLogo();
		System.out.println("LOGIN SCREEN: ");
		Logo.displayLine();
		MessageProcessor.processMessage(1, "AutoFilling Username with " + user, true);
		ConsoleHandler.getConsole();
		System.out.println("Username: " + user);
		System.out.println("Password: ");
		password = CustomScanner.nextLine();
		if (password.equals("off")) {
			SwitchController.forceLogoff(user);
			loginScreen();
		} else {
			if (LoginUserController.checkPassword(user, password)) {
				SwitchController.updateCurrentUser(user);
				MainMenu.mainMenu();
			} else if (!LoginUserController.checkPassword(user, password)) {
				loginScreen(user);
			}
		}
	}

	public static void commandInterface() {
		stage.close();
		stage = new Stage();
		stage.initStyle(StageStyle.DECORATED);
		stage.setMaximized(true);
		VBox.clearConstraints(vbox);
		GridPane.clearConstraints(gridPane);
		
		// Create a VBox to hold the Labels and buttons
		vbox = new VBox();
		vbox.setSpacing(10); // Set spacing between elements

		// Create a Label and button for each command, add them to the VBox
		Label swiLabel = new Label(
				"[SWI,SWITCH]: \"Swi\" or \"Switch\" will bring you to the Switch Control Menu if 2 or more users are logged in.");
		Button swiButton = new Button("Swi/Switch");
		swiButton.setOnAction(e -> {
			SwitchController.switchMenu(0, stage);
		});
		vbox.getChildren().addAll(swiLabel, swiButton);

		Label forceoffLabel = new Label("[FORCEOFF]: \"ForceOff\" Forces all users to logoff");
		Button forceoffButton = new Button("ForceOff");
		forceoffButton.setOnAction(e -> {
			SwitchController.forceAllLogoff();
			commandInterface();
		});
		vbox.getChildren().addAll(forceoffLabel, forceoffButton);

		Label FirstTime = new Label(
				"[FIRST TIME ON]: \"First Time On\" Activates the First Time Setup... Program will restart into First Time Mode");
		Button firstTime = new Button("Activate First Time");
		firstTime.setOnAction(e -> {
			SettingsController.setSetting("FirstTime", "true");
			stage.close();
			Main restart = new Main();
			restart.start(new Stage());
		});
		vbox.getChildren().addAll(FirstTime, firstTime);

		Label restart = new Label("[RESTART]: \\\"Restart\\\" Restarts the program...(Thought that might be obvious)");
		Button Restart = new Button("Restart Program");
		Restart.setOnAction(e -> {
			String [] args = null;
			stage.close();
			Main prestart = new Main();
			prestart.start(new Stage());
		});
		vbox.getChildren().addAll(restart, Restart);

		Label back = new Label("[BACK]: \\\"Back\\\" takes you back to the Login Screen");
		Button Back = new Button("Back");
		Back.setOnAction(e -> {
			//showLoginScreen(stage);
			showLoginScreen();
		});
		vbox.getChildren().addAll(back, Back);

		Label create = new Label(
				"[CREATE]: \"Create\" puts in a request for an account... The Admin has to follow up on the request");
		Button Create = new Button("Create Account Request");
		Create.setOnAction(e -> {
			create(stage);
		});
		vbox.getChildren().addAll(create, Create);

		Label ResetAdmin = new Label(
				"[_RESETADMIN]: \\\"_ResetAdmin\\\" re-enables the admin account after it is disabled. Automatically sets the passflag so you will have to change your password.");
		Button resetAdmin = new Button("Reset Admin");
		resetAdmin.setOnAction(e -> {
			if (AdministrativeFunctions.enableAdminAccount()) {
				MessageProcessor.processMessage(2, "Admin Account Re-Enabled", true);
			} else {
				MessageProcessor.processMessage(-1, "Failed to Re-Enable Admin Account", true);
			}
			commandInterface();
		});
		vbox.getChildren().addAll(ResetAdmin, resetAdmin);

		Label dump = new Label("[DUMP]: Dump Logs To File [ALL]");
		Button Dump = new Button("Dump Logs [ALL]");
		resetAdmin.setOnAction(e -> {

		});
		vbox.getChildren().addAll(dump, Dump);

		Label exit = new Label("[_EXIT]: \\\"_Exit\\\" Quits the program.");
		Button Exit = new Button("Exit");
		resetAdmin.setOnAction(e -> {
			System.exit(0);
		});
		vbox.getChildren().addAll(exit, Exit);

		// ...create the rest of your labels and buttons here...
		stage.centerOnScreen();
		// Create a new Scene for the VBox and show it on the stage
		scene = new Scene(vbox);
		stage.setResizable(true);
		stage.setScene(scene);
		stage.show();
	}

	private static void create(Stage stage) {
		vbox = new VBox();
		// Create a Label for the user prompt
		Label promptLabel = new Label("New Username:");
		vbox.getChildren().add(promptLabel);

		// Create a TextField for user input
		TextField userField = new TextField();
		vbox.getChildren().add(userField);

		// Create a Button to submit the username
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> {
			String user = userField.getText();
			AdministrativeFunctions.newRequest("Guest", "new Account", "Blank Account", user);
			AdministrativeFunctions.accountRequestNamePool.add(user);

			// If loginScreen() method opens a new window,
			// you might want to handle it differently, depending on your exact
			// requirements.
			// This example assumes loginScreen() is a method that sets up the login screen
			// on the current stage
			//showLoginScreen(stage);
			showLoginScreen();
		});
		vbox.getChildren().add(submitButton);

		// Then you would add the VBox to your stage and show it:
		scene = new Scene(vbox);
		stage.setScene(scene);
		stage.show();
	}

	private static void command(int mode) {
		Logo.displayLogo();
		ConsoleHandler.getConsole();
		if (mode == 1) {
			System.out.println(
					"[SWI,SWITCH]: \"Swi\" or \"Switch\" will bring you to the Switch Control Menu if 2 or more users are logged in.");
			System.out.println(
					"[HELP, LIST]: \"HELP\" or \"LIST\" Lists all the possible commands in the Login screen. You should know this command as you typed it to show this statement anyway");
			System.out.println("[RESTART]: \"Restart\" Restarts the program...(Thought that might be obvious)");
			System.out.println(
					"[FIRST TIME ON]: \"First Time On\" Activates the First Time Setup... Program will restart into First Time Mode");
			System.out.println("[BACK]: \"Back\" takes you back to the Login Screen");
			System.out.println(
					"[CREATE]: \"Create\" puts in a request for an account... The Admin has to follow up on the request");
			System.out.println(
					"[_RESETADMIN]: \"_ResetAdmin\" re-enables the admin account after it is disabled. Automatically sets the passflag so you will have to change your password.");
			System.out.println("[FORCEOFF]: \"ForceOff\" Forces all users to logoff");
			System.out.println("[DUMP]: Dump Logs To File [ALL]");
			System.out.println("[_EXIT]: \"_Exit\" Quits the program.");
		}
		System.out.println("Command: ");
		String command = CustomScanner.nextLine().toLowerCase();
		if (command.equals("swi") || command.equals("switch")) {
			SwitchController.switchMenu(1);
		} else if (command.equals("forceoff")) {
			SwitchController.forceAllLogoff();
			command(0);
		} else if (command.equals("help") || command.equals("list")) {
			command(1);
		} else if (command.equals("restart")) {
			ProgramController.Start();
		} else if (command.equals("first time on")) {
			FirstTimeManager.updateFirstTime();
			ProgramController.Start();
		} else if (command.equals("back")) {
			loginScreen();
		} else if (command.equals("create")) {
			System.out.println("New Username: ");
			String user = CustomScanner.nextLine();
			AdministrativeFunctions.newRequest("Guest", "new Account", "Blank Account", user);
			AdministrativeFunctions.accountRequestNamePool.add(user);
			loginScreen();
		} else if (command.equals("_resetadmin")) {
			if (AdministrativeFunctions.enableAdminAccount()) {
				MessageProcessor.processMessage(2, "Admin Account Re-Enabled", true);
			} else {
				MessageProcessor.processMessage(-1, "Failed to Re-Enable Admin Account", true);
			}
			loginScreen();
		} else if (command.equals("rab")) {
			try {
				URI uri = new URI(SettingsController.getSetting("debugSite"));
				java.awt.Desktop.getDesktop().browse(uri);
				MessageProcessor.processMessage(1, "Webpage opened in your default Browser...", true);
				MessageProcessor.processMessage(2, "WebPage: " + SettingsController.getSetting("debugSite"), true);
			} catch (Exception e) {
				MessageProcessor.processMessage(-1, "Unable to Launch Webpage, [" + e.toString() + "]", true);
			}
			loginScreen();
		} else if (command.equals("_exit")) {
			System.exit(1);
		} else {
			command(0);
		}
	}

	public static boolean validateAdmin() {
		Logo.displayLogo();
		System.out.println("ADMIN PASSWORD: ");
		String password = CustomScanner.nextLine();
		return (LoginUserController.checkPassword("Admin", password));
	}

	public static String getCurrentUser() {
		return SwitchController.focusUser;
	}

}