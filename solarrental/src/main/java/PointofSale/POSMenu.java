package PointofSale;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import assets.CustomScanner;
import assets.Logo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Login.SwitchController;
import MainSystem.MainMenu;
import UserController.MainSystemUserController;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;

public class POSMenu {
    public static List<String> ItemsOnInvoice = new ArrayList<>();
    public static List<Double> CurrentPricesOnInvoice = new ArrayList<>();
    public static List<Double> OrigPricesOnInvoice = new ArrayList<>();
    public static List<Boolean> isItemDiscounted = new ArrayList<>();    
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
        System.out.println("[APP]: Apply Discount Menu");
        System.out.println("[CAT]: Sales Catalogue");
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
            System.out.println("[ADD] Add Item to MasterList ; Items: " + POSItem.itemObjects.size());
            System.out.println("[ADDC] Add Category");
            System.out.println("[DEL]: Delete an Item from the MasterList");
            System.out.println("[DELC]: Remove a Category");
            System.out.println("[RET]: Returns Menu");
        }
        System.out.println("[VIEW]: View Items on MasterList");
        System.out.println("[OFF]: Log Off");
        System.out.println("[RETURN]: RETURN to Main Menu");
        try {
        	ConsoleHandler.getConsole();
		}catch(IndexOutOfBoundsException e) {
			MessageProcessor.processMessage(-2, e.toString(), true);
			System.out.println(e.toString());
			System.out.println("FAILED TO LOAD CONSOLE");
		}
        
        String option = CustomScanner.nextLine().toLowerCase();
        switch (option) {
            case "sale":
                SalesMenu.TheSalesMenu();
                break;
            case "ret":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >=8){
                    ReturnsMenu.TheReturnsMenu();
                }else{
                    MessageProcessor.processMessage(-1, SwitchController.focusUser + " does not have the proper permissions to use this function", true);
                    PointofSaleMenu();
                }
                break;
            case "add":
                if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8){
                	createNewItem();
                }
                break;
            case "view":
                
                break;
            case "del":
            	
            	break;
            case "delc":
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
                PointofSaleMenu();
                break;
            case "app":
                DiscountManager.DiscountMenu();
                break;
            case "cat":
                //Categories.SalesCatalogue();
                boolean success = CategoriesManager.ListAllCat();
                if(!success){
                    MessageProcessor.processMessage(2, "No Categories to list at this time", success);
                }
                System.out.println("Press Enter To Continue");
                String Enter = CustomScanner.nextLine();
                POSMenu.PointofSaleMenu();
                break;
            case "off":
                SwitchController.removeCurrentUser(SwitchController.focusUser);
                //Login.Login.loginScreen();
                break;
            case "return":
                MainMenu.mainMenu();
                break;
            default:
                MessageProcessor.processMessage(-1, "Invalid Option, Try again!" ,true);
                PointofSaleMenu();
                break;
        }
    }
    
    public synchronized static void displayPointOfSaleMenu(Stage stage) {
        VBox layout = new VBox(10);

        // Display logo and welcome message
        // Logo.displayLogo(); // Adjust this line to display the logo in a JavaFX compatible way
        Label welcomeLabel = new Label("Welcome to the Solar Point of Sale Menu; User: " + SwitchController.focusUser);
        layout.getChildren().add(welcomeLabel);

        // Creating buttons for each menu option
        Button saleButton = new Button("[SALE]: Sales Menu");
        saleButton.setOnAction(e -> SalesMenu.UISalesMenu(stage)); // Adjust to open Sales Menu in JavaFX
        layout.getChildren().add(saleButton);
        
        Button app = new Button("[APP]: Apply Discount Menu");
        app.setOnAction(e -> DiscountManager.DiscountMenu());
        layout.getChildren().add(app);
        
        Button cat = new Button("[CAT]: Sales Catalogue");
        cat.setOnAction(e -> displayPointOfSaleMenu(stage));
        layout.getChildren().add(cat);
        
        
        // ... Similar buttons for other menu options ...

        if (Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 8) {
            // Buttons for admin-level options
        	Button add = new Button("[ADD]: Add Item to MasterList");
        	add.setOnAction(e -> SalesMenu.UIAddItem(stage));
        	layout.getChildren().add(add);
        	
        	Button addc = new Button("[ADDC]: Add Category");
        	addc.setOnAction(e -> SalesMenu.UIAddCategory(stage));
        	layout.getChildren().add(addc);
        	
        	Button del = new Button("[DEL]: Delete an Item from the MasterList");
        	del.setOnAction(e -> SalesMenu.UIDeleteItem(stage));
        	layout.getChildren().add(del);
        	
        	Button delc = new Button("[DELC]: Remove a Category");
        	delc.setOnAction(e -> SalesMenu.UIDeleteCategory(stage));
        	layout.getChildren().add(delc);
        	;
        }
        
        if(Integer.parseInt(MainSystemUserController.GetProperty("PermissionLevel")) >= 6) {
        	Button returns = new Button("[RET]: Returns Menu");
        	returns.setOnAction(e -> ReturnsMenu.UIReturnsMenu(stage));
        	layout.getChildren().add(returns);
        	
        	Button view = new Button("[VIEW]: View Items on MasterList");
        	view.setOnAction(e -> SalesMenu.UIViewItems(stage));
        	layout.getChildren().add(view);
        }
        Button off = new Button("[OFF]: Log Off ");
        off.setOnAction(e -> SwitchController.removeCurrentUser(SwitchController.focusUser));
        layout.getChildren().add(off);
        
        
        Button returnHome = new Button("[RETURN]: Return");
        returnHome.setOnAction(e -> MainMenu.showMainMenu(stage));
        layout.getChildren().add(returnHome);

        // Set scene and stage
        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }
    public static void createNewItem() {
        Logo.displayLogo();
        try {
            System.out.println("New Item Number: ");
            int itemNum = CustomScanner.nextInt();
            CustomScanner.nextLine(); // Add this line to consume the newline character

            System.out.println("New Item Name: ");
            String itemName = CustomScanner.nextLine();

            System.out.println("New Item Description: ");
            String itemDescription = CustomScanner.nextLine();

            System.out.println("New Original Price: ");
            Double origPrice = CustomScanner.nextDouble();
            
            CustomScanner.nextLine(); // Add this line to consume the newline character
            Double currentPrice = origPrice;
            boolean isDiscounted = false;
            System.out.println("Can this item be Discounted ever? (Y/N)");
            String canThisBeDiscounted = CustomScanner.nextLine();
            boolean canBeDiscounted = Boolean.parseBoolean(canThisBeDiscounted);
            
            System.out.println("Can this item be returned? (Y/N)");
            String canThisBeReturned = CustomScanner.nextLine();
            boolean isItemReturnable = Boolean.parseBoolean(canThisBeReturned);
            
            System.out.println("Is this item tax exempt? (Y/N)");
            String isThisTaxExempt = CustomScanner.nextLine();
            boolean isTaxExempt = Boolean.parseBoolean(isThisTaxExempt);
            
            System.out.println("Is this item sellable? (Y/N)");
            String isThisSellable = CustomScanner.nextLine();
            boolean isItemSellable = Boolean.parseBoolean(isThisSellable);
            
            
            System.out.println("New Item Qty: ");
            int itemQty = CustomScanner.nextInt();
            CustomScanner.nextLine(); // Add this line to consume the newline character

            System.out.println("New Category ID: ");
            int categoryID = CustomScanner.nextInt();
            CustomScanner.nextLine(); // Add this line to consume the newline character
            String category = CategoriesManager.RetrieveCatbyID(String.valueOf(categoryID));
            
           
            if(itemNum != 0) {
            	 POSItem item = new POSItem(itemNum, itemName, origPrice, currentPrice, 
                        isDiscounted, canBeDiscounted, isItemReturnable, 
                        isTaxExempt, isItemSellable, itemQty, category, 
                        categoryID, itemDescription) ;
            } else {
            	 POSItem item = new POSItem(itemName, origPrice, currentPrice, 
                        isDiscounted, canBeDiscounted, isItemReturnable, 
                        isTaxExempt, isItemSellable, itemQty, category, 
                        categoryID, itemDescription) ;
            }
            
            //int itemNum, String itemName, String itemDescription, double originalPrice, double currentPrice, boolean isDiscounted, boolean canBeDiscounted, double discountPercent, boolean isItemReturnable, boolean isTaxExempt, boolean isItemSellable, int itemQty, int categoryID, String category
            PointofSaleMenu();
        } catch (InputMismatchException e) {
        	MessageProcessor.processMessage(-2, e.toString(), true);
        	createNewItem();
        }
    }

}
