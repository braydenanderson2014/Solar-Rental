package PointofSale;

import assets.CustomScanner;
import assets.Logo;
import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;

public class DiscountManager {
    static double itemDiscountTotal;
    static double invoiceDiscountTotal;
    public static void DiscountMenu() {
        Logo.displayLogo();
        System.out.println("Discount Menu");
        if(invoiceDiscountTotal == 0){
            System.out.println("[APP]: Apply Discount to Item");
            System.out.println("[REM]: Remove Discount from item");
        }
        System.out.println("[APPI]: Apply Item to Invoice");
        System.out.println("[REMI]: Remove Discount from invoice");
        System.out.println("[RET]: Return to POS Menu");
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        if(option.equals("app")){
            appDis();
        }else if(option.equals("rem")){
            remDis();
        }else if(option.equals("appi")){
            appIDis();
        }else if(option.equals("remi")){
            remIDis();
        }else if(option.equals("ret")){
            POSMenu.PointofSaleMenu();
        }else{
            MessageProcessor.processMessage(-1, "Invalid option, Try again.", true);
            DiscountMenu();
        }
    }

    private static void remIDis() {
    }

    private static void appIDis() {
    }

    private static void remDis() {
    }

    private static void appDis() {
    }

}
