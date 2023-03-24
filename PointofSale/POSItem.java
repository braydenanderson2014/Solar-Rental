package PointofSale;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private List<POSItem> Items = new ArrayList<POSItem>();
    

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
        Items.add(this);
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
    public static void saveJSONObjectsToFile(List<JSONObject> jsonObjects, String filename) {
        Path path = Paths.get(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write("[");
            for (int i = 0; i < jsonObjects.size(); i++) {
                writer.write(jsonObjects.get(i).toString(4)); // Indentation of 4 spaces
                if (i < jsonObjects.size() - 1) {
                    writer.write(",");
                }
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<POSItem> loadPOSItemsFromFile(String filename) {
        List<POSItem> items = new ArrayList<>();
        Path path = Paths.get(filename);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            JSONArray jsonArray = new JSONArray(content.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                POSItem item = POSItem.fromJSON(jsonObject.toString());
                items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}


