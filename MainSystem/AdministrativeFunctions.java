package MainSystem;

import java.util.ArrayList;
import java.util.InputMismatchException;

import Assets.Logo;
import Assets.customScanner;
import Login.SwitchController;
import UserController.UserController;
import messageHandler.ClearAllMessages;
import messageHandler.Console;
import messageHandler.messageHandler;

public class AdministrativeFunctions {
    public static ArrayList<String> AdministrativeRequests = new ArrayList<String>();
    public static ArrayList<String> AccountRequestNamePool = new ArrayList<String>();
    public static int requestsMade = 0;
    public static int updateRequestsMade(){
        requestsMade = AdministrativeRequests.size();
        return AdministrativeRequests.size();
    }
    public static void AdministrativeMenu(){
        updateRequestsMade();
        Logo.displayLogo();
        System.out.println("Welcome to the Solar Administrative Menu User:" + UserController.getUserProp("AccountName"));
        System.out.println("[CREATE]:  Create a User");
        System.out.println("[DELETE]:  Delete a User");
        System.out.println("[REQUESTS]: View User Requests; Current Request Count: [" + requestsMade + "]");
        System.out.println("[CHANGE]:  Change a User Pass");
        System.out.println("[ENABLE]:  Enable an Account");
        System.out.println("[PASSFLAG]: Set Passflag for an account");
        System.out.println("[DISABLE]: Disable a User Account");
        System.out.println("[TAX]: Tax Percentage adjustment; Current Tax Percentage: " + SettingsController.getSetting("TaxP") + "%");
        System.out.println("[CLS]:     Clear Logs");
        System.out.println("[RETURN]:  Return to Main Menu");
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        if(option.equals("create") && Integer.parseInt(UserController.SearchForProp("PermissionLevel")) >= 8){
            UserController.createNewUser();
            AdministrativeMenu();
        }else if(option.equals("delete") && Integer.parseInt(UserController.SearchForProp("PermissionLevel")) >= 8){
            System.out.println("User to delete: ");
            String user = customScanner.nextLine();
            if(UserController.SearchForUser(user) == true){
                UserController.deleteUser(user);
            }else{
                messageHandler.HandleMessage(-1, "Unable to find user [" + user + "]", true);
            }
            AdministrativeMenu();
        }else if(option.equals("change") && Integer.parseInt(UserController.SearchForProp("PermissionLevel")) >= 8){
            System.out.println("Target Account for password change: ");
            String user = customScanner.nextLine();
            Settings.ChangePass(user, 2,1);
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
        }else if(option.equals("passflag")){
            Logo.displayLogo();
            System.out.println("Target Account: ");
            String Account = customScanner.nextLine();
            messageHandler.HandleMessage(1, Account + " was targeted for PassFlag change", false);
            if(UserController.SearchForUser(Account) == true){
                UserController.loadUserproperties(Account);
                UserController.SetUserProp(UserController.getUserProp("Username"), "PassFlag", "true");
                messageHandler.HandleMessage(1, "PassFlag set for user: " + UserController.getUserProp("Username"), true);
                UserController.loadUserproperties(SwitchController.focusUser);
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
            if(Integer.parseInt(UserController.SearchForProp("PermissionLevel")) >= 8){
                messageHandler.HandleMessage(-1, "Invalid option, try again!", true);
            }else{
                messageHandler.HandleMessage(-1, SwitchController.focusUser + " does not have the proper permissions for this function. Please return to Main Menu!", true);
            }
            AdministrativeMenu();
        }
    }

    private static boolean resolutionAdvisory() {
        if(updateRequestsMade() == 0){
            messageHandler.HandleMessage(-1, "No Requests have been made", true);
            return false;
        }
        Logo.displayLogo();
        Logo.displayLine();
        int size = updateRequestsMade();
        messageHandler.HandleMessage(1, "Current Requests: ", false);
        for(int i = 0; i < AdministrativeRequests.size(); i++){
            System.out.println(size + ". " + AdministrativeRequests.get(i));
            messageHandler.HandleMessage(1, size + ". " + AdministrativeRequests.get(i), false);
        }
        try{
            int option = customScanner.nextInt();
            option--;
            System.out.println("Account Name: ");
            String user = customScanner.nextLine();
            if(AdministrativeRequests.get(option).contains("Permissions")){
                UserController.adjPermLev(user);
                AdministrativeRequests.remove(option);
                //}else if(AdministrativeRequests.get(option).contains("")){}
            }else if(AdministrativeRequests.get(option).contains("new Account")){
                viewAccountList();
                AdministrativeRequests.remove(option);
                AdministrativeMenu();
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
                UserController.createNewUser(AccountRequestNamePool.get(choice));
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
        if(UserController.SearchForUser(Account) == true){
            UserController.loadUserproperties(Account);
            UserController.SetUserProp(UserController.getUserProp("Username"), "Account", "Enabled");
            messageHandler.HandleMessage(1, "User: " + UserController.getUserProp("Username") + " Account was Enabled", true);
            UserController.loadUserproperties(SwitchController.focusUser);
        }else{
            messageHandler.HandleMessage(-1, "Unable to find user [" + Account + "]", true);
        }
        AdministrativeMenu();
        return true;
    }
    public static boolean enableAdminAccount() {
        Logo.displayLogo();
        String Account = "Admin";
        messageHandler.HandleMessage(1, Account + " was enabled", false);
        if(UserController.SearchForUser(Account) == true){
            UserController.loadUserproperties(Account);
            UserController.SetUserProp(UserController.getUserProp("Username"), "Account", "Enabled");
            messageHandler.HandleMessage(1, "User: " + UserController.getUserProp("Username") + " Account was Enabled", true);
            UserController.SetUserProp(UserController.getUserProp("Username"), "PassFlag", "true");
        }else{
            messageHandler.HandleMessage(-1, "Unable to find user [" + Account + "]", true);
        }
        return true;
    }

    private static boolean disableAnAccount() {
        Logo.displayLogo();
        System.out.println("Disable An Account");
        Logo.displayLine();
        System.out.println("Target Account: ");
        String Account = customScanner.nextLine();
        messageHandler.HandleMessage(1, Account + " was disabled", false);
        if(UserController.SearchForUser(Account) == true){
            UserController.loadUserproperties(Account);
            UserController.SetUserProp(UserController.getUserProp("Username"), "Account", "Disabled");
            messageHandler.HandleMessage(1, "User: " + UserController.getUserProp("Username") + " Account was Disabled", true);
            UserController.loadUserproperties(SwitchController.focusUser);
        }else{
            messageHandler.HandleMessage(-1, "Unable to find user [" + Account + "]", true);
        }
        AdministrativeMenu();
        return true;
    }

    public static boolean newRequest(String focusUser, String request){
        AdministrativeRequests.add("[" +  focusUser + "] is requesting [" + request + "] changes to their account");
        messageHandler.HandleMessage(1, "Request saved successfully, an Administrator will review the request as soon as possible.", true);
        return true;
    }
    public static boolean removeRequest(){
        return true;
    }
}
