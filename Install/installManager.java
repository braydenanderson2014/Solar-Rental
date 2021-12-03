package Install;
import java.util.Scanner;

import Assets.Logo;
import Assets.VersionController;
import Login.Login;
import MainSystem.ProgramController;
import MainSystem.Settings;
import messageHandler.AllMessages;
import messageHandler.Console;
import messageHandler.SystemMessages;
import messageHandler.messageHandler;


public class installManager{
    public static Scanner scan = new Scanner(System.in);
    public static String DefaultPath = "\\Users\\Public\\Documents\\Solar Rentals\\InstallationFiles/";
    public static String DefaultdriveLetter = "C";
    public static String Directory = DefaultPath;
    public static String driveLetter = DefaultdriveLetter;
    
    public static void installMenu(){
        Logo.displayLogo();
        System.out.println("Please wait while we check some things...");
        messageHandler.HandleMessage(1, "Checking For FirstTime Install...");
        boolean firstTime = FirstTimeController.checkFirstTime();
        if(firstTime){
            //#region FIRST TIME TRUE
            messageHandler.HandleMessage(1, "Entering FirstTime Setup Mode >>>");
            Logo.displayLogo();
            System.out.println();
            System.out.println("Welcome to Solar, Please choose an Install Type");
            System.out.println("[MAN]: Manual Setup");
            System.out.println("[AUTO]: Auto Setup");
            System.out.println("[HELP]: Help");
            System.out.println();
            Console.getConsole();
            String option = scan.nextLine().toLowerCase();
            if(option.equals("man")){
                //manualSetup
                manualSetup.ManualMenu();
            }else if(option.equals("auto")){
                //autoSetup
                autoSetup.StartSetup();
            }else if(option.equals("help")){

            }else if(option.equals("isfirsttimeoff")){
                messageHandler.HandleMessage(1, "FirstTime: OFF");
                FirstTimeController.updateFirstTime(false);
                System.out.println(SystemMessages.getLastMessage());
                installMenu();
            }else if(option.equals("isfirsttime?")){
                messageHandler.HandleMessage(1, "FirstTime: " + FirstTimeController.firstTime + ": Queryed");
                System.out.println(FirstTimeController.firstTime);
                installMenu();
            }else if(option.equals("get last!")){
                System.out.println(AllMessages.getLastMessage());
                installMenu();
            }else{
                messageHandler.HandleMessage(-1, "Invalid Option, Try again");
                installMenu();
            }
            //#endregion
        }else{
            //#region FIRST TIME FALSE
            messageHandler.HandleMessage(1, "FirstTime Setup was Already completed, Now Entering Start Menu");
            ProgramController.clearScreen();
            Logo.displayLogo();
            System.out.println();
            System.out.println("Welcome to Solar! We are so Glad You are Here!");
            System.out.println("============================================");
            System.out.println("[Start]: Start Program");
            System.out.println("[Config]: Configure Settings");
            System.out.println("[Help]: Help");
            System.out.println("[Stop]: Stop Program and quit");
            System.out.println("Program Version: " + VersionController.getVersion());
            Console.getConsole();
            String option = scan.nextLine();
            if(option.equals("start")){
                getSystemSet.populateSystem();
                Login.LoginScreen();
            }else if(option.equals("config")){
                Settings.configMenu();
            }else if(option.equals("help")){

            }else if(option.equals("stop")){
                ProgramController.stop();
            }else{
                messageHandler.HandleMessage(-1, "Invalid option, Try again");
                installMenu();
            }
            //#endregion
        } 
    }
    public static String getPath() {
        return driveLetter + ":" + Directory;
    }
    public static String setPath(String autoPath, String autoPathLetter) {
        driveLetter = autoPathLetter;
        Directory = autoPath;
        messageHandler.HandleMessage(1, "Path Set");
        return autoPath;
    }
    public static String getSystemPath() {
        return DefaultdriveLetter + ":" + DefaultPath;
    }
}