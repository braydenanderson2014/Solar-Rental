package MainSystem;
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;
import com.solarrental.assets.VersionController;

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
import messageHandler.MessageProcessor;
import messageHandler.SystemMessages;
import messageHandler.ViewLogManager;
public class Settings{
    private static final String LOG_TYPE = "LogType";
	public static String path = ProgramController.userRunPath;
    public static List<String> myRequests = new ArrayList<>();
    public static List<String> myNotifications = new ArrayList<>();
    public static int myNotificationsAmount = 0;
    public static String logType = "all";
    public static int requestsMade = 0;
    public static int updateRequestsMade(){ // Check admin requests
        requestsMade = AdministrativeFunctions.administrativeRequests.size();
        return requestsMade;
    }
    public static boolean checkRequests(){ //Check User Requests to admin
        myRequests.clear();
        for(int i = 0; i < AdministrativeFunctions.administrativeRequestUser.size(); i++){
            if(AdministrativeFunctions.administrativeRequestUser.get(i).equals(SwitchController.focusUser)){
                myRequests.add(AdministrativeFunctions.administrativeRequestFull.get(i));
            }
        }
        return true;
    }
    public static boolean checkNotifications(){// Checking notifications from admin or other users
        if(MainSystemUserController.GetProperty("UserNotification").equals("Enabled")){
            myNotifications.clear();
            String path = ProgramController.userRunPath + "\\Users\\Notifications\\" + SwitchController.focusUser + ".txt";
            boolean exists = (new File(path)).exists();
            File file = new File(path);
            if(exists){
                try {
                    BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
                    String line;
                    while((line = reader.readLine()) != null){
                        if(!line.equals("")){
                            myNotifications.add(line);
                        }
                    }
                    reader.close();
                    return true;
                } catch (Exception e) {
                    MessageProcessor.processMessage(-2, "Failed to read Notification File for User: " + SwitchController.focusUser, true);
                    return false;
                }
            }else{
                MessageProcessor.processMessage(1, "No Notification File for User: " + SwitchController.focusUser, false);
                return false;
            }
        }else{
            MessageProcessor.processMessage(-1, "User Notifications are Disabled", false);
            return false;
        }
    }

    public static boolean printRequests(){//Print User Requests. (submitted requests to admin)
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
            option = CustomScanner.nextInt();
            option--;
            if(option == -1){
                settingsMenu();
            }else{
                String temp;
                Logo.displayLogo();
                System.out.println("Selected Item: " + myRequests.get(option));
                System.out.println("Would you like to Revoke your Request? (y/n)");
                temp = CustomScanner.nextLine();
                temp = CustomScanner.nextLine();
                if(temp.equals("y") || temp.equals("yes")){
                    for(int i = 0; i < AdministrativeFunctions.administrativeRequestKeyWord.size(); i++){
                        if(AdministrativeFunctions.administrativeRequestFull.get(i).contains(myRequests.get(option))){
                            myRequests.remove(option);
                            AdministrativeFunctions.administrativeRequestFull.remove(i);
                            MessageProcessor.processMessage(1, "Removed From Administrative Request Full", false);
                            AdministrativeFunctions.administrativeRequestID.remove(i);
                            MessageProcessor.processMessage(1, "Removed From Request ID", false);
                            AdministrativeFunctions.administrativeRequestKeyWord.remove(i);
                            MessageProcessor.processMessage(1, "Removed From Request Keyword", false);
                            AdministrativeFunctions.administrativeRequestUser.remove(i);
                            MessageProcessor.processMessage(1, "Removed From Request User", false);
                            AdministrativeFunctions.administrativeRequestedName.remove(i);
                            MessageProcessor.processMessage(1, "Removed From Requested Name", false);
                            AdministrativeFunctions.administrativeRequests.remove(i);
                            MessageProcessor.processMessage(1, "Removed From Administrative Requests", false);
                            MessageProcessor.processMessage(1, "Successfully Removed Requests", true);
                        }else{
                            MessageProcessor.processMessage(-1, "Unable to find requests in the main Request queue", true);
                        }
                    }
                }else{
                    MessageProcessor.processMessage(-1, "Invalid option, Try again.", true);
                    printRequests();
                }
            }
        } catch (InputMismatchException|IndexOutOfBoundsException e) {
            MessageProcessor.processMessage(-2,e.toString(), true);
        }
        return true;
    }

    public static void settingsMenu() throws NumberFormatException{
        checkRequests();
        checkNotifications();
        requestsMade = AdministrativeFunctions.updateRequestsMade();
        Logo.displayLogo();
        System.out.println();
        System.out.println("Settings Menu");
        System.out.println("============================================");
        System.out.println("[PATH]: Program's Working Directory: \"" + path + "\"");
        System.out.println("[CONSOLE]: Console Settings");
        System.out.println("[RAB]: Report a Bug");
        if(logType.equals("debugmt")){
            System.out.println("[LOG]: Log Dump Type: DEBUG");
        }else{
            System.out.println("[LOG]: Log Dump Type: " + logType.toUpperCase());
        }
        System.out.println("[VIEWLOGS]: View logs Menu");
        System.out.println("[DUMP]: Dump Logs to File");
        System.out.println("[UPDATE]: Manually Update User Profile");
        System.out.println("[FORCE]: Force User Profile to update to latest Settings");
        String key = "PermissionLevel";
		if(parseInt(MainSystemUserController.GetProperty(key)) >= 6){
            System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimeManager.checkFirstTime());  	
            System.out.println("[SETUPDATE]: Forces Configuration File to add any new changes without having to reinstall program");
        }
        if(parseInt(MainSystemUserController.GetProperty(key)) >= 8) {
        	System.out.println("[REQUESTS]: View User Requests; Current Request Count: [" + requestsMade + "]");
        }else {
        	System.out.println("[REQUESTS]: My Requests: Current Requests: [" + myRequests.size() + "]");
        }
        if(parseInt(MainSystemUserController.GetProperty(key)) < 8){
            System.out.println("[NOTI]: My Notifications Notification Count: [" + myNotifications.size() + "]");
        }
        System.out.println("[RETURN]: Return");
        System.out.println();
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        if(option.equals("path")){
        	MessageProcessor.processMessage(-1, "Path Controller has not yet been implemented, Check back at a later update", true);
        	settingsMenu();
        }else if(option.equals("noti")){
            MessageProcessor.processMessage(1, "This Option is not yet Available, Check back at a later update", true);
            checkNotifications();
            
            settingsMenu();
        }else if(option.equals("update")){
            MaintainUserController.loadUserProperties(SwitchController.focusUser);
            MaintainUserController.updateProfileSettings(MaintainUserController.GetProperty("Username"));  
        }else if(option.equals("rab")){
            try {
                URI uri= new URI(SettingsController.getSetting("debugSite"));
                java.awt.Desktop.getDesktop().browse(uri);
                MessageProcessor.processMessage(1, "Webpage opened in your default Browser...", true);
                MessageProcessor.processMessage(2, SettingsController.getSetting("debugSite"), true);
            } catch (Exception e) {
                MessageProcessor.processMessage(-1, "Unable to Launch Webpage, [" + e.toString() + "]", true);
            }
            settingsMenu();
        }else if(option.equals("force")){
        	MaintainUserController.forceProfileUpdate(SwitchController.focusUser);
            settingsMenu();
        }else if(option.equals("viewlogs")){
            ViewLogManager.viewMenu(1);
        }else if(option.equals("dump")){
            LogDump.DumpLog(logType);
            settingsMenu();   
        }else if(option.equals("console")){
            ConsoleSettings.ConsoleSettingsMenu();
        }else if(option.equals("requests")){
        	if(SwitchController.focusUser.equals("Admin")) {
        		AdministrativeFunctions.resolutionAdvisory();
        	}else {
        		printRequests();
        	}
            settingsMenu();
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
                logType = "debugmt";
            }else if(logType.equals("debugmt")) {
            	logType = "all";
            }
            String settingType = LOG_TYPE;
			SettingsController.setSetting(settingType, logType);
            MessageProcessor.processMessage(1, "Log Type: " + logType, true);
            settingsMenu();
        }else if(option.equals("first")){
            if(parseInt(MainSystemUserController.GetProperty(key)) >= 6){
                if(FirstTimeManager.checkFirstTime()){
                    SettingsController.setSetting("FirstTime", "false");
                    FirstTimeManager.firstTime = false;
                }else if(!FirstTimeManager.checkFirstTime()){
                    FirstTimeManager.firstTime = true;
                    SettingsController.setSetting("FirstTime", "true");
                }
                MessageProcessor.processMessage(1, "IsFirstTime: " + FirstTimeManager.checkFirstTime(), true);
                settingsMenu();
            }else{
                MessageProcessor.processMessage(-1, "User Does not have permission to use this function", true);
                settingsMenu();
            }
        }else if(option.equals("setupdate")){
            if(parseInt(MainSystemUserController.GetProperty(key)) >= 6){
                ForceUpdateConfiguration();
            }else{
                MessageProcessor.processMessage(-1, "User does not have permission to use this function", false);
                settingsMenu();
            }
        }else if(option.equals("return")){
            //MainSystem
            MainMenu.mainMenu();
        }else {
            MessageProcessor.processMessage(-1, "Invalid option, Try again", true);
            settingsMenu();
        }
    }

    private static void ForceUpdateConfiguration() {
        boolean exists = SettingsController.searchForSet("DebugSet");
        if(!exists){
            SettingsController.setSetting("DebugSet", "true");
            MessageProcessor.processMessage(1, "Console Setting \"DebugSet\" was created successfully. Default Value: true", false);
        }
        SettingsController.saveSettings();
        SettingsController.loadSettings();
        MessageProcessor.processMessage(1, "Configuration File Updated", true);
        String userFolder = ProgramController.userRunPath + "\\Users\\Notifications";
        File file = new File(userFolder);
        if(!file.exists()){
            file.mkdirs();
            MessageProcessor.processMessage(1, "Successfully created Directories at: " + userFolder, true);
        }
        settingsMenu();
    }
    public static boolean loadSettings(){ //Loads main Settings for the program to launch
        MessageProcessor.processMessage(1, "Loading Settings file from config.properties", false);
        System.out.println(SystemMessages.getLastMessage());
        SettingsController.loadSettings();
        MessageProcessor.processMessage(1, "Settings File Loaded... Now reading from Settings", false);
        System.out.println(SystemMessages.getLastMessage());

        MessageProcessor.processMessage(1, "FirstTime Setting: False", false);
        System.out.println(SystemMessages.getLastMessage());
        MessageProcessor.processMessage(1, "Path Letter: " + SettingsController.getSetting("PathLetter") + ", Path: " + SettingsController.getSetting("Path"), false);
        System.out.println(SystemMessages.getLastMessage());
        VersionController.setVersion(SettingsController.getSetting("Version"));
        MessageProcessor.processMessage(1, "Version: " + SettingsController.getSetting("Version"), false);
        System.out.println(SystemMessages.getLastMessage());
        Settings.logType = SettingsController.getSetting(LOG_TYPE);
        MessageProcessor.processMessage(1, "LogType: " + SettingsController.getSetting(LOG_TYPE), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.ErrorSet = Boolean.parseBoolean(SettingsController.getSetting("ErrorSet"));
        MessageProcessor.processMessage(1, "ErrorSet: " + Boolean.parseBoolean(SettingsController.getSetting("ErrorSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.WarningSet = Boolean.parseBoolean(SettingsController.getSetting("WarningSet"));
        MessageProcessor.processMessage(1, "WarningSet: " + Boolean.parseBoolean(SettingsController.getSetting("WarningSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.SystemSet = Boolean.parseBoolean(SettingsController.getSetting("SystemSet"));
        MessageProcessor.processMessage(1, "SystemSet: " + Boolean.parseBoolean(SettingsController.getSetting("SystemSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        
        ConsoleSettings.DebugSet = Boolean.parseBoolean(SettingsController.getSetting("DebugSet"));
        MessageProcessor.processMessage(1, "DebugSet: " + Boolean.parseBoolean(SettingsController.getSetting("DebugSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        
        ConsoleSettings.UserNotifySet = Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet"));
        MessageProcessor.processMessage(1, "UserNotifySet: " + Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet")), false);
        System.out.println(SystemMessages.getLastMessage());
        ConsoleSettings.timeSet = Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet"));
        MessageProcessor.processMessage(1, "Time Set: " + Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet")), false);
        System.out.println(SystemMessages.getLastMessage());
        LoginUserController.passFlag = Boolean.parseBoolean(SettingsController.getSetting("PassFlag"));
        MessageProcessor.processMessage(1, "PassFlag: " + Boolean.parseBoolean(SettingsController.getSetting("PassFlag")), false);
        System.out.println(SystemMessages.getLastMessage());
        MessageProcessor.processMessage(1, "All Settings Loaded", false);
        System.out.println(SystemMessages.getLastMessage());
        return true;
    }
}