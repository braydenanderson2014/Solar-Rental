package PointofSale;

import org.json.JSONArray;
import org.json.JSONObject;

import InstallManager.ProgramController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemManager {
	private static String filePath = ProgramController.systemRunPath + "/ProgramFiles/ItemMasterList.json";
	private static List<JSONObject> itemObjects = new ArrayList<>();

	@SuppressWarnings("static-method")
	public void createItem(String itemName, double originalPrice, double currentPrice, boolean isDiscounted,
			boolean canBeDiscounted, boolean isItemReturnable, boolean isTaxExempt, boolean isItemSellable,
			int quantity, String category, int categoryID, String description) {
		POSItem newItem = new POSItem(itemName, originalPrice, currentPrice, isDiscounted, canBeDiscounted,
				isItemReturnable, isTaxExempt, isItemSellable, quantity, category, categoryID, description);
		getItemObjects().add(newItem.toJSON());
		saveItemsToFile();
	}

	public static List<JSONObject> searchItems(String query) {
        loadItemsFromFile();
        query = query.toLowerCase(); // Convert the query to lowercase for case-insensitive search
        List<JSONObject> matchingItems = new ArrayList<>();

        for (JSONObject itemJson : getItemObjects()) {
            if (itemMatchesQuery(itemJson, query)) {
                matchingItems.add(itemJson);
            }
        }

        return matchingItems;
    }
	
	public static boolean itemExists(int itemNumber) {
		for (JSONObject itemJson : itemObjects) {
			if (itemJson.getInt("itemNumber") == itemNumber) {
				return true;
			}
		}
		return false;
	}

    public static boolean itemMatchesQuery(JSONObject item, String query) {
        return item.getString("itemName").toLowerCase().contains(query) ||
               item.getString("itemDescription").toLowerCase().contains(query) ||
               item.getString("category").toLowerCase().contains(query) ||
               String.valueOf(item.getDouble("currentPrice")).contains(query) ||
               String.valueOf(item.getInt("itemNumber")).contains(query);
    }
	
	public static JSONObject getItem(int itemNumber) {
		loadItemsFromFile();
		for (JSONObject itemJson : getItemObjects()) {
			if (itemJson.getInt("itemNumber") == itemNumber) {
				return itemJson;
			}
		}
		return null;
	}
	
	public static Double getOriginalPrice(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getDouble("originalPrice");
		}
		return null; // or throw an exception
	}
	
    public static Double getCurrentPrice(int itemNumber) {
        JSONObject item = getItem(itemNumber);
        if (item != null) {
            return item.getDouble("currentPrice");
        }
        return null; // or throw an exception
    }

    public static Integer getItemNumber(String itemName) {
        for (JSONObject itemJson : getItemObjects()) {
            if (itemJson.getString("itemName").equalsIgnoreCase(itemName)) {
                return itemJson.getInt("itemNumber");
            }
        }
        return null; // or throw an exception
    }
    
	public static String getItemName(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getString("itemName");
		}
		return null; // or throw an exception
	}
	
	public static String getItemDescription(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getString("itemDescription");
		}
		return null; // or throw an exception
	}
	
	public static Boolean isDiscounted(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getBoolean("isDiscounted");
		}
		return null; // or throw an exception
	}
	
	public static Boolean canBeDiscounted(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getBoolean("canBeDiscounted");
		}
		return null; // or throw an exception
	}
	
	public static Boolean isItemReturnable(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getBoolean("isItemReturnable");
		}
		return null; // or throw an exception
	}
	
	public static Boolean isTaxExempt(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getBoolean("isTaxExempt");
		}
		return null; // or throw an exception
	}
	
	public static Boolean isItemSellable(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getBoolean("isItemSellable");
		}
		return null; // or throw an exception
	}
	
	public static Integer getQuantity(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getInt("quantity");
		}
		return null; // or throw an exception
	}
	
	public static String getCategory(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getString("category");
		}
		return null; // or throw an exception
	}
	
	public static Integer getCategoryID(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			return item.getInt("categoryID");
		}
		return null; // or throw an exception
	}
	
	public static void setItemName(int itemNumber, String itemName) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("itemName", itemName);
			saveItemsToFile();
		}
	}
	
	public static void setItemDescription(int itemNumber, String itemDescription) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("itemDescription", itemDescription);
			saveItemsToFile();
		}
	}
	
	public static void setOriginalPrice(int itemNumber, double originalPrice) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("originalPrice", originalPrice);
			saveItemsToFile();
		}
	}
	
	public static void setCurrentPrice(int itemNumber, double currentPrice) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("currentPrice", currentPrice);
			saveItemsToFile();
		}
	}
	
	public static void setIsDiscounted(int itemNumber, boolean isDiscounted) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("isDiscounted", isDiscounted);
			saveItemsToFile();
		}
	}
	
	public static void setCanBeDiscounted(int itemNumber, boolean canBeDiscounted) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("canBeDiscounted", canBeDiscounted);
			saveItemsToFile();
		}
	}
	
	public static void setIsItemReturnable(int itemNumber, boolean isItemReturnable) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("isItemReturnable", isItemReturnable);
			saveItemsToFile();
		}
	}
	
	public static void setIsTaxExempt(int itemNumber, boolean isTaxExempt) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("isTaxExempt", isTaxExempt);
			saveItemsToFile();
		}
	}
	
	public static void setIsItemSellable(int itemNumber, boolean isItemSellable) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("isItemSellable", isItemSellable);
			saveItemsToFile();
		}
	}
	
	public static void setQuantity(int itemNumber, int quantity) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("quantity", quantity);
			saveItemsToFile();
		}
	}
	
	public static void setCategory(int itemNumber, String category) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("category", category);
			saveItemsToFile();
		}
	}
	
	public static void setCategoryID(int itemNumber, int categoryID) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("categoryID", categoryID);
			saveItemsToFile();
		}
	}
	
	public static void removeItem(int itemNumber) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			getItemObjects().remove(item);
			saveItemsToFile();
		}
	}
	
	public static void increaseQuantity(int itemNumber, int quantity) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("quantity", item.getInt("quantity") + quantity);
			saveItemsToFile();
		}
	}
	
	public static void decreaseQuantity(int itemNumber, int quantity) {
		JSONObject item = getItem(itemNumber);
		if (item != null) {
			item.put("quantity", item.getInt("quantity") - quantity);
			saveItemsToFile();
		}
	}
	
	public static void saveItemsToFile() {
		JSONArray jsonArray = new JSONArray(getItemObjects());
		try {
			Files.write(Paths.get(filePath), jsonArray.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadItemsFromFile() {
		try {
			String content = new String(Files.readAllBytes(Paths.get(filePath)));
			JSONArray jsonArray = new JSONArray(content);
			getItemObjects().clear();
			getItemObjects().addAll(jsonArray.toList().stream().map(obj -> new JSONObject((Map<String, Object>) obj))
					.collect(Collectors.toList()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the itemObjects
	 */
	public static List<JSONObject> getItemObjects() {
		return itemObjects;
	}

	/**
	 * @param itemObjects the itemObjects to set
	 */
	public static void setItemObjects(List<JSONObject> itemObjects) {
		ItemManager.itemObjects = itemObjects;
	}
}
