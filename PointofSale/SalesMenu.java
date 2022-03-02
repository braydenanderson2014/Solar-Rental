package PointofSale;

import Assets.Logo;
import Assets.customScanner;
import Login.SwitchController;
import messageHandler.Console;
import messageHandler.LogDump;
import messageHandler.messageHandler;

public class SalesMenu {

    public static void TheSalesMenu() {
        Logo.displayLogo();
        System.out.println("Sales Menu");
        System.out.println("[ADD]: Add Item to Invoice");
        System.out.println("[CAT]: Sales Catalogue");
        System.out.println("[APP]: Apply Discount");
        System.out.println("[REM]: Remove Item from Current Invoice");
        System.out.println("[CLS]: Clear All Items and Discounts from invoice");
        System.out.println("[RET]: Return to POSMenu");
        System.out.println("[OFF]: Log Off");
        System.out.println("[EXIT]: Exit");
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        switch(option){
            case "add":
                addItem();
            break;
            case "cat":
                SalesCatalogue();
            break;
            case "app":
                DiscountManager.DiscountMenu();
            break;
            case "rem":
                removeItem();
            break;
            case "ret":
                POSMenu.PointofSaleMenu();
            break;
            case "off":
                SwitchController.removeCurrentUser(SwitchController.focusUser);
            break;
            case "exit":
                LogDump.DumpLog("all");
                System.exit(1);
            break;
            default:
                messageHandler.HandleMessage(-1, "Invalid option, try again");
                TheSalesMenu();
            break;
        }
    }

    private static void removeItem() {
    }

    private static void addItem() {
    }

    public static void SalesCatalogue() {
    }

}
