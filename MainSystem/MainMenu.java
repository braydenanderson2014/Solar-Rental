package MainSystem;

import Login.SwitchController;
import PointofSale.POSMenu;
import UserController.MainSystemUserController;
import assets.CustomScanner;
import assets.Logo;
import assets.Notebook;
import messageHandler.AllMessages;
import messageHandler.ConsoleHandler;
import messageHandler.LogDump;
import messageHandler.MessageProcessor;

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
        System.out.println("[SET TEST]: Set a Test Message");
        System.out.println("[OFF]:  Log Off");
        System.out.println("[EXIT]: Exit the Program");
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        switch(option){
            case "pos":
                POSMenu.PointofSaleMenu();
                break;
            case "set test":
            System.out.println("Test Message: ");
            String newMessage = CustomScanner.nextLine();
            String newMessageT = newMessage;
            AllMessages.allMessages.add(newMessage);
            AllMessages.allMessagesT.add(newMessageT);
            AllMessages.visibleToConsole.add(true); // or false, depending on the visibility you want
            mainMenu();
            break;
            case "admin":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    AdministrativeFunctions.AdministrativeMenu();
                }else{
                    MessageProcessor.processMessage(-1, SwitchController.focusUser + " does not have the proper permissions to use this function", true);
                    mainMenu();
                }
                break;
            case "note":
                Notebook.notebookMenu();
                break;
            case "swi": 
                SwitchController.switchMenu(2);
                break;
            case "set":
                try{
                    Settings.settingsMenu();
                }catch(NumberFormatException e){
                    MessageProcessor.processMessage(-2, "Failed to access Settings Menu", true);
                    mainMenu();
                }
                break;
            case "help":
                MessageProcessor.processMessage(-2, "This option is not yet available! Check back on the next update!", true);
                mainMenu();
                break;
            case "off":
                SwitchController.removeCurrentUser(MainSystemUserController.GetProperty("Username"));
                Login.Login.loginScreen();
                break;
            case "exit":
                LogDump.DumpLog("all");
                System.exit(1);
                break;
            default:
                MessageProcessor.processMessage(-1, "Invalid Option, try again!", true);
                mainMenu();
                break;
        }
    }
}