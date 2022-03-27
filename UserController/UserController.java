package UserController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Properties;

import Assets.Logo;
import Assets.customScanner;
import InstallManager.ProgramController;
import Login.SwitchController;
import MainSystem.AdministrativeFunctions;
import MainSystem.Settings;
import messageHandler.Console;
import messageHandler.messageHandler;

public class UserController {
    static String currentUser = SwitchController.focusUser;
    static String User;
    static String Pass;
    static String UserList = ProgramController.UserRunPath + "\\Users/Userlist.properties";
    static String UserProperties = ProgramController.UserRunPath + "\\Users/" + User + ".properties";
    public static Properties userprop = new Properties();
    public static Properties userlist = new Properties();
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static boolean passFlag = false;
    public static boolean userExisted = false;
    //#region UserCreation
    public static boolean createUserFile(String user){
        String tempPath = ProgramController.UserRunPath + "\\Users/" + user + ".properties";
        File file = new File(tempPath);
        if(!file.exists()){
            userExisted = false;
            try {
                file.createNewFile();
                messageHandler.HandleMessage(1, user + "'s User File was created, Now ready to populate", true);
                userprop = new Properties();
            } catch (Exception e) {
                messageHandler.HandleMessage(-2, e.toString(), true);
                AdministrativeFunctions.AdministrativeMenu();
            }
        }else {
            userExisted = true;
        }
        return true;
    }
    public static boolean createNewUser(){

        System.out.println("Create User");
        Logo.displayLine();
        if(SwitchController.focusUser.equals("Admin") || Integer.parseInt(UserController.getUserProp("PermissionLevel"))>=8){
            System.out.println("New Username");
            String Username = customScanner.nextLine();
            createUserFile(Username);
            userprop = new Properties();
            userprop.setProperty("Username", Username);
            System.out.println("Password: ");
            userprop.setProperty("Password", "");
            System.out.println("Permission Level: ");
            try{
                int PermissionLevel = customScanner.nextInt();
                userprop.setProperty("PermissionLevel", String.valueOf(PermissionLevel));
                userlist.setProperty(Username, String.valueOf(PermissionLevel));

            }catch(InputMismatchException e){
                messageHandler.HandleMessage(-2, e.toString(), true);
                messageHandler.dumpAll();
                return false;
            }
            userprop.setProperty("PassFlag", "true");
            userprop.setProperty("AccountName", "Blank");
            userprop.setProperty("PassExpires", "true");
            userprop.setProperty("Account", "Enabled");
            userprop.setProperty("LastLogin", "Never");
            userprop.setProperty("FailedLoginAttempts", "0");
            userprop.setProperty("SuccessfulLogins", "0");
            userprop.setProperty("AllTimeFailedLoginAttempts", "0");
        }else{
            messageHandler.HandleMessage(-1, "User does not have Permission to use this function", false);
            return false;
        }
        return true;
    }
    public static boolean updateUserProfile(int mode){
        if(mode == 1){
            if(getUserProp("AccountName").equals("Blank")){
                Logo.displayLogo();
                System.out.println("Account Updater: Set Account Name");
                Logo.displayLine();
                String AccountName = customScanner.nextLine();
                SetUserProp(SwitchController.focusUser, "AccountName", AccountName);
            }
            
        }else if(mode == 2){
            Logo.displayLogo();
            System.out.println("Account Updater: Menu");
            Logo.displayLine();
            System.out.println("[NAME]: Set Account Name");
            System.out.println("[PASS]: Change Password");
            System.out.println("[RET]: Return to Settings Menu");
            Console.getConsole();
            String option = customScanner.nextLine().toLowerCase();
            if(option.equals("name")){
                setAccountName();
            }else if(option.equals("pass")){
                Settings.ChangePass(SwitchController.focusUser, 2, 2);
                updateUserProfile(mode);
            }else if(option.equals("ret")){
                Settings.SettingsMenu();
            }else{
                messageHandler.HandleMessage(-1, "Invalid option, Try again!", true);
                updateUserProfile(mode);
            }
        }
        return true;
    }
    public static boolean createUser(String user, int PermissionLevel, int mode){
        Logo.displayLogo();
        System.out.println("createUser");
        if(mode == 1){
            if(user.equals("Admin") || PermissionLevel >= 8){
                System.out.println("New UserName: ");
                User = customScanner.nextLine();
                System.out.println("Password: ");
                Pass = customScanner.nextLine();
                System.out.println("Set Permission Level for Account: ");
                PermissionLevel = customScanner.nextInt();
                userlist.setProperty(User, String.valueOf(PermissionLevel)); 
                saveUserList();
                setupUser(User, PermissionLevel, 2);
            }else{
                System.out.println("New UserName: ");
                User = customScanner.nextLine();
                System.out.println("Password: ");
                Pass = customScanner.nextLine();
                System.out.println("Set Permission Level for Account: ");
                PermissionLevel = customScanner.nextInt();
                userlist.setProperty(User, String.valueOf(PermissionLevel)); 
                saveUserList();
                userprop = new Properties();
                setupUser(user, PermissionLevel, 2);
            }
        }else if(mode ==2){
            User = user;
            userprop = new Properties();
            userlist.setProperty(user, String.valueOf(PermissionLevel));
            saveUserList();
            setupUser(user, PermissionLevel, 1);
        }else if(mode == 3){
            User = user;
            userprop = new Properties();
            userlist.setProperty(user, String.valueOf(PermissionLevel));
            saveUserList();
            setupUser(user, PermissionLevel, 3);
        }
        return true;
    }
    public static boolean setupUser(String user, int PermissionLevel, int mode){
        UserProperties = ProgramController.UserRunPath + "\\Users/" + user + ".properties";
        
        createUserFile(user);
        if(userExisted){
            return true;
        }else{
            Logo.displayLogo();
            if(mode == 1){
                
            }else if(mode == 2){
                System.out.println("Please Set a Account Name");
                String name = customScanner.nextLine();
                userprop.setProperty("AccountName", name);
                userprop.setProperty("Username", user);
                userprop.setProperty("Password", "Solar");
                userprop.setProperty("PassExpires", "true");
                userprop.setProperty("Account", "Enabled");
                userprop.setProperty("PermissionLevel", String.valueOf(PermissionLevel));
                userprop.setProperty("LastLogin", "Never");
                userprop.setProperty("LoginAttempts", "0");
                userprop.setProperty("PassFlag", "true");
    
                saveUserProp();
            }else if(mode == 3){
                userprop.setProperty("AccountName", "ADMIN ACCOUNT");
                userprop.setProperty("Username", user);
                userprop.setProperty("Password", "SolarAdmin");
                userprop.setProperty("PassExpires", "true");
                userprop.setProperty("Account", "Enabled");
                userprop.setProperty("PermissionLevel", String.valueOf(PermissionLevel));
                userprop.setProperty("LastLogin", "Never");
                userprop.setProperty("FailedLoginAttempts", "0");
                userprop.setProperty("SuccessfulLogins", "0");
                userprop.setProperty("AllTimeFailedLoginAttempts", "0");                
                userprop.setProperty("PassFlag", "true");
                saveUserProp();
            }
            return true;
        }
        
    }
    //#endregion
    //#region Deletion
    public static boolean deleteAccount(){
        if(SwitchController.focusUser.equals("Admin") || SwitchController.focusUser.equals("System")){
            messageHandler.HandleMessage(-1, "Unable to Delete an Admin Account!", true);
            return false;
        }
        User = SwitchController.focusUser;
        userlist.remove(User);
        userprop.clear();
        saveUserProp(User);
        messageHandler.HandleMessage(-1, "User Settings have been erased...", true);
        File file = new File(ProgramController.UserRunPath + "\\Users/Userlist.properties");
        if(file.exists()){
            file.deleteOnExit();
            messageHandler.HandleMessage(1, "User Account will be erased on Program Exit", true);
        }else{
            messageHandler.HandleMessage(-2, "User Account Not Found!", true);
        }
        return true;
    }
    public static boolean deleteUser(String user){
        userlist.remove(user);
        messageHandler.HandleMessage(-1, "User Settings have been erased...", true);
        File file = new File(ProgramController.UserRunPath + "\\Users/" + user + ".properties");
        if(file.exists()){
            file.deleteOnExit();
            messageHandler.HandleMessage(1, "User Account will be erased on Program Exit", true);
        }else{
            messageHandler.HandleMessage(-2, "User Account Not Found!", true);
        }
        return true;
    }
    //#endregion
    //#region AdjustUserSettings
    public static int adjPermLev(){
        return 1;
    }
    public static String setAccountName(){
        Logo.displayLogo();
        System.out.println("Last Account Name: " + userprop.getProperty("AccountName"));
        System.out.println("Account Name:");
        userprop.setProperty("AccountName", "Solar");
        return userprop.getProperty("AccountName");
    }
    //#endregion
    //#region get
    public static String getUser(String user){
        return userlist.getProperty(user);
    }
    public static String getUserProp(String prop){
        return userprop.getProperty(prop);
    }
    //#endregion
    //#region loadProperties files
    public static boolean loadUserList(){
        try (InputStream input = new FileInputStream(ProgramController.UserRunPath + "\\Users/Userlist.properties")){
            userlist.load(input);
            messageHandler.HandleMessage(1, "UserList Loaded", true);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return false;
        }
    }
    public static boolean loadUserproperties(){
        try (InputStream input = new FileInputStream(UserProperties)){
            userprop.load(input);
            messageHandler.HandleMessage(1, "UserProperties Loaded", true);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return false;
        }
    }
    public static boolean loadUserproperties(String Username){
        try (InputStream input = new FileInputStream(ProgramController.UserRunPath + "\\Users/" + Username + ".properties")){
            userprop.load(input);
            messageHandler.HandleMessage(1, "UserProperties Loaded for User: " + Username, true);
            messageHandler.HandleMessage(1, ProgramController.UserRunPath, false);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2,e.toString(), true);
            return false;
        }
    }
    //#endregion
    //#region SaveProperties files

    public static boolean saveUserList(){
        try (OutputStream output = new FileOutputStream(UserList)){
            userlist.store(output, null);
            messageHandler.HandleMessage(1, "UserList Saved!", false);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return false;
        }
    }
    public static boolean saveUserProp(){
        try (OutputStream output = new FileOutputStream(UserProperties)){
            userprop.store(output, null);
            messageHandler.HandleMessage(1, "UserProperties Saved!", false);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return false;
        }
    }
    public static boolean saveUserProp(String user){
        try (OutputStream output = new FileOutputStream(ProgramController.UserRunPath + "\\Users/" + user + ".properties")){
            userprop.store(output, null);
            messageHandler.HandleMessage(1, "UserProperties Saved!", false);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return false;
        }
    }
    //#endregion
    //#region Add/Set Users/Props
    public static String SetUserProp(String prop, String value){
        messageHandler.HandleMessage(1, prop + " was updated with value: " + value, false);
        userprop.setProperty(prop, value);
        saveUserProp();
        return "";
    }
    public static String SetUserProp(String user, String prop, String value){
        messageHandler.HandleMessage(1, user + " " + prop + " was updated with value: " + value, false);
        userprop.setProperty(prop, value);
        saveUserProp(user);
        return "";
    }
    public static String addUsertoList(String user, int PermissionLevel){
        String PermissionLevels = String.valueOf(PermissionLevel);
        userlist.setProperty(user, PermissionLevels);
        return "";
    }
    //#endregion
    //#region Remove Users/Props
    public static String removeUserfromList(String user){
        userlist.remove(user);
        return "";
    }
    public static String removeUserProp(String prop){
        userprop.remove(prop);
        return "";
    } 
    //#endregion
    //#region SearchforUser
    public static boolean SearchForUser(String user){
        boolean exists = userlist.containsKey(user);
        return exists;
    }
    //#endregion
    //#region SearchforProp
    public static String SearchForProp(String prop){
        return userprop.getProperty(prop);
    }
    //#endregion

    public static boolean createDefaultProperties(){
        userprop.setProperty("Console", "true");
        userprop.setProperty("Date/time", "true");
        userprop.setProperty("SystemMessages","true");
        userprop.setProperty("WarningMessages", "true");
        userprop.setProperty("ErrorMessages", "true");
        userprop.setProperty("UserMessages", "true");
        saveUserProp();
        return true;
    }

}
