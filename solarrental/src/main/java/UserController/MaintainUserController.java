package UserController;

import java.util.ArrayList;
import java.util.Properties;
import java.util.List;

import assets.CustomScanner;
import assets.Logo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import InstallManager.ProgramController;
import Login.SwitchController;
import MainSystem.AdministrativeFunctions;
import MainSystem.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;
import messageHandler.UserMessageHandler;

public class MaintainUserController {
    static String UserProperties = ProgramController.userRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.userRunPath + "\\Users/";
    public static Properties userprop = new Properties();
    public static String status = "Disabled";
    public static List<String> newAccountName = new ArrayList<>();
    public static List<String> originalUserName = new ArrayList<>();
    public static List<Integer> requestID = new ArrayList<>();
    private static boolean loadUserlist(){
        return UserListController.loadUserList();
    }
    public static String checkStatus() {
    	if(MainSystemUserController.GetProperty("UserNotification").equals("Disabled")) {
    		status = "Disabled";
    	}else if(MainSystemUserController.GetProperty("UserNotification").equals("Enabled")) {
    		status = "Enabled";
    	}
    	return status;
    }
    public static boolean setValue(String user, String key, String value){
        loadUserProperties(user);
        userprop.setProperty(key, value);
        saveUserProperties(user);
        return true;
    }
    public static void displayCreateNewUserUI(Stage stage){
    	stage.setTitle("Create New User");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Please Create a new Username For the User: ");
        TextField usernameInput = new TextField();
        Label labelAccount = new Label("Please Type An Account Name for the New User:");
        TextField accountNameInput = new TextField();
        Label labelPermission = new Label("Please Set a Permission Level for the User (0<10)");
        TextField permissionLevelInput = new TextField();

        Button createButton = new Button("Create User");
        createButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String accountName = accountNameInput.getText();
            int permissionLevel;

            try {
                permissionLevel = Integer.parseInt(permissionLevelInput.getText());
            } catch (Exception ex) {
            	StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
                MessageProcessor.processMessage(-2, ex.toString(), true);
                MessageProcessor.processMessage(-1, "Failed to set permission Level, Assigning Level 0", true);
                permissionLevel = 0;
            }
            if(createNewFile(username)) {
            	setValue(username, "Username", username);
            	setValue(username, "AccountName", accountName);
            	setValue(username, "PermissionLevel", String.valueOf(permissionLevel));
            	setValue(username, "SuccessfulLogins", "0");
                setValue(username, "Account", "Disabled");
                setValue(username, "FailedLoginAttempts", "0");
                setValue(username, "PassFlag", "true");
                setValue(username, "LastPassChange", "Never");
                setValue(username, "Password", "Solar");
                setValue(username, "AllTimeFailedLoginAttempts", "0");
                setValue(username, "PassExpires", "true");
                setValue(username, "isPassExpired", "true");
                setValue(username, "LastLogin", "Never");
                setValue(username, "NotifyPath", "NULL");
                setValue(username, "NotepadPath", "NULL");
                if(Integer.parseInt(GetProperty("PermissionLevel")) >=6){
                    setValue(username, "UserNotification", "Enabled");
                }else{
                    setValue(username, "UserNotification", "Disabled");
                }
                saveUserProperties(username);
            }
            UserListController.addUserToList(username, permissionLevel);
            AdministrativeFunctions.displayAdministrativeMenu(stage);

            // Add the user creation logic here...

        });

        layout.getChildren().addAll(label, usernameInput, labelAccount, accountNameInput, labelPermission, permissionLevelInput, createButton);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.show();
    }
    public static void displayCreateNewUserUI(Stage stage, String username){
    	stage.setTitle("Auto Create New User");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        
        Label labelAccount = new Label("Please Type An Account Name for the New User:");
        TextField accountNameInput = new TextField();
        Label labelPermission = new Label("Please Set a Permission Level for the User (0<10)");
        TextField permissionLevelInput = new TextField();

        Button createButton = new Button("Create User");
        createButton.setOnAction(e -> {
            String accountName = accountNameInput.getText();
            int permissionLevel;

            try {
                permissionLevel = Integer.parseInt(permissionLevelInput.getText());
            } catch (Exception ex) {
                MessageProcessor.processMessage(-2, ex.toString(), true);
                MessageProcessor.processMessage(-1, "Failed to set permission Level, Assigning Level 0", true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
                permissionLevel = 0;
            }
            if(createNewFile(username)) {
            	setValue(username, "Username", username);
            	setValue(username, "AccountName", accountName);
            	setValue(username, "PermissionLevel", String.valueOf(permissionLevel));
            	setValue(username, "SuccessfulLogins", "0");
                setValue(username, "Account", "Disabled");
                setValue(username, "FailedLoginAttempts", "0");
                setValue(username, "PassFlag", "true");
                setValue(username, "LastPassChange", "Never");
                setValue(username, "Password", "Solar");
                setValue(username, "AllTimeFailedLoginAttempts", "0");
                setValue(username, "PassExpires", "true");
                setValue(username, "isPassExpired", "true");
                setValue(username, "LastLogin", "Never");
                setValue(username, "NotifyPath", "NULL");
                setValue(username, "NotepadPath", "NULL");
                if(Integer.parseInt(GetProperty("PermissionLevel")) >=6){
                    setValue(username, "UserNotification", "Enabled");
                }else{
                    setValue(username, "UserNotification", "Disabled");
                }
                saveUserProperties(username);
            }
            UserListController.addUserToList(username, permissionLevel);
            AdministrativeFunctions.displayAdministrativeMenu(stage);
            // Add the user creation logic here...

        });

        layout.getChildren().addAll(labelAccount, accountNameInput, labelPermission, permissionLevelInput, createButton);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.show();
    }
    public static boolean createNewUser(String UserToCreate){
        Logo.displayLogo();
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >=8){
            System.out.println("Please Create a new Username For the User: ");
            String UserName = UserToCreate;
            if(UserToCreate.equals("Blank")){
                UserName = CustomScanner.nextLine();
            }
            if(createNewFile(UserName) == true){
                System.out.println("Please Type An Account Name for the New User:");
                String AccountName = CustomScanner.nextLine();
                setValue(UserName, "Username", UserName);
                setValue(UserName, "AccountName", AccountName);
                System.out.println("Please Set a Permission Level for the User (0<10)");
                try {
                    int PLevel = CustomScanner.nextInt();
                    setValue(UserName, "PermissionLevel", String.valueOf(PLevel));
                    UserListController.addUserToList(UserName, PLevel);
                } catch (Exception e) {
                    MessageProcessor.processMessage(-2, e.toString(), true);
                    MessageProcessor.processMessage(-1, "Failed to set permission Level, Assigning Level 0", true);
                    setValue(UserName, "PermissionLevel", "0");
                    UserListController.addUserToList(UserName, 0);
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    String stackTrace = sw.toString();

                    MessageProcessor.processMessage(2, stackTrace, true);
                }
                MessageProcessor.processMessage(2, "All Manual Input Complete, System Now Completing Profile Setup", true);
                setValue(UserName, "SuccessfulLogins", "0");
                setValue(UserName, "Account", "Disabled");
                setValue(UserName, "FailedLoginAttempts", "0");
                setValue(UserName, "PassFlag", "true");
                setValue(UserName, "LastPassChange", "Never");
                setValue(UserName, "Password", "Solar");
                setValue(UserName, "AllTimeFailedLoginAttempts", "0");
                setValue(UserName, "PassExpires", "true");
                setValue(UserName, "isPassExpired", "true");
                setValue(UserName, "LastLogin", "Never");
                setValue(UserName, "NotifyPath", "NULL");
                setValue(UserName, "NotepadPath", "NULL");
                if(Integer.parseInt(GetProperty("PermissionLevel")) >=6){
                    setValue(UserName, "UserNotification", "Enabled");
                }else{
                    setValue(UserName, "UserNotification", "Disabled");
                }
                saveUserProperties(UserName);
                return true;
            }
			MessageProcessor.processMessage(-1, "Failed to create User File", true);
			return false; 
        }
		MessageProcessor.processMessage(-1, "User Does NOT have Permission to Use this tool", true);
		return false;
    }

    public static boolean createNewFile(String User){
        if(UserListController.SearchForUser(User)){
            MessageProcessor.processMessage(-1, "This User Already Exists: " + User, true);
            return false;
        }
		UserProperties = UserProperties2 + User + ".properties";
		File file = new File(UserProperties);
		if(!file.exists()){
		    try {
		        file.createNewFile();
		        MessageProcessor.processMessage(1, "Successfully Created User File", false);
		        return true;
		    } catch (Exception e) {
		    	StringWriter sw = new StringWriter();
		        PrintWriter pw = new PrintWriter(sw);
		        e.printStackTrace(pw);
		        String stackTrace = sw.toString();

		        MessageProcessor.processMessage(2, stackTrace, true);
		        MessageProcessor.processMessage(-1, "Failed to create new File", true);
		        return false;
		    }
		}
        return true;
    }

    public static boolean loadUserProperties(String User){
        loadUserlist();
        if(UserListController.SearchForUser(User)){
            UserProperties = UserProperties2 + User + ".properties";
            try (InputStream input = new FileInputStream(UserProperties)){
                userprop.load(input);
                MessageProcessor.processMessage(1, "User Profile Loaded, Ready for Control Functions", false);
                return true;
            }catch(IOException e){
                MessageProcessor.processMessage(-2, e.toString(), true);
                MessageProcessor.processMessage(-1, "Unable to load User Profile", true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
                return false;
            }
        }
		MessageProcessor.processMessage(-1, "Unable to find User on Userlist.", true);
		return false;
    }

    public static boolean saveUserProperties(String User){
        loadUserlist();
        boolean success = UserListController.SearchForUser(User);
        if(success){
            UserProperties = UserProperties2 + User + ".properties";
            try (OutputStream output = new FileOutputStream(UserProperties)){
                userprop.store(output, null);
                MessageProcessor.processMessage(1, "User Profile Saved! MaintainUserController", false);
                return true;
            }catch(IOException e){
                MessageProcessor.processMessage(-2, e.toString(), true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
                return false;
            }
        }
		MessageProcessor.processMessage(-1, "User Not Found: LoginUserController: SaveUserProperties", false);
		return false;
    }

    public static boolean SearchForKey(String Key){
        return userprop.containsKey(Key);
    }

    public static String GetProperty(String Key){
        return userprop.getProperty(Key);
    }

    public static void updateProfileSettings(String User){
    	checkStatus();
        Logo.displayLogo();
        System.out.println("Account Updater: Menu");
        Logo.displayLine();
        System.out.println("[NAME]: Set Account Name");
        System.out.println("[PASS]: Change Password");
        System.out.println("[NOTI]: Request Notification Systems: Status: " + status);
        if(!SwitchController.focusUser.equals("Admin")){
            System.out.println("[PERM]: Request Permission Level Change");
        }
        System.out.println("[RETURN]: Return to Settings Menu");
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        if(option.equals("name")){
            requestAccountChange(User);
            updateProfileSettings(User);
        }else if(option.equals("pass")){
            LoginUserController.ChangePass(User);
            updateProfileSettings(User);
        }else if(option.equals("noti")){
            AdministrativeFunctions.newRequest(User, "Noti", "Enable Notifications", "Blank");
            if(status.equals("Enabled")) {
            	MessageProcessor.processMessage(-1, "Notifications have already been Enabled On your account!", true);
            	updateProfileSettings(User);
            }
            updateProfileSettings(User);
        }else if(option.equals("perm")){
            if(!SwitchController.focusUser.equals("Admin")){
                AdministrativeFunctions.newRequest(SwitchController.focusUser, "Permissions", "User Requesting permissions change", "NA");
                updateProfileSettings(User);
            }else{
                MessageProcessor.processMessage(-1, "Admin is not allowed to update this account setting", true);
                updateProfileSettings(User);
            }
        }else if(option.equals("return")){
            try {
                Settings.settingsMenu();
            } catch (Exception e) {
                MessageProcessor.processMessage(-2, "Failed to access Settings Menu, Reattempting to access Settings Menu", true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
                Settings.settingsMenu();
            }
        }else{
            MessageProcessor.processMessage(-1, "Invalid option, Try again!", true);
            updateProfileSettings(User);
        }
    }

    public static boolean forceProfileUpdate(String user){
        UserListController.loadUserList();
        if(UserListController.SearchForUser(user)){
            MaintainUserController.loadUserProperties(user);
            String message = "Successfully added new Value to profile.";
			if(!SearchForKey("UserNotification")){
                setValue(user, "UserNotification", "true"); 
                MessageProcessor.processMessage(1, message, true);
            }else {
                MessageProcessor.processMessage(-1, "User: " + SwitchController.focusUser + " Already had \"Notification\"", true);
            }
            if(!SearchForKey("NotifyPath")) {
            	setValue(user, "NotifyPath", "NULL");
            	MessageProcessor.processMessage(1, message, true);
            }else {
            	MessageProcessor.processMessage(-1, "User: " + SwitchController.focusUser + " Already had \"NotifyPath\"", true);
            }
            if(!SearchForKey("NotepadPath")) {
            	setValue(user, "NotepadPath", "NULL");
            	MessageProcessor.processMessage(1, message, true);
            }else {
            	MessageProcessor.processMessage(-1, "User: " + SwitchController.focusUser + " Already had \"NotepadPath\"", true);
            }
            saveUserProperties(user);
            return true;
        }
		MessageProcessor.processMessage(-1, "Failed to find user to update", true);
		return false;
    }

    public static boolean requestAccountChange(String User){
        UserListController.loadUserList();
        if(UserListController.SearchForUser(User)){
            MaintainUserController.loadUserProperties(User);
            Logo.displayLogo();
            System.out.println("Update Account Name: ");
            Logo.displayLine();
            System.out.println("Last Account Name: " + GetProperty("AccountName"));
            System.out.println("Account Name:");
            String requestAccountName = CustomScanner.nextLine();
            AdministrativeFunctions.newRequest(User, "Change Account Name", "Change Account Name to: " + requestAccountName, requestAccountName);
        }
        return true;
    }
    public static void updateAccountName(Stage stage, String User, String AccountName){
    	stage.setTitle("Update Account Name");
        UserListController.loadUserList();
        if(UserListController.SearchForUser(User)){
            MaintainUserController.loadUserProperties(User);
            
            Label accountLabel = new Label("Update Account Name:");
            Label lastAccountNameLabel = new Label("Last Account Name: " + GetProperty("AccountName"));
            Label requestedAccountNameLabel = new Label("Requested Account Name: " + AccountName);
            
            Button approveButton = new Button("Approve");
            approveButton.setOnAction(e -> {
                AdministrativeFunctions.removeRequest();
                setValue(User, "AccountName", AccountName);
                UserMessageHandler.sendMessageToUser(User, "Admin Approved Account Name Change Request, Change Made Successfully; Original Account Name: " + originalUserName + "; Request ID: " + requestID + "; " + newAccountName);
                Alert alert = new Alert(AlertType.INFORMATION, "The account name change has been approved.", ButtonType.OK);
                alert.showAndWait();
                stage.close();
            });

            Button denyButton = new Button("Deny");
            denyButton.setOnAction(e -> {
                MessageProcessor.processMessage(-1, "Admin Denied Request", true);
                UserMessageHandler.sendMessageToUser(User, "Admin Denied Your Request for Account Name Change; Original Account Name: " + originalUserName + "; Request ID: " + requestID + "; " + newAccountName);
                Alert alert = new Alert(AlertType.INFORMATION, "The account name change has been denied.", ButtonType.OK);
                alert.showAndWait();
                stage.close();
            });

            VBox layout = new VBox(10, accountLabel, lastAccountNameLabel, requestedAccountNameLabel, approveButton, denyButton);
            Scene scene = new Scene(layout, 300, 200);
            stage.setScene(scene);
            stage.show();
        } else {
            MessageProcessor.processMessage(-1, "Failed to find the User Properties.", true);
            Alert alert = new Alert(AlertType.ERROR, "Failed to find the User Properties.", ButtonType.OK);
            alert.showAndWait();
        }
        originalUserName.clear();
        requestID.clear();
        newAccountName.clear();
    }
    public static boolean updateAccountName(String User, String AccountName){
        UserListController.loadUserList();
        if(UserListController.SearchForUser(User)){
            MaintainUserController.loadUserProperties(User);
            Logo.displayLogo();
            System.out.println("Update Account Name: ");
            Logo.displayLine();
            System.out.println("Last Account Name: " + GetProperty("AccountName"));
            System.out.println("Requested Account Name: " + AccountName);
            ConsoleHandler.getConsole();
            System.out.println("Do you approve the New Account Name? (y/n)");
            String approve = CustomScanner.nextLine().toLowerCase();
            if(approve.equals("y") || approve.equals("yes")){
                AdministrativeFunctions.removeRequest();
                setValue(User, "AccountName", AccountName);
                UserMessageHandler.sendMessageToUser(User, "Admin Approved Account Name Change Request, Change Made Successfully; Original Account Name: "  + originalUserName + "; Request ID: " + requestID + "; " + newAccountName);
                return true;
            }
			if(approve.equals("n") || approve.equals("no")){
			    MessageProcessor.processMessage(-1, "Admin Denied Request", true);
			    UserMessageHandler.sendMessageToUser(User, "Admin Denied Your Request for Account Name Change; Original Account Name: "  + originalUserName + "; Request ID: " + requestID + "; " + newAccountName);
			}else{
			    MessageProcessor.processMessage(-1, "Invalid Option, Try Again", true);
			}
			return false;
        }
        originalUserName.clear();
        requestID.clear();
        newAccountName.clear();
		MessageProcessor.processMessage(-1, "Failed to find the User Properties.", true);
		return false;

    }

    public static boolean deleteUser(String user) {
        return true;
    }

	public static void deleteUser(String targetAccount, Stage stage) {
		// TODO Auto-generated method stub
		
	}

    /**
     * @param stage
     * @param user
     */
    private void updateProfileSettings(Stage stage, String user) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15, 20, 10, 10));
        vbox.setAlignment(Pos.CENTER);

        Text title = new Text("Account Updater: Menu");
        vbox.getChildren().add(title);

        Button btnName = new Button("Set Account Name");
        btnName.setOnAction(e -> requestAccountChange(user)); // Implement requestAccountChange

        Button btnPass = new Button("Change Password");
        btnPass.setOnAction(e -> LoginUserController.changePassword(stage, user)); // Implement changePassword

        Button btnNoti = new Button("Request Notification Systems");
        btnNoti.setOnAction(e -> AdministrativeFunctions.newRequest(SwitchController.focusUser, "Notify", "Notifications", "NA")); // Implement handleNotificationRequest

        Button btnPerm = new Button("Request Permission Level Change");
        btnPerm.setOnAction(e -> AdministrativeFunctions.newRequest(SwitchController.focusUser, "Permissions", "Permissions", "NA")); // Implement handlePermissionRequest

        Button btnReturn = new Button("Return to Settings Menu");
        btnReturn.setOnAction(e -> Settings.settingsMenu(stage)); // Implement returnToSettings

        vbox.getChildren().addAll(btnName, btnPass, btnNoti, btnPerm, btnReturn);

        // Optionally hide the permission button for non-admin users
        if (!user.equals("Admin")) {
            vbox.getChildren().add(btnPerm);
        }

        Scene scene = new Scene(vbox, 300, 250);
        stage.setScene(scene);
        stage.show();
    }
}
