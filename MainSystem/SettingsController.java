package MainSystem;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import InstallManager.ProgramController;
import messageHandler.MessageProcessor;

public class SettingsController{
    protected static Properties prop = new Properties();
    private static String path = ProgramController.systemPath + ProgramController.systemSubPath + ProgramController.systemInstallPath + ProgramController.systemConfig;

    public static String getSetting(String settingType){
        String setting = prop.getProperty(settingType);
        MessageProcessor.processMessage(1, "Setting: " + settingType + " " + setting, true);
        return setting;
    }

    public static boolean saveSettings(){
        try (OutputStream output = new FileOutputStream(path)){
            prop.store(output, null);
            return true;
        }catch(IOException e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            return false;
        }
    }

    public static boolean searchForSet(String setting){
        return prop.containsKey(setting);
    }

    public static boolean loadSettings(){
        try (InputStream input = new FileInputStream(path)){
            prop.load(input);
            return true;
        }catch(IOException e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            return false;
        }
    }

    public static String setSetting(String settingType, String setting){
        loadSettings();
        prop.setProperty(settingType, setting);
        saveSettings();
        return settingType + setting;
    }
}