package MainSystem;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

import Login.SwitchController;
import UserController.LoginUserController;
import UserController.MainSystemUserController;
import UserController.MaintainUserController;
import UserController.SecondaryUserController;
import UserController.UserListController;
import assets.CustomScanner;
import assets.Logo;
import messageHandler.ClearAllMessages;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;

public class AdministrativeFunctions {
    private static final String ADMIN = "Admin";
	private static final String USER = "User: ";
	private static final String ACCOUNT2 = "Account";
	private static final String TARGET_ACCOUNT = "Target Account: ";
	private static final String UNABLE_TO_FIND_USER = "Unable to find user [";
	private static Random gen = new Random();
	public static List<String>administrativeRequests = new ArrayList<>();
    public static List<String>administrativeRequestKeyWord = new ArrayList<>();
    public static List<String>administrativeRequestUser = new ArrayList<>();
    public static List<String>administrativeRequestedName = new ArrayList<>();
    public static List<String>administrativeRequestFull = new ArrayList<>();
    public static List<Integer>administrativeRequestID = new ArrayList<>();
    public static List<String>accountRequestNamePool = new ArrayList<>();
    public static int requestsMade = 0;
    public static int updateRequestsMade(){
        requestsMade = administrativeRequests.size();
        return requestsMade;
    }

    public static void AdministrativeMenu(){
        updateRequestsMade();
        Logo.displayLogo();
        System.out.println("Welcome to the Solar Administrative Menu User: " + MainSystemUserController.GetProperty("AccountName"));
        System.out.println("[CREATE]:  Create a User");
        System.out.println("[DELETE]:  Delete a User");
        System.out.println("[REQUESTS]: View User Requests; Current Request Count: [" + requestsMade + "]");
        System.out.println("[CHANGE]:  Change a User Pass");
        System.out.println("[ENABLE]:  Enable an Account");
        System.out.println("[PASSFLAG]: Set Passflag for an account");
        System.out.println("[LIST]: List All Registered Users");
        System.out.println("[DISABLE]: Disable a User Account");
        System.out.println("[TAX]: Tax Percentage adjustment; Current Tax Percentage: " + SettingsController.getSetting("TaxP") + "%");
        System.out.println("[CLS]:     Clear Logs");
        System.out.println("[RETURN]:  Return to Main Menu");
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        try{
            String key = "PermissionLevel";
			if(option.equals("create") && Integer.parseInt(MainSystemUserController.GetProperty(key)) >= 8){
                MaintainUserController.createNewUser("Blank");
                AdministrativeMenu();
            }else if(option.equals("delete") && Integer.parseInt(MainSystemUserController.GetProperty(key)) >= 8){
                System.out.println("User to delete: ");
                String user = CustomScanner.nextLine();
                if(UserListController.SearchForUser(user)){
                   MaintainUserController.deleteUser(user);
                }else{
                    MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + user + "]", true);
                }
                AdministrativeMenu();
            }else if(option.equals("change") && Integer.parseInt(MainSystemUserController.GetProperty(key)) >= 8){
                System.out.println("Target Account for password change: ");
                String user = CustomScanner.nextLine();
                LoginUserController.adminUpdateUserPass(user);
                AdministrativeMenu();
            }else if(option.equals("requests")){
                resolutionAdvisory();
                AdministrativeMenu();
            }else if(option.equals("tax")){
                System.out.println("Current TaxP: " + SettingsController.getSetting("TaxP") + "%");
                System.out.println("New TaxP: ");
                try{
                    Double taxP = CustomScanner.nextDouble();
                    SettingsController.setSetting("TaxP", String.valueOf(taxP));
                    AdministrativeMenu();
                }catch(InputMismatchException e){
                    MessageProcessor.processMessage(-2, e.toString(), true);
                    AdministrativeMenu();
                }
            }else if(option.equals("list")){
            	Logo.displayLogo();
            	System.out.println("Users on List");
            	Logo.displayLine();
            	UserListController.loadUserList();
            	Enumeration keys = UserListController.userlist.keys();
            	while (keys.hasMoreElements()) {
                    String key1 = (String)keys.nextElement();
                    String value = (String)UserListController.userlist.get(key1);
                    System.out.println(key1 + ": " + value);
                    MessageProcessor.processMessage(1, key1 + ": " + value, false);
                }
            	String enter = CustomScanner.nextLine();
            	AdministrativeMenu();
            }else if(option.equals("passflag")){
                Logo.displayLogo();
                System.out.println(TARGET_ACCOUNT);
                String Account = CustomScanner.nextLine();
                MessageProcessor.processMessage(1, Account + " was targeted for PassFlag change", false);
                if(UserListController.SearchForUser(Account) == true){
                    LoginUserController.loadUserProperties(Account);
                    LoginUserController.setValue(Account, "PassFlag", "true");
                    MessageProcessor.processMessage(1, "PassFlag set for user: " + MaintainUserController.GetProperty("Username"), true);
                }else{
                    MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + Account + "]", true);
                }
                AdministrativeMenu();
            }else if(option.equals("cls")){
                System.out.println("Are you sure you want to delete the logs?");
                String choice = CustomScanner.nextLine().toLowerCase();
                if(choice.equals("y")|| choice.equals("yes")){
                    System.out.println("[WARNING]: This process is not reversible! Would you like to dump Logs into file first?");
                    String Choice = CustomScanner.nextLine().toLowerCase();
                    if(Choice.equals("yes") || Choice.equals("y")){
                        MessageProcessor.dumpAll();
                        ClearAllMessages.clearAll();
                        AdministrativeMenu();
                    }else{
                        ClearAllMessages.clearAll();
                        AdministrativeMenu();
                    }
                }else{
                    MessageProcessor.processMessage(-1, "User did not erase logs", true);
                    AdministrativeMenu();
                }
            }else if(option.equals("disable")){
                disableAnAccount(); 
                AdministrativeMenu();
            }else if(option.equals("enable")){
                enableAnAccount();
                AdministrativeMenu();
            }else if(option.equals("return")){
                MainMenu.mainMenu();
            }else{
                if(Integer.parseInt(MaintainUserController.GetProperty(key)) >= 8){
                    MessageProcessor.processMessage(-1, "Invalid option, try again!", true);
                }else{
                    MessageProcessor.processMessage(-1, SwitchController.focusUser + " does not have the proper permissions for this function. Please return to Main Menu!", true);
                }
                AdministrativeMenu();
            }
        }catch(NumberFormatException e){
            MessageProcessor.processMessage(-2, e.toString() + " [E1T0]", true);
            MessageProcessor.processMessage(-1, "An Error Occured with the Administrative Menu... Reloading Menu", true);
            AdministrativeMenu();
        }
    }

    public static boolean resolutionAdvisory() {
        if(updateRequestsMade() == 0){
            MessageProcessor.processMessage(-1, "No Requests have been made", true);
            return false;
        }
        Logo.displayLogo();
        System.out.println("Resolution Advisory");
        Logo.displayLine();
        int size = updateRequestsMade();
        MessageProcessor.processMessage(1, "Current Requests: ", false);
        for(int i = 0; i < administrativeRequests.size(); i++){
            System.out.println(size + ". " + administrativeRequests.get(i));
            MessageProcessor.processMessage(1, size + ". " + administrativeRequests.get(i) + "[" + administrativeRequestID.get(i) + "]", false);
        }
        try{
            int option = CustomScanner.nextInt();
            option--;
            Logo.displayLogo();
            System.out.println("Account Name: ");
            Logo.displayLine();
            System.out.println(size + ". " + administrativeRequests.get(option));
            String user = CustomScanner.nextLine();
            if(administrativeRequestKeyWord.get(option).contains("Permissions")){
                SecondaryUserController.adjPermLev(user);
                administrativeRequests.remove(option);
                administrativeRequestFull.remove(option);
                administrativeRequestID.remove(option);
                administrativeRequestKeyWord.remove(option);
                administrativeRequestUser.remove(option);   
                administrativeRequestedName.remove(option);
            }else if(administrativeRequestKeyWord.get(option).contains("new Account")){
                MaintainUserController.createNewUser(administrativeRequestedName.get(option));
                administrativeRequests.remove(option);
                administrativeRequestFull.remove(option);
                administrativeRequestID.remove(option);
                administrativeRequestKeyWord.remove(option);
                administrativeRequestUser.remove(option); 
                administrativeRequestedName.remove(option);
                AdministrativeMenu();
            }else if(administrativeRequestKeyWord.get(option).contains("Change Account Name")){
                System.out.println("Target Username: ");
                String user1 = administrativeRequestUser.get(option);
                System.out.println(user);
                System.out.println("New Account Name: ");
                String accountName = administrativeRequestedName.get(option);
                System.out.println(accountName);
                administrativeRequests.remove(option);
                administrativeRequestFull.remove(option);
                administrativeRequestID.remove(option);
                administrativeRequestKeyWord.remove(option);
                administrativeRequestUser.remove(option);
                administrativeRequestedName.remove(option);
                MaintainUserController.updateAccountName(user1, accountName);
            }else {
                MessageProcessor.processMessage(-1, "No Resolutions Available", true);
                AdministrativeMenu();
            }
        }catch(InputMismatchException e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            return false;
        }

        //if(request.contains("Permissions")){

        //}
        return true;
    }

    private static boolean viewAccountList() {
        if(accountRequestNamePool.isEmpty()){
            MessageProcessor.processMessage(-1, "No Account Requests have been made", true);
            return false;
        }else{
            int selection = 0;
            Logo.displayLogo();
            System.out.println("Account creation requests");
            Logo.displayLine();
            for(int i = 0; i < accountRequestNamePool.size(); i++){
                selection++;
                System.out.println(selection + ". " + accountRequestNamePool.get(i));
            }
            try{
                int choice = CustomScanner.nextInt();
                choice -- ;
                MaintainUserController.createNewUser(accountRequestNamePool.get(choice));
                return true;
            }catch(InputMismatchException e){
                MessageProcessor.processMessage(-2, e.toString(), true);
                viewAccountList();
            }
            return true;
        }
    }

    private static boolean enableAnAccount() { 
        Logo.displayLogo();
        System.out.println("Enable An Account");
        Logo.displayLine();
        System.out.println(TARGET_ACCOUNT);
        String account = CustomScanner.nextLine();
        MessageProcessor.processMessage(1, account + " was enabled", false);
        if(UserListController.SearchForUser(account)){
            LoginUserController.setValue(account, ACCOUNT2, "Enabled");
            MessageProcessor.processMessage(1, USER + SecondaryUserController.getUserProp("Username") + " Account was Enabled", true);
            LoginUserController.setValue(account, "PassFlag", "true");
        }else{
            MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + account + "]", true);
        }
        AdministrativeMenu();
        return true;
    }

    public static boolean enableAdminAccount() {
        if(UserListController.SearchForUser(ADMIN)){
            LoginUserController.setValue(ADMIN, ACCOUNT2, "Enabled");
            MessageProcessor.processMessage(1, USER + ADMIN + " Account was Enabled", true);
            LoginUserController.setValue(ADMIN, "PassFlag", "true");
            return true;
        }else{
            MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + ADMIN + "]", true);
            return false;
        }
    }

    private static boolean disableAnAccount() {
        Logo.displayLogo();
        System.out.println("Disable An Account");
        Logo.displayLine();
        System.out.println(TARGET_ACCOUNT);
        String account = CustomScanner.nextLine();
        MessageProcessor.processMessage(1, account + " was disabled", false);
        if(UserListController.SearchForUser(account)){
            if(!account.equals(ADMIN)){
                LoginUserController.setValue(account, ACCOUNT2, "Disabled");
                MessageProcessor.processMessage(1, USER + LoginUserController.getProperty(account, "Username") + " Account was Disabled", true);
                return true;
            }else {
            	MessageProcessor.processMessage(-1, "CANNOT Disable Admin Account", true);
            	return false;
            }
        }else{
            MessageProcessor.processMessage(-1, UNABLE_TO_FIND_USER + account + "]", true);
            return false;
        }
    }

    public static boolean newRequest(String focusUser, String request, String description, String newAccountName){
        int requestID = requestIDGenerator();
        administrativeRequestedName.add(newAccountName);
        administrativeRequestID.add(requestID);
        administrativeRequestUser.add(focusUser);
        administrativeRequestKeyWord.add(request);
        administrativeRequests.add(description);
        administrativeRequestFull.add("[" + focusUser + "] is requesting [" + request + "]...Full Description: " + description + "[Request ID: " + requestID + "]");
        MessageProcessor.processMessage(1, "Request saved successfully, an Administrator will review the request as soon as possible. [Request ID: " + requestID + "]", true);
        return true;
    }

    private static int requestIDGenerator() {
        
        return gen.nextInt(99999);
    }

    public static boolean removeRequest(){
        return true;
    }
}
