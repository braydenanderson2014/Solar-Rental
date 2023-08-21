package messageHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import InstallManager.ProgramController;
import Login.SwitchController;
import MainSystem.AdministrativeFunctions;
import UserController.MaintainUserController;
import UserController.UserListController;

public class UserMessageHandler {
    private static String UserProperties = ProgramController.userRunPath + "\\Users/";
    private static String UserProperties2 = ProgramController.userRunPath + "\\Users/";
    public static List<String> userNotifications = new ArrayList<>();
    public static ArrayList<String> Contents = new ArrayList<>();
    public static boolean sendMessageToUser(String user, String message) {
        if(UserListController.SearchForUser(user)){
            if(CheckUserAccount(user)){
               MaintainUserController.loadUserProperties(user);
               String path = MaintainUserController.GetProperty("NotifyPath");
               if(MaintainUserController.GetProperty("NotifyPath").equals("NULL")){
                    path = ProgramController.userRunPath + "\\Users\\Notifications\\" + user + ".txt";
               }
                File file = new File(path);
                if(!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        MessageProcessor.processMessage(-2, "Failed to create Notification File for User: " + user, true);
                        return false;
                    }
                }
                try{
                    @SuppressWarnings("resource")
					BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
                    String line;
                    while((line = reader.readLine()) != null){
                        if(line.equals(message)){
                            MessageProcessor.processMessage(-2, "Message already Exists in Notification File for User: " + user, true);
                            reader.close();
                            return false;
                        }
                        if(!line.equals("")){
                            Contents.add(line);
                        }
                    }
                    reader.close();
                } catch (Exception e) {
                    MessageProcessor.processMessage(-2, "Failed to read Notification File for User: " + user, true);
                    return false;
                }
                try {
                    @SuppressWarnings("resource")
					BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                    Contents.removeIf(null);
                    for(String line : Contents){
                        writer.write(line);
                        writer.newLine();
                    }
                    writer.write(message);
                    writer.newLine();
                    writer.close();
                    return true;
                } catch (IOException e) {
                    MessageProcessor.processMessage(-2, "Failed to write to Notification File for User: " + user, true);
                    return false;
                }
            }
			MessageProcessor.processMessage(-1, "User Account Notifications cannot be found, Sending Request to admin for a new Account", true);
			AdministrativeFunctions.newRequest(user, "Notifications File", "User requesting Notifications Enabled", "NA");
			return false;
        }
		MessageProcessor.processMessage(-1, "Failed to find Target User: " + user, true);
		return false;
       
    }

    private static boolean CheckUserAccount(String user) {
        UserListController.loadUserList();
        if(UserListController.SearchForUser(user)){
            UserProperties = UserProperties2 + user + ".properties";
            File file = new File(UserProperties);
            return file.exists();
        }
		MessageProcessor.processMessage(-1, "Unable to find User on Userlist.", true);
		return false;
    }
    public static int checkUserMessages() {
    	Boolean isMessages = readFileContents();
    	MessageProcessor.processMessage(2, "User has Messages?: " + isMessages, true);
    	return userNotifications.size();
    }
    
    private static Boolean readFileContents() {
    	String path = ProgramController.userRunPath + "\\Users\\Notifications\\" + SwitchController.focusUser + ".txt";
    	File file = new File(path);
    	if(file.exists()) {
    		try {
    			BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
                String line;
                while((line = reader.readLine()) != null){
                    if(!line.equals("")){
                        userNotifications.add(line);
                    }
                }
                return true;
    		} catch(IOException e) {
    			StringWriter sw = new StringWriter();
    		    PrintWriter pw = new PrintWriter(sw);
    		    e.printStackTrace(pw);
    		    String stackTrace = sw.toString();

    		    MessageProcessor.processMessage(2, stackTrace, true);
    			return false;
    		}
    	}
		MessageProcessor.processMessage(-1, "No Notification File exists!", true);
		return false;
	}

	public static void readMessages() {
    	
    }
}
