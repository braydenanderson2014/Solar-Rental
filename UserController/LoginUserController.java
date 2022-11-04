package UserController;
import java.io.*;
import java.util.Properties;

//Custom Import
import Assets.Logo;
import Assets.customScanner;
import InstallManager.ProgramController;
import MainSystem.SettingsController;
import messageHandler.AllMessages;
import messageHandler.messageHandler;
public class LoginUserController {
    static String UserProperties = ProgramController.UserRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.UserRunPath + "\\Users/";
    static int FailedAttemptsLOMG = 0;
    static byte passChangeAttempts = 0;
    public static Properties userprop = new Properties();
    private static boolean loadUserlist(){
        boolean success = UserListController.loadUserList();
        return success;
    }

    public static boolean loadUserProperties(String User){
        loadUserlist();
        if(UserListController.SearchForUser(User) == true){
            UserProperties = UserProperties2 + User + ".properties";
            try (InputStream input = new FileInputStream(UserProperties)){
                userprop.load(input);
                messageHandler.HandleMessage(1, "User Profile Loaded, Ready for Login Functions", true);
                return true;
            }catch(IOException e){
                messageHandler.HandleMessage(-2, e.toString(), true);
                messageHandler.HandleMessage(-1, "Unable to load User Profile", false);
                return false;
            }
        }else{
            messageHandler.HandleMessage(-1, "Unable to find User on Userlist.", true);
            return false;
        }
    }

    public static boolean saveUserProperties(String User){
        loadUserlist();
        boolean success = UserListController.SearchForUser(User);
        if(success){
            UserProperties = UserProperties2 + User + ".properties";
            try (OutputStream output = new FileOutputStream(UserProperties)){
                userprop.store(output, null);
                messageHandler.HandleMessage(1, "User Profile Saved! LoginUserController", false);
                return true;
            }catch(IOException e){
                messageHandler.HandleMessage(-2, e.toString(), true);
                return false;
            }
        }else if(!success){
            messageHandler.HandleMessage(-1, "User Not Found: LoginUserController: SaveUserProperties", false);
            return false;
        }
        return false;
    }

    public static boolean checkPassword(String User, String Pass){
        loadUserlist();
        if(UserListController.SearchForUser(User) == true){
            if(checkUserProfileFile(User) == true){
                loadUserProperties(User);
                if(SearchForKey("Password") == true){
                    if(GetProperty("Password").equals(Pass)){
                        if(SearchForKey("Account") == true){
                            if(GetProperty("Account").equals("Enabled")){
                                int logins = Integer.parseInt(GetProperty("SuccessfulLogins"));
                                logins ++;
                                setValue(User, "SuccessfulLogins", String.valueOf(logins));
                                setValue(User, "s", "0");
                                if(Boolean.parseBoolean(GetProperty("PassFlag")) == true){
                                    ChangePass(User);
                                    return true;
                                }else if(Boolean.parseBoolean(GetProperty("PassFlag")) == false){
                                    return true;
                                }
                            }else if(GetProperty("Account").equals("Disabled")){
                                messageHandler.HandleMessage(-1, "Account is Disabled", true);
                                return false;
                            }
                        }else if(SearchForKey("Account") == false){
                            messageHandler.HandleMessage(-1, "Unable to locate Account Property, Repair needed for profile, Auto Account Disable Activating...", true);
                            return false;
                        }
                    }else{
                        messageHandler.HandleMessage(-1, "Invalid Username or Password.",true);
                        FailedAttemptsLOMG = Integer.parseInt(GetProperty("FailedLoginAttempts"));
                        FailedAttemptsLOMG= FailedAttemptsLOMG ++;
                        setValue(User, "FailedLoginAttempts", String.valueOf(FailedAttemptsLOMG));
                        setValue(User, "AllTimeFailedLoginAttempts", String.valueOf(Integer.parseInt(GetProperty("AllTimeFailedLoginAttempts")) +1));
                        int FailedAttempts = Integer.parseInt(GetProperty("FailedLoginAttempts"));
                        if(FailedAttempts >= Integer.parseInt(SettingsController.getSetting("FailedAttempts"))){
                            setValue(User, "Account", "Disabled");
                        }
                        return false;
                    }
                }else if(SearchForKey("Password") == false){
                    messageHandler.HandleMessage(-1, "Unable to locate Password Property, Repair needed for profile... Auto Account Disable Activating...", true);
                    return false;
                    //Auto Disable.
                }
            }else if(checkUserProfileFile(User) == false){
                messageHandler.HandleMessage(-1, "Unable to locate User Profile.", true);
                messageHandler.HandleMessage(-2, "User is on Userlist, But the Profile is not able to found.", true);
                return false;
            }
        }else if(UserListController.SearchForUser(User) == false){
            messageHandler.HandleMessage(-1, "User Not Found: LoginUserController: CheckPassword", true);
            return false;
        }
        return true;
    }

    public static boolean ChangePass(String User) {
        messageHandler.HandleMessage(1, "Password Change Initiated: ChangePass", false);
        loadUserProperties(User);
        Logo.displayLogo();
        System.out.println("Old Password: ");
        String oldPass = customScanner.nextLine();
        if(oldPass.equals("back") || oldPass.equals("Back")){
            return false;
        }else {
            System.out.println("New Password: ");
            String newPass = customScanner.nextLine();
            if(newPass.equals("back") || newPass.equals("Back")){
                return false;
            }else{
                System.out.println("Confirm New Password: ");
                String cNewPass = customScanner.nextLine();
                if(cNewPass.equals("back") || cNewPass.equals("Back")){
                    return false;
                }else{
                    if(oldPass.equals(GetProperty("Password"))){
                        if(cNewPass.equals(newPass)){
                            setValue(User, "Password", newPass);
                            setValue(User, "PassFlag", "false");
                            setValue(User, "LastPassChange", AllMessages.getTime());
                            messageHandler.HandleMessage(1, "Password Changed Sucessfully: ChangePass", true);
                            return true;
                        }else{
                            messageHandler.HandleMessage(-1, "Passwords do not match, Try Again. Type \"Back\" to cancel", true);
                            ChangePass(User);
                        }
                    }else {
                        passChangeAttempts++;
                        if(passChangeAttempts >=3){
                            setValue(User, "Account", "Disabled");
                            messageHandler.HandleMessage(-1, "Account was Disabled due to too many Attempts", true);
                            return false;
                        }else{
                            messageHandler.HandleMessage(-1, "Old Password was incorrect, Password Change Failed", true);
                            return false;
                        }
                    }
                    return true;
                }
            }
        }

    }
    public static boolean AdminUpdateUserPass(String User){
        return true;
    }

    public static boolean setValue(String User, String key, String value){
        loadUserProperties(User);
        userprop.setProperty(key, value);
        saveUserProperties(User);
        return true;
    }

    private static boolean SearchForKey(String Key){
        boolean exists = userprop.containsKey(Key);
        return exists;
    }

    private static String GetProperty(String Key){
        return userprop.getProperty(Key);
    }

    private static boolean checkUserProfileFile(String User) {
        UserProperties = UserProperties2 + User + ".properties";
        File file = new File(UserProperties);
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }
}