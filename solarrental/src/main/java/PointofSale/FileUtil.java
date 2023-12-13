package PointofSale;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public class FileUtil {

    public static List<JSONObject> retrieveItemsFromJsonFile(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            Type listType = new TypeToken<ArrayList<JSONObject>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveItemsToJsonFile(List<JSONObject> newItems, String filename) {
        Gson gson = new Gson();
        Set<JSONObject> combinedItems = new HashSet<>();

        // Load existing items
        List<JSONObject> existingItems = retrieveItemsFromJsonFile(filename);
        combinedItems.addAll(existingItems);

        // Add new items, avoiding duplicates
        combinedItems.addAll(newItems);

        // Convert the combined set to JSON
        String json = gson.toJson(combinedItems);

        // Save the JSON string to the file
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
}