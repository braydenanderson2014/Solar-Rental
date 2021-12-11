package Install;
//java imports
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
//custom imports
import Assets.VersionController;
import MainSystem.Settings;
import MainSystem.SettingsController;
import messageHandler.ConsoleSettings;
import messageHandler.ErrorMessages;
import messageHandler.SystemMessages;
import messageHandler.messageHandler;

public class InstallSystemSet {
    public static String pathLetter = "C";
    public static String workingPath = "\\Users\\Public\\Documents\\Solar Rentals\\InstallationFiles";
    public static String SystemSetPath = pathLetter + ":" + workingPath;
    public static String FilePath = SystemSetPath;
    public static boolean installSystemSets(){
        FilePath = SystemSetPath + "config.properties";
        File file = new File(installManager.getSystemPath() + "config.properties");
        try{
            if(!file.exists()){
                messageHandler.HandleMessage(-2, "Configuration File Missing... Attempting to create.");
                SettingsController.setSetting("All", "Settings");
                SettingsController.saveSettings();
                messageHandler.HandleMessage(1, "Configuration saved");
                SettingsController.loadSettings();
                messageHandler.HandleMessage(1, "Reloading Configuration");
            }else{
                //#region PathLetter
                boolean exists = SettingsController.SearchForSet("PathLetter");
                if(exists){
                    pathLetter = SettingsController.getSetting("PathLetter");
                }else{
                    SettingsController.setSetting("PathLetter", pathLetter);
                }
                //#endregion
                //#region Path
                    exists = SettingsController.SearchForSet("Path");
                    if(exists){
                        workingPath = SettingsController.getSetting("Path");
                    }else{
                        SettingsController.setSetting("Path", workingPath);
                    }
                //#endregion
                installManager.setPath(pathLetter + ":", workingPath);
                //#region MainFolderLayout
                file = new File(SystemSetPath); 
                if(!file.exists()){
                    messageHandler.HandleMessage(1, "Unable to find Directory: " + SystemSetPath + ", at the expected location. Now Creating The Folder Structure");
                    System.out.println(SystemMessages.getLastMessage());
                    file.mkdirs();
                    if(file.exists()){
                        messageHandler.HandleMessage(1, "Main Folder Structure Created Successfully");
                        System.out.println(SystemMessages.getLastMessage());
                    }else {
                        messageHandler.HandleMessage(2, "Failed to Create Main Folder Structure");
                        System.out.println(ErrorMessages.getLastMessage());
                    }
                }else {
                    messageHandler.HandleMessage(1,"Directory already Existed!");
                    System.out.println(SystemMessages.getLastMessage());
                }
                //#region Version
                exists = SettingsController.SearchForSet("Version");
                if(exists){
                    VersionController.setVersion(SettingsController.getSetting("Version"));
                    messageHandler.HandleMessage(1, "Version Set: " + VersionController.getVersion());
                }else {
                    SettingsController.setSetting("Version", VersionController.getVersion());
                    messageHandler.HandleMessage(1, "Version Setting updated: " + VersionController.getVersion());
                }
                //#endregion
                //#region FirstTime
                exists = SettingsController.SearchForSet("FirstTime");
                if(exists){
                    //boolean firstTime = Boolean.parseBoolean(SettingsController.getSetting("FirstTime"));
                    FirstTimeController.updateFirstTime(false);
                }else{
                    SettingsController.setSetting("FirstTime", String.valueOf(FirstTimeController.checkFirstTime()));
                    FirstTimeController.updateFirstTime(false);
                }
                //#endregion
                //#region LogType
                exists = SettingsController.SearchForSet("LogType");
                if(exists){
                    Settings.logType = SettingsController.getSetting("LogType");
                }else {
                    SettingsController.setSetting("LogType", Settings.logType);
                }
                //#endregion
                //#region Console Settings
                    //#region Console Title
                        exists = SettingsController.SearchForSet("ConsoleSettings");
                        if(!exists){
                            SettingsController.setSetting("ConsoleSettings", "");
                        }
                    //#endregion
                    //#region Timeset
                        exists = SettingsController.SearchForSet("DateTimeSet");
                        if(exists){
                            ConsoleSettings.timeSet = Boolean.parseBoolean(SettingsController.getSetting("DateTimeSet"));
                        }else{
                            SettingsController.setSetting("DateTimeSet", String.valueOf(true));
                            ConsoleSettings.timeSet = true;
                        }
                    //#endregion
                    //#region ErrorSet
                    exists = SettingsController.SearchForSet("ErrorSet");
                    if(exists){
                        ConsoleSettings.ErrorSet = Boolean.parseBoolean(SettingsController.getSetting("ErrorSet"));
                    }else{
                        SettingsController.setSetting("ErrorSet", String.valueOf(true));
                        ConsoleSettings.ErrorSet = true;
                    }
                    //#endregion
                    //#region WarningSet
                    exists = SettingsController.SearchForSet("WarningSet");
                    if(exists){
                        ConsoleSettings.WarningSet = Boolean.parseBoolean(SettingsController.getSetting("WarningSet"));
                    }else{
                        SettingsController.setSetting("WarningSet", String.valueOf(true));
                        ConsoleSettings.WarningSet = true;
                    }
                    //#endregion
                    //#region SystemSet
                        exists = SettingsController.SearchForSet("SystemSet");
                        if(exists){
                            ConsoleSettings.SystemSet = Boolean.parseBoolean(SettingsController.getSetting("SystemSet"));
                        }else{
                            SettingsController.setSetting("SystemSet", String.valueOf(true));
                            ConsoleSettings.SystemSet = true;
                        }
                    //#endregion
                    //#region UserNotifySet
                        exists = SettingsController.SearchForSet("UserNotifySet");
                        if(exists){
                            ConsoleSettings.UserNotifySet = Boolean.parseBoolean(SettingsController.getSetting("UserNotifySet"));
                        }else{
                            SettingsController.setSetting("UserNotifySet", String.valueOf(true));
                            ConsoleSettings.SystemSet = true;
                        }
                    //#endregion
                //#endregion
            }
            return true;
        }catch(NullPointerException e){
            messageHandler.HandleMessage(-2, e.toString());
            System.exit(1);
            return false;
        }
        //#endregion
    }    
}
