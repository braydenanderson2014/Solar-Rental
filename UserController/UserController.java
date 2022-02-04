package UserController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import Assets.Logo;
import Assets.customScanner;
import Install.installManager;
import Login.SwitchController;
import messageHandler.messageHandler;

public class UserController {
    static String currentUser = SwitchController.focusUser;
    static String User;
    static String Pass;
    static String UserList = installManager.getPath() + "UserList.properties";
    static String UserProperties = installManager.getPath() + "Users/" + User + ".properties";
    public static Properties userprop = new Properties();
    public static Properties userlist = new Properties();
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    //#region UserCreation
    public static boolean createUser(String user, int PermissionLevel){
        Logo.displayLogo();
        if(user.equals("Admin") || PermissionLevel >= 8){
            System.out.println("New UserName: ");
            User = customScanner.nextLine();
            System.out.println("Password: ");
            Pass = customScanner.nextLine();
            System.out.println("Set Permission Level for Account: ");
            PermissionLevel = customScanner.nextInt();
            userlist.setProperty(User, String.valueOf(PermissionLevel)); 
            setupUser(User, PermissionLevel, 2);
        }else{
            System.out.println("New UserName: ");
            User = customScanner.nextLine();
            System.out.println("Password: ");
            Pass = customScanner.nextLine();
            userlist.setProperty(User, String.valueOf(PermissionLevel)); 
            saveUserList();
        }
        return true;
    }
    public static boolean setupUser(String user, int PermissionLevel, int mode){
        Logo.displayLogo();
        if(mode == 1){
            userprop.setProperty("AccountName", "System");
            userprop.setProperty("Username", "System");
            userprop.setProperty("Password", "NOLOGIN");
            userprop.setProperty("PassExpires", "false");
            userprop.setProperty("Account", "Disabled");
            userprop.setProperty("PermissionLevel", String.valueOf(PermissionLevel));
            userprop.setProperty("LastLogin", "Never");
            userprop.setProperty("LoginAttempts", "0");
            saveUserProp();
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
            saveUserProp();
        }
        return true;
    }
    //#endregion
    //#region Deletion
    public static boolean deleteAccount(){
        User = SwitchController.focusUser;
        userlist.remove(User);
        userprop.clear();
        messageHandler.HandleMessage(-1, "User Settings have been erased...");
        File file = new File(installManager.getSystemPath() + "Users/" + User + "UserList.properties");
        if(file.exists()){
            file.deleteOnExit();
            messageHandler.HandleMessage(1, "User Account will be erased on Program Exit");
        }else{
            messageHandler.HandleMessage(-2, "User Account Not Found!");
        }
        return true;
    }
    public static boolean deleteUser(String user){
        userprop.clear();
        userlist.remove(user);
        messageHandler.HandleMessage(-1, "User Settings have been erased...");
        File file = new File(installManager.getSystemPath() + "Users/" + User + ".properties");
        if(file.exists()){
            file.deleteOnExit();
            messageHandler.HandleMessage(1, "User Account will be erased on Program Exit");
        }else{
            messageHandler.HandleMessage(-2, "User Account Not Found!");
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
        userprop.setProperty("AccountName", customScanner.nextLine());
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
        try (InputStream input = new FileInputStream(installManager.getPath() + "UserList.properties")){
            userlist.load(input);
            messageHandler.HandleMessage(1, "UserList Loaded");
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString());
            return false;
        }
    }
    public static boolean loadUserproperties(){
        try (InputStream input = new FileInputStream(UserProperties)){
            userprop.load(input);
            messageHandler.HandleMessage(1, "UserProperties Loaded");
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString());
            return false;
        }
    }
    //#endregion
    //#region SaveProperties files

    public static boolean saveUserList(){
        try (OutputStream output = new FileOutputStream(UserList)){
            userlist.store(output, null);
            messageHandler.HandleMessage(1, "UserList Saved!");
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString());
            return false;
        }
    }
    public static boolean saveUserProp(){
        try (OutputStream output = new FileOutputStream(UserProperties)){
            userprop.store(output, null);
            messageHandler.HandleMessage(1, "UserProperties Saved!");
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString());
            return false;
        }
    }
    //#endregion
    //#region Add/Set Users/Props
    public static String SetUserProp(String prop, String value){
        userprop.setProperty(prop, value);
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
