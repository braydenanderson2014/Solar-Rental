package MainSystem;
import java.util.Scanner;

import javax.sound.midi.Patch;

import Assets.Logo;
import Install.FirstTimeController;
import Install.PathController;
import Install.installManager;
import messageHandler.ClearAllMessages;
import messageHandler.Console;
import messageHandler.ConsoleSettings;
import messageHandler.ViewLogManager;
import messageHandler.messageHandler;
public class Settings{
    public static Scanner scan = new Scanner(System.in);
    public static String path = installManager.getPath();
    public static String logType = "all";
    public static boolean FirstTimed = false;
    public static void SettingsMenu(){
        Logo.displayLogo();
        System.out.println();
        System.out.println("Settings Menu");
        System.out.println("============================================");
        System.out.println("[PATH]: Program's Working Directory" + path);
        System.out.println("[CONSOLE]: Console Settings");
        System.out.println("[LOG]: Log Dump Type: " + logType);
        System.out.println("[ViewLogs]: View logs Menu");
        System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimed);
        System.out.println("[CLS]: Clear Logs");
        System.out.println("[RETURN]: Return");
        System.out.println();
        Console.getConsole();
        String option = scan.nextLine().toLowerCase();
        if(option.equals("path")){
            PathController.pathMenu(1);
        }else if(option.equals("viewlogs")){
            ViewLogManager.ViewMenu(1);
        }else if(option.equals("cls")){
            ClearAllMessages.clearAll();
            SettingsMenu();
        }else if(option.equals("console")){
            ConsoleSettings.ConsoleSettingsMenu();
        }else if(option.equals("log")){
            if(logType.equals("all")){
                logType = "debug";
            }else if(logType.equals("debug")){
                logType = "limited";
            }else if(logType.equals("limited")){
                logType = "all";
            }
            messageHandler.HandleMessage(1, "Log Type: " + logType);
            SettingsMenu();
        }else if(option.equals("first")){
            if(FirstTimed == true){
                FirstTimed = false;
            }else if(FirstTimed == false){
                FirstTimed = true;
            }
            messageHandler.HandleMessage(1, "IsFirstTime: " + FirstTimed);
            FirstTimeController.updateFirstTime(FirstTimed);
            SettingsMenu();
        }else if(option.equals("return")){
            //MainSystem
            MainMenu.mainMenu();
        }else {
            messageHandler.HandleMessage(-1, "Invalid option, Try again");
            SettingsMenu();
        }
    }
    public static void configMenu(){
        Logo.displayLogo();
        System.out.println();
        System.out.println("Settings Menu");
        System.out.println("============================================");
        System.out.println("[PATH]: Program's Working Directory: " + path);
        System.out.println("[CONSOLE]: Console Settings");
        System.out.println("[LOG]: Log Dump Type: " + logType);
        System.out.println("[ViewLogs]: View logs Menu");
        System.out.println("[FIRST]: Enable/Disable FirstTime Setup: " + FirstTimed);
        System.out.println("[RETURN]: Return");
        System.out.println();
        Console.getConsole();
        String option = scan.nextLine().toLowerCase();
        if(option.equals("path")){
            PathController.pathMenu(2);
        }else if(option.equals("viewlogs")){
            ViewLogManager.ViewMenu(2);
        }else if(option.equals("console")){
            ConsoleSettings.dConsoleSettingsMenu();
        }else if(option.equals("log")){
            if(logType.equals("all")){
                logType = "debug";
            }else if(logType.equals("debug")){
                logType = "limited";
            }else if(logType.equals("limited")){
                logType = "all";
            }
            messageHandler.HandleMessage(1, "Log Type: " + logType);
            configMenu();
        }else if(option.equals("first")){
            if(FirstTimed == true){
                FirstTimed = false;
            }else if(FirstTimed == false){
                FirstTimed = true;
            }
            FirstTimeController.updateFirstTime(FirstTimed);
            configMenu();
        }else if(option.equals("return")){
            installManager.installMenu();
        }else {
            messageHandler.HandleMessage(-1, "Invalid option, Try again");
            configMenu();
        }
    }
    public static boolean refreshSettings(){
        path = installManager.getPath();
        logType  = "all";
        FirstTimed = false;
        messageHandler.HandleMessage(1, "Setting Refresh");
        return true;
    }
}