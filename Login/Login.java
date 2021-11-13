package Login;

import Assets.Logo;
import Assets.customScanner;
import Install.installManager;

public class Login{
    private static String path = installManager.getPath();
    private static String CurrentUser = "Null";
    static String Username;
    protected String Password;
    public Login(){
        
        System.out.println(CurrentUser);
    }
    public static void LoginScreen(){
        Logo.displayLogo();
        System.out.println("Username: ");
        Username = customScanner.nextLine();
        if(Username.equals("command") || Username.equals("Command")){
            Command();
        }
    }
    private static void Command() {
        Logo.displayLogo();
        System.out.println("Command: ");
        String Command = customScanner.nextLine().toLowerCase();
        if(Command.equals("swi") || Command.equals("switch")){
            SwitchController.switchMenu();
        }
    }
    public static boolean ValidateUserSignIn(String user, String Password){
        return true;
    }
    public static String getCurrentUser() {
        return CurrentUser;
    }
    public static void SignIn() {
    
    }
    
}