package MainSystem;
import java.io.*;
import Assets.Logo;
import Assets.VersionController;
import Assets.customScanner;
import Install.installManager;
import messageHandler.ErrorMessages;
import messageHandler.SystemMessages;
import messageHandler.messageHandler;
public class ProgramController{
    public static boolean isRunning = false;
    public static String Version;
    public static boolean start(){
        Logo.displayLogo();
        new customScanner();
        System.out.println("Console: ");
        messageHandler.HandleMessage(1, "Starting Solar on Thread main");
        System.out.println(SystemMessages.getLastMessage());
        isRunning = true;
        return isRunning;
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
            messageHandler.HandleMessage(-2, e.toString());
            System.out.println(ErrorMessages.getLastMessage());
        }
        return true;
    }
    public static void stop(){
        System.exit(0);
    }
    public static void main(String[] args) {
        start();
        new VersionController();//Version Controller sets the Default Version as the Version for now.
        System.out.println(SystemMessages.getLastMessage());
        installManager.installMenu();
    }
}