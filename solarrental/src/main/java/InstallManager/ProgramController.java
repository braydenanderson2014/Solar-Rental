package InstallManager;

import java.io.IOException;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;

import Login.Login;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;

public class ProgramController {
    public static String userPathLetter = "C:";
    public static String userDefaultPath = "\\Users\\Public\\Documents\\Solar\\ProgramFiles/";
    public static String systemPathLetter = "C:";
    public static String systemDefaultPath = "\\Users\\Public\\Documents";
    public static String systemPath = "C:\\Users\\Public\\Documents";
    public static String systemSubPath = "\\Solar";
    public static String systemInstallPath = "\\InstallationFiles";
    public static String systemDefaultRunPath = "\\ProgramFiles";
    public static String systemConfig = "\\config.properties";
    public static String systemRunPath = systemPath + "\\Solar\\ProgramFiles";
    public static String userRunPath = "C:\\Users\\Public\\Documents\\Solar\\ProgramFiles";
    public static void SetupMenu(){
        Logo.displayLogo();
        System.out.println();
        System.out.println("Welcome to Solar! Please select a Setup Mode Below!");
        Logo.displayLine();
        System.out.println("[AUTO]: Auto Setup Program");
        System.out.println("[MANUAL]: Manually Setup Program");
        System.out.println("[QUIT]: Quit Program");
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        if(option.equals("auto")){
            AutoSetup.startAutoSetup();
        }else if(option.equals("manual")){
            ManualSetup.configureSetup();
        }else if(option.equals("quit")){
            System.exit(1);
        }else{
            MessageProcessor.processMessage(-1, "Invalid Option, Try again", true);
            SetupMenu();
        }
    }


    public static void Start() {
        new CustomScanner();
        boolean firstTime = FirstTimeManager.checkFirstTime();
        if(firstTime){
            SetupMenu();
        }else{
            SystemSetLoader.loadSystems();
            Login.loginScreen();
        }
    }

    public static boolean clearScreen() throws IOException, InterruptedException{
        if (System.getProperty("os.name").contains("Windows")){
		    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		    System.out.flush();
		}else{
		    Runtime.getRuntime().exec("clear");
		}
        System.out.print("\u000C");
		return true;
    }
}
