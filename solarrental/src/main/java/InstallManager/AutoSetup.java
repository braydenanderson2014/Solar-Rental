package InstallManager;

import java.io.File;
import java.io.IOException;

import Login.Login;
import MainSystem.Main;
import MainSystem.RequestLoader;
import MainSystem.SettingsController;
import UserController.AutoSetupUserController;
import javafx.stage.Stage;
import messageHandler.MessageProcessor;

public class AutoSetup {
    public static String systemPathLetter = ProgramController.systemPathLetter;
    public static String systemWorkingPath = ProgramController.systemDefaultPath;
    public static String userPathLetter = ProgramController.userPathLetter;
    public static String userWorkingPath = ProgramController.userDefaultPath;
    private static Stage stage = Main.getStage();
    public static void startAutoSetup() {
        handleSetting("SystemPathLetter", systemPathLetter);
        handleSetting("debugSite", "https://github.com/login?return_to=%2Fbraydenanderson2014%2FSolar-Rental%2Fissues%2Fnew");
        handleSetting("SystemPath", systemWorkingPath);
        checkCreateDir(ProgramController.systemRunPath = systemPathLetter + systemWorkingPath + ProgramController.systemSubPath);
        handleSetting("UserPathLetter", userPathLetter);
        handleSetting("UserPath", userWorkingPath);
        checkCreateDir(ProgramController.userRunPath = userPathLetter + userWorkingPath);
        checkCreateDir(ProgramController.userRunPath + "\\Users\\Notebooks");
        checkCreateDir(ProgramController.userRunPath + "\\Users\\Notifications");
        checkCreateFile(ProgramController.userRunPath + "\\Users/Userlist.properties");
        checkCreateDir(ProgramController.userRunPath + "\\Categories/");
        checkCreateFile(ProgramController.userRunPath + "\\Categories/Categories.properties");
        RequestLoader.createRequestFile();
        RequestLoader.loadJson();
        createDefaultSysSet();
    }
    
    private static void handleSetting(String setting, String defaultValue) {
        if(SettingsController.searchForSet(setting)) {
            MessageProcessor.processMessage(1, "Found " + setting + " in Configuration... ", false);
            if (setting.contains("Letter")) {
                defaultValue = SettingsController.getSetting(setting);
            }
        } else {
            SettingsController.setSetting(setting, defaultValue);
        }
    }

    private static void checkCreateDir(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
            MessageProcessor.processMessage(1, "Successfully created Directory at: " + path, false);
        }
    }

    private static void checkCreateFile(String path) {
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
                MessageProcessor.processMessage(1, "Created file at: " + path, false);
            } catch (IOException e) {
                handleFileCreationError(path);
            }
        }
    }

    private static void handleFileCreationError(String path) {
        MessageProcessor.processMessage(-2, "Failed to create file at: " + path, true);
        MessageProcessor.processMessage(-1, "Unable to complete Setup", true);
        ProgramController.SetupMenu();
    }

    private static void createDefaultSysSet() {
        String[] settings = {"FirstTime", "LogType", "ErrorSet", "WarningSet", "SystemSet", "UserNotifySet", "DebugSet", "Date/TimeSet", "TaxP", "FailedAttempts", "MaxConsole", "UI"};
        String[] defaultValues = {"false", "all", "true", "true", "true", "true", "true", "true", "7.43", "3", "5", "Enabled"};
       
        for(int i = 0; i < settings.length; i++) {
            if(!SettingsController.searchForSet(settings[i])) {
                SettingsController.setSetting(settings[i], defaultValues[i]);
                MessageProcessor.processMessage(1, "Setting \""+settings[i]+"\" was created successfully. Default Value: "+defaultValues[i], false);
            }
        }
        createAdminAccount();
    }

    private static void createAdminAccount() {
        AutoSetupUserController.AutoCreateAdmin();
        FirstTimeManager.updateFirstTime();
        Login.showLoginScreen();
        //Login.showLoginScreen(stage);
    }
}
