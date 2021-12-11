package Login;

import Assets.Logo;
import Assets.customScanner;
import Install.FirstTimeController;
import Install.installManager;
import MainSystem.ProgramController;
import MainSystem.SettingsController;

public class Login{
    private static String path = installManager.getPath();
    private static String CurrentUser = "Null";
    static String Username;
    protected static String Password;
    public Login(){
        
        System.out.println(CurrentUser);
    }
    public static void LoginScreen(){
        Logo.displayLogo();
        System.out.println("Username: ");
        Username = customScanner.nextLine();
        if(Username.equals("command") || Username.equals("Command")){
            Command();
        }else{
            System.out.println("Password: ");
            Password = customScanner.nextLine();
        }
    }
    private static void Command() {
        Logo.displayLogo();
        System.out.println("Command: ");
        String Command = customScanner.nextLine().toLowerCase();
        if(Command.equals("swi") || Command.equals("switch")){
            SwitchController.switchMenu();
        }else if(Command.equals("restart")){
            ProgramController.start();
            installManager.installMenu();
        }else if(Command.equals("firsttime on")){
            FirstTimeController.updateFirstTime(true);
            SettingsController.setSetting("FirstTime", "true");
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