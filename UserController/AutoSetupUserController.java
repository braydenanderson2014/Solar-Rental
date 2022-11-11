package UserController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import InstallManager.ProgramController;
import messageHandler.messageHandler;

public class AutoSetupUserController {
    public static String UserProperties = ProgramController.UserRunPath + "\\Users/Admin.properties";
    public static Properties userprop = new Properties();
    public static Boolean AutoCreateAdmin(){
        boolean success = checkFile("Admin");
        if(success){
            return !success;
        }else{
            File file = new File(UserProperties);
            if(!file.exists()){
                try {
                    file.createNewFile();
                    success = PopulateUserProperties();
                    UserListController.addUserToList("Admin", 8);
                } catch (Exception e) {
                    // TODO: handle exception
                    messageHandler.HandleMessage(-2, e.toString(), true);
                    return false;
                }
            }
            return success;
        }
        
    }
    private static boolean PopulateUserProperties() {
        userprop.setProperty("Account", "Enabled");
        userprop.setProperty("AccountName", "ADMIN ACCOUNT");
        userprop.setProperty("AllTimeFailedLoginAttempts", "0");
        userprop.setProperty("FailedLoginAttempts", "0");
        userprop.setProperty("LastLogin", "Never");
        userprop.setProperty("LastPassChange", "Never");
        userprop.setProperty("PassExpires", "true");
        userprop.setProperty("PassFlag", "true");
        userprop.setProperty("Password", "SolarAdmin");
        userprop.setProperty("PermissionLevel", "8");
        userprop.setProperty("SuccessfulLogins", "0");
        userprop.setProperty("Username", "Admin");
        saveUserProp();
        return false;
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
    private static boolean checkFile(String string) {
        return false;
    }
}
