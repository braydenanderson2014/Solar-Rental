package messageHandler;
import java.util.Scanner;


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
    
    public static void ConsoleSettingsMenu(){
        Logo.displayLogo();
        System.out.println();
        System.out.println("Console Settings Menu");
        System.out.println("============================================");
        System.out.println("[ERROR]: Error Messages; Current Setting: " + ErrorSet);
        System.out.println("[WARNING]: Warning Messages; Current Setting: " + WarningSet);
        System.out.println("[SYSTEM]: System Messages; Current Setting: " + SystemSet);
        System.out.println("[USER]: User Notification Messages; Current Setting: " + UserNotifySet);
        System.out.println("[TIME]: Date Time Set: " + timeSet);
        System.out.println("[HELP]: Help");
        System.out.println("[RETURN]: Return ");
        Console.getConsole();
        String setToChange = customScanner.nextLine().toLowerCase();
        if(setToChange.equals("error")){
            if(ErrorSet == true){
                ErrorSet = false;
            }else if(ErrorSet == false){
                ErrorSet = true;
            }
            SettingsController.setSetting("ErrorSet", String.valueOf(ErrorSet));
            messageHandler.HandleMessage(1, "Error Messages ON?: " + ErrorSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("warning")){
            if(WarningSet == true){
                WarningSet = false;
            }else if(WarningSet == false){
                WarningSet = true;
            }
            SettingsController.setSetting("WarningSet", String.valueOf(WarningSet));
            messageHandler.HandleMessage(1, "Warning Messages ON?: " + WarningSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("system")){
            if(SystemSet == true){
                SystemSet = false;
            }else if(SystemSet == false){
                SystemSet = true;
            }
            SettingsController.setSetting("SystemSet", String.valueOf(SystemSet));
            messageHandler.HandleMessage(1, "System Messages ON?: " + SystemSet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("user")){
            if(UserNotifySet == true){
                UserNotifySet = false;
            }else if(UserNotifySet == false){
                UserNotifySet = true;
            }
            SettingsController.setSetting("UserNotifySet", String.valueOf(UserNotifySet));
            messageHandler.HandleMessage(1, "User Notifications Messages ON?: " + UserNotifySet, true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("time")){
            if(timeSet == true){
                timeSet = false;
            }else if(timeSet == false){
                timeSet = true;
            }
            SettingsController.setSetting("Date/TimeSet", String.valueOf(timeSet));
            messageHandler.HandleMessage(1, "Date Time Stamps ON?: " + timeSet, false);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("help")){
            messageHandler.HandleMessage(-2, "Help System Not Implemented yet, Try again in a later update", true);
            ConsoleSettingsMenu();
        }else if(setToChange.equals("return")){
            Settings.SettingsMenu();
        }else{
            messageHandler.HandleMessage(-1, "Invalid option, try again", true);
            ConsoleSettingsMenu();
        }
    }
    
}
