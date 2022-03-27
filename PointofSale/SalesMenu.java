package PointofSale;

import java.util.ArrayList;

import Assets.Logo;
import Assets.customScanner;
import Login.SwitchController;
import messageHandler.Console;
import messageHandler.LogDump;
import messageHandler.messageHandler;

public class SalesMenu {
    public static ArrayList<String> ItemsOnInvoice = new ArrayList<String>();
    public static ArrayList<Double> CurrentPricesOnInvoice = new ArrayList<Double>();
    public static ArrayList<Double> OrigPricesOnInvoice = new ArrayList<Double>();
    public static ArrayList<Boolean> isItemDiscounted = new ArrayList<Boolean>();
    public static double taxPercentage = 0;
    public static double taxDAmount = 0;
    public static double discountTotal = 0;
    public static double TotalAmount = 0;
    public static void TheSalesMenu() {
        Logo.displayLogo();
        System.out.println("Sales Menu");
        System.out.println("[ADD]: Add Item to Invoice");
        System.out.println("[CAT]: Sales Catalogue");
        System.out.println("[APP]: Apply Discount");
        System.out.println("[REM]: Remove Item from Current Invoice");
        System.out.println("[CLS]: Clear All Items and Discounts from invoice");
        System.out.println("[VIEW]: View Items on Invoice");
        System.out.println("[PAY]: Pay For Items");
        System.out.println("[RET]: Return to POSMenu");
        System.out.println("[OFF]: Log Off");
        System.out.println("[EXIT]: Exit");
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        switch(option){
            case "add":
                addItem();
            break;
            case "pay":
                if(TotalAmount > 0 && ItemsOnInvoice.size() > 0){
                    SalesProcessor.ProcessSale();
                }else if(TotalAmount == 0 && ItemsOnInvoice.size() > 0){
                    System.out.println("Total Amount is \"0\" Are you sure you want to Process Payment?");
                    String options = customScanner.nextLine().toLowerCase();
                    if(options.equals("y") || options.equals("yes")){
                        SalesProcessor.ProcessSale();
                    }else{
                        messageHandler.HandleMessage(1, "User chose not to proceed with transaction", true);
                        TheSalesMenu();
                    }
                }
            break;
            case "view":
                viewInvoice();
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
                messageHandler.HandleMessage(-1, "Invalid option, try again", true);
                TheSalesMenu();
            break;
        }
    }

    private static void viewInvoice() {
    }

    private static void removeItem() {
    }

    private static void addItem() {
    }

    public static void SalesCatalogue() {
    }

}
