package MainSystem;
import java.net.URI;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Assets.Logo;
import Assets.VersionController;
import Assets.customScanner;
import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import Login.SwitchController;
import UserController.MainSystemUserController;
import UserController.MaintainUserController;
import UserController.UserController;
import messageHandler.AllMessages;
import messageHandler.Console;
import messageHandler.ConsoleSettings;
import messageHandler.LogDump;
import messageHandler.SystemMessages;
import messageHandler.ViewLogManager;
import messageHandler.messageHandler;
public class Settings{
    public static Scanner scan = new Scanner(System.in);
    public static String path = ProgramController.UserRunPath;
    public static ArrayList<String> MyRequests = new ArrayList<String>();
    public static String logType = "all";
    public static boolean checkRequests(){
        MyRequests.clear();
        for(int i = 0; i < AdministrativeFunctions.AdministrativeRequestUser.size(); i++){
            if(AdministrativeFunctions.AdministrativeRequestUser.get(i).equals(SwitchController.focusUser)){
                MyRequests.add(AdministrativeFunctions.AdministrativeRequestFull.get(i));
            }
        }
        return true;
    }
    public static boolean printRequests(){
        checkRequests();
        int item = 0;
        Logo.displayLogo();
        for(int i = 0; i < MyRequests.size(); i++){
            item++;
            System.out.println(item + ": " + MyRequests.get(i));
        }
        try {
            int option = 23;
            System.out.println("Select An Item: (Type \"0\" to go back to the main menu)");
            option = customScanner.nextInt();
            option--;
            if(option == 0){
                SettingsMenu();
            }else{
                String temp = "NULL";
                Logo.displayLogo();
                System.out.println("Selected Item: " + MyRequests.get(option));
                System.out.println("Would you like to Revoke your Request? (y/n)");
                temp = scan.nextLine();
                if(temp.equals("y") || temp.equals("yes")){
                    for(int i = 0; i < AdministrativeFunctions.AdministrativeRequestKeyWord.size(); i++){
                        if(AdministrativeFunctions.AdministrativeRequestFull.get(i).contains(MyRequests.get(option))){
                            MyRequests.remove(option);
                            AdministrativeFunctions.AdministrativeRequestFull.remove(i);
                            messageHandler.HandleMessage(1, "Removed From Administrative Request Full", false);
                            AdministrativeFunctions.AdministrativeRequestID.remove(i);
                            messageHandler.HandleMessage(1, "Removed From Request ID", false);
                            AdministrativeFunctions.AdministrativeRequestKeyWord.remove(i);
                            messageHandler.HandleMessage(1, "Removed From Request Keyword", false);
                            AdministrativeFunctions.AdministrativeRequestUser.remove(i);
                            messageHandler.HandleMessage(1, "Removed From Request User", false);
                            AdministrativeFunctions.AdministrativeRequestedName.remove(i);
                            messageHandler.HandleMessage(1, "Removed From Requested Name", false);
                            AdministrativeFunctions.AdministrativeRequests.remove(i);
                            messageHandler.HandleMessage(1, "Removed From Administrative Requests", false);
                            messageHandler.HandleMessage(1, "Successfully Removed Requests", true);
                        }else{
                            messageHandler.HandleMessage(-1, "Unable to find requests in the main Request queue", true);
                        }
                    }
                }else{
                    messageHandler.HandleMessage(-1, "Invalid option, Try again.", true);
                    printRequests();
                }
            }
        } catch (InputMismatchException e) {
            messageHandler.HandleMessage(-2,e.toString(), true);
        }catch (IndexOutOfBoundsException e) {
            messageHandler.HandleMessage(-2, e.toString(), true);
        }
        return true;
    }
    public static void SettingsMenu() throws NumberFormatException{
        checkRequests();
        Logo.displayLogo();
        System.out.println();
        System.out.println("Settings Menu");
        System.out.println("============================================");
        System.out.println("[PATH]: Program's Working Directory: \"" + path + "\"");
        System.out.println("[CONSOLE]: Console Settings");
        System.out.println("[RAB]: Report a Bug");
        System.out.println("[LOG]: Log Dump Type: " + logType.toUpperCase());
        System.out.println("[VIEWLOGS]: View logs Menu");
        System.out.println("[DUMP]: Dump Logs to File");
        System.out.println("[UPDATE]: Manually Update User Profile");
        System.out.println("[FORCE]: Force User Profile to update to latest Settings");
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 6){
            System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimeManager.checkFirstTime());
        }
        System.out.println("[REQUESTS]: My Requests: Current Requests: " + MyRequests.size());
        System.out.println("[RETURN]: Return");
        System.out.println();
        Console.getConsole();
        String option = scan.nextLine().toLowerCase();
        if(option.equals("path")){
            //PathController.pathMenu(1);
        }else if(option.equals("update")){
            MaintainUserController.loadUserProperties(SwitchController.focusUser);
            MaintainUserController.updateProfileSettings(MaintainUserController.GetProperty("Username"));  
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
            //UserController.updateUserProfile(1);
            SettingsMenu();
        }else if(option.equals("viewlogs")){
            ViewLogManager.ViewMenu(1);
        }else if(option.equals("dump")){
            LogDump.DumpLog(logType);
            SettingsMenu();   
        }else if(option.equals("console")){
            ConsoleSettings.ConsoleSettingsMenu();
        }else if(option.equals("requests")){
            if(MyRequests.size() == 0){
                messageHandler.HandleMessage(-1, "No Requests Have Been Made", true);
            }else {
                printRequests();
            }
            SettingsMenu();
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
            if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 6){
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