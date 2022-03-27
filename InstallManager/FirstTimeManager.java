package InstallManager;

import java.io.File;
import java.io.IOException;

import MainSystem.SettingsController;
import messageHandler.messageHandler;

public class FirstTimeManager {
    public static boolean FirstTime = false;
    public static String path;
    public static boolean checkFirstTime() {
        SettingsController.loadSettings();
        path = ProgramController.SystemPath + ProgramController.SystemSubPath + ProgramController.SystemInstallPath;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        path = path + ProgramController.SystemConfig;
        file = new File(path);
        if(file.exists()){
            messageHandler.HandleMessage(1, "FirstTime Configuration Found", false);
            boolean exists = SettingsController.SearchForSet("FirstTime");
            if(exists){
                FirstTime = Boolean.parseBoolean(SettingsController.getSetting("FirstTime"));
                messageHandler.HandleMessage(1, "FirstTime: " + FirstTime, true);
                return FirstTime;
            }else{
                messageHandler.HandleMessage(1, "Unable to Find Setting \"FirstTime\" ", true);
                SettingsController.setSetting("FirstTime", "false");
                FirstTime = true;
                return FirstTime;
            }
        }else{
            try{
                file.createNewFile();
                SettingsController.setSetting("FirstTime", "false");
                FirstTime = true;
                return FirstTime;
            }catch(IOException e){
                messageHandler.HandleMessage(-2, "Unable to create Configuration File", true);
                messageHandler.dumpAll();
                System.exit(1);
                return true;
            }
        }
    }
    public static boolean updateFirstTime(){
        SettingsController.loadSettings();
        if(FirstTime){
           FirstTime=  !FirstTime;
        }else if(!FirstTime){
            FirstTime=true;
        }
        SettingsController.setSetting("FirstTime", String.valueOf(FirstTime));
        
        return FirstTime;
    }
}
