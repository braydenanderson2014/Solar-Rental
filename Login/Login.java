package Login;


import java.net.URI;

import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import MainSystem.AdministrativeFunctions;
import MainSystem.MainMenu;

import MainSystem.SettingsController;
import UserController.LoginUserController;
import assets.CustomScanner;
import assets.Logo;

import messageHandler.ConsoleHandler;

import messageHandler.MessageProcessor;

public class Login{
    private static String currentUser = "Null";
    static String username;
    protected static String password;
    
    private Login(){
        System.out.println(currentUser);
    }

    public static void loginScreen(){
        Logo.displayLogo();
        System.out.println("LOGIN SCREEN: ");
        Logo.displayLine();
        ConsoleHandler.getConsole();
        System.out.println("Username: ");
        username = CustomScanner.nextLine();
        if(username.equals("command") || username.equals("Command")){
            command(0);
        }else if (username.equals("alloff")){
            SwitchController.forceAllLogoff();
            loginScreen();   
        }else{
            System.out.println("Password: ");
            password = CustomScanner.nextLine();
            if(password.equals("off")){
                SwitchController.forceLogoff(username);
                loginScreen();
            }else{
                if(LoginUserController.checkPassword(username, password)){
                    SwitchController.updateCurrentUser(username);
                    MainMenu.mainMenu();
                }else if(!LoginUserController.checkPassword(username, password)){
                    loginScreen();
                }
            }
        }
    }

    public static void loginScreen(String user){
        Logo.displayLogo();
        System.out.println("LOGIN SCREEN: ");
        Logo.displayLine();
        MessageProcessor.processMessage(1, "AutoFilling Username with " + user, true);
        ConsoleHandler.getConsole();
        System.out.println("Username: " + user);
        System.out.println("Password: ");
        password = CustomScanner.nextLine();
        if(password.equals("off")){
            SwitchController.forceLogoff(user);
            loginScreen();
        }else{
            if(LoginUserController.checkPassword(user, password)){
                SwitchController.updateCurrentUser(user);
                MainMenu.mainMenu();
            }else if(!LoginUserController.checkPassword(user, password)){
                loginScreen(user);
            }
        }
    }

    private static void command(int mode) {
        Logo.displayLogo();
        ConsoleHandler.getConsole();
        if(mode == 1) {
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
        }
        System.out.println("Command: ");
        String command = CustomScanner.nextLine().toLowerCase();
        if(command.equals("swi") || command.equals("switch")){
            SwitchController.switchMenu(1);
        }else if(command.equals("forceoff")){
            SwitchController.forceAllLogoff();
            command(0);
        }else if(command.equals("help") || command.equals("list")){
            command(1);
        }else if(command.equals("restart")){
            ProgramController.Start();
        }else if(command.equals("first time on")){
            FirstTimeManager.updateFirstTime();
            ProgramController.Start();
        }else if(command.equals("back")){
            loginScreen();
        }else if(command.equals("create")){
            System.out.println("New Username: ");
            String user = CustomScanner.nextLine();
            AdministrativeFunctions.newRequest("Guest", "new Account", "Blank Account", user);
            AdministrativeFunctions.accountRequestNamePool.add(user);
            loginScreen();
        }else if(command.equals("_resetadmin")){
            if(AdministrativeFunctions.enableAdminAccount()){
                MessageProcessor.processMessage(2, "Admin Account Re-Enabled", true);
            }else{
                MessageProcessor.processMessage(-1, "Failed to Re-Enable Admin Account", true);
            }
            loginScreen();
        }else if(command.equals("rab")){
            try {
                URI uri= new URI(SettingsController.getSetting("debugSite"));
                java.awt.Desktop.getDesktop().browse(uri);
                MessageProcessor.processMessage(1, "Webpage opened in your default Browser...", true);
                MessageProcessor.processMessage(2, "WebPage: " + SettingsController.getSetting("debugSite"), true);
            } catch (Exception e) {
                MessageProcessor.processMessage(-1, "Unable to Launch Webpage, [" + e.toString() + "]", true);
            }
            loginScreen();
        }else if(command.equals("_exit")){
            System.exit(1);
        }else{
            command(0);
        }
    }

   

    public static boolean validateAdmin(){
        Logo.displayLogo();
        System.out.println("ADMIN PASSWORD: ");
        String password = CustomScanner.nextLine();
        return (LoginUserController.checkPassword("Admin", password));
    }

    public static String getCurrentUser() {
        return SwitchController.focusUser;
    }

}