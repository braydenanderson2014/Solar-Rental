package PointofSale;

import org.json.JSONObject;

import com.solarrental.assets.Logo;

import messageHandler.MessageProcessor;

import java.util.ArrayList;
import java.util.List;

public class POSItem {
    private int itemNum;
    private String itemName;
    private String itemDescription;
    private double originalPrice;
    private double currentPrice;
    private boolean isDiscounted;
    private boolean canBeDiscounted;
    private double discountPercent;
    private boolean isItemReturnable;
    private boolean isTaxExempt;
    private boolean isItemSellable;
    private int itemQty;
    private int categoryID;
    private String category;
    public List<JSONObject> itemObjects = new ArrayList<>();

    public POSItem(int itemNum, String itemName, String itemDescription, double originalPrice, double currentPrice, 
                   boolean isDiscounted, boolean canBeDiscounted, double discountPercent, boolean isItemReturnable, 
                   boolean isTaxExempt, boolean isItemSellable, int itemQty, int categoryID, String category) {
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.isDiscounted = isDiscounted;
        this.canBeDiscounted = canBeDiscounted;
        this.discountPercent = discountPercent;
        this.isItemReturnable = isItemReturnable;
        this.isTaxExempt = isTaxExempt;
        this.isItemSellable = isItemSellable;
        this.itemQty = itemQty;
        this.categoryID = categoryID;
        this.category = category;
        MessageProcessor.processMessage(1, "New Item being Defined", true);
        toJSON();
    }

    public static POSItem fromJSON(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);

        int itemNum = jsonObject.getInt("itemNum");
        String itemName = jsonObject.getString("itemName");
        String itemDescription = jsonObject.getString("itemDescription");
        double originalPrice = jsonObject.getDouble("originalPrice");
        double currentPrice = jsonObject.getDouble("currentPrice");
        boolean isDiscounted = jsonObject.getBoolean("isDiscounted");
        boolean canBeDiscounted = jsonObject.getBoolean("canBeDiscounted");
        double discountPercent = jsonObject.getDouble("discountPercent");
        boolean isItemReturnable = jsonObject.getBoolean("isItemReturnable");
        boolean isTaxExempt = jsonObject.getBoolean("isTaxExempt");
        boolean isItemSellable = jsonObject.getBoolean("isItemSellable");
        int itemQty = jsonObject.getInt("itemQty");
        int categoryID = jsonObject.getInt("categoryID");
        String category = jsonObject.getString("category");
        
        return new POSItem(itemNum, itemName, itemDescription, originalPrice, currentPrice, isDiscounted, canBeDiscounted, 
                           discountPercent, isItemReturnable, isTaxExempt, isItemSellable, itemQty, categoryID, category);
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("itemNum", this.itemNum);
        jsonObject.put("itemName", this.itemName);
        jsonObject.put("itemDescription", this.itemDescription);
        jsonObject.put("originalPrice", this.originalPrice);
        jsonObject.put("currentPrice", this.currentPrice);
        jsonObject.put("isDiscounted", this.isDiscounted);
        jsonObject.put("canBeDiscounted", this.canBeDiscounted);
        jsonObject.put("discountPercent", this.discountPercent);
        jsonObject.put("isItemReturnable", this.isItemReturnable);
        jsonObject.put("isTaxExempt", this.isTaxExempt);
        jsonObject.put("isItemSellable", this.isItemSellable);
        jsonObject.put("itemQty", this.itemQty);
        jsonObject.put("categoryID", this.categoryID);
        jsonObject.put("category", this.category);
        MessageProcessor.processMessage(1, "New Item Created, Assigning to MasterList!", true);
        itemObjects.add(jsonObject);
        MessageProcessor.processMessage(1, "Added NEW Item to MasterList!", true);
        return jsonObject;
    }

    public static List<JSONObject> searchItems(List<POSItem> items, String query) {
        List<JSONObject> matchingItems = new ArrayList<>();

        query = query.toLowerCase(); // Convert the query to lowercase for case-insensitive search

        for (POSItem item : items) {
            JSONObject itemJson = item.toJSON();

            // Check if any of the item properties match the search query
            if (String.valueOf(item.itemNum).contains(query) ||
                item.itemName.toLowerCase().contains(query) ||
                item.itemDescription.toLowerCase().contains(query) ||
                String.valueOf(item.originalPrice).contains(query) ||
                String.valueOf(item.currentPrice).contains(query) ||
                String.valueOf(item.discountPercent).contains(query) ||
                String.valueOf(item.itemQty).contains(query) || String.valueOf(item.categoryID).contains(query) ||
                item.category.toLowerCase().contains(query)) {
                matchingItems.add(itemJson);
            }
        }

        return matchingItems;
    }

	
}

