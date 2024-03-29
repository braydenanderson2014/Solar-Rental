package UserController;

import java.io.FileInputStream;
//import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import assets.CustomScanner;
import assets.Logo;

import InstallManager.ProgramController;
import messageHandler.MessageProcessor;
public class SecondaryUserController{
    static String UserList = ProgramController.userRunPath + "\\Users/Userlist.properties";
    public static String TargetedAccount;
    public static Properties userprop = new Properties();
    public static Properties userlist = new Properties();
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static boolean passFlag = false;
    public static boolean userExisted = false;

    public static boolean TargetAccount(){//Target an account from within the class.
        System.out.println("Target Account: ");
        TargetedAccount = CustomScanner.nextLine();
        return true;
    }

    public static boolean TargetAccount(String AccountToTarget){//target the account in the arguments.
        AccountToTarget = TargetedAccount;
        return UserListController.SearchForUser(TargetedAccount);
    }

    public static boolean loadTargetedAccount(){ //Load the Users Properties.
        try (InputStream input = new FileInputStream(ProgramController.userRunPath + "\\Users/" + TargetedAccount + ".properties")){
            userprop.load(input);
            MessageProcessor.processMessage(1, "UserProperties Loaded for User: " + TargetedAccount, true);
            MessageProcessor.processMessage(2, ProgramController.userRunPath, false);
            return true;
        }catch(IOException e){
            MessageProcessor.processMessage(-2,e.toString(), true);
            return false;
        }    
     }

    public static boolean saveTargetUserProps(){ //Save The properties to the users properties file.
        try (OutputStream output = new FileOutputStream(ProgramController.userRunPath + "\\Users/" + TargetedAccount + ".properties")){
            userprop.store(output, null);
            MessageProcessor.processMessage(1, "UserProperties Saved!", false);
            return true;
        }catch(IOException e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            return false;
        }
    }

    public static boolean doesUserPropExist(String key){//Checks to see if a certain key type exists in the user's properties.
        return userprop.containsKey(key);
    }

    public static String getUserProp(String key){//retrieves the value stored under the key specified in the argument
        return userprop.getProperty(key);
    }

    public static boolean setUserProp(String key, String prop){//sets a key value pair into the properties list.
        userprop.setProperty(key, prop);
        MessageProcessor.processMessage(1, key + ": " + prop + " was set to users properties. User: " + TargetedAccount, false);
        saveTargetUserProps();
        return true;
    }

    public static Boolean adjPermLev(String user) {
    	Logo.displayLogo();
    	MaintainUserController.loadUserProperties(user);
    	System.out.println("Current User : " + user);
    	System.out.println("Current Permission Level: " + MaintainUserController.GetProperty("PermissionLevel")); 
    	System.out.println("New Permission Level: ");
    	String permissionLevel = CustomScanner.nextLine();
    	try {
        	int permLevel = Integer.parseInt(permissionLevel);
        	MaintainUserController.setValue(user, "PermissionLevel", String.valueOf(permLevel));
        	return true;
    	} catch (Exception e) {
    		MessageProcessor.processMessage(-2, "Invalid Number, try again!", true);
    		StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
		    String stackTrace = sw.toString();

		    MessageProcessor.processMessage(2, stackTrace, true);
		    return false;
    	}
    }

    public static void loadUserproperties(String account) {
    }

}