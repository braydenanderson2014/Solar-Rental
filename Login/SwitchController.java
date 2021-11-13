package Login;
import java.util.ArrayList;
public class SwitchController {
    private static ArrayList<String>loggedInUsers = new ArrayList<String>();
    public SwitchController(){
        loggedInUsers.add("Temp");
    }
    public static String updateCurrentUser(){
        return Login.getCurrentUser();
    }
    public static void switchMenu() {
    
    }
}
