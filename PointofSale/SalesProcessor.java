package PointofSale;

import java.util.ArrayList;

public class SalesProcessor {
    public static ArrayList<String> ItemsOnInvoice = new ArrayList<String>();
    public static ArrayList<Double> CurrentPricesOnInvoice = new ArrayList<Double>();
    public static ArrayList<Double> OrigPricesOnInvoice = new ArrayList<Double>();
    public static ArrayList<Boolean> isItemDiscounted = new ArrayList<Boolean>();
    public static double taxPercentage = 0;
    public static double taxDAmount = 0;
    public static double discountTotal = 0;
    public static double TotalAmount = 0;
    public static void ProcessSale() {
        updateArrays();
    }

    public static boolean updateArrays(){
        ItemsOnInvoice = SalesMenu.ItemsOnInvoice;
        CurrentPricesOnInvoice = SalesMenu.CurrentPricesOnInvoice;
        OrigPricesOnInvoice = SalesMenu.OrigPricesOnInvoice;
        isItemDiscounted = SalesMenu.isItemDiscounted;
        return true;
    }
}
