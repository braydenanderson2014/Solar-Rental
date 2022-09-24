package messageHandler;

import java.io.File;

import InstallManager.ProgramController;
import MainSystem.AdministrativeFunctions;
import UserController.UserListController;

public class UserMessageHandler {
    static String UserProperties = ProgramController.UserRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.UserRunPath + "\\Users/";
    public static boolean sendMessageToUser(String user, String message) {
        if(UserListController.SearchForUser(user) == true){
            if(CheckUserAccount(user) == true){

            }else{
                messageHandler.HandleMessage(-1, "User Account Notifications cannot be found, Sending Request to admin for a new Account", true);
                AdministrativeFunctions.newRequest(user, "Notifications File", "User requesting Notifications Enabled", "NA");
            }
        }else{
            messageHandler.HandleMessage(-1, "Failed to find Target User: " + user, true);
            return false;
        }
        return true;
    }

    

    private static boolean CheckUserAccount(String user) {
        UserListController.loadUserList();
        if(UserListController.SearchForUser(user) == true){
            UserProperties = UserProperties2 + user + ".properties";
            File file = new File(UserProperties);
            if(file.exists()){
                return true;
            }else{
                return false;
            }
        }else{
            messageHandler.HandleMessage(-1, "Unable to find User on Userlist.", true);
            return false;
        }
    }
    
}
