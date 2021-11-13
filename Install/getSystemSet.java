package Install;

import java.io.File;

import messageHandler.messageHandler;

public class getSystemSet {
private static String path = installManager.getSystemPath();
private static String tempPath;
    public static boolean populateSystem() {
        tempPath = path + "";
        messageHandler.HandleMessage(1, "File To Read: " + tempPath);
        File file = new File(tempPath);
        if(file.exists()){
            messageHandler.HandleMessage(1, "Now Reading From File: " + tempPath + ", Status: Successfully Found");
        }else{
            messageHandler.HandleMessage(-2, "PATH NOT FOUND at " + tempPath);
            messageHandler.HandleMessage(-1, "Changing to first time setup");
            FirstTimeController.updateFirstTime(true);
            return false;
        }
        return true;
    }
    
}
