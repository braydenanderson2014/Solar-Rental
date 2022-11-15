package PointofSale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import Assets.Logo;
import InstallManager.AutoSetup;
import InstallManager.ProgramController;
import messageHandler.messageHandler;

public class CategoriesManager {
    private String item;
    private String Category;
    private double price;
    private String description;
    public static String itemID;
    private static Properties CatList = new Properties();
    public static Boolean SearchForCat(String cat){
        return true;
    }

    public static Boolean LoadCatProperties(){
        String path = ProgramController.UserRunPath + "\\Categories/Categories.properties";
        File file = new File(path);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return false;
        }
        try (InputStream input = new FileInputStream(path)){
            CatList.load(input);
            messageHandler.HandleMessage(1, "Categories Loaded", true);
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            messageHandler.HandleMessage(-1, "Unable to load Categories", false);
            return false;
        }
    }

    public static Boolean SaveCatProperties(){
        String path = ProgramController.UserRunPath + "\\Categories/Categories.properties";
        try (OutputStream output = new FileOutputStream(path)){
            CatList.store(output, null);
            messageHandler.HandleMessage(1, "Categories List Saved! Categories", false);
            LoadCatProperties();
            return true;
        }catch(IOException e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            return false;
        }
    }

    public static Boolean RetrieveCat(){
        return true;
    }

    public static Boolean ListAllCat(){
        LoadCatProperties();
        Enumeration keys = CatList.keys();
        ProgramController.clearScreen();
        Logo.displayLogo();
        System.out.println("Categories:");
        Logo.displayLine();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = (String)CatList.get(key);
            System.out.println(key + ": " + value);
            messageHandler.HandleMessage(1, key + ": " + value, false);
        }
        return true;
    }

    public static Boolean AddCat(String Category, String CatID){
        LoadCatProperties();
        if(CatList.contains(CatID) || CatList.containsKey(Category)){
            messageHandler.HandleMessage(-1, "ID or Category already Exists", false);
            return false;
        }else{
            CatList.put(Category, CatID);
            SaveCatProperties();
            return true;    
        }
    }

    public CategoriesManager(String item, String Category, double price, String description, int itemID){
        this.item = item;
        this.Category = Category;
        this.price = price;
        this.description = description;

    }

    public static void serializeToXML(){
        //XMLMapper xmlMapper = new XMLMapper();
    }

    public static boolean checkID(String id) {
        return CatList.containsValue(id);
    }

    public static String RetrieveCatbyID(String id) {
        LoadCatProperties();
        boolean exists = checkID(id);
        if(exists){
            Enumeration keys = CatList.keys();
            while (keys.hasMoreElements()) {
                String key = (String)keys.nextElement();
                String value = (String)CatList.get(key);
                System.out.println(key + ": " + value);
                messageHandler.HandleMessage(1, key + ": " + value, false);
                if(value.equals(id)){
                    return key;
                }else{
                    messageHandler.HandleMessage(-1, "Unable to find category", true);
                    return "Null";
                }
            }
            return "NULL";
        }else{
            messageHandler.HandleMessage(-1, "Unable to find category", true);
            return "Null";
        }
    }

    public static String RetrieveLastCatbyID(String id) {
        return itemID;
    }

    public static boolean removeCategoryByID(String id) {
        LoadCatProperties();
        boolean exists = checkID(id);
        if(exists){
            Enumeration keys = CatList.keys();
            while (keys.hasMoreElements()) {
                String key = (String)keys.nextElement();
                String value = (String)CatList.get(key);
                messageHandler.HandleMessage(1, key + ": " + value, false);
                if(value.equals(id)){
                    itemID = "Category: " + key + ", ID: " + value;
                    CatList.remove(key, value);
                    SaveCatProperties();
                    return true;
                }else{
                    messageHandler.HandleMessage(-1, "Unable to find category", true);
                    return false;
                }
            }
            return true;
        }else{
            messageHandler.HandleMessage(-1, "Unable to find category", true);
            return false;
        }
    }
}
