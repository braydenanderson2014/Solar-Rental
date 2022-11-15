package InstallManager;

import MainSystem.SettingsController;
import messageHandler.messageHandler;

public class SystemSetLoader {

    public static boolean loadSystems() {
        SettingsController.loadSettings();
        //#region SystemPath
        if(SettingsController.SearchForSet("SystemPathLetter")){
            ProgramController.SystemPathLetter = SettingsController.getSetting("SystemPathLetter");
        }else{
            messageHandler.HandleMessage(-2, "Failed to load SystemPathLetter", false);
            messageHandler.HandleMessage(1, "Applying default SystemPathLetter", false);
        }
        if(SettingsController.SearchForSet("SystemPath")){
            ProgramController.SystemPath = SettingsController.getSetting("SystemPath");
        }else {
            messageHandler.HandleMessage(-2, "Failed to load SystemPath", false);
            messageHandler.HandleMessage(1, "Applying default SystemPath", false);

        }
        ProgramController.SystemRunPath = ProgramController.SystemPathLetter + AutoSetup.SystemWorkingPath + ProgramController.SystemSubPath;
        //#endregion
        //#region UserPath
        return true;
    }

}
