package InstallManager;

import java.io.File;
import java.io.IOException;

import Login.Login;
import MainSystem.SettingsController;
import UserController.AutoSetupUserController;
import messageHandler.MessageProcessor;
import messageHandler.SystemMessages;

public class AutoSetup {
    public static String systemPathLetter = ProgramController.systemPathLetter;
    public static String systemWorkingPath = ProgramController.systemDefaultPath;
    public static String userPathLetter = ProgramController.userPathLetter;
    public static String userWorkingPath = ProgramController.userDefaultPath;
    public static void startAutoSetup() {
        //#region Folder/File Install
        //#region SystemPathLetter
        MessageProcessor.processMessage(1, "Searching for System PathLetter in Configuration...", false);
        String setting = "SystemPathLetter";
		boolean exists = SettingsController.searchForSet(setting);
        System.out.println(SystemMessages.getLastMessage());
        if(exists){
            MessageProcessor.processMessage(1, "Found SystemPathLetter in Configuration... Now Assigning SystemPathLetter to variable", false);
            System.out.println(SystemMessages.getLastMessage());
            systemPathLetter = SettingsController.getSetting(setting);
        }else{
            SettingsController.setSetting(setting, systemPathLetter);
        }
        //#endregion
        //#region Debug
        setting = "debugSite";
        exists = SettingsController.searchForSet(setting);
        if(exists) {
        	MessageProcessor.processMessage(1, "Found debugSite in Configuration... ", false);
        	System.out.println(SystemMessages.getLastMessage());
        }else {
        	SettingsController.setSetting(setting,"https://github.com/login?return_to=%2Fbraydenanderson2014%2FSolar-Rental%2Fissues%2Fnew");
        }
        //#endregion
        //#region SystemPath
        String setting2 = "SystemPath";
		exists = SettingsController.searchForSet(setting2);
        if(exists){
            systemWorkingPath = SettingsController.getSetting(setting2);
        }else{
            SettingsController.setSetting(setting2, systemWorkingPath);

        }
        //#endregion
        //#region SystemPathCheck/Creation
        ProgramController.systemRunPath = systemPathLetter + systemWorkingPath + ProgramController.systemSubPath;
        File file = new File(ProgramController.systemRunPath);
        if(!file.exists()){
            file.mkdirs();
            MessageProcessor.processMessage(1, "Successfully created Directory at: " + ProgramController.systemRunPath, false);
        }
        //#endregion
        //#region UserPathLetter
        MessageProcessor.processMessage(1, "Searching for User PathLetter in Configuration...", false);
        String setting21 = "UserPathLetter";
		exists = SettingsController.searchForSet(setting21);
        System.out.println(SystemMessages.getLastMessage());
        if(exists){
            MessageProcessor.processMessage(1, "Found UserPathLetter in Configuration... Now Assigning UserPathLetter to variable", false);
            System.out.println(SystemMessages.getLastMessage());
            userPathLetter = SettingsController.getSetting(setting21);
        }else{
            SettingsController.setSetting(setting21, userPathLetter);
        }
        //#endregion
        //#region UserPath
        String setting3 = "UserPath";
		exists = SettingsController.searchForSet(setting3);
        if(exists){
            userWorkingPath = SettingsController.getSetting(setting3);
        }else{
            SettingsController.setSetting(setting3, userWorkingPath);

        }
        //#endregion
        //#region UserSystemPathCheck/Creation
        ProgramController.userRunPath = userPathLetter + userWorkingPath;
        file = new File(ProgramController.userRunPath);
        if(!file.exists()){
            file.mkdirs();
            MessageProcessor.processMessage(1, "Successfully created Directory at: " + ProgramController.userRunPath, false);
        }
        //#endregion
        //#region UserFolders
        String userFolder = ProgramController.userRunPath + "\\Users\\Notebooks";
        file = new File(userFolder);
        if(!file.exists()){
            file.mkdirs();
            MessageProcessor.processMessage(1, "Successfully created Directories at: " + userFolder, false);
        }
        userFolder = ProgramController.userRunPath + "\\Users\\Notifications";
        file = new File(userFolder);
        if(!file.exists()){
            file.mkdirs();
            MessageProcessor.processMessage(1, "Successfully created Directories at: " + userFolder, false);
        }
        //#endregion
        //#region UserList.properties
        String userDir = ProgramController.userRunPath + "\\Users/Userlist.properties";
        file = new File(userDir);
        if(!file.exists()){
            try {
                file.createNewFile();
                MessageProcessor.processMessage(1, "Created Userlist.properties at: " + userDir, false);
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, "Failed to create Userlist.properties at: " + userDir, true);
                MessageProcessor.processMessage(-1, "Unable to complete Setup", true);
                ProgramController.SetupMenu();
            }
        }
        //#endregion
        //#region Categories
        String categories = ProgramController.userRunPath + "\\Categories/";
        file = new File(categories);
        if(!file.exists()){
            try {
                file.mkdirs();
                MessageProcessor.processMessage(1, "Created Categories at: " + categories, false);
                categories = categories + "Categories.properties";
                if(!file.exists()){
                    file.createNewFile();
                }
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, "Failed to create Directory at: " + categories, true);
                MessageProcessor.processMessage(-1, "Unable to complete Setup", true);
                ProgramController.SetupMenu();
            }
        }
        //#endregion
        createDefaultSysSet();
    }//end function
    private static void createDefaultSysSet() {
        boolean exists = SettingsController.searchForSet("FirstTime");
        if(!exists){
            SettingsController.setSetting("FirstTime", "false");
            MessageProcessor.processMessage(1, "Setting \"FirstTime\" was created successfully. Default Value: false", false);
        }
        exists = SettingsController.searchForSet("LogType");
        if(!exists){
            SettingsController.setSetting("LogType", "all");
            MessageProcessor.processMessage(1, "Setting \"LogType\" was created successfully. Default Value: all", false);
        }
        exists = SettingsController.searchForSet("ErrorSet");
        if(!exists){
            SettingsController.setSetting("ErrorSet", "true");
            MessageProcessor.processMessage(1, "Console Setting \"ErrorSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.searchForSet("WarningSet");
        if(!exists){
            SettingsController.setSetting("WarningSet", "true");
            MessageProcessor.processMessage(1, "Console Setting \"WarningSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.searchForSet("SystemSet");
        if(!exists){
            SettingsController.setSetting("SystemSet", "true");
            MessageProcessor.processMessage(1, "Console Setting \"SystemSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.searchForSet("UserNotifySet");
        if(!exists){
            SettingsController.setSetting("UserNotifySet", "true");
            MessageProcessor.processMessage(1, "Console Setting \"UserNotifySet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.searchForSet("DebugSet");
        if(!exists){
            SettingsController.setSetting("DebugSet", "true");
            MessageProcessor.processMessage(1, "Console Setting \"DebugSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.searchForSet("Date/TimeSet");
        if(!exists){
            SettingsController.setSetting("Date/TimeSet", "true");
            MessageProcessor.processMessage(1, "Console Setting \"Date/TimeSet\" was created successfully. Default Value: true", false);
        }
        exists = SettingsController.searchForSet("TaxP");
        if(!exists){
            SettingsController.setSetting("TaxP", "7.43");
            MessageProcessor.processMessage(1, "Setting \"TaxP\" was created successfully. Default Value: 7.43%", false);
        }
        exists = SettingsController.searchForSet("FailedAttempts");
        if(!exists){
            SettingsController.setSetting("FailedAttempts",  "3");
            MessageProcessor.processMessage(1, "Setting \"FailedAttempts\" was created successfully. Default Value: 7.43%", false);
        }
        exists = SettingsController.searchForSet("MaxConsole");
        if(!exists){
            SettingsController.setSetting("MaxConsole",  "5");
            MessageProcessor.processMessage(1, "Setting \"FailedAttempts\" was created successfully. Default Value: 7.43%", false);
        }
        createAdminAccount();
    }

    private static void createAdminAccount() {
        AutoSetupUserController.AutoCreateAdmin();
        FirstTimeManager.updateFirstTime();
        Login.showLoginScreen(null);
    }

}
