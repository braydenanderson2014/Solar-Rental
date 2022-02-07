package Install;

import java.io.File;

import messageHandler.messageHandler;

public class installDirectories{
    private static boolean isFirstTime = FirstTimeController.checkFirstTime();
    private static String path = installManager.getPath();
    private static String tempPath = path;
    public static boolean checkIsFirstTime() {
        return isFirstTime;
    }
    public static boolean updateIsFirstTime(){
        return isFirstTime;
    }
    public static boolean installTheDirectoriesDamnit(){
        //#region Notebooks
        tempPath = tempPath + "\\ProgramFiles/Notebooks";
        messageHandler.HandleMessage(1, "Path: " + tempPath);
        File file = new File(tempPath);
        if(!file.exists()){
            file.mkdirs();
            messageHandler.HandleMessage(1, "Directory Created at " + tempPath);
        }else {
            messageHandler.HandleMessage(-1, "Directory already Exists at "+ tempPath);
        }
        //#endregion
        //#region Users
        tempPath = path + "\\Solar Program\\Users/Passwords";
        messageHandler.HandleMessage(1, "Path: " + tempPath);
        file = new File(tempPath);
        if(!file.exists()){
            file.mkdirs();
            messageHandler.HandleMessage(1, "Directory Created at " + tempPath);
        }else {
            messageHandler.HandleMessage(-1, "Directory already Exists at " + tempPath);
        }
        //#endregion
        //#region 
        
        //#endregion
        return true;
    }
}