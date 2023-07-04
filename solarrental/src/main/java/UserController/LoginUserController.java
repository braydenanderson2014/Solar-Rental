package UserController;
import java.io.*;
import java.util.Properties;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;
import com.solarrental.assets.Notebook;

import InstallManager.ProgramController;
import MainSystem.SettingsController;
import messageHandler.AllMessages;
import messageHandler.MessageProcessor;
public class LoginUserController {
    static String UserProperties = ProgramController.userRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.userRunPath + "\\Users/";
    static int FailedAttemptsLOMG = 0;
    static byte passChangeAttempts = 0;
    public static boolean passFlag = false;
    public static Properties userprop = new Properties();
    private static boolean loadUserlist(){
        return UserListController.loadUserList();
    }

    public static boolean loadUserProperties(String User){
        loadUserlist();
        if(UserListController.SearchForUser(User)){
            UserProperties = UserProperties2 + User + ".properties";
            try (InputStream input = new FileInputStream(UserProperties)){
                userprop.load(input);
                MessageProcessor.processMessage(1, "User Profile Loaded, Ready for Login Functions", false);
                return true;
            }catch(IOException e){
                MessageProcessor.processMessage(-2, e.toString(), true);
                MessageProcessor.processMessage(-1, "Unable to load User Profile", true);
                return false;
            }
        }
		MessageProcessor.processMessage(-1, "Unable to find User on Userlist.", true);
		return false;
    }

    public static boolean saveUserProperties(String User){
        loadUserlist();
        boolean success = UserListController.SearchForUser(User);
        if(success){
            UserProperties = UserProperties2 + User + ".properties";
            try (OutputStream output = new FileOutputStream(UserProperties)){
                userprop.store(output, null);
                MessageProcessor.processMessage(1, "User Profile Saved! LoginUserController", false);
                return true;
            }catch(IOException e){
                MessageProcessor.processMessage(-2, e.toString(), true);
                return false;
            }
        }
		MessageProcessor.processMessage(-1, "User Not Found: LoginUserController: SaveUserProperties", false);
		return false;
    }

    public static boolean checkPassword(String User, String Pass){
        loadUserlist();
        if(UserListController.SearchForUser(User)){
            if(checkUserProfileFile(User)){
                loadUserProperties(User);
                if(SearchForKey("Password")){
                    if(getProperty("Password").equals(Pass)){
                        if(SearchForKey("Account")){
                            if(getProperty("Account").equals("Enabled")){
                                int logins = Integer.parseInt(getProperty("SuccessfulLogins"));
                                logins ++;
                                setValue(User, "SuccessfulLogins", String.valueOf(logins));
                                setValue(User, "LastLogin", AllMessages.dTime);

                                if(Boolean.parseBoolean(getProperty("PassFlag"))){
                                    ChangePass(User);
                                    Notebook.currentNoteName = null;
                                    Notebook.currentNotePath = null;
                                    Notebook.currentNote.clear();
                                    return true;
                                }else if(!Boolean.parseBoolean(getProperty("PassFlag"))){
                                    Notebook.currentNoteName = null;
                                    Notebook.currentNotePath = null;
                                    Notebook.currentNote.clear();
                                    return true;
                                }
                            }else if(getProperty("Account").equals("Disabled")){
                                MessageProcessor.processMessage(-1, "Account is Disabled", true);
                                return false;
                            }
                        }else if(!SearchForKey("Account")){
                            MessageProcessor.processMessage(-1, "Unable to locate Account Property, Repair needed for profile, Auto Account Disable Activating...", true);
                            return false;
                        }
                    }else{
                        MessageProcessor.processMessage(-1, "Invalid Username or Password.",true);
                        FailedAttemptsLOMG = Integer.parseInt(getProperty("FailedLoginAttempts"));
                        FailedAttemptsLOMG= FailedAttemptsLOMG ++;
                        setValue(User, "FailedLoginAttempts", String.valueOf(FailedAttemptsLOMG));
                        setValue(User, "AllTimeFailedLoginAttempts", String.valueOf(Integer.parseInt(getProperty("AllTimeFailedLoginAttempts")) +1));
                        int FailedAttempts = Integer.parseInt(getProperty("FailedLoginAttempts"));
                        if(FailedAttempts >= Integer.parseInt(SettingsController.getSetting("FailedAttempts"))){
                            setValue(User, "Account", "Disabled");
                        }
                        return false;
                    }
                }else if(!SearchForKey("Password")){
                    MessageProcessor.processMessage(-1, "Unable to locate Password Property, Repair needed for profile... Auto Account Disable Activating...", true);
                    return false;
                    //Auto Disable.
                }
            }else if(!checkUserProfileFile(User)){
                MessageProcessor.processMessage(-1, "Unable to locate User Profile.", true);
                MessageProcessor.processMessage(-2, "User is on Userlist, But the Profile is not able to found.", true);
                return false;
            }
        }else if(!UserListController.SearchForUser(User)){
            MessageProcessor.processMessage(-1, "User Not Found: LoginUserController: CheckPassword", true);
            return false;
        }
        return true;
    }

    public static boolean ChangePass(String User) {
        MessageProcessor.processMessage(1, "Password Change Initiated: ChangePass for Account: " + User, false);
        loadUserProperties(User);
        Logo.displayLogo();
        System.out.println("Old Password: ");
        String oldPass = CustomScanner.nextLine();
        if(oldPass.equals("back") || oldPass.equals("Back")){
            return false;
        }
		System.out.println("New Password: ");
		String newPass = CustomScanner.nextLine();
		if(newPass.equals("back") || newPass.equals("Back")){
		    return false;
		}
		System.out.println("Confirm New Password: ");
		String cNewPass = CustomScanner.nextLine();
		if(cNewPass.equals("back") || cNewPass.equals("Back")){
		    return false;
		}
		if(oldPass.equals(getProperty("Password"))){
		    if(cNewPass.equals(newPass)){
		        setValue(User, "Password", newPass);
		        setValue(User, "PassFlag", "false");
		        setValue(User, "LastPassChange", AllMessages.getTime());
		        MessageProcessor.processMessage(1, "Password Changed Sucessfully: ChangePass", true);
		        return true;
		    }
			MessageProcessor.processMessage(-1, "Passwords do not match, Try Again. Type \"Back\" to cancel", true);
			ChangePass(User);
		}else {
		    passChangeAttempts++;
		    if(passChangeAttempts >=3){
		        setValue(User, "Account", "Disabled");
		        MessageProcessor.processMessage(-1, "Account was Disabled due to too many Attempts", true);
		        return false;
		    }
			MessageProcessor.processMessage(-1, "Old Password was incorrect, Password Change Failed", true);
			return false;
		}
		return true;
    }
    public static boolean adminUpdateUserPass(String user){
    	setValue(user, "Password", "Solar");
    	setValue(user, "PassFlag", "true");
    	MessageProcessor.processMessage(1, "Password Update for User: " + user + "; Password: Solar", true);
        return true;
    }

    public static boolean setValue(String user, String key, String value){
        loadUserProperties(user);
        userprop.setProperty(key, value);
        saveUserProperties(user);
        return true;
    }

    private static boolean SearchForKey(String key){
        return userprop.containsKey(key);
        
    }

    private static String getProperty(String key){
        return userprop.getProperty(key);
    }
    public static String getProperty(String user, String key) {
    	loadUserProperties(user);
    	return userprop.getProperty(key);
    }	

    private static boolean checkUserProfileFile(String user) {
        UserProperties = UserProperties2 + user + ".properties";
        File file = new File(UserProperties);
        return file.exists();
    }
}