package MainSystem;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;
import com.solarrental.assets.VersionController;

import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import Login.Login;
import Login.SwitchController;
import UserController.LoginUserController;
import UserController.MainSystemUserController;
import UserController.MaintainUserController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import messageHandler.AllMessages;
import messageHandler.ConsoleHandler;
import messageHandler.ConsoleSettings;
import messageHandler.LogDump;
import messageHandler.MessageProcessor;
import messageHandler.ViewLogManager;

public class Settings {
	private static final String LOG_TYPE = "LogType";
	public static String path = ProgramController.userRunPath;
	public static List<String> myRequests = new ArrayList<>();
	public static List<String> myNotifications = new ArrayList<>();
	public static int myNotificationsAmount = 0;
	public static String logType = "all";
	public static int requestsMade = 0;

	public static int updateRequestsMade() { // Check admin requests
		requestsMade = AdministrativeFunctions.administrativeRequests.size();
		return requestsMade;
	}

	public static boolean checkRequests() { // Check User Requests to admin
		myRequests.clear();
		for (int i = 0; i < AdministrativeFunctions.administrativeRequestUser.size(); i++) {
			if (AdministrativeFunctions.administrativeRequestUser.get(i).equals(SwitchController.focusUser)) {
				myRequests.add(AdministrativeFunctions.administrativeRequestFull.get(i));
			}
		}
		return true;
	}

	public static boolean checkNotifications() {// Checking notifications from admin or other users
		if (MainSystemUserController.GetProperty("UserNotification").equals("Enabled")) {
			myNotifications.clear();
			String path = ProgramController.userRunPath + "\\Users\\Notifications\\" + SwitchController.focusUser
					+ ".txt";
			boolean exists = (new File(path)).exists();
			File file = new File(path);
			if (exists) {
				try {
					BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						if (!line.equals("")) {
							myNotifications.add(line);
						}
					}
					reader.close();
					return true;
				} catch (Exception e) {
					MessageProcessor.processMessage(-2,	"Failed to read Notification File for User: " + SwitchController.focusUser, true);
					StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				    e.printStackTrace(pw);
				    String stackTrace = sw.toString();

				    MessageProcessor.processMessage(2, stackTrace, true);
					return false;
				}
			}
			MessageProcessor.processMessage(1, "No Notification File for User: " + SwitchController.focusUser, false);
			return false;
		}
		MessageProcessor.processMessage(-1, "User Notifications are Disabled", false);
		return false;
	}

	public static boolean printRequests() {// Print User Requests. (submitted requests to admin)
		checkRequests();
		int item = 0;
		Logo.displayLogo();
		for (int i = 0; i < myRequests.size(); i++) {
			item++;
			System.out.println(item + ": " + myRequests.get(i));
		}
		try {
			int option;
			System.out.println("Select An Item: (Type \"0\" to go back to the main menu)");
			option = CustomScanner.nextInt();
			option--;
			if (option == -1) {
				settingsMenu();
			} else {
				String temp;
				Logo.displayLogo();
				System.out.println("Selected Item: " + myRequests.get(option));
				System.out.println("Would you like to Revoke your Request? (y/n)");
				temp = CustomScanner.nextLine();
				temp = CustomScanner.nextLine();
				if (temp.equals("y") || temp.equals("yes")) {
					for (int i = 0; i < AdministrativeFunctions.administrativeRequestKeyWord.size(); i++) {
						if (AdministrativeFunctions.administrativeRequestFull.get(i).contains(myRequests.get(option))) {
							myRequests.remove(option);
							AdministrativeFunctions.administrativeRequestFull.remove(i);
							MessageProcessor.processMessage(1, "Removed From Administrative Request Full", false);
							AdministrativeFunctions.administrativeRequestID.remove(i);
							MessageProcessor.processMessage(1, "Removed From Request ID", false);
							AdministrativeFunctions.administrativeRequestKeyWord.remove(i);
							MessageProcessor.processMessage(1, "Removed From Request Keyword", false);
							AdministrativeFunctions.administrativeRequestUser.remove(i);
							MessageProcessor.processMessage(1, "Removed From Request User", false);
							AdministrativeFunctions.administrativeRequestedName.remove(i);
							MessageProcessor.processMessage(1, "Removed From Requested Name", false);
							AdministrativeFunctions.administrativeRequests.remove(i);
							MessageProcessor.processMessage(1, "Removed From Administrative Requests", false);
							MessageProcessor.processMessage(1, "Successfully Removed Requests", true);
						} else {
							MessageProcessor.processMessage(-1, "Unable to find requests in the main Request queue",
									true);
						}
					}
				} else {
					MessageProcessor.processMessage(-1, "Invalid option, Try again.", true);
					printRequests();
				}
			}
		} catch (InputMismatchException | IndexOutOfBoundsException e) {
			MessageProcessor.processMessage(-2, e.toString(), true);
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
		    String stackTrace = sw.toString();

		    MessageProcessor.processMessage(2, stackTrace, true);
		}
		return true;
	}

	public static void printRequests(Stage primaryStage) {
		checkRequests();
		int item = 0;

		VBox vbox = new VBox(); // Create a layout to hold your items

		for (int i = 0; i < myRequests.size(); i++) {
			item++;
			Label label = new Label(item + ": " + myRequests.get(i));
			vbox.getChildren().add(label); // Add each request to the layout
		}

		// Add a ComboBox (drop-down menu) for selecting an item
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setItems(FXCollections.observableArrayList(myRequests));
		comboBox.setPromptText("Select an Item");
		vbox.getChildren().add(comboBox);

		// Add a Button for user to confirm selection
		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(e -> {
			String selectedRequest = comboBox.getValue();
			if (selectedRequest == null) {
				// If user didn't select anything, show an alert
				Alert alert = new Alert(AlertType.WARNING, "Please select an item");
				alert.showAndWait();
			} else {
				// If user selected an item, process it
				for (int i = 0; i < AdministrativeFunctions.administrativeRequestKeyWord.size(); i++) {
					if (AdministrativeFunctions.administrativeRequestFull.get(i)
							.contains(myRequests.get(Integer.parseInt(selectedRequest)))) {
						myRequests.remove(selectedRequest);
						AdministrativeFunctions.administrativeRequestFull.remove(i);
						MessageProcessor.processMessage(1, "Removed From Administrative Request Full", false);
						AdministrativeFunctions.administrativeRequestID.remove(i);
						MessageProcessor.processMessage(1, "Removed From Request ID", false);
						AdministrativeFunctions.administrativeRequestKeyWord.remove(i);
						MessageProcessor.processMessage(1, "Removed From Request Keyword", false);
						AdministrativeFunctions.administrativeRequestUser.remove(i);
						MessageProcessor.processMessage(1, "Removed From Request User", false);
						AdministrativeFunctions.administrativeRequestedName.remove(i);
						MessageProcessor.processMessage(1, "Removed From Requested Name", false);
						AdministrativeFunctions.administrativeRequests.remove(i);
						MessageProcessor.processMessage(1, "Removed From Administrative Requests", false);
						MessageProcessor.processMessage(1, "Successfully Removed Requests", true);
					} else {
						MessageProcessor.processMessage(-1, "Unable to find requests in the main Request queue", true);
					}
				}
			}
		});
		vbox.getChildren().add(confirmButton);

		// Create a new Scene and show it
		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();

//go back to main menu FIXME:
	}

	public static void settingsMenu() throws NumberFormatException {
		checkRequests();
		checkNotifications();
		requestsMade = AdministrativeFunctions.updateRequestsMade();
		Logo.displayLogo();
		System.out.println();
		System.out.println("Settings Menu");
		System.out.println("============================================");
		System.out.println("[PATH]: Program's Working Directory: \"" + path + "\"");
		System.out.println("[CONSOLE]: Console Settings");
		System.out.println("[RAB]: Report a Bug");
		if (logType.equals("debugmt")) {
			System.out.println("[LOG]: Log Dump Type: DEBUG");
		} else {
			System.out.println("[LOG]: Log Dump Type: " + logType.toUpperCase());
		}
		System.out.println("[VIEWLOGS]: View logs Menu");
		System.out.println("[DUMP]: Dump Logs to File");
		System.out.println("[UPDATE]: Manually Update User Profile");
		if (SettingsController.getSetting("UI").equals("Enabled")) {
			System.out.println("[UI]: Enable/Disable UI Mode: Enabled");
		} else {
			System.out.println("[UI]: Enable/Disable UI Mode: Disabled");
		}
		System.out.println("[FORCE]: Force User Profile to update to latest Settings");
		String key = "PermissionLevel";
		if (parseInt(MainSystemUserController.GetProperty(key)) >= 6) {
			System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimeManager.checkFirstTime());
			System.out.println(
					"[SETUPDATE]: Forces Configuration File to add any new changes without having to reinstall program");
		}
		if (parseInt(MainSystemUserController.GetProperty(key)) >= 8) {
			System.out.println("[REQUESTS]: View User Requests; Current Request Count: [" + requestsMade + "]");
		} else {
			System.out.println("[REQUESTS]: My Requests: Current Requests: [" + myRequests.size() + "]");
		}
		if (parseInt(MainSystemUserController.GetProperty(key)) < 8) {
			System.out.println("[NOTI]: My Notifications Notification Count: [" + myNotifications.size() + "]");
		}
		System.out.println("[RETURN]: Return");
		System.out.println();
		ConsoleHandler.getConsole();
		String option = CustomScanner.nextLine().toLowerCase();
		if (option.equals("path")) {
			MessageProcessor.processMessage(-1,
					"Path Controller has not yet been implemented, Check back at a later update", true);
			settingsMenu();
		} else if (option.equals("noti")) {
			MessageProcessor.processMessage(1, "This Option is not yet Available, Check back at a later update", true);
			checkNotifications();
			printNotifications();

		} else if (option.equals("update")) {
			MaintainUserController.loadUserProperties(SwitchController.focusUser);
			MaintainUserController.updateProfileSettings(MaintainUserController.GetProperty("Username"));
		} else if (option.equals("rab")) {
			try {
				URI uri = new URI(SettingsController.getSetting("debugSite"));
				java.awt.Desktop.getDesktop().browse(uri);
				MessageProcessor.processMessage(1, "Webpage opened in your default Browser...", true);
				MessageProcessor.processMessage(2, SettingsController.getSetting("debugSite"), true);
			} catch (Exception e) {
				MessageProcessor.processMessage(-1, "Unable to Launch Webpage, [" + e.toString() + "]", true);
				StringWriter sw = new StringWriter();
			    PrintWriter pw = new PrintWriter(sw);
			    e.printStackTrace(pw);
			    String stackTrace = sw.toString();

			    MessageProcessor.processMessage(2, stackTrace, true);
			}
			settingsMenu();
		} else if (option.equals("force")) {
			MaintainUserController.forceProfileUpdate(SwitchController.focusUser);
			settingsMenu();
		} else if (option.equals("viewlogs")) {
			ViewLogManager.viewMenu(1);
		} else if (option.equals("dump")) {
			LogDump.DumpLog(logType);
			settingsMenu();
		} else if (option.equals("console")) {
			ConsoleSettings.ConsoleSettingsMenu();
		} else if (option.equals("requests")) {
			if (SwitchController.focusUser.equals("Admin")) {
				AdministrativeFunctions.resolutionAdvisory();
			} else {
				printRequests();
			}
			settingsMenu();
		} else if (option.equals("log")) {
			if (logType.equals("all")) {
				logType = "system";
			} else if (logType.equals("system")) {
				logType = "user";
			} else if (logType.equals("user")) {
				logType = "warning";
			} else if (logType.equals("warning")) {
				logType = "error";
			} else if (logType.equals("error")) {
				logType = "debugmt";
			} else if (logType.equals("debugmt")) {
				logType = "all";
			}
			String settingType = LOG_TYPE;
			SettingsController.setSetting(settingType, logType);
			MessageProcessor.processMessage(1, "Log Type: " + logType, true);
			settingsMenu();
		} else if (option.equals("first")) {
			if (parseInt(MainSystemUserController.GetProperty(key)) >= 6) {
				if (FirstTimeManager.checkFirstTime()) {
					SettingsController.setSetting("FirstTime", "false");
					FirstTimeManager.firstTime = false;
				} else if (!FirstTimeManager.checkFirstTime()) {
					FirstTimeManager.firstTime = true;
					SettingsController.setSetting("FirstTime", "true");
				}
				MessageProcessor.processMessage(1, "IsFirstTime: " + FirstTimeManager.checkFirstTime(), true);
				settingsMenu();
			} else {
				MessageProcessor.processMessage(-1, "User Does not have permission to use this function", true);
				settingsMenu();
			}
		} else if (option.equals("setupdate")) {
			if (parseInt(MainSystemUserController.GetProperty(key)) >= 6) {
				ForceUpdateConfiguration();
			} else {
				MessageProcessor.processMessage(-1, "User does not have permission to use this function", false);
				settingsMenu();
			}
		} else if (option.equals("ui")) {
			if (SettingsController.getSetting("UI").equals("Enabled")) {
				SettingsController.setSetting("UI", "Disabled");
			} else {
				SettingsController.setSetting("UI", "Enabled");
				if (Platform.isFxApplicationThread()) {
					// current thread is the JavaFX Application Thread
					Platform.runLater(() -> {
						Main.showUI(Main.getStage());
						settingsMenu(Main.getStage());
					});
				}

			}
		} else if (option.equals("return")) {
			// MainSystem
			MainMenu.mainMenu();
		} else {
			MessageProcessor.processMessage(-1, "Invalid option, Try again", true);
			settingsMenu();
		}
	}

	private static void printNotifications() {
		if(MainSystemUserController.GetProperty("UserNotification").equals("Disabled")) {
			MessageProcessor.processMessage(-1, "User Messages Have not been Enabled, Please contact Admin!", true);
			return;
		}
		for(int i = 0; i < myNotifications.size(); i++) {
			System.out.println(myNotifications.get(i));
		}
		System.out.println("Would you like to clear the Notification?");
		// TODO Auto-generated method stub
		String enter = CustomScanner.nextLine().toLowerCase();
		if(enter.equals("y") || enter.equals("yes")) {
			try {
				File file = new File(ProgramController.userRunPath + "\\Users\\Notifications\\" + SwitchController.focusUser + ".txt");
				BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
	            myNotifications.clear();
	            for(String line : myNotifications){
	                writer.write("");
	                writer.newLine();
	            }
	            writer.write("");
	            writer.newLine();
	            writer.close();
			}catch(IOException e) {
				StringWriter sw = new StringWriter();
			    PrintWriter pw = new PrintWriter(sw);
			    e.printStackTrace(pw);
			    String stackTrace = sw.toString();

			    MessageProcessor.processMessage(2, stackTrace, true);
			}
		}
		settingsMenu();
		
	}

	public static void settingsMenu(Stage primaryStage) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		VBox vbox = new VBox(20); // Create a layout to hold your items
		checkRequests();
		checkNotifications();
		requestsMade = AdministrativeFunctions.updateRequestsMade();
		primaryStage.setTitle("SettingsMenu");
		vbox.setAlignment(Pos.CENTER);

		Button path = new Button("Path: \"" + Settings.path + "\"");
		path.setOnAction(e -> {

		});
		HBox hbox = new HBox(path);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		Button Console = new Button("Console Settings");
		Console.setOnAction(e -> {
			ConsoleHandler.ConsoleSettings(primaryStage);
		});
		hbox = new HBox(Console);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		Button RAB = new Button("Report A Bug");
		RAB.setOnAction(e -> {
			try {
				URI uri = new URI(SettingsController.getSetting("debugSite"));
				java.awt.Desktop.getDesktop().browse(uri);
				MessageProcessor.processMessage(1, "Webpage opened in your default Browser...", true);
				MessageProcessor.processMessage(2, SettingsController.getSetting("debugSite"), true);
			} catch (Exception GE) {
				MessageProcessor.processMessage(-1, "Unable to Launch Webpage, [" + GE.toString() + "]", true);
				StringWriter sw = new StringWriter();
			    PrintWriter pw = new PrintWriter(sw);
			    GE.printStackTrace(pw);
			    String stackTrace = sw.toString();

			    MessageProcessor.processMessage(2, stackTrace, true);
			}
		});
		hbox = new HBox(RAB); 
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		Button log;
		if (logType.equals("debugmt")) {
			log = new Button("Log: Log Dump Type: DEBUG");
		} else {
			log = new Button("Log: Log Dump Type: " + logType.toUpperCase());
		}
		log.setOnAction(e -> {
			if (logType.equals("all")) {
				logType = "system";
			} else if (logType.equals("system")) {
				logType = "user";
			} else if (logType.equals("user")) {
				logType = "warning";
			} else if (logType.equals("warning")) {
				logType = "error";
			} else if (logType.equals("error")) {
				logType = "debugmt";
			} else if (logType.equals("debugmt")) {
				logType = "all";
			}
			String settingType = LOG_TYPE;
			SettingsController.setSetting(settingType, logType);
			MessageProcessor.processMessage(1, "Log Type: " + logType, true);
			if (logType.equals("debugmt")) {
				log.setText("Log: Log Dump Type: DEBUG");
			} else {
				log.setText("Log: Log Dump Type: " + logType.toUpperCase());
			}

		});
		hbox = new HBox(log);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		Button viewLogs = new Button("View Logs");
		viewLogs.setOnAction(e -> {

		});
		hbox = new HBox(viewLogs);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		Button Dump = new Button("Dump Logs");
		Dump.setOnAction(e -> {
			if(logType.equals("debugmt")) {
				logType.equals("Debug");
			}
			LogDump.DumpLog(logType);
			Label label = LogDump.getLabel();
			vbox.getChildren().add(label);
		});
		hbox = new HBox(Dump);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		
		Button Update = new Button("Update Account Menu");
		Update.setOnAction(e -> {

		});
		hbox = new HBox(Update);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		Button UI = new Button("UI: Enable/Disable UI Mode: Enabled");
		UI.setOnAction(e -> {
			Main.setStage(primaryStage);
			if (SettingsController.getSetting("UI").equals("Enabled")) {
				SettingsController.setSetting("UI", "Disabled");
				UI.setText("UI: Enable/Disable UI Mode: Disabled");
				Main.hideUI(primaryStage);
				settingsMenu();
			}
		});
		hbox = new HBox(UI);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		Button Force = new Button("Force Profile Update");
		Force.setOnAction(e -> {

		});
		hbox = new HBox(Force);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		Button First;
		Button setupdate;
		String key = "PermissionLevel";
		if (parseInt(MainSystemUserController.GetProperty(key)) >= 6) {
			First = new Button("First: Enable/Disable FirstTime Setup: " + String.valueOf(FirstTimeManager.checkFirstTime()).toUpperCase());
			First.setOnAction(e -> {
				if (SettingsController.getSetting("FirstTime").equals("true")) {
					SettingsController.setSetting("FirstTime", "false");
					First.setText("First: Enable/Disable FirstTime Setup: " + String.valueOf(FirstTimeManager.checkFirstTime()).toUpperCase());
				} else {
					SettingsController.setSetting("FirstTime", "true");
					First.setText("First: Enable/Disable FirstTime Setup: " + String.valueOf(FirstTimeManager.checkFirstTime()).toUpperCase());
				}
			});
			hbox = new HBox(First);
			hbox.setAlignment(Pos.CENTER);
			vbox.getChildren().add(hbox);
			
			
			setupdate = new Button("Force Configuration update");
			setupdate.setOnAction(e -> {
				ForceUpdateConfiguration(primaryStage);
			});
			hbox = new HBox(setupdate);
			hbox.setAlignment(Pos.CENTER);
			vbox.getChildren().add(hbox);
		}
		Label requests;
		Button Requests;
		if (parseInt(MainSystemUserController.GetProperty(key)) >= 8) {
			requests = new Label("View User Requests; Current Request Count: [" + requestsMade + "]");
			Requests = new Button("Requests: View User Requests");
			Requests.setOnAction(e -> {
				if (requestsMade == 0) {
					MessageProcessor.processMessage(-1, "No Requests at this time", true);
					Requests.setText("Requests: View User Requests; NO REQUESTS AT THIS TIME!!!");
				} else {
					AdministrativeFunctions.resolutionAdvisory(primaryStage);
				}
			});
		} else {
			requests = new Label("My Request Count: [" + requestsMade + "]");
			Requests = new Button("My Requests");
			Requests.setOnAction(e -> {
				if (myRequests.size() == 0) {
					Requests.setText("My Request Count: NO REQUESTS HAVE BEEN MADE!");
					MessageProcessor.processMessage(-1, "No Requests Have been made at this time!", true);
				} else {
					printRequests(primaryStage);
				}
			});
		}
		hbox = new HBox(requests);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		hbox = new HBox(Requests);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		Button notify = null;
		Label notificationCount = null;
		if (parseInt(MainSystemUserController.GetProperty(key)) < 8) {
			notificationCount = new Label("My Notifications; Count: [" + myNotifications.size() + "]");
			notify = new Button("Notifications");
			notify.setOnAction(e -> {

			});
			hbox = new HBox(notificationCount);
			hbox.setAlignment(Pos.CENTER);
			vbox.getChildren().add(hbox);
			
			hbox = new HBox(notify);
			hbox.setAlignment(Pos.CENTER);
			vbox.getChildren().add(hbox);
		}
		Button back = new Button("Return to Main Menu");
		back.setOnAction(e -> {
			MainMenu.showMainMenu(primaryStage);
		});
		hbox = new HBox(back);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		TextFlow console = MessageProcessor.getUIConsole(primaryStage);
		vbox.getChildren().add(console);
		//HBox hbox = new HBox();
		//hbox.getChildren().addAll(path, Console, RAB, log, viewLogs, Dump, Update, UI, Force, requests, Requests, back,
			//oo	console);
		primaryStage.setWidth(screenBounds.getWidth() - 20); // 20 pixels less than screen width
		primaryStage.setHeight(screenBounds.getHeight() - 40); // 40 pixels less than screen height

		Scene scene = new Scene(vbox, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.centerOnScreen();
		primaryStage.setMaximized(false);
		primaryStage.requestFocus();
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	private static void ForceUpdateConfiguration() {
		boolean exists = SettingsController.searchForSet("DebugSet");
		if (!exists) {
			SettingsController.setSetting("DebugSet", "true");
			MessageProcessor.processMessage(1,
					"Console Setting \"DebugSet\" was created successfully. Default Value: true", false);
		}
		
		SettingsController.saveSettings();
		SettingsController.loadSettings();
		MessageProcessor.processMessage(1, "Configuration File Updated", true);
		String userFolder = ProgramController.userRunPath + "\\Users\\Notifications";
		File file = new File(userFolder);
		if (!file.exists()) {
			file.mkdirs();
			MessageProcessor.processMessage(1, "Successfully created Directories at: " + userFolder, true);
		}
		settingsMenu();
	}

	private static void ForceUpdateConfiguration(Stage stage) {
		boolean exists = SettingsController.searchForSet("DebugSet");
		if (!exists) {
			SettingsController.setSetting("DebugSet", "true");
			MessageProcessor.processMessage(1,
					"Console Setting \"DebugSet\" was created successfully. Default Value: true", false);
		}
		SettingsController.saveSettings();
		SettingsController.loadSettings();
		MessageProcessor.processMessage(1, "Configuration File Updated", true);
		String userFolder = ProgramController.userRunPath + "\\Users\\Notifications";
		File file = new File(userFolder);
		if (!file.exists()) {
			file.mkdirs();
			MessageProcessor.processMessage(1, "Successfully created Directories at: " + userFolder, true);
		}
		settingsMenu(stage);
	}

	public static boolean loadSettings() { // Loads main Settings for the program to launch
		MessageProcessor.processMessage(1, "Loading Settings file from config.properties", false);
		SettingsController.loadSettings();
		MessageProcessor.processMessage(1, "Settings File Loaded... Now reading from Settings", false);
		MessageProcessor.processMessage(1, "FirstTime Setting: False", false);
		MessageProcessor.processMessage(1, "Path Letter: " + SettingsController.getSetting("PathLetter") + ", Path: " + SettingsController.getSetting("Path"), false);
		VersionController.setVersion(SettingsController.getSetting("Version"));
		MessageProcessor.processMessage(1, "Version: " + SettingsController.getSetting("Version"), false);
		Settings.logType = SettingsController.getSetting(LOG_TYPE);
		MessageProcessor.processMessage(1, "LogType: " + SettingsController.getSetting(LOG_TYPE), false);
		ConsoleSettings.ErrorSet = Boolean.parseBoolean(SettingsController.getSetting("ErrorSet"));
		MessageProcessor.processMessage(1,
				"ErrorSet: " + Boolean.parseBoolean(SettingsController.getSetting("ErrorSet")), false);
		ConsoleSettings.WarningSet = Boolean.parseBoolean(SettingsController.getSetting("WarningSet"));
		MessageProcessor.processMessage(1,
				"WarningSet: " + Boolean.parseBoolean(SettingsController.getSetting("WarningSet")), false);
		ConsoleSettings.SystemSet = Boolean.parseBoolean(SettingsController.getSetting("SystemSet"));
		MessageProcessor.processMessage(1,
				"SystemSet: " + Boolean.parseBoolean(SettingsController.getSetting("SystemSet")), false);
		ConsoleSettings.DebugSet = Boolean.parseBoolean(SettingsController.getSetting("DebugSet"));
		MessageProcessor.processMessage(1,
				"DebugSet: " + Boolean.parseBoolean(SettingsController.getSetting("DebugSet")), false);
		ConsoleSettings.UserNotifySet = Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet"));
		MessageProcessor.processMessage(1,
				"UserNotifySet: " + Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet")), false);
		ConsoleSettings.timeSet = Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet"));
		MessageProcessor.processMessage(1,
				"Time Set: " + Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet")), false);
		LoginUserController.passFlag = Boolean.parseBoolean(SettingsController.getSetting("PassFlag"));
		MessageProcessor.processMessage(1,
				"PassFlag: " + Boolean.parseBoolean(SettingsController.getSetting("PassFlag")), false);
		MessageProcessor.processMessage(1, "All Settings Loaded", false);
		return true;
	}
}