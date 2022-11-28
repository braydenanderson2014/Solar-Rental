package InstallManager;

import java.io.File;
import java.io.IOException;

import Login.Login;
import MainSystem.SettingsController;
import UserController.AutoSetupUserController;

import messageHandler.SystemMessages;
import messageHandler.messageHandler;

public class AutoSetup {
    public static String SystemPathLetter = ProgramController.SystemPathLetter;
    public static String SystemWorkingPath = ProgramController.SystemDefaultPath;
    public static String userPathLetter = ProgramController.UserPathLetter;
    public static String userWorkingPath = ProgramController.UserDefaultPath;
    public static void startAutoSetup() {
        //#region Folder/File Install
        //#region SystemPathLetter
        messageHandler.HandleMessage(1, "Searching for System PathLetter in Configuration...", false);
        String setting = "SystemPathLetter";
		boolean exists = SettingsController.SearchForSet(setting);
        System.out.println(SystemMessages.getLastMessage());
        if(exists){
            messageHandler.HandleMessage(1, "Found SystemPathLetter in Configuration... Now Assigning SystemPathLetter to variable", false);
            System.out.println(SystemMessages.getLastMessage());
            SystemPathLetter = SettingsController.getSetting(setting);
        }else{
            SettingsController.setSetting(setting, SystemPathLetter);
        }
        //#endregion
        //#region Debug
        setting = "debugSite";
        exists = SettingsController.SearchForSet(setting);
        if(exists) {
        	messageHandler.HandleMessage(1, "Found debugSite in Configureation... ", false);
        	System.out.println(SystemMessages.getLastMessage());
        }else {
        	SettingsController.setSetting(setting,"https://github.com/login?return_to=%2Fbraydenanderson2014%2FSolar-Rental%2Fissues%2Fnew");
        }
        //#endregion
        //#region SystemPath
        exists = SettingsController.SearchForSet("SystemPath");
        if(exists){
            SystemWorkingPath = SettingsController.getSetting("SystemPath");
        }else{
            SettingsController.setSetting("SystemPath", SystemWorkingPath);

        }
        //#endregion
        //#region SystemPathCheck/Creation
        ProgramController.SystemRunPath = SystemPathLetter + SystemWorkingPath + ProgramController.SystemSubPath;
        File file = new File(ProgramController.SystemRunPath);
        if(!file.exists()){
            file.mkdirs();
            messageHandler.HandleMessage(1, "Successfully created Directory at: " + ProgramController.SystemRunPath, false);
        }
        //#endregion
        //#region UserPathLetter
        messageHandler.HandleMessage(1, "Searching for User PathLetter in Configuration...", false);
        exists = SettingsController.SearchForSet("UserPathLetter");
        System.out.println(SystemMessages.getLastMessage());
        if(exists){
            messageHandler.HandleMessage(1, "Found UserPathLetter in Configuration... Now Assigning UserPathLetter to variable", false);
            System.out.println(SystemMessages.getLastMessage());
            userPathLetter = SettingsController.getSetting("UserPathLetter");
        }else{
            SettingsController.setSetting("UserPathLetter", userPathLetter);
        }
        //#endregion
        //#region UserPath
        exists = SettingsController.SearchForSet("UserPath");
        if(exists){
            userWorkingPath = SettingsController.getSetting("UserPath");
        }else{
            SettingsController.setSetting("UserPath", userWorkingPath);

        }
        //#endregion
        //#region UserSystemPathCheck/Creation
        ProgramController.UserRunPath = userPathLetter + userWorkingPath;
        file = new File(ProgramController.UserRunPath);
        if(!file.exists()){
            file.mkdirs();
            messageHandler.HandleMessage(1, "Successfully created Directory at: " + ProgramController.UserRunPath, false);
        }
        //#endregion
        //#region UserFolders
        String UserFolder = ProgramController.UserRunPath + "\\Users\\Notebooks";
        file = new File(UserFolder);
        if(!file.exists()){
            file.mkdirs();
            messageHandler.HandleMessage(1, "Successfully created Directories at: " + UserFolder, false);
        }
        //#endregion
        //#region UserList.properties
        String UserDir = ProgramController.UserRunPath + "\\Users/Userlist.properties";
        file = new File(UserDir);
        if(!file.exists()){
            try {
                file.createNewFile();
                messageHandler.HandleMessage(1, "Created Userlist.properties at: " + UserDir, false);
            } catch (IOException e) {
                messageHandler.HandleMessage(-2, "Failed to create Userlist.properties at: " + UserDir, true);
                messageHandler.HandleMessage(-1, "Unable to complete Setup", true);
                ProgramController.SetupMenu();
            }
        }
        //#endregion
        //#region Categories
        String Categories = ProgramController.UserRunPath + "\\Categories/";
        file = new File(Categories);
        if(!file.exists()){
            try {
                file.mkdirs();
                messageHandler.HandleMessage(1, "Created Categories at: " + Categories, false);
                Categories = Categories + "Categories.properties";
                if(!file.exists()){
                    file.createNewFile();
                }
            } catch (IOException e) {
                messageHandler.HandleMessage(-2, "Failed to create Directory at: " + Categories, true);
                messageHandler.HandleMessage(-1, "Unable to complete Setup", true);
                ProgramController.SetupMenu();
            }
        }
        //#endregion
        createDefaultSysSet();
    }//end function
    private static void createDefaultSysSet() {
        boolean exists = SettingsController.SearchForSet("FirstTime");
        if(!exists){
            SettingsController.setSetting("FirstTime", "false");
            messageHandler.HandleMessage(1, "Setting \"FirstTime\" was created successfully. Default Value: false", false);
        }
        exists = SettingsController.SearchForSet("LogType");
        if(!exists){
            SettingsController.setSetting("LogType", "all");
            messageHandler.HandleMessage(1, "Setting \"LogType\" was created successfully. Default Value: all", false);
        }
        exists = SettingsController.SearchForSet("ErrorSet");
        if(!exists){
            SettingsController.setSetting("ErrorSet", "true");
            messageHandler.HandleMessage(1, "Console Setting \"ErrorSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.SearchForSet("WarningSet");
        if(!exists){
            SettingsController.setSetting("WarningSet", "true");
            messageHandler.HandleMessage(1, "Console Setting \"WarningSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.SearchForSet("SystemSet");
        if(!exists){
            SettingsController.setSetting("SystemSet", "true");
            messageHandler.HandleMessage(1, "Console Setting \"SystemSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.SearchForSet("UserNotifySet");
        if(!exists){
            SettingsController.setSetting("UserNotifySet", "true");
            messageHandler.HandleMessage(1, "Console Setting \"UserNotifySet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.SearchForSet("Date/TimeSet");
        if(!exists){
            SettingsController.setSetting("Date/TimeSet", "true");
            messageHandler.HandleMessage(1, "Console Setting \"Date/TimeSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.SearchForSet("TaxP");
        if(!exists){
            SettingsController.setSetting("TaxP", "7.43");
            messageHandler.HandleMessage(1, "Setting \"TaxP\" was created successfully. Default Value: 7.43%", false);
        }
        exists = SettingsController.SearchForSet("FailedAttempts");
        if(!exists){
            SettingsController.setSetting("FailedAttempts",  "3");
            messageHandler.HandleMessage(1, "Setting \"FailedAttempts\" was created successfully. Default Value: 7.43%", false);
        }
        exists = SettingsController.SearchForSet("MaxConsole");
        if(!exists){
            SettingsController.setSetting("MaxConsole",  "5");
            messageHandler.HandleMessage(1, "Setting \"FailedAttempts\" was created successfully. Default Value: 7.43%", false);
        }
        createAdminAccount();
    }

    private static void createAdminAccount() {
        AutoSetupUserController.AutoCreateAdmin();
        FirstTimeManager.updateFirstTime();
        Login.LoginScreen();
    }

}
