package UserController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import Assets.customScanner;
import InstallManager.ProgramController;

import messageHandler.messageHandler;
public class SecondaryUserController{
    static String UserList = ProgramController.UserRunPath + "\\Users/Userlist.properties";
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
        TargetedAccount = customScanner.nextLine();
        return true;
    }

    public static boolean TargetAccount(String AccountToTarget){//target the account in the arguments.
        AccountToTarget = TargetedAccount;
        //todo: Check target against user list to verify user exists.
        return true;
    }

    public static boolean loadTargetedAccount(){ //Load the Users Properties.
        try (InputStream input = new FileInputStream(ProgramController.UserRunPath + "\\Users/" + TargetedAccount + ".properties")){
            userprop.load(input);
            messageHandler.HandleMessage(1, "UserProperties Loaded for User: " + TargetedAccount, true);
            messageHandler.HandleMessage(1, ProgramController.UserRunPath, false);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2,e.toString(), true);
            return false;
        }    
     }

    public static boolean saveTargetUserProps(){ //Save The properties to the users properties file.
        try (OutputStream output = new FileOutputStream(ProgramController.UserRunPath + "\\Users/" + TargetedAccount + ".properties")){
            userprop.store(output, null);
            messageHandler.HandleMessage(1, "UserProperties Saved!", false);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
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
        messageHandler.HandleMessage(1, key + ": " + prop + " was set to users properties. User: " + TargetedAccount, false);
        saveTargetUserProps();
        return true;
    }

    public static Boolean adjPermLev(String user) {
    	return true;
    }

    public static void loadUserproperties(String account) {
    }

}