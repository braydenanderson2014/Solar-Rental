package PointofSale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Properties;

import com.solarrental.assets.Logo;

import InstallManager.AutoSetup;
import InstallManager.ProgramController;
import messageHandler.MessageProcessor;

public class CategoriesManager {
    private String item;
    private String Category;
    private double price;
    private String description;
    public  int itemID;
    private static Properties CatList = new Properties();
    public static Boolean SearchForCat(String cat){
        return true;
    }

    public static Boolean LoadCatProperties(){
        String path = ProgramController.userRunPath + "\\Categories/Categories.properties";
        File file = new File(path);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            return false;
        }
        try (InputStream input = new FileInputStream(path)){
            CatList.load(input);
            MessageProcessor.processMessage(1, "Categories Loaded", true);
            return true;
        }catch(IOException e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            MessageProcessor.processMessage(-1, "Unable to load Categories", false);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            return false;
        }
    }

    public static Boolean SaveCatProperties(){
        String path = ProgramController.userRunPath + "\\Categories/Categories.properties";
        try (OutputStream output = new FileOutputStream(path)){
            CatList.store(output, null);
            MessageProcessor.processMessage(1, "Categories List Saved! Categories", false);
            LoadCatProperties();
            return true;
        }catch(IOException e){
        	StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            MessageProcessor.processMessage(2, stackTrace, true);
            MessageProcessor.processMessage(-2, e.toString(), true);
            return false;
        }
    }

    public static Boolean RetrieveCat(){
        return true;
    }

    public static Boolean ListAllCat(){
        LoadCatProperties();
        @SuppressWarnings("rawtypes")
		Enumeration keys = CatList.keys();
        try {
			ProgramController.clearScreen();
		} catch (IOException | InterruptedException e) {
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
		    String stackTrace = sw.toString();

		    MessageProcessor.processMessage(2, stackTrace, true);
			MessageProcessor.processMessage(-2, e.toString(), true);
		}
			Logo.displayLogo();
			System.out.println("Categories:");
			Logo.displayLine();
			while (keys.hasMoreElements()) {
				String key = (String)keys.nextElement();
				String value = (String)CatList.get(key);
				System.out.println(key + ": " + value);
				MessageProcessor.processMessage(1, key + ": " + value, false);
			}
        return true;
    }

    public static Boolean AddCat(String category, String catID){
        LoadCatProperties();
        if(CatList.contains(catID) || CatList.containsKey(category)){
            MessageProcessor.processMessage(-1, "ID or Category already Exists", false);
            return false;
        }
		CatList.put(category, catID);
		SaveCatProperties();
		return true;
    }

    public CategoriesManager(String item, String category, double price, String description, int itemID){
        this.item = item;
        this.Category = category;
        this.price = price;
        this.description = description;
        this.itemID = itemID;
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
            @SuppressWarnings("rawtypes")
			Enumeration keys = CatList.keys();
            while (keys.hasMoreElements()) {
                String key = (String)keys.nextElement();
                String value = (String)CatList.get(key);
                System.out.println(key + ": " + value);
                MessageProcessor.processMessage(1, key + ": " + value, false);
                if(value.equals(id)){
                    return key;
                }
				MessageProcessor.processMessage(-1, "Unable to find category", true);
				return "Null";
            }
            return "NULL";
        }
		MessageProcessor.processMessage(-1, "Unable to find category", true);
		return "Null";
    }

    public static String RetrieveLastCatbyID(String id) {
        return "itemID";  
        		
    }

    public static boolean removeCategoryByID(String id) {
        LoadCatProperties();
        boolean exists = checkID(id);
        if(exists){
            @SuppressWarnings("rawtypes")
			Enumeration keys = CatList.keys();
            while (keys.hasMoreElements()) {
                String key = (String)keys.nextElement();
                String value = (String)CatList.get(key);
                MessageProcessor.processMessage(1, key + ": " + value, false);
                if(value.equals(id)){
                    //itemID = "Category: " + key + ", ID: " + value;
                    CatList.remove(key, value);
                    SaveCatProperties();
                    return true;
                }
				MessageProcessor.processMessage(-1, "Unable to find category", true);
				return false;
            }
            return true;
        }
		MessageProcessor.processMessage(-1, "Unable to find category", true);
		return false;
    }
}
