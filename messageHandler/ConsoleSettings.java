package messageHandler;
import java.util.Scanner;


import Assets.Logo;
import MainSystem.Settings;

public class ConsoleSettings {
    public static boolean ErrorSet = true;
    public static boolean WarningSet = true;
    public static boolean SystemSet = true;
    public static boolean UserNotifySet = true;
    public static boolean timeSet = true;
    public static Scanner scan = new Scanner(System.in);
    
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
        String setToChange = scan.nextLine().toLowerCase();
        if(setToChange.equals("error")){
            if(ErrorSet == true){
                ErrorSet = false;
            }else if(ErrorSet == false){
                ErrorSet = true;
            }
            ConsoleSettingsMenu();
        }else if(setToChange.equals("warning")){
            if(WarningSet == true){
                WarningSet = false;
            }else if(WarningSet == false){
                WarningSet = true;
            }
            ConsoleSettingsMenu();
        }else if(setToChange.equals("system")){
            if(SystemSet == true){
                SystemSet = false;
            }else if(SystemSet == false){
                SystemSet = true;
            }
            ConsoleSettingsMenu();
        }else if(setToChange.equals("user")){
            if(UserNotifySet == true){
                UserNotifySet = false;
            }else if(UserNotifySet == false){
                UserNotifySet = true;
            }
            ConsoleSettingsMenu();
        }else if(setToChange.equals("time")){
            if(timeSet == true){
                timeSet = false;
            }else if(timeSet == false){
                timeSet = true;
            }
            ConsoleSettingsMenu();
        }else if(setToChange.equals("help")){

        }else if(setToChange.equals("return")){
            Settings.SettingsMenu();
        }else{
            messageHandler.HandleMessage(-1, "Invalid option, try again");
            ConsoleSettingsMenu();
        }
    }
    public static void dConsoleSettingsMenu(){
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
        String setToChange = scan.nextLine().toLowerCase();
        if(setToChange.equals("error")){
            if(ErrorSet == true){
                ErrorSet = false;
            }else if(ErrorSet == false){
                ErrorSet = true;
            }
            dConsoleSettingsMenu();
        }else if(setToChange.equals("warning")){
            if(WarningSet == true){
                WarningSet = false;
            }else if(WarningSet == false){
                WarningSet = true;
            }
            dConsoleSettingsMenu();
        }else if(setToChange.equals("system")){
            if(SystemSet == true){
                SystemSet = false;
            }else if(SystemSet == false){
                SystemSet = true;
            }
            dConsoleSettingsMenu();
        }else if(setToChange.equals("user")){
            if(UserNotifySet == true){
                UserNotifySet = false;
            }else if(UserNotifySet == false){
                UserNotifySet = true;
            }
            dConsoleSettingsMenu();
        }else if(setToChange.equals("time")){
            if(timeSet == true){
                timeSet = false;
            }else if(timeSet == false){
                timeSet = true;
            }
            dConsoleSettingsMenu();
        }else if(setToChange.equals("help")){
            dConsoleSettingsMenu();
        }else if(setToChange.equals("return")){
            Settings.configMenu();
        }else{
            messageHandler.HandleMessage(-1, "Invalid option, try again");
            dConsoleSettingsMenu();
        }
    }
}
