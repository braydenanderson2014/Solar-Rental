package InstallManager;

import MainSystem.SettingsController;
import messageHandler.MessageProcessor;

public class SystemSetLoader {

    public static boolean loadSystems() {
        SettingsController.loadSettings();
        //#region SystemPath
        if(SettingsController.searchForSet("SystemPathLetter")){
            ProgramController.systemPathLetter = SettingsController.getSetting("SystemPathLetter");
        }else{
            MessageProcessor.processMessage(-2, "Failed to load SystemPathLetter", false);
            MessageProcessor.processMessage(1, "Applying default SystemPathLetter", false);
        }
        if(SettingsController.searchForSet("SystemPath")){
            ProgramController.systemPath = SettingsController.getSetting("SystemPath");
        }else {
            MessageProcessor.processMessage(-2, "Failed to load SystemPath", false);
            MessageProcessor.processMessage(1, "Applying default SystemPath", false);

        }
        ProgramController.systemRunPath = ProgramController.systemPathLetter + AutoSetup.systemWorkingPath + ProgramController.systemSubPath;
        //#endregion
        //#region UserPath
        
        return true;
    }

}
