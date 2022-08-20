package UserController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import InstallManager.ProgramController;
import messageHandler.messageHandler;

public class MainSystemUserController {
    static String UserProperties = ProgramController.UserRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.UserRunPath + "\\Users/";
    public static Properties userprop = new Properties();
    private static boolean loadUserlist(){
        boolean success = UserListController.loadUserList();
        return success;
    }
    public static boolean loadUserProperties(String User){
        loadUserlist();
        if(UserListController.SearchForUser(User) == true){
            UserProperties = UserProperties2 + User + ".properties";
            try (InputStream input = new FileInputStream(UserProperties)){
                userprop.load(input);
                messageHandler.HandleMessage(1, "User Profile Loaded, Ready for Login Functions", true);
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
                messageHandler.HandleMessage(1, "User Profile Saved! LoginUserController", false);
                return true;
            }catch(IOException e){
                messageHandler.HandleMessage(-2, e.toString(), true);
                return false;
            }
        }else if(!success){
            messageHandler.HandleMessage(-1, "User Not Found: LoginUserController: SaveUserProperties", false);
            return false;
        }
        return false;
    }
    public static boolean SearchForKey(String Key){
        boolean exists = userprop.containsKey(Key);
        return exists;
    }
    public static String GetProperty(String Key){
        return userprop.getProperty(Key);
    }
    public static boolean checkUserProfileFile(String User) {
        UserProperties = UserProperties2 + User + ".properties";
        File file = new File(UserProperties);
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }
}
