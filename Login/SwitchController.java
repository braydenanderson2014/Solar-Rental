package Login;
import java.util.ArrayList;

import messageHandler.messageHandler;
public class SwitchController {
    private static ArrayList<String>loggedInUsers = new ArrayList<String>();
    public static String focusUser;
    public SwitchController(){
        loggedInUsers.add("Temp");
    }
    public static String updateCurrentUser(String currentUser){
        if(!loggedInUsers.contains(currentUser)){
            loggedInUsers.add(currentUser);
            messageHandler.HandleMessage(1, "User: " + currentUser + " was added to the list of logged in users");
        }
        messageHandler.HandleMessage(1, currentUser + " requested focus");
        focusUser = currentUser;
        return Login.getCurrentUser();
    }
    public static int removeCurrentUser(String currentUser){
        messageHandler.HandleMessage(-1, "Attempting to Log out User: " + currentUser);
        if(loggedInUsers.contains(currentUser)){
            messageHandler.HandleMessage(1, currentUser + "Logging out!");
            loggedInUsers.remove(currentUser);
            messageHandler.HandleMessage(-1, currentUser + "Logged out!");
            messageHandler.HandleMessage(1, "Attempting to Switch Focus User to Last logged in User in CurrentUser list");
            if(focusUser.equals(currentUser)){
                int size = loggedInUsers.size();
                if(size > 0){
                    size --;
                    messageHandler.HandleMessage(1, "User: " + loggedInUsers.get(size) + "Needs password to login... Moving to LoginScreen");
                    Login.LoginScreen(loggedInUsers.get(size));
                }else{
                    messageHandler.HandleMessage(-1, "No Users are logged in... Switching to Login Screen");
                    Login.LoginScreen();
                }
            }
            return 0;
        }else {
            return 0;
        }
    }
    public static void switchMenu() {
    
    }
}
