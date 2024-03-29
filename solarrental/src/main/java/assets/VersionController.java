package assets;
//import java.io.*;
import java.util.Scanner;

import MainSystem.SettingsController;
import messageHandler.MessageProcessor;
/**
 * Controls the version of the program.
 *
 * @author (Brayden Anderson)
 * @version (Base Version: ALPHA 1.0.0, Snapshot: 1A3XV)
 * @classversion (ALPHA V1.0.0)
 */
public class VersionController {
    private static String defaultVersion = "ALPHA V1.1.0";
    private static Scanner scan = new Scanner(System.in);
    private static String version1 = "";
    public VersionController(){
        MessageProcessor.processMessage(1, "Version Set as Default, May Correct later", false);
        version1 = defaultVersion;
    }

    public static String updateVersion(){
        version1 = scan.nextLine();
        String string = "Version";
		String settingType = string;
		SettingsController.setSetting(settingType, version1);
        return version1;
    }

    public static String setVersion(String version){
        version1 = version;
        SettingsController.setSetting("Version", version1);
        return version1;
    }

    public static String getVersion(){
        boolean exists = SettingsController.searchForSet("Version");
        if(exists){
            version1 = SettingsController.getSetting("Version");
        }else{
            version1 = "ALPHA 1.0.1";
            System.out.println("Version" + version1);
        }
        return version1;
    }
}
