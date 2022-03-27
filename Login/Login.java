package Login;

import java.io.File;

import Assets.Logo;
import Assets.customScanner;
import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import MainSystem.MainMenu;
import MainSystem.Settings;
import UserController.UserController;
import messageHandler.AllMessages;
import messageHandler.Console;
import messageHandler.messageHandler;

public class Login{
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
        messageHandler.HandleMessage(1, "AutoFilling Username with " + User, true);
        Console.getConsole();
        System.out.println("Username: " + User);
        System.out.println("Password: ");
        Password = customScanner.nextLine();
        ValidateUserSignIn(Username, Password);
    }
    private static void Command() {
        Logo.displayLogo();
        Console.getConsole();
        System.out.println("Command: ");
        String Command = customScanner.nextLine().toLowerCase();
        if(Command.equals("swi") || Command.equals("switch")){
            SwitchController.switchMenu(1);
        }else if(Command.equals("restart")){
            ProgramController.Start();
        }else if(Command.equals("firsttime on")){
            FirstTimeManager.updateFirstTime();
            ProgramController.Start();
        }else if(Command.equals("back")){
            LoginScreen();
        }else if(Command.equals("create")){
            UserController.createUser("user", 0, 1);
        }else{
            Command();
        }
    }
    
    public static boolean ValidateUserSignIn(String user, String Password){
        UserController.loadUserList();
        boolean userList = UserController.SearchForUser(user);
        if(userList){
            String path = ProgramController.UserRunPath + "\\Users/" + user + ".properties";
            UserController.loadUserproperties(user);
            File file = new File(path);
            if(file.exists()){
                boolean exists = UserController.userprop.containsKey("Password");
                if(exists){
                    String password = UserController.getUserProp("Password");
                    if(Password.equals(password)){
                        if(UserController.getUserProp("Account").equals("Disabled")){
                            messageHandler.HandleMessage(-1, "This Account Is Disabled, Try a different Account Account:" + user, true);
                            LoginScreen();
                        }else{
                            failedLoginAttempts = 0;
                            if(Boolean.parseBoolean(UserController.getUserProp("PassFlag")) == true){
                                Settings.ChangePass(UserController.getUserProp("Username"), 1,0);
                                messageHandler.HandleMessage(-1, "", false);
                                messageHandler.HandleMessage(2, user + " Last Login :" + UserController.getUserProp("LastLogin"), true);
                                UserController.SetUserProp(user, "LastLogin", AllMessages.dTime);
                                SwitchController.updateCurrentUser(user);
                                MainMenu.mainMenu();
                            }else{
                                messageHandler.HandleMessage(1, user + " Logging in...", true);
                                messageHandler.HandleMessage(2, user + " Last Login: " + UserController.getUserProp("LastLogin"), true);
                                UserController.SetUserProp(user, "LastLogin", AllMessages.dTime);
                                SwitchController.updateCurrentUser(user);
                                int SuccessfulLogins = Integer.parseInt(UserController.getUserProp("SuccessfulLogins")) + 1;
                                UserController.SetUserProp(user, "SuccessfulLogins", String.valueOf(SuccessfulLogins));
                                MainMenu.mainMenu();
                            }
                        }
                    }else{
                        //SwitchController.updateCurrentUser(user);
                        failedLoginAttempts ++;
                        messageHandler.HandleMessage(-1, "Username or Password was incorrect; ATTEMPTS: [" + failedLoginAttempts + "]", true);
                        UserController.SetUserProp(user, "FailedLoginAttempts", String.valueOf(failedLoginAttempts));
                        UserController.SetUserProp(user, "AllTimeFailedLoginAttempts", String.valueOf(failedLoginAttempts + Integer.parseInt(UserController.getUserProp("AllTimeFailedLoginAttempts"))));
                        if(failedLoginAttempts >= 3){
                            SwitchController.updateCurrentUser(user);
                            UserController.loadUserproperties(user);
                            UserController.SetUserProp(user, "Account", "Disabled");
                        }
                        LoginScreen();
                    }
                }else{
                    messageHandler.HandleMessage(-1, "User Password Property seems to be missing... Unable to Log In. Please fix \"Password\" Property in User Property field", true);
                    LoginScreen();
                }
            }else{
                messageHandler.HandleMessage(-1, "User properties seem to be missing... Please sign in as an admin and use the Create user Function", true);
                LoginScreen();
            }
        }else{
            messageHandler.HandleMessage(-1, "User Not Found, Check userlist to verify User Exists", true);
            LoginScreen();
        }
        return true;
    }
    public static String getCurrentUser() {
        return SwitchController.focusUser;
    }
    
    
}