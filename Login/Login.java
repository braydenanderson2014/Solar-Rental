package Login;

import java.io.File;

import Assets.Logo;
import Assets.customScanner;
import Install.FirstTimeController;
import Install.installManager;
import MainSystem.MainMenu;
import MainSystem.ProgramController;
import MainSystem.Settings;
import MainSystem.SettingsController;
import UserController.UserController;
import messageHandler.Console;
import messageHandler.messageHandler;

public class Login{
    private static String path = installManager.getPath();
    private static String CurrentUser = "Null";
    static String Username;
    protected static String Password;
    private static int failedLoginAttempts = 0;
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
        if(Username.equals("command") || Username.equals("Command")){
            Command();
        }else if(SwitchController.focusUser.equals(User)){
            MainMenu.mainMenu();
        }else{
            Password = customScanner.nextLine();
            ValidateUserSignIn(Username, Password);
        }
    }
    private static void Command() {
        Logo.displayLogo();
        System.out.println("Command: ");
        String Command = customScanner.nextLine().toLowerCase();
        if(Command.equals("swi") || Command.equals("switch")){
            SwitchController.switchMenu(1);
        }else if(Command.equals("restart")){
            ProgramController.start();
            installManager.installMenu();
        }else if(Command.equals("firsttime on")){
            FirstTimeController.updateFirstTime(true);
            SettingsController.setSetting("FirstTime", "true");
        }else if(Command.equals("back")){
            LoginScreen();
        }
    }
    public static boolean ValidateUserSignIn(String user, String Password){
        UserController.loadUserList();
        boolean userList = UserController.SearchForUser(user);
        if(userList){
            String path = installManager.getPath() + "\\Program Files\\Users/" + user + ".properties";
            UserController.loadUserproperties(user);
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
                            failedLoginAttempts = 0;
                            UserController.loadUserproperties(user);
                            if(Boolean.parseBoolean(UserController.getUserProp("PassFlag")) == true){
                                Settings.ChangePass(user, 1,0);
                                messageHandler.HandleMessage(-1, "");
                                MainMenu.mainMenu();
                            }else{
                                messageHandler.HandleMessage(-1, "");
                                messageHandler.HandleMessage(1, user + " Logging in...");
                                MainMenu.mainMenu();
                            }
                        }
                    }else{
                        //SwitchController.updateCurrentUser(user);
                        failedLoginAttempts ++;
                        messageHandler.HandleMessage(-1, "Username or Password was incorrect; ATTEMPTS: [" + failedLoginAttempts + "]");
                        if(failedLoginAttempts >= 3){
                            SwitchController.updateCurrentUser(user);
                            UserController.loadUserproperties(user);
                            UserController.SetUserProp(user, "LoginAttempts", String.valueOf(failedLoginAttempts));
                            UserController.SetUserProp(user, "Account", "Disabled");
                            messageHandler.HandleMessage(-1, user + " Account was disabled due to too many failed login attempts");
                        }
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
            messageHandler.HandleMessage(-1, "User Not Found, Check userlist to verify User Exists");
            LoginScreen();
        }
        return true;
    }
    public static String getCurrentUser() {
        return SwitchController.focusUser;
    }
    public static void SignIn() {
    
    }
    
}