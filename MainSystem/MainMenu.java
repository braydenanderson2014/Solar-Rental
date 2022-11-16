package MainSystem;

import Assets.Logo;
import Assets.customScanner;
import Login.SwitchController;
import PointofSale.POSMenu;
import UserController.MainSystemUserController;

import messageHandler.ConsoleHandler;
import messageHandler.LogDump;
import messageHandler.messageHandler;

public class MainMenu{
    public static void mainMenu(){
        MainSystemUserController.loadUserProperties(SwitchController.focusUser);
        if(MainSystemUserController.GetProperty("AccountName").equals("Blank")){
            //UserController.updateUserProfile(1);
        }
        Logo.displayLogo();
        System.out.println("Welcome to Solar! User: " + MainSystemUserController.GetProperty("AccountName"));
        System.out.println("[POS]:  Point of Sale");
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
            System.out.println("[ADMIN]: Administrative Functions");
        }
        System.out.println("[NOTE]: Notebook ");
        System.out.println("[SWI]:  Switch User");
        System.out.println("[SET]:  Settings");
        System.out.println("[HELP]: Display Help Messages");
        System.out.println("[OFF]:  Log Off");
        System.out.println("[EXIT]: Exit the Program");
        ConsoleHandler.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        switch(option){
            case "pos":
                POSMenu.PointofSaleMenu();
                break;
            case "admin":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    AdministrativeFunctions.AdministrativeMenu();
                }else{
                    messageHandler.HandleMessage(-1, SwitchController.focusUser + " does not have the proper permissions to use this function", true);
                    mainMenu();
                }
                break;
            case "note":
                messageHandler.HandleMessage(-1, "This function [NOTE] has not yet been implemented.. Check back in a later update", true);
                mainMenu();
                break;
            case "swi": 
                SwitchController.switchMenu(2);
                break;
            case "set":
                try{
                    Settings.SettingsMenu();
                }catch(NumberFormatException e){
                    messageHandler.HandleMessage(-2, "Failed to access Settings Menu", true);
                    mainMenu();
                }
                break;
            case "help":
                messageHandler.HandleMessage(-2, "This option is not yet available! Check back on the next update!", true);
                mainMenu();
                break;
            case "off":
                SwitchController.removeCurrentUser(MainSystemUserController.GetProperty("Username"));
                Login.Login.LoginScreen();
                break;
            case "exit":
                LogDump.DumpLog("all");
                System.exit(1);
                break;
            default:
                messageHandler.HandleMessage(-1, "Invalid Option, try again!", true);
                mainMenu();
                break;
        }
    }
}