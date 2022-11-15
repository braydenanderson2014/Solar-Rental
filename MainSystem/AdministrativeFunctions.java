package MainSystem;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

import Assets.Logo;
import Assets.customScanner;
import Login.SwitchController;
import UserController.LoginUserController;
import UserController.MainSystemUserController;
import UserController.MaintainUserController;
import UserController.SecondaryUserController;
import UserController.UserListController;
import messageHandler.ClearAllMessages;
import messageHandler.Console;
import messageHandler.ConsoleHandler;
import messageHandler.messageHandler;

public class AdministrativeFunctions {
    public static List<String>AdministrativeRequests = new ArrayList<>();
    public static List<String>AdministrativeRequestKeyWord = new ArrayList<>();
    public static List<String>AdministrativeRequestUser = new ArrayList<>();
    public static List<String>AdministrativeRequestedName = new ArrayList<>();
    public static List<String>AdministrativeRequestFull = new ArrayList<>();
    public static List<Integer>AdministrativeRequestID = new ArrayList<>();
    public static List<String>AccountRequestNamePool = new ArrayList<>();
    public static int requestsMade = 0;
    public static int updateRequestsMade(){
        requestsMade = AdministrativeRequests.size();
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
        String option = customScanner.nextLine().toLowerCase();
        try{
            if(option.equals("create") && Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                MaintainUserController.createNewUser("Blank");
                AdministrativeMenu();
            }else if(option.equals("delete") && Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                System.out.println("User to delete: ");
                String user = customScanner.nextLine();
                if(UserListController.SearchForUser(user) == true){
                   MaintainUserController.deleteUser(user);
                }else{
                    messageHandler.HandleMessage(-1, "Unable to find user [" + user + "]", true);
                }
                AdministrativeMenu();
            }else if(option.equals("change") && Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                System.out.println("Target Account for password change: ");
                String user = customScanner.nextLine();
                LoginUserController.adminUpdateUserPass(user);
                AdministrativeMenu();
            }else if(option.equals("requests")){
                resolutionAdvisory();
                AdministrativeMenu();
            }else if(option.equals("tax")){
                System.out.println("Current TaxP: " + SettingsController.getSetting("TaxP") + "%");
                System.out.println("New TaxP: ");
                try{
                    Double TaxP = customScanner.nextDouble();
                    SettingsController.setSetting("TaxP", String.valueOf(TaxP));
                    AdministrativeMenu();
                }catch(InputMismatchException e){
                    messageHandler.HandleMessage(-2, e.toString(), true);
                    AdministrativeMenu();
                }
            }else if(option.equals("list")){
            	Logo.displayLogo();
            	System.out.println("Users on List");
            	Logo.displayLine();
            	UserListController.loadUserList();
            	Enumeration keys = UserListController.userlist.keys();
            	while (keys.hasMoreElements()) {
                    String key = (String)keys.nextElement();
                    String value = (String)UserListController.userlist.get(key);
                    System.out.println(key + ": " + value);
                    messageHandler.HandleMessage(1, key + ": " + value, false);
                }
            	String enter = customScanner.nextLine();
            	AdministrativeMenu();
            }else if(option.equals("passflag")){
                Logo.displayLogo();
                System.out.println("Target Account: ");
                String Account = customScanner.nextLine();
                messageHandler.HandleMessage(1, Account + " was targeted for PassFlag change", false);
                if(UserListController.SearchForUser(Account) == true){
                    LoginUserController.loadUserProperties(Account);
                    LoginUserController.setValue(Account, "PassFlag", "true");
                    messageHandler.HandleMessage(1, "PassFlag set for user: " + MaintainUserController.GetProperty("Username"), true);
                }else{
                    messageHandler.HandleMessage(-1, "Unable to find user [" + Account + "]", true);
                }
                AdministrativeMenu();
            }else if(option.equals("cls")){
                System.out.println("Are you sure you want to delete the logs?");
                String choice = customScanner.nextLine().toLowerCase();
                if(choice.equals("y")|| choice.equals("yes")){
                    System.out.println("[WARNING]: This process is not reversible! Would you like to dump Logs into file first?");
                    String Choice = customScanner.nextLine().toLowerCase();
                    if(Choice.equals("yes") || Choice.equals("y")){
                        messageHandler.dumpAll();
                        ClearAllMessages.clearAll();
                        AdministrativeMenu();
                    }else{
                        ClearAllMessages.clearAll();
                        AdministrativeMenu();
                    }
                }else{
                    messageHandler.HandleMessage(-1, "User did not erase logs", true);
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
                if(Integer.parseInt(MaintainUserController.GetProperty("PermissionLevel")) >= 8){
                    messageHandler.HandleMessage(-1, "Invalid option, try again!", true);
                }else{
                    messageHandler.HandleMessage(-1, SwitchController.focusUser + " does not have the proper permissions for this function. Please return to Main Menu!", true);
                }
                AdministrativeMenu();
            }
        }catch(NumberFormatException e){
            messageHandler.HandleMessage(-2, e.toString() + " [E1T0]", true);
            messageHandler.HandleMessage(-1, "An Error Occured with the Administrative Menu... Reloading Menu", true);
            AdministrativeMenu();
        }
    }

    public static boolean resolutionAdvisory() {
        if(updateRequestsMade() == 0){
            messageHandler.HandleMessage(-1, "No Requests have been made", true);
            return false;
        }
        Logo.displayLogo();
        System.out.println("Resolution Advisory");
        Logo.displayLine();
        int size = updateRequestsMade();
        messageHandler.HandleMessage(1, "Current Requests: ", false);
        for(int i = 0; i < AdministrativeRequests.size(); i++){
            System.out.println(size + ". " + AdministrativeRequests.get(i));
            messageHandler.HandleMessage(1, size + ". " + AdministrativeRequests.get(i) + "[" + AdministrativeRequestID.get(i) + "]", false);
        }
        try{
            int option = customScanner.nextInt();
            option--;
            Logo.displayLogo();
            System.out.println("Account Name: ");
            Logo.displayLine();
            System.out.println(size + ". " + AdministrativeRequests.get(option));
            String user = customScanner.nextLine();
            if(AdministrativeRequestKeyWord.get(option).contains("Permissions")){
                SecondaryUserController.adjPermLev(user);
                AdministrativeRequests.remove(option);
                AdministrativeRequestFull.remove(option);
                AdministrativeRequestID.remove(option);
                AdministrativeRequestKeyWord.remove(option);
                AdministrativeRequestUser.remove(option);   
                AdministrativeRequestedName.remove(option);
            }else if(AdministrativeRequestKeyWord.get(option).contains("new Account")){
                MaintainUserController.createNewUser(AdministrativeRequestedName.get(option));
                AdministrativeRequests.remove(option);
                AdministrativeRequestFull.remove(option);
                AdministrativeRequestID.remove(option);
                AdministrativeRequestKeyWord.remove(option);
                AdministrativeRequestUser.remove(option); 
                AdministrativeRequestedName.remove(option);
                AdministrativeMenu();
            }else if(AdministrativeRequestKeyWord.get(option).contains("Change Account Name")){
                System.out.println("Target Username: ");
                String User = AdministrativeRequestUser.get(option);
                System.out.println(user);
                System.out.println("New Account Name: ");
                String AccountName = AdministrativeRequestedName.get(option);
                System.out.println(AccountName);
                AdministrativeRequests.remove(option);
                AdministrativeRequestFull.remove(option);
                AdministrativeRequestID.remove(option);
                AdministrativeRequestKeyWord.remove(option);
                AdministrativeRequestUser.remove(option);
                AdministrativeRequestedName.remove(option);
                MaintainUserController.updateAccountName(User, AccountName);
            }else {
                messageHandler.HandleMessage(-1, "No Resolutions Available", true);
                AdministrativeMenu();
            }
        }catch(InputMismatchException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return false;
        }

        //if(request.contains("Permissions")){

        //}
        return true;
    }

    private static boolean viewAccountList() {
        if(AccountRequestNamePool.size() <= 0){
            messageHandler.HandleMessage(-1, "No Account Requests have been made", true);
            return false;
        }else{
            int selection = 0;
            Logo.displayLogo();
            System.out.println("Account creation requests");
            Logo.displayLine();
            for(int i = 0; i > AccountRequestNamePool.size(); i++){
                selection++;
                System.out.println(selection + ". " + AccountRequestNamePool.get(i));
            }
            try{
                int choice = customScanner.nextInt();
                choice -- ;
                MaintainUserController.createNewUser(AccountRequestNamePool.get(choice));
                return true;
            }catch(InputMismatchException e){
                messageHandler.HandleMessage(-2, e.toString(), true);
                viewAccountList();
            }
            return true;
        }
    }

    private static boolean enableAnAccount() { 
        Logo.displayLogo();
        System.out.println("Enable An Account");
        Logo.displayLine();
        System.out.println("Target Account: ");
        String Account = customScanner.nextLine();
        messageHandler.HandleMessage(1, Account + " was enabled", false);
        if(UserListController.SearchForUser(Account) == true){
            LoginUserController.setValue(Account, "Account", "Enabled");
            messageHandler.HandleMessage(1, "User: " + SecondaryUserController.getUserProp("Username") + " Account was Enabled", true);
            LoginUserController.setValue(Account, "PassFlag", "true");
        }else{
            messageHandler.HandleMessage(-1, "Unable to find user [" + Account + "]", true);
        }
        AdministrativeMenu();
        return true;
    }

    public static boolean enableAdminAccount() {
        if(UserListController.SearchForUser("Admin")){
            LoginUserController.setValue("Admin", "Account", "Enabled");
            messageHandler.HandleMessage(1, "User: " + "Admin" + " Account was Enabled", true);
            LoginUserController.setValue("Admin", "PassFlag", "true");
            return true;
        }else{
            messageHandler.HandleMessage(-1, "Unable to find user [" + "Admin" + "]", true);
            return false;
        }
    }

    private static boolean disableAnAccount() {
        Logo.displayLogo();
        System.out.println("Disable An Account");
        Logo.displayLine();
        System.out.println("Target Account: ");
        String Account = customScanner.nextLine();
        messageHandler.HandleMessage(1, Account + " was disabled", false);
        if(UserListController.SearchForUser(Account)){
            if(!Account.equals("Admin")){
                LoginUserController.setValue(Account, "Account", "Disabled");
                messageHandler.HandleMessage(1, "User: " + LoginUserController.getProperty(Account, "Username") + " Account was Disabled", true);
                return true;
            }else {
            	messageHandler.HandleMessage(-1, "CANNOT Disable Admin Account", true);
            	return false;
            }
        }else{
            messageHandler.HandleMessage(-1, "Unable to find user [" + Account + "]", true);
            return false;
        }
    }

    public static boolean newRequest(String focusUser, String request, String Description, String NewAccountName){
        int RequestID = RequestIDGenerator();
        AdministrativeRequestedName.add(NewAccountName);
        AdministrativeRequestID.add(RequestID);
        AdministrativeRequestUser.add(focusUser);
        AdministrativeRequestKeyWord.add(request);
        AdministrativeRequests.add(Description);
        AdministrativeRequestFull.add("[" + focusUser + "] is requesting [" + request + "]...Full Description: " + Description + "[Request ID: " + RequestID + "]");
        messageHandler.HandleMessage(1, "Request saved successfully, an Administrator will review the request as soon as possible. [Request ID: " + RequestID + "]", true);
        return true;
    }

    private static int RequestIDGenerator() {
        Random gen = new Random();
        return gen.nextInt(99999);
    }

    public static boolean removeRequest(){
        return true;
    }
}
