package MainSystem;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import Install.autoSetup;
import messageHandler.messageHandler;

public class SettingsController{
    public static Properties prop = new Properties();
    private static String path = autoSetup.autoPath + "\\Solar\\InstallationFiles/config.properties";
    public static String getSetting(String SettingType){
        String Setting = prop.getProperty(SettingType);
        messageHandler.HandleMessage(1, "Setting: " + SettingType + " " + Setting);
        return Setting;
    }
    public static boolean saveSettings(){
        try (OutputStream output = new FileOutputStream(path)){
            prop.store(output, null);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString());
            return false;
        }
    }
    public static boolean SearchForSet(String Setting){
        boolean exists = prop.containsKey(Setting);
        return exists;
    }
    public static boolean loadSettings(){
        try (InputStream input = new FileInputStream(path)){
            prop.load(input);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString());
            return false;
        }
    }
    public static String setSetting(String SettingType, String Setting){
        prop.setProperty(SettingType, Setting);
        saveSettings();
        return SettingType + Setting;
    }
}