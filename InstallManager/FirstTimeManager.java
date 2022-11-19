package InstallManager;

import java.io.File;
import java.io.IOException;

import MainSystem.SettingsController;
import messageHandler.MessageProcessor;

public class FirstTimeManager {
    public static boolean firstTime = false;
    public static String path;
    public static boolean checkFirstTime() {
        SettingsController.loadSettings();
        path = ProgramController.systemPath + ProgramController.systemSubPath + ProgramController.systemInstallPath;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        path = path + ProgramController.systemConfig;
        file = new File(path);
        String setting = "FirstTime";
		if(file.exists()){
            MessageProcessor.processMessage(1, "FirstTime Configuration Found", false);
            boolean exists = SettingsController.searchForSet(setting);
            if(exists){
                firstTime = Boolean.parseBoolean(SettingsController.getSetting(setting));
                MessageProcessor.processMessage(1, "FirstTime: " + firstTime, false);
                return firstTime;
            }else{
                MessageProcessor.processMessage(1, "Unable to Find Setting \"FirstTime\" ", true);
                SettingsController.setSetting(setting, "false");
                firstTime = true;
                return firstTime;
            }
        }else{
            try{
                file.createNewFile();
                SettingsController.setSetting(setting, "false");
                firstTime = true;
                return firstTime;
            }catch(IOException e){
                MessageProcessor.processMessage(-2, "Unable to create Configuration File", true);
                MessageProcessor.dumpAll();
                System.exit(1);
                return true;
            }
        }
    }

    public static boolean updateFirstTime(){
        SettingsController.loadSettings();
        if(firstTime){
            firstTime=  !firstTime;
        }else if(!firstTime){
            firstTime=true;
        }
        SettingsController.setSetting("FirstTime", String.valueOf(firstTime));

        return firstTime;
    }
}
