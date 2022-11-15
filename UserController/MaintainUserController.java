package UserController;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import Assets.Logo;
import Assets.customScanner;
import InstallManager.ProgramController;
import Login.SwitchController;
import MainSystem.AdministrativeFunctions;
import MainSystem.Settings;
import messageHandler.Console;
import messageHandler.UserMessageHandler;
import messageHandler.messageHandler;

public class MaintainUserController {
    static String UserProperties = ProgramController.UserRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.UserRunPath + "\\Users/";
    public static Properties userprop = new Properties();
    public static String status = "Disabled";
    private static boolean loadUserlist(){
        return UserListController.loadUserList();
    }

    public static boolean setValue(String User, String key, String value){
        loadUserProperties(User);
        userprop.setProperty(key, value);
        saveUserProperties(User);
        return true;
    }

    public static boolean createNewUser(String UserToCreate){
        Logo.displayLogo();
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >=8){
            System.out.println("Please Create a new Username For the User: ");
            String UserName = UserToCreate;
            if(UserToCreate.equals("Blank")){
                UserName = customScanner.nextLine();
            }
            if(createNewFile(UserName) == true){
                System.out.println("Please Type An Account Name for the New User:");
                String AccountName = customScanner.nextLine();
                setValue(UserName, "Username", UserName);
                setValue(UserName, "AccountName", AccountName);
                System.out.println("Please Set a Permission Level for the User (0<10)");
                try {
                    int PLevel = customScanner.nextInt();
                    setValue(UserName, "PermissionLevel", String.valueOf(PLevel));
                    UserListController.addUserToList(UserName, PLevel);
                } catch (Exception e) {
                    messageHandler.HandleMessage(-2, e.toString(), true);
                    messageHandler.HandleMessage(-1, "Failed to set permission Level, Assigning Level 0", true);
                    setValue(UserName, "PermissionLevel", "0");
                    UserListController.addUserToList(UserName, 0);
                }
                messageHandler.HandleMessage(2, "All Manual Input Complete, System Now Completing Profile Setup", true);
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
                if(Integer.parseInt(GetProperty("PermissionLevel")) >=6){
                    setValue(UserName, "Notification", "Enabled");
                }else{
                    setValue(UserName, "Notification", "Disabled");
                }
                return true;
            }else {
                messageHandler.HandleMessage(-1, "Failed to create User File", true);
                return false;
            } 
        }else{
            messageHandler.HandleMessage(-1, "User Does NOT have Permission to Use this tool", true);
            return false;
        }
    }

    public static boolean createNewFile(String User){
        if(UserListController.SearchForUser(User)){
            messageHandler.HandleMessage(-1, "This User Already Exists: " + User, true);
            return false;
        }else{
            UserProperties = UserProperties2 + User + ".properties";
            File file = new File(UserProperties);
            if(!file.exists()){
                try {
                    file.createNewFile();
                    messageHandler.HandleMessage(1, "Successfully Created User File", false);
                    return true;
                } catch (Exception e) {
                    messageHandler.HandleMessage(-1, "Failed to create new File", true);
                    return false;
                }
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
                messageHandler.HandleMessage(1, "User Profile Loaded, Ready for Control Functions", false);
                return true;
            }catch(IOException e){
                messageHandler.HandleMessage(-2, e.toString(), true);
                messageHandler.HandleMessage(-1, "Unable to load User Profile", false);
                return false;
            }
        }else{
            messageHandler.HandleMessage(-1, "Unable to find User on Userlist.", true);
            return false;
        }
    }

    public static boolean saveUserProperties(String User){
        loadUserlist();
        boolean success = UserListController.SearchForUser(User);
        if(success){
            UserProperties = UserProperties2 + User + ".properties";
            try (OutputStream output = new FileOutputStream(UserProperties)){
                userprop.store(output, null);
                messageHandler.HandleMessage(1, "User Profile Saved! MaintainUserController", false);
                return true;
            }catch(IOException e){
                messageHandler.HandleMessage(-2, e.toString(), true);
                return false;
            }
        }else{
            messageHandler.HandleMessage(-1, "User Not Found: LoginUserController: SaveUserProperties", false);
            return false;
        }
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
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
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
                messageHandler.HandleMessage(-1, "Admin is not allowed to update this account setting", true);
                updateProfileSettings(User);
            }
        }else if(option.equals("return")){
            try {
                Settings.SettingsMenu();
            } catch (Exception e) {
                messageHandler.HandleMessage(-2, "Failed to access Settings Menu, Reattempting to access Settings Menu", true);
                Settings.SettingsMenu();
            }
        }else{
            messageHandler.HandleMessage(-1, "Invalid option, Try again!", true);
            updateProfileSettings(User);
        }
    }

    public static boolean forceProfileUpdate(String User){
        UserListController.loadUserList();
        if(UserListController.SearchForUser(User)){
            MaintainUserController.loadUserProperties(User);
            if(!SearchForKey("Notification")){
                setValue(User, "Notification", "true"); 
                messageHandler.HandleMessage(1, "Successfully added new Value to profile.", true);
                return true;
            }else {
                messageHandler.HandleMessage(-1, "User Already had \"Notification\"", true);
                return false;
            }
        }else{
            messageHandler.HandleMessage(-1, "Failed to find user to update", true);
            return false;
        }
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
            String requestAccountName = customScanner.nextLine();
            AdministrativeFunctions.newRequest(User, "Change Account Name", "Change Account Name to: " + requestAccountName, requestAccountName);
        }
        return true;
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
            Console.getConsole();
            System.out.println("Do you approve the New Account Name? (y/n)");
            String approve = customScanner.nextLine().toLowerCase();
            if(approve.equals("y") || approve.equals("yes")){
                AdministrativeFunctions.removeRequest();
                setValue(User, "AccountName", AccountName);
                UserMessageHandler.sendMessageToUser(User, "Admin Approved Account Name Change Request, Change Made Successfully");
                return true;
            }else {
                if(approve.equals("n") || approve.equals("no")){
                    messageHandler.HandleMessage(-1, "Admin Denied Request", true);
                    UserMessageHandler.sendMessageToUser(User, "Admin Denied Your Request for Account Name Change");
                }else{
                    messageHandler.HandleMessage(-1, "Invalid Option, Try Again", true);
                }
                return false;
            }
        }else{
            messageHandler.HandleMessage(-1, "Failed to find the User Properties.", true);
            return false;
        }

    }

    public static boolean deleteUser(String user) {
        return true;
    }
}
