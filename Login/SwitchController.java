package Login;
import java.util.ArrayList;

import MainSystem.MainMenu;
import assets.CustomScanner;
import assets.Logo;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;
public class SwitchController {
    private static ArrayList<String>loggedInUsers = new ArrayList<>();
    public static String focusUser;
    private SwitchController(){
        loggedInUsers.add("Temp");
    }

    public static String updateCurrentUser(String currentUser){
        if(!loggedInUsers.contains(currentUser)){
            loggedInUsers.add(currentUser);
            MessageProcessor.processMessage(1, "User: " + currentUser + " was added to the list of logged in users", true);
        }
        MessageProcessor.processMessage(1, currentUser + " requested focus", false);
        focusUser = currentUser;
        return Login.getCurrentUser();
    }

    public static void removeCurrentUser(String currentUser){
        MessageProcessor.processMessage(-1, "Attempting to Log out User: " + currentUser, false);
        if(loggedInUsers.contains(focusUser)){
            MessageProcessor.processMessage(2, currentUser + " Logging out!", true);
            loggedInUsers.remove(currentUser);
            MessageProcessor.processMessage(-1, currentUser + " Logged out!", true);
            MessageProcessor.processMessage(1, "Attempting to Switch Focus User to Last logged in User in CurrentUser list", false);
            int size = loggedInUsers.size();
            if(size > 0){
                size --;
                MessageProcessor.processMessage(1, "User: " + loggedInUsers.get(size) + "Needs password to login... Moving to LoginScreen", true);
                focusUser = loggedInUsers.get(size);
                Login.loginScreen(loggedInUsers.get(size));
            }else{
                MessageProcessor.processMessage(-1, "No Users are logged in... Switching to Login Screen", true);
                focusUser = "Null";
                Login.loginScreen();
            } 
        }else {
            MessageProcessor.processMessage(-1, "No Current Users detected. Unable to remove CurrentUser from list", true);
            Login.loginScreen();
        }
    }

    public static boolean forceLogoff(String user){
        if(loggedInUsers.contains(user)){
            loggedInUsers.remove(user);
            MessageProcessor.processMessage(-1, user + " Logged out!", true);
            return true;
        }else {
            MessageProcessor.processMessage(-1, user + " is not logged in", true);
            return false;
        }
    }

    public static boolean forceAllLogoff(){
        loggedInUsers.clear();
        MessageProcessor.processMessage(1, "All Users Logged Off", true);
        return true;
    }

    public static void switchMenu(int mode) {
        Logo.displayLogo();
        System.out.println("Switch User Menu; Current user: " + focusUser);
        if(loggedInUsers.size() >= 2){
            MessageProcessor.processMessage(2, "Select a user to log in as", true);
            int x = 1;
            System.out.println("[0]: Go Back");
            for(int i = 0; i < loggedInUsers.size(); i++){
                System.out.println("[" + x + "]: " + loggedInUsers.get(i));
                x++;
            }
            ConsoleHandler.getConsole();
            String person = CustomScanner.nextLine().toLowerCase();
            if(person.equals("back") || person.equals("0")){
                if(mode == 1){
                    Login.loginScreen();
                }else if(mode == 2){
                    MainMenu.mainMenu();
                }
            }else{
                int personAsInt = Integer.parseInt(person);
                if(personAsInt == 0){
                    Login.loginScreen();
                }
                personAsInt--;
                if(loggedInUsers.get(personAsInt).equals(focusUser)) {
                	MainMenu.mainMenu();
                }
                Login.loginScreen(loggedInUsers.get(personAsInt));
            }
        }else{
        	MessageProcessor.processMessage(-1, "No other Logged in users", true);
            Login.loginScreen();
        }
    }
}
