package UserController;

import java.util.Properties;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import InstallManager.ProgramController;
import Login.SwitchController;
import MainSystem.AdministrativeFunctions;
import MainSystem.Settings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;
import messageHandler.UserMessageHandler;

public class MaintainUserController {
    static String UserProperties = ProgramController.userRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.userRunPath + "\\Users/";
    public static Properties userprop = new Properties();
    public static String status = "Disabled";
    private static boolean loadUserlist(){
        return UserListController.loadUserList();
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
                UserMessageHandler.sendMessageToUser(User, "Admin Approved Account Name Change Request, Change Made Successfully");
                Alert alert = new Alert(AlertType.INFORMATION, "The account name change has been approved.", ButtonType.OK);
                alert.showAndWait();
                stage.close();
            });

            Button denyButton = new Button("Deny");
            denyButton.setOnAction(e -> {
                MessageProcessor.processMessage(-1, "Admin Denied Request", true);
                UserMessageHandler.sendMessageToUser(User, "Admin Denied Your Request for Account Name Change");
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
                UserMessageHandler.sendMessageToUser(User, "Admin Approved Account Name Change Request, Change Made Successfully");
                return true;
            }
			if(approve.equals("n") || approve.equals("no")){
			    MessageProcessor.processMessage(-1, "Admin Denied Request", true);
			    UserMessageHandler.sendMessageToUser(User, "Admin Denied Your Request for Account Name Change");
			}else{
			    MessageProcessor.processMessage(-1, "Invalid Option, Try Again", true);
			}
			return false;
        }
		MessageProcessor.processMessage(-1, "Failed to find the User Properties.", true);
		return false;

    }

    public static boolean deleteUser(String user) {
        return true;
    }

	public static void deleteUser(String targetAccount, Stage stage) {
		// TODO Auto-generated method stub
		
	}
}