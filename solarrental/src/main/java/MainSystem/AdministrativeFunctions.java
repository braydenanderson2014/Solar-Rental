package MainSystem;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;

import Login.SwitchController;
import MainSystem.RequestLoader.RequestData;
import UserController.LoginUserController;
import UserController.MainSystemUserController;
import UserController.MaintainUserController;
import UserController.SecondaryUserController;
import UserController.UserListController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;

public class AdministrativeFunctions {
	private static final String ADMIN = "Admin";
	private static final String USER = "User: ";
	private static final String ACCOUNT2 = "Account";
	private static final String TARGET_ACCOUNT = "Target Account: ";
	private static final String UNABLE_TO_FIND_USER = "Unable to find user [";
	private static Random gen = new Random();
	public static List<String> administrativeRequests = new ArrayList<>();
	public static List<String> administrativeRequestKeyWord = new ArrayList<>();
	public static List<String> administrativeRequestUser = new ArrayList<>();
	public static List<String> administrativeRequestedName = new ArrayList<>();
	public static List<String> administrativeRequestFull = new ArrayList<>();
	public static List<Integer> administrativeRequestID = new ArrayList<>();
	public static List<String> accountRequestNamePool = new ArrayList<>();
	public static String targetAccount = "";
	public static int requestsMade = 0;
	private static Properties userlist = new Properties();
	private static Properties requestList = new Properties();
	private static String requestFile;

	public static int updateRequestsMade() {
		RequestLoader.loadJson();
		if(requestsMade == 0) {
			RequestLoader.loadJson();
		}
		requestsMade = administrativeRequests.size();
		return requestsMade;
	}

	public static void displayAdministrativeMenu(Stage stages) {
		stages.close();
		final Stage stage = new Stage();
		updateRequestsMade();
		//#region NavBar
		HBox navBar = new HBox(10);
		Button logout = new Button("Logout");
		logout.setOnAction(e -> {
			SwitchController.removeCurrentUser(MainSystemUserController.GetProperty("Username"), new Stage());
		});
		Button settings = new Button("Settings");
		settings.setOnAction(e -> {
			Settings.settingsMenu(new Stage());
		});
		Button ConsoleButton = new Button("Console");
		ConsoleButton.setOnAction(e -> {
			ConsoleHandler.ConsoleSettings(new Stage());
		});
		Label welcomeLabel = new Label("Welcome to the Solar Administrative Menu; Current User: "
				+ MainSystemUserController.GetProperty("AccountName"));
		
		navBar.getChildren().addAll(logout, settings, ConsoleButton, welcomeLabel);
		//#endregion
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setTop(navBar); // Navigation bar on top
		
		
		// Get the dimensions of the screen
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		VBox layout = new VBox(20);
		layout.setAlignment(Pos.TOP_LEFT);
		layout.setPadding(new Insets(20));
		layout.setMargin(layout, new Insets(20));
		//layout.setFillWidth(true);

		
		//layout.getChildren().add(welcomeLabel);

		if (!targetAccount.equals("")) {
			Label target = new Label("Target Account: " + targetAccount);
			layout.getChildren().add(target);
		}

		Button createButton = new Button("Create a User");
		layout.getChildren().add(createButton);
		createButton.setOnAction(e -> {
			MaintainUserController.displayCreateNewUserUI(new Stage());
		});

		Button deleteButton = new Button("Delete a User");
		layout.getChildren().add(deleteButton);
		deleteButton.setOnAction(e -> {
			MaintainUserController.deleteUser(targetAccount, new Stage());
			// Your code here...
		});

		Label requestsLabel = new Label("Current Request Count: [" + requestsMade + "]");
		layout.getChildren().add(requestsLabel);

		Button requestButton = new Button("Requests");
		layout.getChildren().add(requestButton);
		requestButton.setOnAction(e -> {
			if(requestsMade == 0) {
				requestsLabel.setText("No Requests Have been Made at this Time! Requests made: [" + requestsMade + "]");
			} else {
				resolutionAdvisory(new Stage());
			}
		});

		Button changePass = new Button("Change a User's Pass");
		layout.getChildren().add(changePass);
		changePass.setOnAction(e -> {

		});

		Button enableDisable = new Button("Enable/Disable an Account; Currently: " + MaintainUserController.GetProperty("Account"));
		layout.getChildren().add(enableDisable);
		enableDisable.setOnAction(e -> {
			if(targetAccount.equals("")) {
				MessageProcessor.processMessage(-1, "No Account has been loaded, please load an account first!", true);
				displayAdministrativeMenu(stage);
			}else {
				if(MaintainUserController.GetProperty("Account").equals("Enabled")) {
					MaintainUserController.setValue(targetAccount, "Account", "Disabled");
					enableDisable.setText("Enable/Disable an Account; Currently: " + MaintainUserController.GetProperty("Account"));
				}else {
					MaintainUserController.setValue(targetAccount, "Account", "Enabled");
					enableDisable.setText("Enable/Disable an Account; Currently: " + MaintainUserController.GetProperty("Account"));				}
			}
			
		});

		Button passFlag = new Button("Pass Flag; Currently: " + MaintainUserController.GetProperty("PassFlag"));
		layout.getChildren().add(passFlag);
		passFlag.setOnAction(e -> {
			if(targetAccount.equals("")) {
				MessageProcessor.processMessage(-1, "No Account has been loaded, please load an account first!", true);
				displayAdministrativeMenu(stage);
			}else {
				if(Boolean.parseBoolean(MaintainUserController.GetProperty("PassFlag"))) {
					MaintainUserController.setValue(targetAccount, "PassFlag", String.valueOf(false));
					passFlag.setText("Pass Flag; Currently: " + MaintainUserController.GetProperty("PassFlag"));
				} else {
					MaintainUserController.setValue(targetAccount, "PassFlag", String.valueOf(true));
					passFlag.setText("Pass Flag; Currently: " + MaintainUserController.GetProperty("PassFlag"));
				}
			}
		});
		UserListController.loadUserList();
		Button list = new Button("List All Users; Current User Count: " + UserListController.userlist.size());
		layout.getChildren().add(list);
		list.setOnAction(e -> {
			//stage.close();
			displayUsers();
		});

		Button tax = new Button("Set Tax %");
		layout.getChildren().add(tax);
		tax.setOnAction(e -> {

		});

		Button target = new Button("Set Target Account");
		layout.getChildren().add(target);
		target.setOnAction(e -> {
			stage.close();
			setTarget();
			displayAdministrativeMenu(new Stage());
		});

		Button cls = new Button("Clear Logs");
		layout.getChildren().add(cls);
		cls.setOnAction(e -> {

		});

		Button back = new Button("Return to Main Menu");
		layout.getChildren().add(back);
		back.setOnAction(e -> {
			stage.close();
			MainMenu.showMainMenu(new Stage());
		});
		// Set the max width of buttons to fill the available space
	    
	    
		TextFlow consoleOutput = MessageProcessor.getUIConsole(stage);
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		// Add consoleOutput to the grid
		gridPane.add(consoleOutput, 0, 4, 2, 1);
		GridPane.setVgrow(consoleOutput, Priority.ALWAYS);
		GridPane.setHgrow(consoleOutput, Priority.ALWAYS);
		layout.getChildren().add(gridPane);
		Platform.runLater(new Runnable() {
			public void run() {
				layout.getChildren().add(consoleOutput);

			}
		});
		mainLayout.setCenter(layout);
		// Create a new Scene for your main menu.
		Scene mainMenuScene = new Scene(mainLayout);
		// Create buttons and add them to the VBox...
		// ...

		// Set the new Scene to the stage.
		stage.setScene(mainMenuScene);

		stage.setMaximized(true);
		// Set the fullscreen exit key combination to no combination, so user cannot
		// accidentally exit fullscreen
		//stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setTitle("Administrative Menu");
		// Show the stage
		stage.show();
	}


	private static void displayUsers() {
	    userlist = UserListController.GetAllUsersUI();

	    Stage stage = new Stage();
	    VBox layout = new VBox(10);

	    if (userlist != null) {
	        for (String username : userlist.stringPropertyNames()) {
	            Label userLabel = new Label(username);
	            layout.getChildren().add(userLabel);
	        }
	    } else {
	        Label noUsersLabel = new Label("No users to display.");
	        layout.getChildren().add(noUsersLabel);
	    }
	    Button ok = new Button("Ok");
	    ok.setOnAction(e -> {
	    	stage.close();
	    });
	    
	    layout.getChildren().add(ok);
	    Scene scene = new Scene(layout, 300, 200);
	    scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
            	stage.close();
            }
	    });
	    stage.setScene(scene);
	    stage.showAndWait();
	}


	private static void setTarget() {
	    Stage stage = new Stage();
	    Label title = new Label("Target a User");
	    TextField targetUserField = new TextField();
	    Button confirmButton = new Button("Confirm");
	    Button backButton = new Button("Back");

	    Label messageLabel = new Label(); // To display messages

	    confirmButton.setOnAction(event -> {
	        targetAccount = targetUserField.getText();
	        boolean exists = UserListController.checkUserList(targetAccount);
	        if (exists) {
	    		MaintainUserController.loadUserProperties(targetAccount);
	            stage.close();
	        } else {
	            messageLabel.setText("User does not exist. Please try again.");
	        }
	    });

	    backButton.setOnAction(event -> {
	        targetAccount = ""; // Set an empty targetAccount
	        stage.close();
	    });

	    VBox layout = new VBox(10);
	    layout.getChildren().addAll(title, targetUserField, confirmButton, backButton, messageLabel);

	    Scene scene = new Scene(layout, 300, 200);
	    scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
            	targetAccount = targetUserField.getText();
    	        boolean exists = UserListController.checkUserList(targetAccount);
    	        if (exists) {
    	    		MaintainUserController.loadUserProperties(targetAccount);
    	            stage.close();
    	        } else {
    	            messageLabel.setText("User does not exist. Please try again.");
    	        }            }
	    });
	    stage.setScene(scene);
	    stage.showAndWait();
	}




	public static void AdministrativeMenu() {
		updateRequestsMade();
		Logo.displayLogo();
		System.out.println("Welcome to the Solar Administrative Menu User: "
				+ MainSystemUserController.GetProperty("AccountName"));
		System.out.println("[CREATE]:  Create a User");
		System.out.println("[DELETE]:  Delete a User");
		System.out.println("[REQUESTS]: View User Requests; Current Request Count: [" + requestsMade + "]");
		System.out.println("[CHANGE]:  Change a User Pass");
		System.out.println("[ENABLE]:  Enable an Account");
		System.out.println("[PASSFLAG]: Set Passflag for an account");
		System.out.println("[LIST]: List All Registered Users");
		System.out.println("[DISABLE]: Disable a User Account");
		System.out.println("[TAX]: Tax Percentage adjustment; Current Tax Percentage: "
				+ SettingsController.getSetting("TaxP") + "%");
		System.out.println("[CLS]:     Clear Logs");
		System.out.println("[RETURN]:  Return to Main Menu");
		ConsoleHandler.getConsole();
		String option = CustomScanner.nextLine().toLowerCase();
		try {
			String key = "PermissionLevel";
			if (option.equals("create") && Integer.parseInt(MainSystemUserController.GetProperty(key)) >= 8) {
				MaintainUserController.createNewUser("Blank");
				AdministrativeMenu();
			} else if (option.equals("delete") && Integer.parseInt(MainSystemUserController.GetProperty(key)) >= 8) {
				System.out.println("User to delete: ");
				String user = CustomScanner.nextLine();
				if (UserListController.SearchForUser(user)) {
					MaintainUserController.deleteUser(user);
				} else {
					MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + user + "]", true);
				}
				AdministrativeMenu();
			} else if (option.equals("change") && Integer.parseInt(MainSystemUserController.GetProperty(key)) >= 8) {
				System.out.println("Target Account for password change: ");
				String user = CustomScanner.nextLine();
				LoginUserController.adminUpdateUserPass(user);
				AdministrativeMenu();
			} else if (option.equals("requests")) {
				resolutionAdvisory();
				AdministrativeMenu();
			} else if (option.equals("tax")) {
				System.out.println("Current TaxP: " + SettingsController.getSetting("TaxP") + "%");
				System.out.println("New TaxP: ");
				try {
					Double taxP = CustomScanner.nextDouble();
					SettingsController.setSetting("TaxP", String.valueOf(taxP));
					AdministrativeMenu();
				} catch (InputMismatchException e) {
					MessageProcessor.processMessage(-2, e.toString(), true);
					StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				    e.printStackTrace(pw);
				    String stackTrace = sw.toString();

				    MessageProcessor.processMessage(2, stackTrace, true);
					AdministrativeMenu();
				}
			} else if (option.equals("list")) {
				Logo.displayLogo();
				System.out.println("Users on List");
				Logo.displayLine();
				UserListController.loadUserList();
				Enumeration<Object> keys = UserListController.userlist.keys();
				while (keys.hasMoreElements()) {
					String key1 = (String) keys.nextElement();
					String value = (String) UserListController.userlist.get(key1);
					System.out.println(key1 + ": " + value);
					MessageProcessor.processMessage(1, key1 + ": " + value, false);
				}
				System.out.println();
				System.out.println("Press Enter to continue");
				String enter = CustomScanner.nextLine();
				MessageProcessor.processMessage(-3, enter, false);
				AdministrativeMenu();
			} else if (option.equals("passflag")) {
				Logo.displayLogo();
				System.out.println(TARGET_ACCOUNT);
				String Account = CustomScanner.nextLine();
				MessageProcessor.processMessage(1, Account + " was targeted for PassFlag change", false);
				if (UserListController.SearchForUser(Account) == true) {
					LoginUserController.loadUserProperties(Account);
					LoginUserController.setValue(Account, "PassFlag", "true");
					MessageProcessor.processMessage(1,
							"PassFlag set for user: " + MaintainUserController.GetProperty("Username"), true);
				} else {
					MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + Account + "]", true);
				}
				AdministrativeMenu();
			} else if (option.equals("cls")) {
				System.out.println("Are you sure you want to delete the logs?");
				String choice = CustomScanner.nextLine().toLowerCase();
				if (choice.equals("y") || choice.equals("yes")) {
					System.out.println(
							"[WARNING]: This process is not reversible! Would you like to dump Logs into file first?");
					String Choice = CustomScanner.nextLine().toLowerCase();
					if (Choice.equals("yes") || Choice.equals("y")) {
						// MessageProcessor.dumpAll();
						MessageProcessor.clearAll();
						AdministrativeMenu();
					} else {
						MessageProcessor.clearAll();
						AdministrativeMenu();
					}
				} else {
					MessageProcessor.processMessage(-1, "User did not erase logs", true);
					AdministrativeMenu();
				}
			} else if (option.equals("disable")) {
				disableAnAccount();
				AdministrativeMenu();
			} else if (option.equals("enable")) {
				enableAnAccount();
				AdministrativeMenu();
			} else if (option.equals("return")) {
				MainMenu.mainMenu();
			} else {
				if (Integer.parseInt(MaintainUserController.GetProperty(key)) >= 8) {
					MessageProcessor.processMessage(-1, "Invalid option, try again!", true);
				} else {
					MessageProcessor.processMessage(-1, SwitchController.focusUser
							+ " does not have the proper permissions for this function. Please return to Main Menu!",
							true);
				}
				AdministrativeMenu();
			}
		} catch (NumberFormatException e) {
			MessageProcessor.processMessage(-2, e.toString() + " [E1T0]", true);
			MessageProcessor.processMessage(-1, "An Error Occured with the Administrative Menu... Reloading Menu",
					true);
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
		    String stackTrace = sw.toString();

		    MessageProcessor.processMessage(2, stackTrace, true);
			AdministrativeMenu();
		}
	}

	public static void resolutionAdvisory(Stage stage) {
		stage.setTitle("Resolution Advisory");
		if (updateRequestsMade() == 0) {
			MessageProcessor.processMessage(-1, "No Requests have been Made; Requests: [0]", true);
			displayAdministrativeMenu(stage);
		}
		ListView<String> listView = new ListView<>();
		for (int i = 0; i < administrativeRequests.size(); i++) {
			listView.getItems().add(administrativeRequests.get(i) + "[" + administrativeRequestID.get(i) + "]");
		}
		listView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) { // double clicking
				int option = listView.getSelectionModel().getSelectedIndex();
				// Perform actions based on the selected index...
				// For example, if permissions were requested
				if (administrativeRequestKeyWord.get(option).contains("Permissions")) {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Account Name");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter account name:");

					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						String user = result.get();
						SecondaryUserController.adjPermLev(user);
						administrativeRequests.remove(option);
						administrativeRequestFull.remove(option);
						administrativeRequestID.remove(option);
						administrativeRequestKeyWord.remove(option);
						administrativeRequestUser.remove(option);
						administrativeRequestedName.remove(option);
					}
				} else if (administrativeRequestKeyWord.get(option).contains("new Account")) {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("New Account Name: ");
					dialog.setHeaderText(null);
					dialog.setContentText("Please set a new Account Name: ");
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						String user = result.get();
						MaintainUserController.displayCreateNewUserUI(stage, administrativeRequestedName.get(option));
						administrativeRequests.remove(option);
						administrativeRequestFull.remove(option);
						administrativeRequestID.remove(option);
						administrativeRequestKeyWord.remove(option);
						administrativeRequestUser.remove(option);
						administrativeRequestedName.remove(option);
					}
				} else if (administrativeRequestKeyWord.get(option).contains("Change Account Name")) {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Target Account");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter Target Account Username:");
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						String user = result.get();
						MaintainUserController.newAccountName.add(administrativeRequests.get(option));
						MaintainUserController.updateAccountName(stage, user, administrativeRequestedName.get(option));
						administrativeRequests.remove(option);
						administrativeRequestFull.remove(option);
						administrativeRequestID.remove(option);
						administrativeRequestKeyWord.remove(option);
						administrativeRequestUser.remove(option);
						administrativeRequestedName.remove(option);
					}
				} else { 				// Handle other cases here...
					MessageProcessor.processMessage(-1, "No Resolutions Available", true);
					displayAdministrativeMenu(stage);
				}
			}
		});

		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(new Label("Resolution Advisory"), listView);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
		stage.show();
	}

	public static boolean resolutionAdvisory() {
		if (updateRequestsMade() == 0) {
			MessageProcessor.processMessage(-1, "No Requests have been made", true);
			return false;
		}
		Logo.displayLogo();
		System.out.println("Resolution Advisory");
		Logo.displayLine();
		int size = updateRequestsMade();
		int itemnum = 1;
		MessageProcessor.processMessage(1, "Current Requests: ", false);
		for (int i = 0; i < administrativeRequests.size(); i++) {
			System.out.println(itemnum + ". " + administrativeRequests.get(i));
			MessageProcessor.processMessage(1,
					itemnum + ". " + administrativeRequests.get(i) + "[" + administrativeRequestID.get(i) + "]", false);
			itemnum++;
		}
		try {
			RequestData data = new RequestData();
			int option = CustomScanner.nextInt();
			option--;
			Logo.displayLogo();
			System.out.println("Account Name: ");
			Logo.displayLine();
			System.out.println(size + ". " + administrativeRequests.get(option));
			String user = CustomScanner.nextLine();
			if (administrativeRequestKeyWord.get(option).contains("Permissions")) {
				System.out.println("Target User?: ");
				user = CustomScanner.nextLine();
				itemnum--;
				Boolean itWorked = SecondaryUserController.adjPermLev(user);
				if(!itWorked) {
					AdministrativeMenu();
				}
				
				RequestLoader.removeRequestByID(administrativeRequestID.get(option));
			} else if (administrativeRequestKeyWord.get(option).contains("new Account")) {
				MaintainUserController.createNewUser(administrativeRequestedName.get(option));
				RequestLoader.removeRequestByID(administrativeRequestID.get(option));
//				data.administrativeRequests.remove(option);
//				data.administrativeRequestFull.remove(option);
//				data.administrativeRequestID.remove(option);
//				data.administrativeRequestKeyWord.remove(option);
//				data.administrativeRequestUser.remove(option);
//				data.administrativeRequestedName.remove(option);
				AdministrativeMenu();
			} else if (administrativeRequestKeyWord.get(option).contains("Change Account Name")) {
				System.out.println("Target Username: ");
				String user1 = administrativeRequestUser.get(option);
				System.out.println(user);
				System.out.println("New Account Name: ");
				String accountName = administrativeRequestedName.get(option);
				System.out.println(accountName);
				MaintainUserController.newAccountName.add(administrativeRequests.get(option));
				MaintainUserController.originalUserName.add(administrativeRequestUser.get(option));
				MaintainUserController.requestID.add(administrativeRequestID.get(option));
				RequestLoader.removeRequestByID(administrativeRequestID.get(option));
//				data.administrativeRequests.remove(option);
//				data.administrativeRequestFull.remove(option);
//				data.administrativeRequestID.remove(option);
//				data.administrativeRequestKeyWord.remove(option);
//				data.administrativeRequestUser.remove(option);
//				data.administrativeRequestedName.remove(option);
				MaintainUserController.updateAccountName(user1, accountName);
			} else {
				MessageProcessor.processMessage(-1, "No Resolutions Available", true);
				AdministrativeMenu();
			}
		} catch (InputMismatchException e) {
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
		    String stackTrace = sw.toString();

		    MessageProcessor.processMessage(2, stackTrace, true);
			MessageProcessor.processMessage(-2, e.toString(), true);
			return false;
		}

		// if(request.contains("Permissions")){

		// }
		return true;
	}

	private static boolean viewAccountList() {
		if (accountRequestNamePool.isEmpty()) {
			MessageProcessor.processMessage(-1, "No Account Requests have been made", true);
			return false;
		}
		int selection = 0;
		Logo.displayLogo();
		System.out.println("Account creation requests");
		Logo.displayLine();
		for (int i = 0; i < accountRequestNamePool.size(); i++) {
			selection++;
			System.out.println(selection + ". " + accountRequestNamePool.get(i));
		}
		try {
			int choice = CustomScanner.nextInt();
			choice--;
			MaintainUserController.createNewUser(accountRequestNamePool.get(choice));
			return true;
		} catch (InputMismatchException e) {
			MessageProcessor.processMessage(-2, e.toString(), true);
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
		    String stackTrace = sw.toString();

		    MessageProcessor.processMessage(2, stackTrace, true);
			viewAccountList();
		}
		return true;
	}

	private static boolean enableAnAccount() {
		Logo.displayLogo();
		System.out.println("Enable An Account");
		Logo.displayLine();
		System.out.println(TARGET_ACCOUNT);
		String account = CustomScanner.nextLine();
		MessageProcessor.processMessage(1, account + " was enabled", false);
		if (UserListController.SearchForUser(account)) {
			LoginUserController.setValue(account, ACCOUNT2, "Enabled");
			MessageProcessor.processMessage(1, USER + account + "'s Account was Enabled", true);
			LoginUserController.setValue(account, "PassFlag", "true");
		} else {
			MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + account + "]", true);
		}
		AdministrativeMenu();
		return true;
	}

	public static boolean enableAdminAccount() {
		if (UserListController.SearchForUser(ADMIN)) {
			LoginUserController.setValue(ADMIN, ACCOUNT2, "Enabled");
			MessageProcessor.processMessage(1, USER + ADMIN + " Account was Enabled", true);
			LoginUserController.setValue(ADMIN, "PassFlag", "true");
			return true;
		}
		MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + ADMIN + "]", true);
		return false;
	}

	private static boolean disableAnAccount() {
		Logo.displayLogo();
		System.out.println("Disable An Account");
		Logo.displayLine();
		System.out.println(TARGET_ACCOUNT);
		String account = CustomScanner.nextLine();
		MessageProcessor.processMessage(1, account + " was disabled", false);
		if (UserListController.SearchForUser(account)) {
			if (!account.equals(ADMIN)) {
				LoginUserController.setValue(account, ACCOUNT2, "Disabled");
				MessageProcessor.processMessage(1,
						USER + LoginUserController.getProperty(account, "Username") + " Account was Disabled", true);
				return true;
			}
			MessageProcessor.processMessage(-1, "CANNOT Disable Admin Account", true);
			return false;
		}
		MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + account + "]", true);
		return false;
	}

	public static boolean newRequest(String focusUser, String request, String description, String newAccountName) {
		int requestID = requestIDGenerator();
		String temp = "[" + focusUser + "] is requesting [" + request + "]...Full Description: "
				+ description + "[Request ID: " + requestID + "]";
		administrativeRequestedName.add(newAccountName);
		administrativeRequestID.add(requestID);
		administrativeRequestUser.add(focusUser);
		administrativeRequestKeyWord.add(request);
		administrativeRequests.add(description);
		administrativeRequestFull.add("[" + focusUser + "] is requesting [" + request + "]...Full Description: "
				+ description + "[Request ID: " + requestID + "]");
		MessageProcessor.processMessage(1,
				"Request saved successfully, an Administrator will review the request as soon as possible. [Request ID: "
						+ requestID + "]",
				true);
		RequestLoader.addRequest(newAccountName, requestID, focusUser, request, description, temp);

		return true;
	}
	public static boolean newRequest(String focusUser, String request, String description, String newAccountName, int id) {
		administrativeRequestedName.add(newAccountName);
		administrativeRequestID.add(id);
		administrativeRequestUser.add(focusUser);
		administrativeRequestKeyWord.add(request);
		administrativeRequests.add(description);
		administrativeRequestFull.add("[" + focusUser + "] is requesting [" + request + "]...Full Description: "
				+ description + "[Request ID: " + id + "]");
		String temp = "[" + focusUser + "] is requesting [" + request + "]...Full Description: "
				+ description + "[Request ID: " + id + "]";
		MessageProcessor.processMessage(1,
				"Request saved successfully, an Administrator will review the request as soon as possible. [Request ID: "
						+ id + "]",
				true);
		RequestLoader.addRequest(newAccountName, id, newAccountName, request, description, temp);
		return true;
	}

	private static int requestIDGenerator() {

		return gen.nextInt(99999);
	}

	public static boolean removeRequest() {
		return true;
	}
}
