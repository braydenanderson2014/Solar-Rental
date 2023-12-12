package PointofSale;

import org.json.JSONObject;

import assets.Logo;

import messageHandler.MessageProcessor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class POSItem {
	 private int itemNumber;
	 private String itemName;
	 private double currentPrice;
	 private double originalPrice;
	 private boolean isDiscounted;
	 private String description;
	 private int quantity; // Quantity of the item in stock
	 private String category; // Category of the item
	 private boolean canBeDiscounted;
	 private boolean isItemReturnable;
	 private boolean isTaxExempt;
	 private boolean isItemSellable;
	 private int categoryID;
	
	

	private Random gen = new Random();
    public static List<JSONObject> itemObjects = new ArrayList<>();

 // First Constructor
    public POSItem(String itemName, double originalPrice, double currentPrice, boolean isDiscounted, 
                   boolean canBeDiscounted, boolean isItemReturnable, boolean isTaxExempt, 
                   boolean isItemSellable, int quantity, String category, int categoryID, String description) {
        this.itemNumber = gen.nextInt(100000); // Assuming 'gen' is a Random instance
        this.itemName = itemName;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.isDiscounted = isDiscounted;
        this.canBeDiscounted = canBeDiscounted;
        this.isItemReturnable = isItemReturnable;
        this.isTaxExempt = isTaxExempt;
        this.isItemSellable = isItemSellable;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.category = category;
        this.description = description;
        
        toJSON();
    }
    
    

    // Second Constructor
    public POSItem(int itemNumber, String itemName, double originalPrice, double currentPrice, 
                   boolean isDiscounted, boolean canBeDiscounted, boolean isItemReturnable, 
                   boolean isTaxExempt, boolean isItemSellable, int quantity, String category, 
                   int categoryID, String description) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.isDiscounted = isDiscounted;
        this.canBeDiscounted = canBeDiscounted;
        this.isItemReturnable = isItemReturnable;
        this.isTaxExempt = isTaxExempt;
        this.isItemSellable = isItemSellable;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.category = category;
        this.description = description;
        toJSON();
    }

    public static POSItem fromJSON(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);

        int itemNumber = jsonObject.getInt("itemNumber");
        String itemName = jsonObject.getString("itemName");
        String itemDescription = jsonObject.getString("itemDescription");
        double originalPrice = jsonObject.getDouble("originalPrice");
        double currentPrice = jsonObject.getDouble("currentPrice");
        boolean isDiscounted = jsonObject.getBoolean("isDiscounted");
        boolean canBeDiscounted = jsonObject.getBoolean("canBeDiscounted");
        boolean isItemReturnable = jsonObject.getBoolean("isItemReturnable");
        boolean isTaxExempt = jsonObject.getBoolean("isTaxExempt");
        boolean isItemSellable = jsonObject.getBoolean("isItemSellable");
        int itemQty = jsonObject.getInt("itemQty");
        int categoryID = jsonObject.getInt("categoryID");
        String category = jsonObject.getString("category");
        
        
        return new POSItem(itemNumber, itemName, originalPrice, currentPrice, 
                isDiscounted, canBeDiscounted, isItemReturnable, 
                isTaxExempt, isItemSellable, itemQty, category, 
                categoryID, itemDescription) ;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("itemNumber", this.itemNumber);
        jsonObject.put("itemName", this.itemName);
        jsonObject.put("itemDescription", this.description);
        jsonObject.put("originalPrice", this.originalPrice);
        jsonObject.put("currentPrice", this.currentPrice);
        jsonObject.put("isDiscounted", this.isDiscounted);
        jsonObject.put("canBeDiscounted", this.canBeDiscounted);
        jsonObject.put("isItemReturnable", this.isItemReturnable);
        jsonObject.put("isTaxExempt", this.isTaxExempt);
        jsonObject.put("isItemSellable", this.isItemSellable);
        jsonObject.put("itemQty", this.quantity);
        jsonObject.put("categoryID", this.category);
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
            if (String.valueOf(item.itemNumber).contains(query) ||
                item.itemName.toLowerCase().contains(query) ||
                item.description.toLowerCase().contains(query) ||
                String.valueOf(item.originalPrice).contains(query) ||
                String.valueOf(item.currentPrice).contains(query) ||
                String.valueOf(item.isDiscounted).contains(query) ||
                String.valueOf(item.canBeDiscounted).contains(query) ||
                String.valueOf(item.isItemReturnable).contains(query) ||
                String.valueOf(item.isTaxExempt).contains(query) ||
                String.valueOf(item.isItemSellable).contains(query) ||
                String.valueOf(item.quantity).contains(query) || 
                String.valueOf(item.categoryID).contains(query) ||
                item.category.toLowerCase().contains(query)) {
                matchingItems.add(itemJson);
            }
        }

        return matchingItems;
    }

    private static final long serialVersionUID = 1L; // Add a serialVersionUID

}

