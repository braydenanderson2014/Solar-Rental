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
import UserController.LoginUserController;
import UserController.UserController;
import messageHandler.AllMessages;
import messageHandler.Console;
import messageHandler.LogDump;
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
                if(LoginUserController.checkPassword(Username, Password) == true){
                    SwitchController.updateCurrentUser(Username);
                    MainMenu.mainMenu();
                }else if(LoginUserController.checkPassword(Username, Password) == false){
                    LoginScreen();
                }
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
                if(LoginUserController.checkPassword(User, Password) == true){
                    SwitchController.updateCurrentUser(User);
                    MainMenu.mainMenu();
                }else if(LoginUserController.checkPassword(User, Password) == false){
                    LoginScreen(User);
                }
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
            AdministrativeFunctions.newRequest("Guest", "new Account", "Blank Account", user);
            AdministrativeFunctions.AccountRequestNamePool.add(user);
            LoginScreen();
        }else if(Command.equals("_resetadmin")){
            if(AdministrativeFunctions.enableAdminAccount() == true){
                messageHandler.HandleMessage(2, "Admin Account Re-Enabled", true);
            }else{
                messageHandler.HandleMessage(-1, "Failed to Re-Enable Admin Account", true);
            }
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
        System.out.println("[DUMP]: Dump Logs To File [ALL]");
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
        }else if(Command.equals("dump")){
            LogDump.DumpLog("all");
            Command();
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
            AdministrativeFunctions.newRequest("Guest", "new Account", "Admin Created Blank Account" , user);
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
    
    public static boolean validateAdmin(){
        Logo.displayLogo();
        System.out.println("ADMIN PASSWORD: ");
        String password = customScanner.nextLine();
        if((LoginUserController.checkPassword("Admin", password) == true)){
            return true;
        }else{
            return false;
        }
    }
    public static String getCurrentUser() {
        return SwitchController.focusUser;
    }
    
    
}