package MainSystem;
import static java.lang.Integer.parseInt;

import java.net.URI;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import Assets.Logo;
import Assets.VersionController;
import Assets.customScanner;
import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import Login.SwitchController;
import UserController.LoginUserController;
import UserController.MainSystemUserController;
import UserController.MaintainUserController;
import messageHandler.AllMessages;

import messageHandler.ConsoleHandler;
import messageHandler.ConsoleSettings;
import messageHandler.LogDump;
import messageHandler.SystemMessages;
import messageHandler.ViewLogManager;
import messageHandler.messageHandler;
public class Settings{
    public static String path = ProgramController.UserRunPath;
    public static List<String> myRequests = new ArrayList<>();
    public static List<String> myNotifications = new ArrayList<>();
    public static int myNotificationsAmount = 0;
    public static String logType = "all";
    public static int requestsMade = 0;
    public static int updateRequestsMade(){
        requestsMade = AdministrativeFunctions.AdministrativeRequests.size();
        return requestsMade;
    }
    public static boolean checkRequests(){
        myRequests.clear();
        for(int i = 0; i < AdministrativeFunctions.AdministrativeRequestUser.size(); i++){
            if(AdministrativeFunctions.AdministrativeRequestUser.get(i).equals(SwitchController.focusUser)){
                myRequests.add(AdministrativeFunctions.AdministrativeRequestFull.get(i));
            }
        }
        return true;
    }
    public static boolean CheckNotifications(){
        return false;
    }

    public static boolean printRequests(){
        checkRequests();
        int item = 0;
        Logo.displayLogo();
        for(int i = 0; i < myRequests.size(); i++){
            item++;
            System.out.println(item + ": " + myRequests.get(i));
        }
        try {
            int option;
            System.out.println("Select An Item: (Type \"0\" to go back to the main menu)");
            option = customScanner.nextInt();
            option--;
            if(option == -1){
                SettingsMenu();
            }else{
                String temp;
                Logo.displayLogo();
                System.out.println("Selected Item: " + myRequests.get(option));
                System.out.println("Would you like to Revoke your Request? (y/n)");
                temp = customScanner.nextLine();
                if(temp.equals("y") || temp.equals("yes")){
                    for(int i = 0; i < AdministrativeFunctions.AdministrativeRequestKeyWord.size(); i++){
                        if(AdministrativeFunctions.AdministrativeRequestFull.get(i).contains(myRequests.get(option))){
                            myRequests.remove(option);
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
        } catch (InputMismatchException|IndexOutOfBoundsException e) {
            messageHandler.HandleMessage(-2,e.toString(), true);
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
        String key = "PermissionLevel";
		if(parseInt(MainSystemUserController.GetProperty(key)) >= 6){
            System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimeManager.checkFirstTime());  	
        }
        if(parseInt(MainSystemUserController.GetProperty(key)) >= 8) {
        	System.out.println("[REQUESTS]: View User Requests; Current Request Count: [" + requestsMade + "]");
        }else {
        	System.out.println("[REQUESTS]: My Requests: Current Requests: " + myRequests.size());
        }
        if(parseInt(MainSystemUserController.GetProperty(key)) < 8){
            System.out.println("[NOTI]: My Notifications Notification Count: [" + myNotifications + "]");
        }
        System.out.println("[RETURN]: Return");
        System.out.println();
        ConsoleHandler.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        if(option.equals("path")){
        	messageHandler.HandleMessage(-1, "Path Controller has not yet been implemented, Check back at a later update", true);
        	SettingsMenu();
        }else if(option.equals("noti")){
            messageHandler.HandleMessage(1, "This Option is not yet Available, Check back at a later update", true);
            CheckNotifications();
            
            SettingsMenu();
        }else if(option.equals("update")){
            MaintainUserController.loadUserProperties(SwitchController.focusUser);
            MaintainUserController.updateProfileSettings(MaintainUserController.GetProperty("Username"));  
        }else if(option.equals("rab")){
            try {
                URI uri= new URI(SettingsController.getSetting("debugSite"));
                java.awt.Desktop.getDesktop().browse(uri);
                messageHandler.HandleMessage(1, "Webpage opened in your default Browser...", true);
                messageHandler.HandleMessage(2, SettingsController.getSetting("debugSite"), true);
            } catch (Exception e) {
                messageHandler.HandleMessage(-1, "Unable to Launch Webpage, [" + e.toString() + "]", true);
            }
            SettingsMenu();
        }else if(option.equals("force")){
            messageHandler.HandleMessage(-1, "Force (Update) has not been implemented yet. Check back at a later update", false);
            SettingsMenu();
        }else if(option.equals("viewlogs")){
            ViewLogManager.ViewMenu(1);
        }else if(option.equals("dump")){
            LogDump.DumpLog(logType);
            SettingsMenu();   
        }else if(option.equals("console")){
            ConsoleSettings.ConsoleSettingsMenu();
        }else if(option.equals("requests")){
        	if(SwitchController.focusUser.equals("Admin")) {
        		AdministrativeFunctions.resolutionAdvisory();
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
            String settingType = "LogType";
			SettingsController.setSetting(settingType, logType);
            messageHandler.HandleMessage(1, "Log Type: " + logType, true);
            SettingsMenu();
        }else if(option.equals("first")){
            if(parseInt(MainSystemUserController.GetProperty(key)) >= 6){
                if(FirstTimeManager.checkFirstTime()){
                    SettingsController.setSetting("FirstTime", "false");
                    FirstTimeManager.FirstTime = false;
                }else if(!FirstTimeManager.checkFirstTime()){
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

    public static boolean loadSettings(){
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
        LoginUserController.passFlag = Boolean.parseBoolean(SettingsController.getSetting("PassFlag"));
        messageHandler.HandleMessage(1, "PassFlag: " + Boolean.parseBoolean(SettingsController.getSetting("PassFlag")), false);
        System.out.println(SystemMessages.getLastMessage());
        messageHandler.HandleMessage(1, "All Settings Loaded", false);
        System.out.println(SystemMessages.getLastMessage());
        return true;
    }
}