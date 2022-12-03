package PointofSale;
import java.util.Iterator;
import org.json.JSONObject;
import messageHandler.messageProcessor;
public class ItemController {
	private int itemID;
	private double currentPrice;
	private double originalPrice;
	private String itemName;
	private String itemDesc;
	private boolean isItemDiscounted;
	private boolean canItemBeDiscounted;  
	private String masterItemListJsonPath;
	public ItemController() {
		getKey(masterItemListJsonPath, "itemID");
	}
	public ItemController(int itemID, double currentPrice, double originalPrice, String itemName, String itemDesc, boolean isItemDiscounted, boolean canItemBeDiscounted) {
			this.itemID = itemID;
			this.currentPrice = currentPrice;
			this.originalPrice = originalPrice;
			this.itemName = itemName;
			this.itemDesc = itemDesc;
			this.isItemDiscounted = isItemDiscounted;
			this.canItemBeDiscounted = canItemBeDiscounted;
	}
}


public static void parseObject(JsonObject json, String key) {
	MessageProcessor.processMessage(1, json.has(key), true);
	MessageProcessor.processMessage(1, json.get(key), true);
}
public static void getKey(org.json.simple.JSONObject json, String key) {
	boolean exists = json.has(key);
	Iterator<?> keys;
	String nextKeys;
	if(!exists) {
		keys = json.keys();
		while(keys.hasNext()) {
			nextKeys = (String)keys.next(); 
			try {
				if(json.get(nextKeys) instanceof JSONObject) {
					if(!exists) {
						getKey(json.getJSONObject(nextKeys), key);
					}
				}else if(json.get(nextKeys) instanceof JSONArray) {
					JSONAraay jsonarray = json.getJsonArray(nextKeys);
					for(int i = 0; i < jsonarray.length(); i++) {
						String jsonarrayString = jsonarray.get(i).toString();
						JSONObject innerJson = new JSONObject(jsonarrayString);
						if(!exists) {
							getKey(innerJson, key);
						}
					}
				}
			}catch(Exception e) {
				MessageProcessor.processMessage(-2, e.toString(), true);
			}
		}
	} else {
		parseObject(json, key);
	}
}
public static boolean loadJsonFile() {
	JSONObject inputJSONObject = new JSONObject(masterItemListJsonPath);
	return true;
}