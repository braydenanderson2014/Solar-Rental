package PointofSale;

import org.json.JSONArray;
import org.json.JSONObject;

import InstallManager.ProgramController;
import assets.Logo;

import messageHandler.MessageProcessor;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class POSItem {
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
	 private int itemNumber;

	 private static String FilePath = ProgramController.systemRunPath + "/ProgramFiles/ItemMasterList.json";
	

	private Random gen = new Random();

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

    public POSItem() {
		// TODO Auto-generated constructor stub
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

	public static POSItem toPOSItem(Object object) {
		// TODO Auto-generated method stub
		POSItem item = new POSItem();
		item.itemNumber = ((POSItem) object).getItemNumber();
		item.itemName = ((POSItem) object).getItemName();
		item.originalPrice = ((POSItem) object).getOriginalPrice();
		item.currentPrice = ((POSItem) object).getCurrentPrice();
		item.isDiscounted = ((POSItem) object).isDiscounted();
		item.canBeDiscounted = ((POSItem) object).isCanBeDiscounted();
		item.isItemReturnable = ((POSItem) object).isItemReturnable();
		item.isTaxExempt = ((POSItem) object).isTaxExempt();
		item.isItemSellable = ((POSItem) object).isItemSellable();
		item.categoryID = ((POSItem) object).getCategoryID();
		item.quantity = ((POSItem) object).getQuantity();
		return item;
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
        jsonObject.put("categoryID", this.categoryID);
        jsonObject.put("category", this.category);
        
        MessageProcessor.processMessage(1, "New Item Created, Assigning to MasterList!", true);
        MessageProcessor.processMessage(1, "Added NEW Item to MasterList!", true);
        return jsonObject;
    }
    
    private static final long serialVersionUID = 1L; // Add a serialVersionUID

	 public int getItemNumber() {
		return itemNumber;
	}
	 
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}



	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}



	public double getCurrentPrice() {
		return currentPrice;
	}



	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}



	public double getOriginalPrice() {
		return originalPrice;
	}



	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}



	public boolean isDiscounted() {
		return isDiscounted;
	}



	public void setDiscounted(boolean isDiscounted) {
		this.isDiscounted = isDiscounted;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public boolean isCanBeDiscounted() {
		return canBeDiscounted;
	}



	public void setCanBeDiscounted(boolean canBeDiscounted) {
		this.canBeDiscounted = canBeDiscounted;
	}



	public boolean isItemReturnable() {
		return isItemReturnable;
	}



	public void setItemReturnable(boolean isItemReturnable) {
		this.isItemReturnable = isItemReturnable;
	}



	public boolean isTaxExempt() {
		return isTaxExempt;
	}



	public void setTaxExempt(boolean isTaxExempt) {
		this.isTaxExempt = isTaxExempt;
	}



	public boolean isItemSellable() {
		return isItemSellable;
	}



	public void setItemSellable(boolean isItemSellable) {
		this.isItemSellable = isItemSellable;
	}



	public int getCategoryID() {
		return categoryID;
	}



	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}



   
}

