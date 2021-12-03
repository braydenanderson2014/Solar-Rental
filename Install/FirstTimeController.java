package Install;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import MainSystem.SettingsController;
import messageHandler.ErrorMessages;
import messageHandler.SystemMessages;
import messageHandler.WarningMessages;
import messageHandler.messageHandler;

public class FirstTimeController {
    public static boolean firstTime;
    public static boolean checkFirstTime(){
        String path = "C:\\Users\\Public\\Documents\\Solar Rentals\\InstallationFiles/config.properties";
        File file = new File(path);
        if(file.exists()){
            try {
                SettingsController.loadSettings();
                String FirstTimeSet = SettingsController.getSetting("FirstTime");
                firstTime = Boolean.parseBoolean(FirstTimeSet);
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
        String path = "C:\\Users\\Public\\Documents\\Solar Rentals\\InstallationFiles/config.properties";
        File file = new File(path);
        if(file.exists()){
            messageHandler.HandleMessage(1, "Now Updating config.properties file");
            System.out.println(SystemMessages.getLastMessage());
            messageHandler.HandleMessage(1, "Converting Boolean to String...");
            System.out.println(SystemMessages.getLastMessage());
            String bool = String.valueOf(isFirstTimeUpdate);
            messageHandler.HandleMessage(1, "Successfully converted boolean to String!");
            System.out.println(SystemMessages.getLastMessage());
            try {
                SettingsController.setSetting("FirstTime", bool);
                messageHandler.HandleMessage(1, "Saved converted boolean to isFirstTime.txt");
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
