package Install;

import java.util.ArrayList;

import Assets.Logo;
import Assets.customScanner;
import MainSystem.Settings;
import messageHandler.Console;
import messageHandler.messageHandler;

public class PathController {
    private static ArrayList<String> pathElements = new ArrayList<String>();
    private static String driveLetter = "";
    public static String manualPath(int mode){
        if(mode == 1){
            return "Path";
        }else if(mode == 2){
            String pathElement = "";
            int size;
            if(driveLetter.equals("")){
                System.out.println("Drive Letter: " + driveLetter + ":");
                driveLetter = customScanner.nextLine();
            }
            do{
                pathElement = customScanner.nextLine();
                if(pathElement.equals("_done")){
                    completePath(pathElement);
                    //Move on to the next screen
                }else if(pathElement.equals("_help") || pathElement.equals("Help")){
                    //help
                    manualPath(mode);
                }else if(pathElement.equals("_Path")){
                    //Available Paths to take
                    manualPath(mode);
                }else if(pathElement.equals("_back")){
                    size = pathElements.size();
                    size--;
                    pathElements.remove(size);
                    manualPath(mode);
                }else{
                    pathElements.add(pathElement);
                    completePath(pathElement);
                    manualPath(mode);
                }
            }while(!pathElement.equals("_done"));
            return "Path";
        } else {
            messageHandler.HandleMessage(-1, "Invalid Option for mode in function manualPath(mode) Drawn... Returning to last function called before manualPath(mode) was called");
            return "ERROR";
        }
    }
    public static String completePath(String pathElement){

        return "";
    }

    public static void pathMenu(int mode) {
        Logo.displayLogo();
        System.out.println("[MANUAL]: Manually Type out Path");
        System.out.println("[AUTO]: Automatically obtain a default Path");
        System.out.println("[HELP]: Help");
        System.out.println("[Return]: Return to previous Menus/Activity");
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        if(option.equals("manual")){
            messageHandler.HandleMessage(1, "Initiating Manual Path...");
            manualPath(1);
        }else if(option.equals("auto")){
            messageHandler.HandleMessage(1, "Initiating Automatic Path...");
        }else if(option.equals("help")){
            messageHandler.HandleMessage(-2, "Help System not Implemented, Try again in a later update.");
        }else if(option.equals("return")){
            if(mode == 1){
                Settings.configMenu();
            }else if(mode == 2){
                Settings.SettingsMenu();
            }
        } else {
           messageHandler.HandleMessage(-1, "Invalid Option, Try again");
           pathMenu(mode); 
        }
    }
}
