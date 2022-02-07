package Install;
import java.io.File;


import MainSystem.SettingsController;
import messageHandler.ErrorMessages;
import messageHandler.SystemMessages;
import messageHandler.WarningMessages;
import messageHandler.messageHandler;

public class FirstTimeController {
    public static boolean firstTime;
    public static boolean checkFirstTime(){
        String path = "C:\\Users\\Public\\Public Documents\\Solar\\InstallationFiles/config.properties";
        File file = new File(path);
        if(file.exists()){
            messageHandler.HandleMessage(1, "Path Found");
            try {
                SettingsController.loadSettings();
                messageHandler.HandleMessage(1, "Settings Loaded, Checking firstTime setup");
                boolean exists = SettingsController.SearchForSet("FirstTime");
                if(exists){
                    String FirstTimeSet = SettingsController.getSetting("FirstTime");
                    messageHandler.HandleMessage(1, "First Time Setup?:" + FirstTimeSet);
                    firstTime = Boolean.parseBoolean(FirstTimeSet);
                }else{
                    SettingsController.setSetting("FirstTime", String.valueOf(true));
                    firstTime = true;
                }
                return firstTime;
            } catch (Exception e) {
                messageHandler.HandleMessage(-2, "Failed to read from config.properties [" + e.toString() + "]");
                return true;
            }
        }else{
            messageHandler.HandleMessage(-2, "Failed to find a critical File, Starting System File Setup");
            System.out.println(ErrorMessages.getLastMessage());
            return true;
        }
    }
    public static boolean updateFirstTime(boolean isFirstTimeUpdate){
        firstTime = isFirstTimeUpdate;
        messageHandler.HandleMessage(1, "First Time: " + firstTime);
        System.out.println(SystemMessages.getLastMessage());
        String path = "C:\\Users\\Public\\Public Documents\\Solar\\InstallationFiles/config.properties";
        File file = new File(path);
        if(file.exists()){
            messageHandler.HandleMessage(1, "Path Found");
            System.out.println(SystemMessages.getLastMessage());
            messageHandler.HandleMessage(1, "Now Updating configuration file with value: " + isFirstTimeUpdate);
            System.out.println(SystemMessages.getLastMessage());
            messageHandler.HandleMessage(1, "Converting Boolean to String...");
            System.out.println(SystemMessages.getLastMessage());
            String bool = String.valueOf(isFirstTimeUpdate);
            messageHandler.HandleMessage(1, "Successfully converted boolean to String!");
            System.out.println(SystemMessages.getLastMessage());
            try {
                SettingsController.setSetting("FirstTime", bool);
                messageHandler.HandleMessage(1, "Saved converted boolean to config.properties");
                System.out.println(SystemMessages.getLastMessage());
            } catch (Exception e) {
                messageHandler.HandleMessage(-2, "Failed to convert boolean to String or save the converted boolean" + e.toString());
            }
        }else {
            messageHandler.HandleMessage(-1, "Failed to save config.properties (FILE NOT FOUND)");
            System.out.println(WarningMessages.getLastMessage());
        }
        return isFirstTimeUpdate;
    }
}
