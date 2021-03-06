package InstallManager;

import java.io.IOException;

import Assets.Logo;
import Assets.customScanner;
import Login.Login;
import messageHandler.Console;
import messageHandler.ErrorMessages;
import messageHandler.messageHandler;

public class ProgramController {
    public static String UserPathLetter = "C:";
    public static String UserDefaultPath = "\\Users\\Public\\Documents\\Solar\\ProgramFiles/";
    public static String SystemPathLetter = "C:";
    public static String SystemDefaultPath = "\\Users\\Public\\Documents";
    public static String SystemPath = "C:\\Users\\Public\\Documents";
    public static String SystemSubPath = "\\Solar";
    public static String SystemInstallPath = "\\InstallationFiles";
    public static String SystemDefaultRunPath = "\\ProgramFiles";
    public static String SystemConfig = "\\config.properties";
    public static String SystemRunPath = "BLANK";
    public static String UserRunPath = "C:\\Users\\Public\\Documents\\Solar\\ProgramFiles";
    public static void SetupMenu(){
        Logo.displayLogo();
        System.out.println();
        System.out.println("Welcome to Solar! Please select a Setup Mode Below!");
        Logo.displayLine();
        System.out.println("[AUTO]: Auto Setup Program");
        System.out.println("[MANUAL]: Manually Setup Program");
        System.out.println("[QUIT]: Quit Program");
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        if(option.equals("auto")){
            AutoSetup.startAutoSetup();
        }else if(option.equals("manual")){
            ManualSetup.configureSetup();
        }else if(option.equals("quit")){
            System.exit(1);
        }else{
            messageHandler.HandleMessage(-1, "Invalid Option, Try again", true);
            SetupMenu();
        }
    }
    
    public static void main(String[] args) {
        Start();
    }

    public static void Start() {
        new customScanner();
        boolean firstTime = FirstTimeManager.checkFirstTime();
        if(firstTime){
            SetupMenu();
        }else if(!firstTime){
            messageHandler.HandleMessage(1, "FirstTime: False", true);
            SystemSetLoader.loadSystems();
            Login.LoginScreen();
        }
    }

    public static boolean clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                System.out.flush();
            }else{
                Runtime.getRuntime().exec("clear");
            }
            return true;
        } catch (IOException | InterruptedException e) {
            messageHandler.HandleMessage(-2, e.toString(), true);
            System.out.println(ErrorMessages.getLastMessage());
        }
        return true;
    }
}
