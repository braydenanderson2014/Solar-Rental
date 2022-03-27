package Login;
import java.util.ArrayList;

import Assets.Logo;
import Assets.customScanner;
import MainSystem.MainMenu;
import messageHandler.Console;
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
            messageHandler.HandleMessage(1, "User: " + currentUser + " was added to the list of logged in users", true);
        }
        messageHandler.HandleMessage(1, currentUser + " requested focus", false);
        focusUser = currentUser;
        return Login.getCurrentUser();
    }
    public static void removeCurrentUser(String currentUser){
        messageHandler.HandleMessage(-1, "Attempting to Log out User: " + currentUser, false);
        if(loggedInUsers.contains(focusUser)){
            messageHandler.HandleMessage(2, currentUser + "Logging out!", true);
            loggedInUsers.remove(currentUser);
            messageHandler.HandleMessage(-1, currentUser + "Logged out!", true);
            messageHandler.HandleMessage(1, "Attempting to Switch Focus User to Last logged in User in CurrentUser list", false);
           
                int size = loggedInUsers.size();
                if(size > 0){
                    size --;
                    messageHandler.HandleMessage(1, "User: " + loggedInUsers.get(size) + "Needs password to login... Moving to LoginScreen", true);
                    Login.LoginScreen(loggedInUsers.get(size));
                }else{
                    messageHandler.HandleMessage(-1, "No Users are logged in... Switching to Login Screen", true);
                    focusUser = "Null";
                    Login.LoginScreen();
                }
            
        }else {
            messageHandler.HandleMessage(-1, "No Current Users detected. Unable to remove CurrentUser from list", true);
            Login.LoginScreen();
        }
    }
    public static void switchMenu(int mode) {
        Logo.displayLogo();
        System.out.println("Switch User Menu; Current user: " + focusUser);
        if(loggedInUsers.size() >= 2){
            messageHandler.HandleMessage(2, "Select a user to log in as", true);
            int x = 1;
            for(int i = 0; i < loggedInUsers.size(); i++){
                System.out.println("[" + x + "]" + loggedInUsers.get(i));
                x++;
            }
            Console.getConsole();
            String person = customScanner.nextLine().toLowerCase();
            if(person.equals("back")){
                if(mode == 1){
                    Login.LoginScreen();
                }else if(mode == 2){
                    MainMenu.mainMenu();
                }
            }else{
                int personAsInt = Integer.parseInt(person);
                personAsInt--;
                Login.LoginScreen(loggedInUsers.get(personAsInt));
            }
        }else{
            Login.LoginScreen();
        }
    }
}
