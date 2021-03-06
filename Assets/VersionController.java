package Assets;
//import java.io.*;
import java.util.Scanner;

import MainSystem.SettingsController;
import messageHandler.messageHandler;
/**
 * Controls the version of the program.
 *
 * @author (Brayden Anderson)
 * @version (Base Version: ALPHA 1.0.0, Snapshot: 1A3XV)
 * @classversion (ALPHA V1.0.0)
 */
public class VersionController {
    private static String DefaultVersion = "ALPHA V1.1.0";
    private static Scanner scan = new Scanner(System.in);
    private static String Version = "";
    public VersionController(){
        messageHandler.HandleMessage(1, "Version Set as Default, May Correct later", false);
        Version = DefaultVersion;
    }
    public static String updateVersion(){
        Version = scan.nextLine();
        SettingsController.setSetting("Version", Version);
        return Version;
    }
    public static String setVersion(String version){
        Version = version;
        SettingsController.setSetting("Version", Version);
        return Version;
    }
    public static String getVersion(){
        boolean exists = SettingsController.SearchForSet("Version");
        if(exists){
            Version = SettingsController.getSetting("Version");
        }else{
            Version = "ALPHA 1.0.1";
            System.out.println("Version" + Version);
        }
        return Version;
    }
}
