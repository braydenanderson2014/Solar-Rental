package PointofSale;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

public class SalesProcessor {
    public static List<String> ItemsOnInvoice = new ArrayList<>();
    public static List<Double> CurrentPricesOnInvoice = new ArrayList<>();
    public static List<Double> OrigPricesOnInvoice = new ArrayList<>();
    public static List<Boolean> isItemDiscounted = new ArrayList<>();
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
