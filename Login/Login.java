package Login;

import java.io.File;
import java.net.URI;

import Assets.Logo;
import Assets.customScanner;
import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import MainSystem.AdministrativeFunctions;
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
        }else if (Username.equals("alloff")){
            SwitchController.forceAllLogoff();
            LoginScreen();   
        }else{
            System.out.println("Password: ");
            Password = customScanner.nextLine();
            if(Password.equals("off")){
                SwitchController.forceLogoff(Username);
                LoginScreen();
            }else{
                ValidateUserSignIn(Username, Password);
            }
        }
    }
    public static void LoginScreen(String User){
        Logo.displayLogo();
        messageHandler.HandleMessage(1, "AutoFilling Username with " + User, true);
        Console.getConsole();
        System.out.println("Username: " + User);
        System.out.println("Password: ");
        Password = customScanner.nextLine();
        if(Password.equals("off")){
            SwitchController.forceLogoff(User);
            LoginScreen();
        }else{
            ValidateUserSignIn(User, Password);
        }
    }
    private static void Command() {
        Logo.displayLogo();
        Console.getConsole();
        System.out.println("Command: ");
        String Command = customScanner.nextLine().toLowerCase();
        if(Command.equals("swi") || Command.equals("switch")){
            SwitchController.switchMenu(1);
        }else if(Command.equals("forceoff")){
            SwitchController.forceAllLogoff();
            Command();
        }else if(Command.equals("help") || Command.equals("list")){
            listCommands();
        }else if(Command.equals("restart")){
            ProgramController.Start();
        }else if(Command.equals("first time on")){
            FirstTimeManager.updateFirstTime();
            ProgramController.Start();
        }else if(Command.equals("back")){
            LoginScreen();
        }else if(Command.equals("create")){
            System.out.println("New Username: ");
            String user = customScanner.nextLine();
            AdministrativeFunctions.newRequest(user, "new Account");
            AdministrativeFunctions.AccountRequestNamePool.add(user);
            LoginScreen();
        }else if(Command.equals("_resetadmin")){
            AdministrativeFunctions.enableAdminAccount();
            LoginScreen();
        }else if(Command.equals("rab")){
            try {
                URI uri= new URI("https://github.com/login?return_to=%2Fbraydenanderson2014%2FSolar-Rental%2Fissues%2Fnew");
                java.awt.Desktop.getDesktop().browse(uri);
                messageHandler.HandleMessage(1, "Webpage opened in your default Browser...", true);
                messageHandler.HandleMessage(2, "WebPage: https://github.com/login?return_to=%2Fbraydenanderson2014%2FSolar-Rental%2Fissues%2Fnew", true);
            } catch (Exception e) {
                messageHandler.HandleMessage(-1, "Unable to Launch Webpage, [" + e.toString() + "]", true);
            }
            LoginScreen();
        }else if(Command.equals("_exit")){
            System.exit(1);
        }else{
            Command();
        }
    }
    
    private static void listCommands() {
        Logo.displayLogo();
        System.out.println("Commands:");
        Logo.displayLine();
        System.out.println("[SWI,SWITCH]: \"Swi\" or \"Switch\" will bring you to the Switch Control Menu if 2 or more users are logged in.");
        System.out.println("[HELP, LIST]: \"HELP\" or \"LIST\" Lists all the possible commands in the Login screen. You should know this command as you typed it to show this statement anyway");
        System.out.println("[RESTART]: \"Restart\" Restarts the program...(Thought that might be obvious)");
        System.out.println("[FIRST TIME ON]: \"First Time On\" Activates the First Time Setup... Program will restart into First Time Mode");
        System.out.println("[BACK]: \"Back\" takes you back to the Login Screen");
        System.out.println("[CREATE]: \"Create\" puts in a request for an account... The Admin has to follow up on the request");
        System.out.println("[_RESETADMIN]: \"_ResetAdmin\" re-enables the admin account after it is disabled. Automatically sets the passflag so you will have to change your password.");
        System.out.println("[FORCEOFF]: \"ForceOff\" Forces all users to logoff");
        System.out.println("[_EXIT]: \"_Exit\" Quits the program.");
        Console.getConsole();
        System.out.println("Command: ");
        String Command = customScanner.nextLine().toLowerCase();
        if(Command.equals("swi") || Command.equals("switch")){
            SwitchController.switchMenu(1);
        }else if(Command.equals("forceoff")){
            SwitchController.forceAllLogoff();
            listCommands();
        }else if(Command.equals("help") || Command.equals("list")){
            listCommands();
        }else if(Command.equals("restart")){
            ProgramController.Start();
        }else if(Command.equals("first time on")){
            FirstTimeManager.updateFirstTime();
            ProgramController.Start();
        }else if(Command.equals("back")){
            LoginScreen();
        }else if(Command.equals("create")){
            System.out.println("New Username: ");
            String user = customScanner.nextLine();
            AdministrativeFunctions.newRequest(user, "new Account");
            AdministrativeFunctions.AccountRequestNamePool.add(user);
            LoginScreen();
        }else if(Command.equals("_resetadmin")){
            AdministrativeFunctions.enableAdminAccount();
            LoginScreen();
        }else if(Command.equals("_exit")){
            System.exit(1);
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
                                messageHandler.HandleMessage(2, user + " Last Login: " + UserController.getUserProp("LastLogin"), true);
                                UserController.SetUserProp(user, "LastLogin", AllMessages.getTime());
                                SwitchController.updateCurrentUser(user);
                                MainMenu.mainMenu();
                            }else{
                                messageHandler.HandleMessage(1, user + " Logging in...", true);
                                messageHandler.HandleMessage(2, user + " Last Login: " + UserController.getUserProp("LastLogin"), true);
                                UserController.SetUserProp(user, "LastLogin", AllMessages.getTime());
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
                            messageHandler.HandleMessage(-1, "This account was locked out due to too many attempts", true);
                            failedLoginAttempts = 0;
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
    public static boolean validateAdmin(){
        Logo.displayLogo();
        System.out.println("ADMIN PASSWORD: ");
        String password = customScanner.nextLine();
        String user = SwitchController.focusUser;
        UserController.loadUserproperties("Admin");
        if(password.equals(UserController.getUserProp("Password"))){
            UserController.loadUserproperties(user);
            return true;
        }else{
            UserController.loadUserproperties(user);
            return false;
        }
    }
    public static String getCurrentUser() {
        return SwitchController.focusUser;
    }
    
    
}