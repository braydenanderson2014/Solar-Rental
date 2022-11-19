package UserController;
import java.io.*;
import java.util.Properties;
//Custom Import

import InstallManager.ProgramController;
import messageHandler.MessageProcessor;
public class UserListController {
    static String UserList = ProgramController.userRunPath + "\\Users/Userlist.properties";
    public static Properties userlist = new Properties();
    public static boolean CheckUserListAvailability(){
        File file = new File(UserList);
        if(file.exists()){
            return true;
        }else{
            MessageProcessor.processMessage(-2, "Failed to Load userList in UserlistController", false);
            return false;
        }
    }

    public static boolean loadUserList() {
        if(CheckUserListAvailability()){
            try (InputStream input = new FileInputStream(UserList)){
                userlist.load(input);
                MessageProcessor.processMessage(1, "UserList Loaded", true);
                return true;
            }catch(IOException e){
                MessageProcessor.processMessage(-2, e.toString(), true);
                return false;
            }
        }else{
            MessageProcessor.processMessage(-2, "UserList Missing", true);
            return false;
        }

    }

    public static boolean SearchForUser(String user){
        loadUserList();
        return userlist.containsKey(user);
    }

    private static boolean SaveUserList(){
        loadUserList();
        try (OutputStream output = new FileOutputStream(UserList)){
            userlist.store(output, null);
            MessageProcessor.processMessage(1, "UserList Saved!", false);
            return true;
        }catch(IOException e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            return false;
        }
    }

    public static boolean checkUserList(String User){
        return userlist.containsKey(User);
    }

    public static boolean addUserToList(String user, int PermissionLevel){
        String PermissionLevelToString = String.valueOf(PermissionLevel);
        userlist.setProperty(user, PermissionLevelToString);
        boolean success = checkUserList(user);
        if(success){
            MessageProcessor.processMessage(1, "New User Added to List: " + user, false);
            MessageProcessor.processMessage(1, "User Was Added using UserListController", false);
            SaveUserList();
        }else{
            MessageProcessor.processMessage(-1, "Failed to add user to list", true);
        }
        return success;
    }

    public static boolean removeUserFromList(String user){
        userlist.remove(user);
        SaveUserList();
        return true;
    }

}
