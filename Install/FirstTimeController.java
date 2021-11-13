package Install;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import messageHandler.ErrorMessages;
import messageHandler.SystemMessages;
import messageHandler.WarningMessages;
import messageHandler.messageHandler;

public class FirstTimeController {
    public static boolean firstTime;
    public static boolean checkFirstTime(){
        String path = "C:\\Users\\Public\\Documents\\Solar Rentals\\InstallationFiles/isFirstTime.txt";
        File file = new File(path);
        if(file.exists()){
            try {
                BufferedReader in = new BufferedReader(new FileReader(new File(path)));
                int line = 0;
                messageHandler.HandleMessage(1, "Reading from isFirstTime.txt on line: " + line);
                System.out.println(SystemMessages.getLastMessage());
                for(String x= in.readLine(); x != null; x= in.readLine()){
                    line++;
                    messageHandler.HandleMessage(1, "Reading from isFirstTime.txt on line: " + line);
                    System.out.println(SystemMessages.getLastMessage());
                    firstTime = Boolean.parseBoolean(x);
                }
                in.close();
                messageHandler.HandleMessage(1, "Found a Boolean, Now applying to System Setting... IsFirstTime = " + firstTime);
                System.out.println(SystemMessages.getLastMessage());
                return firstTime;
            } catch (Exception e) {
                messageHandler.HandleMessage(-2, "Failed to read from isFirstTime.txt [" + e.toString() + "]");
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
        System.out.println(isFirstTimeUpdate);
        System.out.println(firstTime);
        String path = "C:\\Users\\Public\\Documents\\Solar Rentals\\InstallationFiles/isFirstTime.txt";
        File file = new File(path);
        if(file.exists()){
            messageHandler.HandleMessage(1, "Now Updating firstTime file");
            System.out.println(SystemMessages.getLastMessage());
            messageHandler.HandleMessage(1, "Converting Boolean to String...");
            System.out.println(SystemMessages.getLastMessage());
            String bool = String.valueOf(isFirstTimeUpdate);
            messageHandler.HandleMessage(1, "Successfully converted boolean to String!");
            System.out.println(SystemMessages.getLastMessage());
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(bool);
                bw.close();
                messageHandler.HandleMessage(1, "Saved converted boolean to isFirstTime.txt");
                System.out.println(SystemMessages.getLastMessage());
            } catch (Exception e) {
                messageHandler.HandleMessage(-2, "Failed to convert boolean to String or save the converted boolean" + e.toString());
            }
        }else {
            messageHandler.HandleMessage(-1, "Failed to save isFirstTime.txt (FILE NOT FOUND)");
            System.out.println(WarningMessages.getLastMessage());
        }
        return isFirstTimeUpdate;
    }
}
