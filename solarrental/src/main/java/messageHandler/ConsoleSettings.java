package messageHandler;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;

import MainSystem.Settings;
import MainSystem.SettingsController;

public class ConsoleSettings {
    public static boolean ErrorSet = true;
    public static boolean WarningSet = true;
    public static boolean SystemSet = true;
    public static boolean UserNotifySet = true;
    public static boolean DebugSet = true;
    public static boolean timeSet = true;
    public static byte max = 5;

    public static void ConsoleSettingsMenu(){
    	if(SettingsController.getSetting("FirstTime").equals("false")) {
    		max = Byte.parseByte(SettingsController.getSetting("MaxConsole"));
    		ErrorSet = Boolean.parseBoolean(SettingsController.getSetting("ErrorSet"));
    		WarningSet = Boolean.parseBoolean(SettingsController.getSetting("WarningSet"));
    		SystemSet = Boolean.parseBoolean(SettingsController.getSetting("SystemSet"));
    		UserNotifySet = Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet"));
    		DebugSet = Boolean.parseBoolean(SettingsController.getSetting("DebugSet"));
    		timeSet = Boolean.parseBoolean(SettingsController.getSetting("Date/TimeSet"));
    	}else {
    		max = 5;
    		ErrorSet = true;
    		WarningSet = true;
    		SystemSet = true;
    		UserNotifySet = true;
    		DebugSet = true;
    		timeSet = true;
    	}
        Logo.displayLogo();
        System.out.println();
        System.out.println("Console Settings Menu");
        System.out.println("============================================");
        System.out.println("[ERROR]: Error Messages; Current Setting: " + ErrorSet);
        System.out.println("[WARNING]: Warning Messages; Current Setting: " + WarningSet);
        System.out.println("[SYSTEM]: System Messages; Current Setting: " + SystemSet);
        System.out.println("[USER]: User Notification Messages; Current Setting: " + UserNotifySet);
        System.out.println("[DEBUG]: Debug Messages; Current Setting: " + DebugSet);
        System.out.println("[TIME]: Date Time Set: " + timeSet);
        System.out.println("[MAX]: Maximum Amount of messages displayed [MAX]: " + max);
        System.out.println("[HELP]: Help");
        System.out.println("[RETURN]: Return ");
        ConsoleHandler.getConsole();
        String setToChange = CustomScanner.nextLine().toLowerCase();
        if(setToChange.equals("error")){
            if(ErrorSet){
                ErrorSet = false;
            }else{
                ErrorSet = true;
            }
            SettingsController.setSetting("ErrorSet", String.valueOf(ErrorSet));
            MessageProcessor.processMessage(1, "Error Messages ON?: " + ErrorSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("warning")){
            if(WarningSet){
                WarningSet = false;
            }else{
                WarningSet = true;
            }
            SettingsController.setSetting("WarningSet", String.valueOf(WarningSet));
            MessageProcessor.processMessage(1, "Warning Messages ON?: " + WarningSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("system")){
            if(SystemSet){
                SystemSet = false;
            }else{
                SystemSet = true;
            }
            SettingsController.setSetting("SystemSet", String.valueOf(SystemSet));
            MessageProcessor.processMessage(1, "System Messages ON?: " + SystemSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("user")){
            if(UserNotifySet){
                UserNotifySet = false;
            }else{
                UserNotifySet = true;
            }
            SettingsController.setSetting("UserNotifySet", String.valueOf(UserNotifySet));
            MessageProcessor.processMessage(1, "User Notifications Messages ON?: " + UserNotifySet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("debug")){
            if(DebugSet){
                DebugSet = false;
            }else{
                DebugSet = true;
            }
            SettingsController.setSetting("DebugSet", String.valueOf(DebugSet));
            MessageProcessor.processMessage(1, "Debug Notifications Messages ON?: " + DebugSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("time")){
            if(timeSet){
                timeSet = false;
            }else{
                timeSet = true;
            }
            SettingsController.setSetting("Date/TimeSet", String.valueOf(timeSet));
            MessageProcessor.processMessage(1, "Date Time Stamps ON?: " + timeSet, false);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("help")){
            MessageProcessor.processMessage(-2, "Help System Not Implemented yet, Try again in a later update", true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("return")){
            try {
                Settings.settingsMenu();
            } catch (Exception e) {
                MessageProcessor.processMessage(-2, "Failed to access Settings Menu, Reattempting to access Settings Menu", true);
                Settings.settingsMenu();
            }
        }else if(setToChange.equals("max")){
        	if(max < 10) {
        		max ++;
        	}else {
        		max = 1;
        	}
        	SettingsController.setSetting("MaxConsole", String.valueOf(max));
        	ConsoleSettingsMenu();
        }else {
            MessageProcessor.processMessage(-1, "Invalid option, try again", true);
            ConsoleSettingsMenu();
        }
    }
}
