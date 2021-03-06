package MainSystem;
import java.net.URI;
import java.util.Scanner;
import Assets.Logo;
import Assets.VersionController;
import Assets.customScanner;
import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import Login.SwitchController;
import UserController.UserController;
import messageHandler.Console;
import messageHandler.ConsoleSettings;
import messageHandler.LogDump;
import messageHandler.SystemMessages;
import messageHandler.ViewLogManager;
import messageHandler.messageHandler;
public class Settings{
    public static Scanner scan = new Scanner(System.in);
    public static String path = ProgramController.UserRunPath;
    public static String logType = "all";
    public static void SettingsMenu(){
        Logo.displayLogo();
        System.out.println();
        System.out.println("Settings Menu");
        System.out.println("============================================");
        System.out.println("[PATH]: Program's Working Directory" + path);
        System.out.println("[CONSOLE]: Console Settings");
        System.out.println("[RAB]: Report a Bug");
        System.out.println("[LOG]: Log Dump Type: " + logType.toUpperCase());
        System.out.println("[VIEWLOGS]: View logs Menu");
        System.out.println("[DUMP]: Dump Logs to File");
        System.out.println("[UPDATE]: Manually Update User Profile");
        System.out.println("[FORCE]: Force User Profile to update to latest Settings");
        if(Integer.parseInt(UserController.getUserProp("PermissionLevel")) >= 6){
            System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimeManager.checkFirstTime());
        }
        System.out.println("[RETURN]: Return");
        System.out.println();
        Console.getConsole();
        String option = scan.nextLine().toLowerCase();
        if(option.equals("path")){
            //PathController.pathMenu(1);
        }else if(option.equals("update")){
            UserController.updateUserProfile(2);   
        }else if(option.equals("rab")){
            try {
                URI uri= new URI("https://github.com/login?return_to=%2Fbraydenanderson2014%2FSolar-Rental%2Fissues%2Fnew");
                java.awt.Desktop.getDesktop().browse(uri);
                messageHandler.HandleMessage(1, "Webpage opened in your default Browser...", true);
                messageHandler.HandleMessage(2, "WebPage: https://github.com/login?return_to=%2Fbraydenanderson2014%2FSolar-Rental%2Fissues%2Fnew", true);
            } catch (Exception e) {
                messageHandler.HandleMessage(-1, "Unable to Launch Webpage, [" + e.toString() + "]", true);
            }
            SettingsMenu();
        }else if(option.equals("force")){
            UserController.updateUserProfile(1);
            SettingsMenu();
        }else if(option.equals("viewlogs")){
            ViewLogManager.ViewMenu(1);
        }else if(option.equals("dump")){
            LogDump.DumpLog(logType);
            SettingsMenu();   
        }else if(option.equals("console")){
            ConsoleSettings.ConsoleSettingsMenu();
        }else if(option.equals("log")){
            if(logType.equals("all")){
                logType = "system";
            }else if(logType.equals("system")){
                logType = "user";
            }else if(logType.equals("user")){
                logType = "warning";
            }else if(logType.equals("warning")){
                logType = "error";
            }else if(logType.equals("error")){
                logType = "all";
            }
            SettingsController.setSetting("LogType", logType);
            messageHandler.HandleMessage(1, "Log Type: " + logType, true);
            SettingsMenu();
        }else if(option.equals("first")){
            if(Integer.parseInt(UserController.getUserProp("PermissionLevel")) >= 6){
                if(FirstTimeManager.checkFirstTime() == true){
                    SettingsController.setSetting("FirstTime", "false");
                    FirstTimeManager.FirstTime = false;
                }else if(FirstTimeManager.checkFirstTime() == false){
                    FirstTimeManager.FirstTime = true;
                    SettingsController.setSetting("FirstTime", "true");
                }
                messageHandler.HandleMessage(1, "IsFirstTime: " + FirstTimeManager.checkFirstTime(), true);
                SettingsMenu();
            }else{
                messageHandler.HandleMessage(-1, "User Does not have permission to use this function", true);
                SettingsMenu();
            }
        }else if(option.equals("return")){
            //MainSystem
            MainMenu.mainMenu();
        }else {
            messageHandler.HandleMessage(-1, "Invalid option, Try again", true);
            SettingsMenu();
        }
    }
    public static boolean ChangePass(String user, int mode, int submode) {
        String focusUser = SwitchController.focusUser;
        messageHandler.HandleMessage(1, user + " Password being updated! ", false);
        if(mode == 1){
            //flagged for pass change
            UserController.loadUserproperties(user);
            messageHandler.HandleMessage(2, user, true);
            if(UserController.SearchForProp("PassFlag").equals("true")){
                UserController.SetUserProp(UserController.getUserProp("Username"), "PassFlag", "false");
                System.out.println("Your Account: " + user + " has been flagged for a password change! Please change your password now to continue!");
                System.out.println("New Password");
                String newPass = customScanner.nextLine();
                if(newPass.equals("IgnoreFlag") && Integer.parseInt(UserController.getUserProp("PermissionLevel")) >= 8){
                    return true;
                }
                System.out.println("Confirm New Password");
                String ConfirmNewPass = customScanner.nextLine();
                if(newPass.equals(UserController.SearchForProp("Password"))){
                    System.out.println("Unable to Use old Password");
                    ChangePass(user, mode, submode);
                }else if(newPass.equals(ConfirmNewPass)){
                    UserController.SetUserProp(UserController.getUserProp("Username"), "LastPassChange", "");
                    UserController.SetUserProp(UserController.getUserProp("Username"), "Password", newPass);
                    messageHandler.HandleMessage(1, "New Password set.", true);
                    SwitchController.updateCurrentUser(user);
                    UserController.loadUserproperties(SwitchController.focusUser);
                }else{
                    System.out.println("Invalid... Your passwords do not match.");
                    ChangePass(user, mode, submode);
                }
                UserController.loadUserproperties(focusUser);
            }
        }else if(mode == 2){
            //changing pass from set
            if(submode == 1){
                Logo.displayLogo();
                if(SwitchController.focusUser.equals("Admin") || SettingsController.getSetting("PermissionLevel").equals("8")){
                    System.out.println("New Password: ");
                    String Password = customScanner.nextLine();
                    UserController.loadUserproperties(user);
                    UserController.SetUserProp(user, "Password", Password);
                    UserController.loadUserproperties(SwitchController.focusUser);
                }
                return true;
            }else if(submode == 2){
                System.out.println("Old Password: ");
                String oldPass = customScanner.nextLine();
                System.out.println("New Password: ");
                String newPass = customScanner.nextLine();
                System.out.println("Confirm New Password");
                String confirmNewPass = customScanner.nextLine();
                UserController.loadUserproperties(SwitchController.focusUser);
                if(newPass.equals(confirmNewPass)){
                    messageHandler.HandleMessage(1, "New Password and Confirm New Password fields matched...", false);
                    if(UserController.getUserProp("Password").equals(oldPass)){
                        UserController.SetUserProp(user, "Password", newPass);
                        messageHandler.HandleMessage(1, "Password Updated!", true);
                        return true;
                    }else{
                        messageHandler.HandleMessage(-1, "Old Password did not Match Password on File... Try again", true);
                        ChangePass(user, mode, submode);
                    }
                }else{
                    messageHandler.HandleMessage(-1, "New Password and Confirm New Password do not match", true);
                    ChangePass(user, mode, submode);
                }
            }
        }
        return true;
    }
    
    public static boolean LoadSettings(){
        messageHandler.HandleMessage(1, "Loading Settings file from config.properties", false);
        System.out.println(SystemMessages.getLastMessage());
        SettingsController.loadSettings();
        messageHandler.HandleMessage(1, "Settings File Loaded... Now reading from Settings", false);
        System.out.println(SystemMessages.getLastMessage());
        
        messageHandler.HandleMessage(1, "FirstTime Setting: False", false);
        System.out.println(SystemMessages.getLastMessage());
        messageHandler.HandleMessage(1, "Path Letter: " + SettingsController.getSetting("PathLetter") + ", Path: " + SettingsController.getSetting("Path"), false);
        System.out.println(SystemMessages.getLastMessage());
        VersionController.setVersion(SettingsController.getSetting("Version"));
        messageHandler.HandleMessage(1, "Version: " + SettingsController.getSetting("Version"), false);
        System.out.println(SystemMessages.getLastMessage());
        Settings.logType = SettingsController.getSetting("LogType");
        messageHandler.HandleMessage(1, "LogType: " + SettingsController.getSetting("LogType"), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.ErrorSet = Boolean.parseBoolean(SettingsController.getSetting("ErrorSet"));
        messageHandler.HandleMessage(1, "ErrorSet: " + Boolean.parseBoolean(SettingsController.getSetting("ErrorSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.WarningSet = Boolean.parseBoolean(SettingsController.getSetting("WarningSet"));
        messageHandler.HandleMessage(1, "WarningSet: " + Boolean.parseBoolean(SettingsController.getSetting("WarningSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.SystemSet = Boolean.parseBoolean(SettingsController.getSetting("SystemSet"));
        messageHandler.HandleMessage(1, "SystemSet: " + Boolean.parseBoolean(SettingsController.getSetting("SystemSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.UserNotifySet = Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet"));
        messageHandler.HandleMessage(1, "UserNotifySet: " + Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet")), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.timeSet = Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet"));
        messageHandler.HandleMessage(1, "Time Set: " + Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        UserController.passFlag = Boolean.parseBoolean(SettingsController.getSetting("PassFlag"));
        messageHandler.HandleMessage(1, "PassFlag: " + Boolean.parseBoolean(SettingsController.getSetting("PassFlag")), false);
        System.out.println(SystemMessages.getLastMessage());
        messageHandler.HandleMessage(1, "All Settings Loaded", false);
        System.out.println(SystemMessages.getLastMessage());
        return true;
    }
}