package PointofSale;

import java.util.ArrayList;

import Assets.Logo;
import Assets.customScanner;
import Login.SwitchController;
import MainSystem.MainMenu;
import UserController.MainSystemUserController;
import messageHandler.Console;
import messageHandler.messageHandler;

public class POSMenu {
    public static ArrayList<String> ItemsOnInvoice = new ArrayList<String>();
    public static ArrayList<Double> CurrentPricesOnInvoice = new ArrayList<Double>();
    public static ArrayList<Double> OrigPricesOnInvoice = new ArrayList<Double>();
    public static ArrayList<Boolean> isItemDiscounted = new ArrayList<Boolean>();    
    public static boolean updateArrays(){
        ItemsOnInvoice = SalesMenu.ItemsOnInvoice;
        CurrentPricesOnInvoice = SalesMenu.CurrentPricesOnInvoice;
        OrigPricesOnInvoice = SalesMenu.OrigPricesOnInvoice;
        isItemDiscounted = SalesMenu.isItemDiscounted;
        return true;
    }

    public static void PointofSaleMenu(){
        Logo.displayLogo();
        System.out.println("Welcome to the Solar Point of Sale Menu; User: " + SwitchController.focusUser);
        System.out.println("[SALE]: Sales Menu");
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
            System.out.println("[RET]: Returns Menu");
        }
        System.out.println("[APP]: Apply Discount Menu");
        System.out.println("[CAT]: Sales Catalogue");
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
            System.out.println("[ADDC]: Add Category");
            System.out.println("[ADDS]: Add Sub-Category");
            System.out.println("[DEL]: Remove a Category");
        }
        System.out.println("[OFF]: Log Off");
        System.out.println("[RETURN]: RETURN to Main Menu");
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        switch (option) {
            case "sale":
                SalesMenu.TheSalesMenu();
                break;
            case "ret":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >=8){
                    ReturnsMenu.TheReturnsMenu();
                }else{
                    messageHandler.HandleMessage(-1, SwitchController.focusUser + " does not have the proper permissions to use this function", true);
                    PointofSaleMenu();
                }
                break;
            case "addc":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    System.out.println("Category Name?");
                    String Cat = customScanner.nextLine();
                    System.out.println("Category ID?");
                    String CatID = customScanner.nextLine();
                    CategoriesManager.AddCat(Cat, CatID);
                    PointofSaleMenu();
                }
                break;
            case "adds":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    System.out.println("Sub-Category Name?");
                    String SubCat = customScanner.nextLine();
                    System.out.println("Sub-Category ID's?");
                    String SubCatID = customScanner.nextLine();
                    SubCategoriesManager.addSubCat(SubCat, SubCatID);
                    PointofSaleMenu();

                }
                break;
            case "del":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                    System.out.println("Category ID?: ");
                    String id = customScanner.nextLine().toLowerCase();
                    boolean idExists = CategoriesManager.checkID(id);
                    if(idExists){
                        boolean successful = CategoriesManager.removeCategoryByID(id);
                        if(successful){
                            messageHandler.HandleMessage(1, "A category was removed! [" + CategoriesManager.RetrieveLastCatbyID(id) + "]", true);
                        }else{
                            messageHandler.HandleMessage(-1, "Failed to remove Category [" + CategoriesManager.RetrieveLastCatbyID(id) + "]", true);
                        }
                    }
                }
                PointofSaleMenu();
                break;
            case "app":
                DiscountManager.DiscountMenu();
                break;
            case "cat":
                //Categories.SalesCatalogue();
                boolean success = CategoriesManager.ListAllCat();
                if(!success){
                    messageHandler.HandleMessage(2, "No Categories to list at this time", success);
                }
                System.out.println("Press Enter To Continue");
                String Enter = customScanner.nextLine();
                POSMenu.PointofSaleMenu();
                break;
            case "off":
                SwitchController.removeCurrentUser(SwitchController.focusUser);
                Login.Login.LoginScreen();
                break;
            case "return":
                MainMenu.mainMenu();
            default:
                messageHandler.HandleMessage(-1, "Invalid Option, Try again!" ,true);
                PointofSaleMenu();
                break;
        }
    }
}
