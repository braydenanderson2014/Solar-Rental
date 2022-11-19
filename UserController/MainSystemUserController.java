package UserController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import InstallManager.ProgramController;
import messageHandler.MessageProcessor;

public class MainSystemUserController {
    static String UserProperties = ProgramController.userRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.userRunPath + "\\Users/";
    public static Properties userprop = new Properties();
    private static boolean loadUserlist(){
        return UserListController.loadUserList();
    }

    public static boolean loadUserProperties(String User){
        loadUserlist();
        if(UserListController.SearchForUser(User)){
            UserProperties = UserProperties2 + User + ".properties";
            try (InputStream input = new FileInputStream(UserProperties)){
                userprop.load(input);
                MessageProcessor.processMessage(1, "User Profile Loaded, Ready for Login Functions", true);
                return true;
            }catch(IOException e){
                MessageProcessor.processMessage(-2, e.toString(), true);
                MessageProcessor.processMessage(-1, "Unable to load User Profile", false);
                return false;
            }
        }else{
            MessageProcessor.processMessage(-1, "Unable to find User on Userlist.", true);
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
                MessageProcessor.processMessage(1, "User Profile Saved! LoginUserController", false);
                return true;
            }catch(IOException e){
                MessageProcessor.processMessage(-2, e.toString(), true);
                return false;
            }
        }else{
            MessageProcessor.processMessage(-1, "User Not Found: LoginUserController: SaveUserProperties", false);
            return false;
        }

    }

    public static boolean SearchForKey(String Key){
        return userprop.containsKey(Key);
    }

    public static String GetProperty(String Key){
        return userprop.getProperty(Key);
    }

    public static boolean checkUserProfileFile(String User) {
        UserProperties = UserProperties2 + User + ".properties";
        File file = new File(UserProperties);
        return file.exists();
    }
}
