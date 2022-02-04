package Login;

import java.io.File;

import Assets.Logo;
import Assets.customScanner;
import Install.FirstTimeController;
import Install.installManager;
import MainSystem.MainMenu;
import MainSystem.ProgramController;
import MainSystem.SettingsController;
import UserController.UserController;
import messageHandler.Console;
import messageHandler.messageHandler;

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
        Console.getConsole();
        System.out.println("Username: ");
        Username = customScanner.nextLine();
        if(Username.equals("command") || Username.equals("Command")){
            Command();
        }else{
            System.out.println("Password: ");
            Password = customScanner.nextLine();
            ValidateUserSignIn(Username, Password);
        }
    }
    public static void LoginScreen(String User){
        Logo.displayLogo();
        messageHandler.HandleMessage(1, "AutoFilling Username with " + User);
        Console.getConsole();
        System.out.println("Username: " + User);
        System.out.println("Password: ");
        ValidateUserSignIn(User, Password);
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
        boolean userList = UserController.SearchForUser(user);
        if(userList){
            String path = installManager.getPath() + "Users/" + user + ".properties";
            File file = new File(path);
            if(file.exists()){
                boolean exists = UserController.userprop.containsKey("Password");
                if(exists){
                    String password = UserController.getUserProp("Password");
                    if(Password.equals(password)){
                        if(UserController.getUserProp("Account").equals("Disabled")){
                            messageHandler.HandleMessage(-1, "This Account Is Disabled, Try a different Account");
                            LoginScreen();
                        }else{
                            MainMenu.mainMenu();
                        }
                    }else{
                        messageHandler.HandleMessage(-1, "Username or Password was incorrect");
                        LoginScreen();
                    }
                }else{
                    messageHandler.HandleMessage(-1, "User Password Property seems to be missing... Unable to Log In. Please fix \"Password\" Property in User Property field");
                    LoginScreen();
                }
            }else{
                messageHandler.HandleMessage(-1, "User properties seem to be missing... Please sign in as an admin and use the Create user Function");
                LoginScreen();
            }
        }else{
            messageHandler.HandleMessage(-1, "User Not Found");
            LoginScreen();
        }
        return true;
    }
    public static String getCurrentUser() {
        return CurrentUser;
    }
    public static void SignIn() {
    
    }
    
}