package Install;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import Assets.VersionController;
import messageHandler.ErrorMessages;
import messageHandler.SystemMessages;
import messageHandler.messageHandler;

public class InstallSystemSet {
    public static String SystemSetPath = "C:\\Users\\Public\\Documents\\Solar Rentals\\InstallationFiles/";
    public static String FilePath = SystemSetPath;
    public static boolean installSystemSets(){
        //#region FOLDERSTRUCTURE
        File file = new File(SystemSetPath); 
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
        //#endregion
        //#region PATH SETTING 
        messageHandler.HandleMessage(1, "Now Creating System Files");
        System.out.println(SystemMessages.getLastMessage());
        FilePath = FilePath + "/Path.txt";
        file = new File(FilePath); 
        try {
            if(!file.exists()){
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Empty File Called Path; Will Populate File later");
                System.out.println(SystemMessages.getLastMessage());   
                messageHandler.HandleMessage(1, "Now Populating Path.txt with Value" + SystemSetPath);
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(SystemSetPath);
                bw.close();
                messageHandler.HandleMessage(1, "Successfully populated isFirstTime.txt");
                System.out.println(SystemMessages.getLastMessage());
                FirstTimeController.updateFirstTime(false);             
            }else{
                messageHandler.HandleMessage(1, "File Already Existed, Moving on to next File...");
                System.out.println(SystemMessages.getLastMessage());
                installManager.setPath("C", "\\Users\\Public\\Documents\\Solar Rentals\\InstallationFiles/");
                messageHandler.HandleMessage(1, "Set path to System path for a bit, will adjust normal path later...");
                System.out.println(SystemMessages.getLastMessage());
            }
            //#endregion
        //#region ISFIRSTTIMESET
            FilePath = SystemSetPath + "/isFirstTime.txt";
            file = new File(FilePath);
            if(!file.exists()){
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Empty File called isFirstTime; Will Populate File in Just a moment");
                System.out.println(SystemMessages.getLastMessage());
                messageHandler.HandleMessage(1, "Now Populating isFirstTime.txt with Value" + false);
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("false");
                bw.close();
                messageHandler.HandleMessage(1, "Successfully populated isFirstTime.txt");
                System.out.println(SystemMessages.getLastMessage());
                FirstTimeController.updateFirstTime(false);
            }else{
                messageHandler.HandleMessage(1, "File Already Existed, Writing Default Value...");
                System.out.println(SystemMessages.getLastMessage());
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("false");
                bw.close();
                messageHandler.HandleMessage(1, "Successfully populated isFirstTime.txt");
                FirstTimeController.updateFirstTime(false);
                System.out.println(SystemMessages.getLastMessage());
            }
            //#endregion
        //#region VERSION
            FilePath = SystemSetPath + "/Version.txt";
            file = new File(FilePath);
            if(!file.exists()){
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Empty File called Version; Will Populate File in Just a moment");
                System.out.println(SystemMessages.getLastMessage());
                messageHandler.HandleMessage(1, "Now Populating Version.txt with Current Version: " + VersionController.getVersion());
                System.out.println(SystemMessages.getLastMessage());
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(VersionController.getVersion());
                bw.close();
                messageHandler.HandleMessage(1, "Version.txt was Successfully populated...");
                System.out.println(SystemMessages.getLastMessage());
            }else{
                messageHandler.HandleMessage(1, "File Already Existed, Moving on to next File...");
                System.out.println(SystemMessages.getLastMessage());
            }
            //#endregion
        //#region LOGType
            FilePath = SystemSetPath + "/LogTypeSet.txt";
            file = new File(FilePath);
            if(!file.exists()){
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Empty File called LogTypeSet; Will Populate File in Just a moment");
                System.out.println(SystemMessages.getLastMessage());
                messageHandler.HandleMessage(1, "Now Populating LogTypeSet.txt with Default Setting: " + true);
                System.out.println(SystemMessages.getLastMessage());
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("all");
                bw.close();
                messageHandler.HandleMessage(1, "LogTypeSet.txt was Successfully populated...");
                System.out.println(SystemMessages.getLastMessage());
            }else{
                messageHandler.HandleMessage(1, "File Already Existed, Moving on to next File...");
                System.out.println(SystemMessages.getLastMessage());
            }
            //#endregion
        //#region CONSOLE SETTINGS
        //#region ConsoleSetFolder
        FilePath = SystemSetPath + "\\ConsoleSettings";
        file = new File(FilePath);
        if(!file.exists()){
            messageHandler.HandleMessage(1, "Unable to find Directory: " + SystemSetPath + ", at the expected location. Now Creating The Folder Structure");
            System.out.println(SystemMessages.getLastMessage());
            file.mkdir();
            messageHandler.HandleMessage(1, "Console Settings Directory Created Successfully!");
            System.out.println(SystemMessages.getLastMessage());
        }else{
            messageHandler.HandleMessage(1, "Directory already Existed!");
        }
        //#endregion
        //#region TimeSet    
            FilePath = SystemSetPath + "\\ConsoleSettings/TimeSet.txt";
            file = new File(FilePath);
            if(!file.exists()){
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Empty File called TimeSet; Will Populate File in Just a moment");
                System.out.println(SystemMessages.getLastMessage());
                messageHandler.HandleMessage(1, "Now Populating TimeSet.txt with Default Value: " + true);
                System.out.println(SystemMessages.getLastMessage());
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("true");
                bw.close();
                messageHandler.HandleMessage(1, "TimeSet.txt was Successfully populated...");
                System.out.println(SystemMessages.getLastMessage());
            }else{
                messageHandler.HandleMessage(1, "File Already Existed, Moving on to next File...");
                System.out.println(SystemMessages.getLastMessage());
            }
            //#endregion
        //#region ErrorSet
            FilePath = SystemSetPath + "\\ConsoleSettings/ErrorSet.txt";
            file = new File(FilePath);
            if(!file.exists()){
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Empty File called ErrorSet; Will Populate File in Just a moment");
                System.out.println(SystemMessages.getLastMessage());
                messageHandler.HandleMessage(1, "Now Populating ErrorSet.txt with Default Setting: " + true);
                System.out.println(SystemMessages.getLastMessage());
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("true");
                bw.close();
                messageHandler.HandleMessage(1, "ErrorSet.txt was Successfully populated...");
                System.out.println(SystemMessages.getLastMessage());
            }else{
                messageHandler.HandleMessage(1, "File Already Existed, Moving on to next File...");
                System.out.println(SystemMessages.getLastMessage());
            }
            //#endregion
        //#region WarningSet
            FilePath = SystemSetPath + "\\ConsoleSettings/WarningSet.txt";
            file = new File(FilePath);
            if(!file.exists()){
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Empty File called WarningSet; Will Populate File in Just a moment");
                System.out.println(SystemMessages.getLastMessage());
                messageHandler.HandleMessage(1, "Now Populating WarningSet.txt with Default Setting: " + true);
                System.out.println(SystemMessages.getLastMessage());
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("true");
                bw.close();
                messageHandler.HandleMessage(1, "WarningSet.txt was Successfully populated...");
                System.out.println(SystemMessages.getLastMessage());
            }else{
                messageHandler.HandleMessage(1, "File Already Existed, Moving on to next File...");
                System.out.println(SystemMessages.getLastMessage());
            }
            //#endregion
        //#region SystemSet
            FilePath = SystemSetPath + "\\ConsoleSettings/SystemSet.txt";
            file = new File(FilePath);
            if(!file.exists()){
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Empty File called SystemSet; Will Populate File in Just a moment");
                System.out.println(SystemMessages.getLastMessage());
                messageHandler.HandleMessage(1, "Now Populating SystemSet.txt with Default Setting: " + true);
                System.out.println(SystemMessages.getLastMessage());
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("true");
                bw.close();
                messageHandler.HandleMessage(1, "SystemSet.txt was Successfully populated...");
                System.out.println(SystemMessages.getLastMessage());
            }else{
                messageHandler.HandleMessage(1, "File Already Existed, Moving on to next File...");
                System.out.println(SystemMessages.getLastMessage());
            }
        //#endregion
        //#region UserNotifySet
        FilePath = SystemSetPath + "\\ConsoleSettings/UserNotifySet.txt";
        file = new File(FilePath);
        if(!file.exists()){
            file.createNewFile();
            messageHandler.HandleMessage(1, "Created Empty File called UserNotifySet; Will Populate File in Just a moment");
            System.out.println(SystemMessages.getLastMessage());
            messageHandler.HandleMessage(1, "Now Populating UserNotify.txt with Default Setting: " + true);
            System.out.println(SystemMessages.getLastMessage());
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("true");
            bw.close();
            messageHandler.HandleMessage(1, "UserNotifySet.txt was Successfully populated...");
            System.out.println(SystemMessages.getLastMessage());
        }else{
            messageHandler.HandleMessage(1, "File Already Existed, Moving on to next File...");
            System.out.println(SystemMessages.getLastMessage());
        }
        //#endregion
        //#endregion
            return true;
        } catch (Exception e) {
            messageHandler.HandleMessage(-2, "Unable to Create System File at specified location [" + e.toString() + "]");
            System.out.println(ErrorMessages.getLastMessage());
            return false;
        }
    }    
}