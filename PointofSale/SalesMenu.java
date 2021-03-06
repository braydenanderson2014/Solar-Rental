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
        System.out.println("[ADD]: Add Manual Item to Invoice");
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
                messageHandler.HandleMessage(-1, "This feature is not yet available", true);
                TheSalesMenu();
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
        Logo.displayLogo();
        System.out.println("View Items On Invoice: ");
        Logo.displayLine();
        for(int i = 0; i < ItemsOnInvoice.size(); i ++){
            System.out.println("ITEM: " + ItemsOnInvoice.get(i) + "ORIGINAL PRICE: $" + OrigPricesOnInvoice.get(i));
            if(isItemDiscounted.get(i) == true){
                System.out.println("DISCOUNTED?: " + isItemDiscounted.get(i) + " DISCOUNTED PRICE: $" + CurrentPricesOnInvoice.get(i));
            }
        }
        System.out.println("[PRESS ENTER TO CONTINUE]: ");
        String enter = customScanner.nextLine();
        TheSalesMenu();
    }

    private static void removeItem() {
        Logo.displayLogo();
        System.out.println("Remove Items On Invoice: ");
        Logo.displayLine();
        int choice = 0;
        for(int i = 0; i < ItemsOnInvoice.size(); i ++){
            System.out.println(choice + ". ITEM: " + ItemsOnInvoice.get(i) + "ORIGINAL PRICE: $" + OrigPricesOnInvoice.get(i));
            if(isItemDiscounted.get(i) == true){
                System.out.println("DISCOUNTED?: " + isItemDiscounted.get(i) + " DISCOUNTED PRICE: $" + CurrentPricesOnInvoice.get(i));
            }
        }
        System.out.println("ITEM TO REMOVE: ");
        int yourChoice = customScanner.nextInt();
        yourChoice--;
        ItemsOnInvoice.remove(yourChoice);
        OrigPricesOnInvoice.remove(yourChoice);
        isItemDiscounted.remove(yourChoice);
        CurrentPricesOnInvoice.remove(yourChoice);
        TheSalesMenu();
    }

    private static void addItem() {
        Logo.displayLogo();
        System.out.println("Add Item:");
        Logo.displayLine();
        System.out.println("ITEM: ");
        String item = customScanner.nextLine();
        System.out.println("Price: $");
        double price = customScanner.nextDouble();
        ItemsOnInvoice.add(item);
        CurrentPricesOnInvoice.add(price);
        OrigPricesOnInvoice.add(price);
        isItemDiscounted.add(false);
        TheSalesMenu();
    }
    

}
