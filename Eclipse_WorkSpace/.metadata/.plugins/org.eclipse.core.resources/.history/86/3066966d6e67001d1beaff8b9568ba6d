package PointofSale;
import java.io.*;
import json.simple.JSONObject;
import messageHandler.messageHandler;
public class CategoriesProcessor{
    public static JSONObject obj;
    public static boolean CreateNewJSONOBJECT(){
        obj = new JSONObject();
        return true;
    }

    public static String EncodeJson(String Name, int ItemNumber, double CurrentPrice, double originalPrice, boolean canBeDiscounted, String CategoryName, int CategoryID, int SubCategoryID, String SubCategory){
        obj.put("Name", Name);
        obj.put("ItemNumber", new Integer(ItemNumber));
        obj.put("CurrentPrice", new Double(CurrentPrice));
        obj.put("OriginalPrice", new Double(originalPrice));
        obj.put("canBeDiscounted", new Boolean(canBeDiscounted));
        obj.put("CategoryName", CategoryName);
        obj.put("CategoryID", new Integer(CategoryID));
        obj.put("SubCategoryID", new Integer(SubCategoryID));
        obj.put("SubCategory", SubCategory);
        StringWriter out = new StringWriter();
        try{
            obj.writeJSONString(out);
            String jsonText = out.toString();
            messageHandler.HandleMessage(1, jsonText, true);
            return jsonText;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return "";
        }
    }   

    public static String DecodeJSON(){
        return "";
    }
}