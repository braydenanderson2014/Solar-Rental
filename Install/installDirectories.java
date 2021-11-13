package Install;

import java.io.File;

import messageHandler.messageHandler;

public class installDirectories{
    private static boolean isFirstTime = FirstTimeController.checkFirstTime();
    private static String path = installManager.getSystemPath();
    private static String tempPath = path;
    public static boolean checkIsFirstTime() {
        return isFirstTime;
    }
    public static boolean updateIsFirstTime(){
        return isFirstTime;
    }
    public static boolean installTheDirectoriesDammit(){
        //#region Notebooks
        tempPath = tempPath + "\\Solar Program Files/Notebooks";
        messageHandler.HandleMessage(1, "Path: " + tempPath);
        File file = new File(tempPath);
        if(!file.exists()){
            file.mkdirs();
            messageHandler.HandleMessage(1, "Directory Created at " + tempPath);
        }else {
            messageHandler.HandleMessage(-1, "Directory already Exits at "+ tempPath);
        }
        return true;
    }
}