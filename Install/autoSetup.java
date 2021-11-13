package Install;

import messageHandler.ErrorMessages;
import messageHandler.SystemMessages;
import messageHandler.messageHandler;

public class autoSetup{
    private static String autoPath = "C:\\Users\\Public\\Public Documents";
    private static String setPathLetter = "C:";
    private static String setPathDir = "\\Users\\Public\\Public Documents";
    public autoSetup(){
        
    }

    public static void StartSetup() {
        messageHandler.HandleMessage(1, "Starting Auto Setup...");
        System.out.println(SystemMessages.getLastMessage());
        messageHandler.HandleMessage(1, "Setting " + autoPath + " as System path...");
        installManager.setPath(setPathDir, setPathLetter);
        messageHandler.HandleMessage(1, "Creating System Launch Files...");
        boolean itWorked = InstallSystemSet.installSystemSets();
        if(itWorked){
            messageHandler.HandleMessage(1, "Installation Files Created Successfully!");
            System.out.println(SystemMessages.getLastMessage());
            installManager.installMenu();
        }else if(!itWorked){
            messageHandler.HandleMessage(-2, "Setup Failed!");
            System.out.println(ErrorMessages.getLastMessage());
        }

    }
}