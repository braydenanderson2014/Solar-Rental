package messageHandler;

import Assets.Logo;
import Assets.customScanner;
import MainSystem.Settings;
import MainSystem.SettingsController;

public class ConsoleSettings {
    public static boolean ErrorSet = true;
    public static boolean WarningSet = true;
    public static boolean SystemSet = true;
    public static boolean UserNotifySet = true;
    public static boolean timeSet = true;
    public static byte max = 5;

    public static void ConsoleSettingsMenu(){
    	if(SettingsController.getSetting("FirstTime").equals("false")) {
    		max = Byte.parseByte(SettingsController.getSetting("MaxConsole"));
    	}
        Logo.displayLogo();
        System.out.println();
        System.out.println("Console Settings Menu");
        System.out.println("============================================");
        System.out.println("[ERROR]: Error Messages; Current Setting: " + ErrorSet);
        System.out.println("[WARNING]: Warning Messages; Current Setting: " + WarningSet);
        System.out.println("[SYSTEM]: System Messages; Current Setting: " + SystemSet);
        System.out.println("[USER]: User Notification Messages; Current Setting: " + UserNotifySet);
        System.out.println("[TIME]: Date Time Set: " + timeSet);
        System.out.println("[MAX]: Maximum Amount of messages displayed [MAX]: " + max);
        System.out.println("[HELP]: Help");
        System.out.println("[RETURN]: Return ");
        ConsoleHandler.getConsole();
        String setToChange = customScanner.nextLine().toLowerCase();
        if(setToChange.equals("error")){
            if(ErrorSet){
                ErrorSet = false;
            }else{
                ErrorSet = true;
            }
            SettingsController.setSetting("ErrorSet", String.valueOf(ErrorSet));
            messageHandler.HandleMessage(1, "Error Messages ON?: " + ErrorSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("warning")){
            if(WarningSet){
                WarningSet = false;
            }else{
                WarningSet = true;
            }
            SettingsController.setSetting("WarningSet", String.valueOf(WarningSet));
            messageHandler.HandleMessage(1, "Warning Messages ON?: " + WarningSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("system")){
            if(SystemSet){
                SystemSet = false;
            }else{
                SystemSet = true;
            }
            SettingsController.setSetting("SystemSet", String.valueOf(SystemSet));
            messageHandler.HandleMessage(1, "System Messages ON?: " + SystemSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("user")){
            if(UserNotifySet){
                UserNotifySet = false;
            }else{
                UserNotifySet = true;
            }
            SettingsController.setSetting("UserNotifySet", String.valueOf(UserNotifySet));
            messageHandler.HandleMessage(1, "User Notifications Messages ON?: " + UserNotifySet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("time")){
            if(timeSet){
                timeSet = false;
            }else{
                timeSet = true;
            }
            SettingsController.setSetting("Date/TimeSet", String.valueOf(timeSet));
            messageHandler.HandleMessage(1, "Date Time Stamps ON?: " + timeSet, false);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("help")){
            messageHandler.HandleMessage(-2, "Help System Not Implemented yet, Try again in a later update", true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("return")){
            try {
                Settings.SettingsMenu();
            } catch (Exception e) {
                messageHandler.HandleMessage(-2, "Failed to access Settings Menu, Reattempting to access Settings Menu", true);
                Settings.SettingsMenu();
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
       
            messageHandler.HandleMessage(-1, "Invalid option, try again", true);
            ConsoleSettingsMenu();
        }
    }
}
