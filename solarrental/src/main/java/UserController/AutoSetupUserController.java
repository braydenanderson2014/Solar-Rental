package UserController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import InstallManager.ProgramController;
import messageHandler.MessageProcessor;

public class AutoSetupUserController {
    public static String UserProperties = ProgramController.userRunPath + "\\Users/Admin.properties";
    public static Properties userprop = new Properties();
    public static Boolean AutoCreateAdmin(){
        boolean success = checkFile("Admin");
        if(success){
            return !success;
        }
		File file = new File(UserProperties);
		if(!file.exists()){
		    try {
		        file.createNewFile();
		        success = PopulateUserProperties();
		        UserListController.addUserToList("Admin", 8);
		    } catch (Exception e) {
		    	StringWriter sw = new StringWriter();
		        PrintWriter pw = new PrintWriter(sw);
		        e.printStackTrace(pw);
		        String stackTrace = sw.toString();

		        MessageProcessor.processMessage(2, stackTrace, true);
		        MessageProcessor.processMessage(-2, e.toString(), true);
		        return false;
		    }
		}
		return success;
        
    }
    private static boolean PopulateUserProperties() {
        userprop.setProperty("Account", "Enabled");
        userprop.setProperty("AccountName", "ADMIN ACCOUNT");
        userprop.setProperty("AllTimeFailedLoginAttempts", "0");
        userprop.setProperty("FailedLoginAttempts", "0");
        userprop.setProperty("LastLogin", "Never");
        userprop.setProperty("LastPassChange", "Never");
        userprop.setProperty("PassExpires", "true");
        userprop.setProperty("PassFlag", "true");
        userprop.setProperty("Password", "SolarAdmin");
        userprop.setProperty("PermissionLevel", "8");
        userprop.setProperty("SuccessfulLogins", "0");
        userprop.setProperty("Username", "Admin");
        userprop.setProperty("NotifyPath", "NULL");
        userprop.setProperty("UserNotification", "Disabled");
        userprop.setProperty("NotepadPath", "NULL");
        saveUserProp();
        return false;
    }
    public static boolean saveUserProp(){
        try (OutputStream output = new FileOutputStream(UserProperties)){
            userprop.store(output, null);
            MessageProcessor.processMessage(1, "UserProperties Saved!", false);
            return true;
        }catch(IOException e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            return false;
        }
    }
    private static boolean checkFile(String string) {
        return false;
    }
}
