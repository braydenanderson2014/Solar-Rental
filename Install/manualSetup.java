package Install;

import Assets.Logo;
import Assets.VersionController;
import messageHandler.Console;

public class manualSetup{
    static int itemsChecked = 0;
    boolean path = false;
    boolean version;
    boolean first;
    public manualSetup(){
        
    }
    public static void ManualMenu(){
        if(itemsChecked == 3){
            installManager.installMenu();
        }
        Logo.displayLogo();
        System.out.println();
        System.out.println("Welcome to Solar: Manual Setup Menu");
        System.out.println("[PATH]: Set The Path; Current Path: " + installManager.getPath());
        System.out.println("[VERSION]: Set The Version: " + VersionController.getVersion());
        System.out.println("[FIRST]: First Time Setup: " + FirstTimeController.firstTime);
        System.out.println();
        Console.getConsole();
    }
}