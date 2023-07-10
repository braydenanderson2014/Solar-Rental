package PointofSale;

import java.util.ArrayList;
import java.util.List;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;

import Login.SwitchController;
import UserController.MainSystemUserController;
import messageHandler.ConsoleHandler;
import messageHandler.LogDump;
import messageHandler.MessageProcessor;

public class SalesMenu {
    public static List<String> ItemsOnInvoice = new ArrayList<>();
    public static List<Double> CurrentPricesOnInvoice = new ArrayList<>();
    public static List<Double> OrigPricesOnInvoice = new ArrayList<>();
    public static List<Boolean> isItemDiscounted = new ArrayList<>();
    public static double taxPercentage = 0;
    public static double taxDAmount = 0;
    public static double discountTotal = 0;
    public static double TotalAmount = 0;
    public static void TheSalesMenu() {
        Logo.displayLogo();
        System.out.println("Sales Menu");
        System.out.println("[ADD]: Add Manual Item to Invoice");
        System.out.println("[Search] Search for item or Category by id");
        System.out.println("[CAT]: Sales Catalogue");
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
        	System.out.println("[ADD] Add Item to MasterList");
            System.out.println("[ADDC] Add Category");
            System.out.println("[DEL]: Delete an Item from the MasterList");
            System.out.println("[DELC]: Remove a Category");
        }
        System.out.println("[APP]: Apply Discount");
        System.out.println("[REM]: Remove Item from Current Invoice");
        System.out.println("[CLS]: Clear All Items and Discounts from invoice");
        System.out.println("[VIEW]: View Items on Invoice");
        System.out.println("[PAY]: Pay For Items");
        System.out.println("[RET]: Return to POSMenu");
        System.out.println("[OFF]: Log Off");
        System.out.println("[EXIT]: Exit");
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        switch(option){
            case "add":
                addItem();
                break;
            case "pay":
                if(TotalAmount > 0 && !ItemsOnInvoice.isEmpty()){
                    SalesProcessor.ProcessSale();
                }else if(TotalAmount == 0 && !ItemsOnInvoice.isEmpty()){
                    System.out.println("Total Amount is \"0\" Are you sure you want to Process Payment?");
                    String options = CustomScanner.nextLine().toLowerCase();
                    if(options.equals("y") || options.equals("yes")){
                        SalesProcessor.ProcessSale();
                    }else{
                        MessageProcessor.processMessage(1, "User chose not to proceed with transaction", true);
                        TheSalesMenu();
                    }
                }
                break;
            case "view":
                viewInvoice();
                break;
            case "cat":
                boolean success = CategoriesManager.ListAllCat();
                if(!success){
                    MessageProcessor.processMessage(2, "No Categories to list at this time", success);
                    System.out.println(MessageProcessor.getMessages());
                }
                String Enter = CustomScanner.nextLine();
                MessageProcessor.processMessage(1, "[Enter]", false);
                TheSalesMenu();
                break;
            case "addc":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    System.out.println("Category Name?");
                    String Cat = CustomScanner.nextLine();
                    System.out.println("Category ID?");
                    String CatID = CustomScanner.nextLine();
                    CategoriesManager.AddCat(Cat, CatID);
                    TheSalesMenu();
                }
                break;
            case "adds":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    System.out.println("Sub-Category Name?");
                    String SubCat = CustomScanner.nextLine();
                    System.out.println("Sub-Category ID's?");
                    String SubCatID = CustomScanner.nextLine();
                    SubCategoriesManager.addSubCat(SubCat, SubCatID);
                    TheSalesMenu();

                }
                break;
            case "del":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    System.out.println("Category ID?: ");
                    String id = CustomScanner.nextLine().toLowerCase();
                    boolean idExists = CategoriesManager.checkID(id);
                    if(idExists){
                        boolean successful = CategoriesManager.removeCategoryByID(id);
                        if(successful){
                            MessageProcessor.processMessage(1, "A category was removed! [" + CategoriesManager.RetrieveLastCatbyID(id) + "]", true);
                        }else{
                            MessageProcessor.processMessage(-1, "Failed to remove Category [" + CategoriesManager.RetrieveLastCatbyID(id) + "]", true);
                        }
                    }
                }
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
                MessageProcessor.processMessage(-1, "Invalid option, try again", true);
                TheSalesMenu();
                break;
        }
    }

    private static boolean SearchForItem(int itemNum){
        return true;
    }

    private static boolean addItem(int item){
        return true;
    }

    private static void viewInvoice() {
        Logo.displayLogo();
        System.out.println("View Items On Invoice: ");
        Logo.displayLine();
        for(int i = 0; i < ItemsOnInvoice.size(); i ++){
            System.out.println("ITEM: " + ItemsOnInvoice.get(i) + "ORIGINAL PRICE: $" + OrigPricesOnInvoice.get(i));
            if(Boolean.TRUE.equals(isItemDiscounted.get(i))){
                System.out.println("DISCOUNTED?: " + isItemDiscounted.get(i) + " DISCOUNTED PRICE: $" + CurrentPricesOnInvoice.get(i));
            }
        }
        System.out.println("[PRESS ENTER TO CONTINUE]: ");
        String enter = CustomScanner.nextLine();
        TheSalesMenu();
    }

    private static void removeItem() {
        Logo.displayLogo();
        System.out.println("Remove Items On Invoice: ");
        Logo.displayLine();
        int choice = 0;
        for(int i = 0; i < ItemsOnInvoice.size(); i ++){
            System.out.println(choice + ". ITEM: " + ItemsOnInvoice.get(i) + "ORIGINAL PRICE: $" + OrigPricesOnInvoice.get(i));
            if(Boolean.TRUE.equals(isItemDiscounted.get(i))){
                System.out.println("DISCOUNTED?: " + isItemDiscounted.get(i) + " DISCOUNTED PRICE: $" + CurrentPricesOnInvoice.get(i));
            }
        }
        System.out.println("ITEM TO REMOVE: ");
        int yourChoice = CustomScanner.nextInt();
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
        String item = CustomScanner.nextLine();
        System.out.println("Price: $");
        double price = CustomScanner.nextDouble();
        ItemsOnInvoice.add(item);
        CurrentPricesOnInvoice.add(price);
        OrigPricesOnInvoice.add(price);
        isItemDiscounted.add(false);
        TheSalesMenu();
    }

}