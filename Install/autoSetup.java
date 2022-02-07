package Install;

import java.io.File;
import java.io.IOException;

import Assets.Logo;
import Assets.VersionController;
import MainSystem.Settings;
import MainSystem.SettingsController;
import messageHandler.ErrorMessages;
import messageHandler.SystemMessages;
import messageHandler.messageHandler;
import Login.Login;

public class autoSetup{
    public static String autoPath = "C:\\Users\\Public\\Public Documents";
    private static String setPathLetter = "C:";
    private static String setPathDir = "\\Users\\Public\\Public Documents";
    static int tries = 0;
    public autoSetup(){
        
    }

    public static void StartSetup() {
        messageHandler.HandleMessage(1, "Starting Auto Setup...");
        System.out.println(SystemMessages.getLastMessage());
        messageHandler.HandleMessage(1, "Setting " + autoPath + " as System path...");
        installManager.setPath(setPathDir, setPathLetter);
        messageHandler.HandleMessage(1, "Creating System Launch Files...");
        boolean itWorked = InstallSystemSet.installSystemSets();
        if(itWorked){
            messageHandler.HandleMessage(1, "Installation Files Created Successfully!");
            System.out.println(SystemMessages.getLastMessage());
            installManager.installMenu();
        }else if(!itWorked){
            tries++;
            if(tries < 2){
                itWorked = InstallSystemSet.installSystemSets();
            }else{
                messageHandler.HandleMessage(-2, "Setup Failed!");
                System.out.println(ErrorMessages.getLastMessage());
                System.exit(1);
            }
        }

    }
    public static void startAutoSetup(){
        Logo.displayLogo();
        messageHandler.HandleMessage(1, "Starting Automatic Setup...");
        System.out.println(SystemMessages.getLastMessage());
        String path = autoPath + "\\Solar\\InstallationFiles";
        messageHandler.HandleMessage(1, "Automatic Directory set as: " + path);
        System.out.println(SystemMessages.getLastMessage());
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
            messageHandler.HandleMessage(1, "Directory created successfully");
        }
        path = path + "/config.properties";
        file = new File(path);
        if(!file.exists()){
            try{
                file.createNewFile();
                messageHandler.HandleMessage(1, "config.properties file created, Now Populating default Properties");
                boolean success = createDefaultProperties();
                if(!success){
                    messageHandler.HandleMessage(-2, "Default Settings Failed to populate!");
                    messageHandler.dumpAll();
                    System.exit(3);
                }else{
                    messageHandler.HandleMessage(1, "Successfully populated Default Settings");
                    System.out.println(SystemMessages.getLastMessage());
                }
            }catch(IOException e){
                messageHandler.HandleMessage(-2, e.toString());
                messageHandler.dumpAll();
                System.exit(3);
            }
        }else{
            SettingsController.loadSettings();
            boolean firstTime = FirstTimeController.checkFirstTime();
            if(firstTime){
                FirstTimeController.updateFirstTime(false);
                InstallSystemSet.installSystemSets();
                installDirectories.installTheDirectoriesDamnit();
                installManager.installMenu();
            }else{
                Login.LoginScreen();
            }
        }
    }

    private static boolean createDefaultProperties() {
        SettingsController.setSetting("SystemPath", autoPath + "\\Solar\\InstallationFiles");
        SettingsController.setSetting("FirstTime", "false");
        SettingsController.setSetting("Version", VersionController.getVersion());
        SettingsController.setSetting("LogType", Settings.logType);
        SettingsController.saveSettings();
        


        return false;
    }
}