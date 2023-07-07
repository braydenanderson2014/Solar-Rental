package MainSystem;

import java.io.File;
import java.util.List;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;
import com.solarrental.assets.Notebook;

import InstallManager.ProgramController;
import Login.Login;
import Login.SwitchController;
import PointofSale.POSMenu;
import UserController.MainSystemUserController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import messageHandler.AllMessages;
import messageHandler.ConsoleHandler;
import messageHandler.LogDump;
import messageHandler.MessageProcessor;

public class MainMenu {
	public static void mainMenu() {
		MainSystemUserController.loadUserProperties(SwitchController.focusUser);
		if (MainSystemUserController.GetProperty("AccountName").equals("Blank")) {
			// UserController.updateUserProfile(1);
		}
		Logo.displayLogo();
		System.out.println("Welcome to Solar! User: " + MainSystemUserController.GetProperty("AccountName"));
		System.out.println("[POS]:  Point of Sale");
		if (Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8) {
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
		switch (option) {
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
			if (Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8) {
				AdministrativeFunctions.AdministrativeMenu();
			} else {
				MessageProcessor.processMessage(-1,
						SwitchController.focusUser + " does not have the proper permissions to use this function",
						true);
				mainMenu();
			}
			break;
		case "note":
			Notebook.notesFolderPath = ProgramController.userRunPath + File.separator + "Users" + File.separator
					+ "Notebooks" + File.separator + SwitchController.focusUser + File.separator + "Notebooks";
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
			try {
				Settings.settingsMenu();
			} catch (NumberFormatException e) {
				MessageProcessor.processMessage(-2, "Failed to access Settings Menu", true);
				mainMenu();
			}
			break;
		case "help":
			MessageProcessor.processMessage(-2, "This option is not yet available! Check back on the next update!",
					true);
			mainMenu();
			break;
		case "off":
			SwitchController.removeCurrentUser(MainSystemUserController.GetProperty("Username"));
			Login.loginScreen();
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

	public synchronized static void showMainMenu(Stage currentStage) {
		// Get the dimensions of the screen
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		MainSystemUserController.loadUserProperties(SwitchController.focusUser);
		VBox vbox = new VBox(20); // add your buttons to this vbox
		vbox.setAlignment(Pos.CENTER);

		Label welcomeLabel = new Label(
				"Welcome to the Solar Main Menu User: " + MainSystemUserController.GetProperty("AccountName"));
		vbox.getChildren().add(welcomeLabel);

		Button posButton = new Button("POS: Point of Sale");
		posButton.setOnAction(e -> {
			POSMenu.PointofSaleMenu();
		});

		Button adminButton = new Button("ADMIN: Administrative Functions");
		MainSystemUserController.loadUserProperties(SwitchController.focusUser);
		if (Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8) {
			adminButton.setOnAction(e -> {
				AdministrativeFunctions.displayAdministrativeMenu(currentStage);
			});
		}

		Button noteButton = new Button("NOTE: Notebook ");
		noteButton.setOnAction(e -> {
			Notebook.notebookMenuUI(currentStage);
		});

		Button swiButton = new Button("SWI: Switch User");
		swiButton.setOnAction(e -> {
			SwitchController.switchMenu(2, currentStage);
		});

		Button setButton = new Button("SET: Settings");
		// FIXME:
		Button helpButton = new Button("HELP: Display Help Messages");

		Button offButton = new Button("OFF: Log Off");
		offButton.setOnAction(e -> {
			SwitchController.removeCurrentUser(MainSystemUserController.GetProperty("Username"), currentStage);
		});
		Button exitButton = new Button("EXIT: Exit the Program");
		exitButton.setOnAction(e -> {
			System.exit(0);
		});

		TextFlow consoleOutput = MessageProcessor.getUIConsole(currentStage);
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));

		// ...

		// Add consoleOutput to the grid
		gridPane.add(consoleOutput, 0, 4, 2, 1);
		GridPane.setVgrow(consoleOutput, Priority.ALWAYS);
		GridPane.setHgrow(consoleOutput, Priority.ALWAYS);

		HBox hBox = new HBox(posButton);
		hBox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hBox);

		if (Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8) {
			hBox = new HBox(adminButton);
			hBox.setAlignment(Pos.CENTER);
			vbox.getChildren().add(hBox);

		}

		hBox = new HBox(noteButton);
		hBox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hBox);

		hBox = new HBox(swiButton);
		hBox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hBox);

		hBox = new HBox(setButton);
		hBox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hBox);

		hBox = new HBox(helpButton);
		hBox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hBox);

		hBox = new HBox(offButton);
		hBox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hBox);

		hBox = new HBox(exitButton);
		hBox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hBox);

		Platform.runLater(new Runnable() {
			public void run() {
				vbox.getChildren().add(consoleOutput);

			}
		});

		// Add the buttons to the VBox

		// Create a new Scene for your main menu.
		Scene mainMenuScene = new Scene(vbox, 300, 200);
		// Create buttons and add them to the VBox...
		// ...
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));

		// Set the new Scene to the stage.
		currentStage.setScene(mainMenuScene);
		currentStage.setTitle("Main Menu");

		// Make stage full screen
		// currentStage.setFullScreen(true);

		// Instead, set the stage size to match the screen dimensions
		// If you want some space around the edges, subtract from the width and height
		currentStage.setWidth(screenBounds.getWidth() - 20); // 20 pixels less than screen width
		currentStage.setHeight(screenBounds.getHeight() - 40); // 40 pixels less than screen height
		// Set the fullscreen exit key combination to no combination, so user cannot
		// accidentally exit fullscreen
		currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		// Set window to maximized
        currentStage.setMaximized(true);
        Platform.runLater(new Runnable() {
			public void run() {
				currentStage.hide();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentStage.show();
			}
		});
        

        // Center window on screen
        currentStage.centerOnScreen();

		// Show the stage
		currentStage.show();

	}
}