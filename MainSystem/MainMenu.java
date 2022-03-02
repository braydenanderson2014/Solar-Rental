package MainSystem;

import Assets.Logo;
import Assets.customScanner;
import Login.SwitchController;
import PointofSale.POSMenu;
import UserController.UserController;
import messageHandler.Console;
import messageHandler.LogDump;
import messageHandler.messageHandler;

public class MainMenu{
    public static void mainMenu(){
        Logo.displayLogo();
        System.out.println("Welcome to Solar! User: " + SwitchController.focusUser);
        System.out.println("[POS]:  Point of Sale");
        if(Integer.parseInt(UserController.SearchForProp("PermissionLevel")) >= 8){
            System.out.println("[ADMIN]: Administrative Functions");
        }
        System.out.println("[NOTE]: Notebook ");
        System.out.println("[SWI]:  Switch User");
        System.out.println("[SET]:  Settings");
        System.out.println("[HELP]: Display Help Messages");
        System.out.println("[OFF]:  Log Off");
        System.out.println("[EXIT]: Exit the Program");
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        switch(option){
            case "pos":
                POSMenu.PointofSaleMenu();
            break;
            case "admin":
            if(Integer.parseInt(UserController.SearchForProp("PermissionLevel")) >= 8){
                AdministrativeFunctions.AdministrativeMenu();
            }else{
                messageHandler.HandleMessage(-1, SwitchController.focusUser + " does not have the proper permissions to use this function");
                mainMenu();
            }
            break;
            case "note":
                messageHandler.HandleMessage(-1, "This function [NOTE] has not yet been implemented.. Check back in a later update");
                mainMenu();
            break;
            case "swi": 
                SwitchController.switchMenu(2);
            break;
            case "set":
                Settings.SettingsMenu();
            break;
            case "help":
                messageHandler.HandleMessage(-2, "This option is not yet available! Check back on the next update!");
                mainMenu();
            break;
            case "off":
                SwitchController.removeCurrentUser(SwitchController.focusUser);
            break;
            case "exit":
                LogDump.DumpLog("all");
                System.exit(1);
            break;
            default:
                messageHandler.HandleMessage(-1, "Invalid Option, try again!");
                mainMenu();
            break;
        }
    }
}