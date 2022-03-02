package PointofSale;

import Assets.Logo;

public class DiscountManager {
    static double itemDiscount;
    static double invoiceDiscount;
    public static void DiscountMenu() {
        Logo.displayLogo();
        System.out.println("Discount Menu");
        if(invoiceDiscount == 0){
            System.out.println("[APP]: Apply Discount to Item");
            System.out.println("[REM]: Remove Discount from item");
        }
        System.out.println("[APPI]: Apply Item to Invoice");
        System.out.println("[REMI]: Remove Discount from invoice");
    }

}
