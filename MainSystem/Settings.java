package MainSystem;
import java.util.Scanner;
import Assets.Logo;
import Assets.VersionController;
import Assets.customScanner;
import Install.FirstTimeController;
import Install.PathController;
import Install.installManager;
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
    public static String path = installManager.getPath();
    public static String logType = "all";
    public static void SettingsMenu(){
        Logo.displayLogo();
        System.out.println();
        System.out.println("Settings Menu");
        System.out.println("============================================");
        System.out.println("[PATH]: Program's Working Directory" + path);
        System.out.println("[CONSOLE]: Console Settings");
        System.out.println("[LOG]: Log Dump Type: " + logType.toUpperCase());
        System.out.println("[VIEWLOGS]: View logs Menu");
        System.out.println("[DUMP]: Dump Logs to File");
        System.out.println("[PASS]:    Change Pass");
        System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimeController.checkFirstTime());
        System.out.println("[RETURN]: Return");
        System.out.println();
        Console.getConsole();
        String option = scan.nextLine().toLowerCase();
        if(option.equals("path")){
            PathController.pathMenu(1);
        }else if(option.equals("pass")){
            ChangePass(SwitchController.focusUser, 2,2);
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
            messageHandler.HandleMessage(1, "Log Type: " + logType);
            SettingsMenu();
        }else if(option.equals("first")){
            if(FirstTimeController.checkFirstTime() == true){
                FirstTimeController.updateFirstTime(false);
            }else if(FirstTimeController.checkFirstTime() == false){
                FirstTimeController.updateFirstTime(true);
            }
            messageHandler.HandleMessage(1, "IsFirstTime: " + FirstTimeController.checkFirstTime());
            SettingsMenu();
        }else if(option.equals("return")){
            //MainSystem
            MainMenu.mainMenu();
        }else {
            messageHandler.HandleMessage(-1, "Invalid option, Try again");
            SettingsMenu();
        }
    }
    public static boolean ChangePass(String user, int mode, int submode) {
        String focusUser = SwitchController.focusUser;
        messageHandler.HandleMessage(1, user + " Password being updated! ");
        if(mode == 1){
            //flagged for pass change
            UserController.loadUserproperties(user);
            if(UserController.SearchForProp("PassFlag").equals("true")){
                UserController.SetUserProp("PassFlag", "false");
                System.out.println("Your Account has been flagged for a password change! Please change your password now to continue!");
                System.out.println("New Password");
                String newPass = customScanner.nextLine();
                System.out.println("Confirm New Password");
                String ConfirmNewPass = customScanner.nextLine();
                if(newPass.equals(UserController.SearchForProp("Password"))){
                    System.out.println("Unable to Use old Password");
                    ChangePass(user, mode, submode);
                }else if(newPass.equals(ConfirmNewPass)){
                    UserController.SetUserProp("Password", newPass);
                    messageHandler.HandleMessage(1, "New Password set.");
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
                    messageHandler.HandleMessage(1, "New Password and Confirm New Password fields matched...");
                    if(UserController.getUserProp("Password").equals(oldPass)){
                        UserController.SetUserProp(user, "Password", newPass);
                        messageHandler.HandleMessage(1, "Password Updated!");
                        return true;
                    }else{
                        messageHandler.HandleMessage(-1, "Old Password did not Match Password on File... Try again");
                        ChangePass(user, mode, submode);
                    }
                }else{
                    messageHandler.HandleMessage(-1, "New Password and Confirm New Password do not match");
                    ChangePass(user, mode, submode);
                }
            }
        }
        return true;
    }
    public static void configMenu(){
        Logo.displayLogo();
        System.out.println();
        System.out.println("Settings Menu");
        System.out.println("============================================");
        System.out.println("[PATH]: Program's Working Directory: " + path);
        System.out.println("[CONSOLE]: Console Settings");
        System.out.println("[LOG]: Log Dump Type: " + logType.toUpperCase());
        System.out.println("[VIEWLOGS]: View logs Menu");
        System.out.println("[DUMP]: Dump Logs to File");
        System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimeController.checkFirstTime());
        System.out.println("[RETURN]: Return");
        System.out.println();
        Console.getConsole();
        String option = scan.nextLine().toLowerCase();
        if(option.equals("path")){
            PathController.pathMenu(2);
        }else if(option.equals("dump")){
            LogDump.DumpLog(logType);
            configMenu();   
        }else if(option.equals("viewlogs")){
            ViewLogManager.ViewMenu(2);
        }else if(option.equals("console")){
            ConsoleSettings.dConsoleSettingsMenu();
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
            messageHandler.HandleMessage(1, "Log Type: " + logType);
            configMenu();
        }else if(option.equals("first")){
            if(FirstTimeController.checkFirstTime() == true){
                FirstTimeController.updateFirstTime(false);
            }else if(FirstTimeController.checkFirstTime() == false){
                FirstTimeController.updateFirstTime(true);
            }
            messageHandler.HandleMessage(1, "IsFirstTime: " + FirstTimeController.checkFirstTime());
            configMenu();
        }else if(option.equals("return")){
            installManager.installMenu();
        }else {
            messageHandler.HandleMessage(-1, "Invalid option, Try again");
            configMenu();
        }
    }
    public static boolean LoadSettings(){
        messageHandler.HandleMessage(1, "Loading Settings file from config.properties");
        System.out.println(SystemMessages.getLastMessage());
        SettingsController.loadSettings();
        messageHandler.HandleMessage(1, "Settings File Loaded... Now reading from Settings");
        System.out.println(SystemMessages.getLastMessage());
        FirstTimeController.updateFirstTime(false);
        messageHandler.HandleMessage(1, "FirstTime Setting: False");
        System.out.println(SystemMessages.getLastMessage());
        installManager.setPath(SettingsController.getSetting("Path"),SettingsController.getSetting("PathLetter"));
        messageHandler.HandleMessage(1, "Path Letter: " + SettingsController.getSetting("PathLetter") + ", Path: " + SettingsController.getSetting("Path"));
        System.out.println(SystemMessages.getLastMessage());
        VersionController.setVersion(SettingsController.getSetting("Version"));
        messageHandler.HandleMessage(1, "Version: " + SettingsController.getSetting("Version"));
        System.out.println(SystemMessages.getLastMessage());
        Settings.logType = SettingsController.getSetting("LogType");
        messageHandler.HandleMessage(1, "LogType: " + SettingsController.getSetting("LogType"));
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.ErrorSet = Boolean.parseBoolean(SettingsController.getSetting("ErrorSet"));
        messageHandler.HandleMessage(1, "ErrorSet: " + Boolean.parseBoolean(SettingsController.getSetting("ErrorSet")));
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.WarningSet = Boolean.parseBoolean(SettingsController.getSetting("WarningSet"));
        messageHandler.HandleMessage(1, "WarningSet: " + Boolean.parseBoolean(SettingsController.getSetting("WarningSet")));
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.SystemSet = Boolean.parseBoolean(SettingsController.getSetting("SystemSet"));
        messageHandler.HandleMessage(1, "SystemSet: " + Boolean.parseBoolean(SettingsController.getSetting("SystemSet")));
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.UserNotifySet = Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet"));
        messageHandler.HandleMessage(1, "UserNotifySet: " + Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet")));
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.timeSet = Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet"));
        messageHandler.HandleMessage(1, "Time Set: " + Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet")));
        System.out.println(SystemMessages.getLastMessage());
        UserController.passFlag = Boolean.parseBoolean(SettingsController.getSetting("PassFlag"));
        messageHandler.HandleMessage(1, "PassFlag: " + Boolean.parseBoolean(SettingsController.getSetting("PassFlag")));
        System.out.println(SystemMessages.getLastMessage());
        messageHandler.HandleMessage(1, "All Settings Loaded");
        System.out.println(SystemMessages.getLastMessage());
        return true;
    }
}