package PointofSale;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import InstallManager.ProgramController;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CategoriesManager {
    private Map<Integer, String> categories;
    private Map<Integer, Set<Integer>> subCategoryMapping;
    private static final String FILENAME = ProgramController.systemRunPath + "/ProgramFiles/Categories/categories.json"; // File to save/load data

    public CategoriesManager() {
        categories = new HashMap<>();
        subCategoryMapping = new HashMap<>();
        loadFromFile();
    }

    public void saveToFile() {
        Gson gson = new Gson();
        Map<String, Object> data = new HashMap<>();
        data.put("categories", categories);
        data.put("subCategoryMapping", subCategoryMapping);
        File file = new File(FILENAME);
        if(!file.exists()) {
        	try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        try (FileWriter writer = new FileWriter(FILENAME)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
	public void loadFromFile() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(FILENAME)) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(reader, type);

            if (data == null) {
                // Handle the case where the file is empty or corrupted
                return;
            }

            // Correctly handle the loading of categories
            Map<String, String> loadedCategories = (Map<String, String>) data.get("categories");
            categories = new HashMap<>();
            if (loadedCategories != null) {
                for (Map.Entry<String, String> entry : loadedCategories.entrySet()) {
                    categories.put(Integer.parseInt(entry.getKey()), entry.getValue());
                }
            }

            // Correctly handle the loading of subcategory mappings
            Map<String, List<Double>> loadedSubCategoryMapping = (Map<String, List<Double>>) data.get("subCategoryMapping");
            subCategoryMapping = new HashMap<>();
            if (loadedSubCategoryMapping != null) {
                for (Map.Entry<String, List<Double>> entry : loadedSubCategoryMapping.entrySet()) {
                    Set<Integer> subSet = new HashSet<>();
                    for (Double subId : entry.getValue()) {
                        subSet.add(subId.intValue());
                    }
                    subCategoryMapping.put(Integer.parseInt(entry.getKey()), subSet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    // Create a new category
    public void createCategory(int id, String name) {
        categories.put(id, name);
        subCategoryMapping.putIfAbsent(id, new HashSet<>());
        saveToFile();
    }

    // Link a subcategory to a category
    public void linkSubCategory(int mainCategoryId, int subCategoryId) {
        if (!categories.containsKey(mainCategoryId) || !categories.containsKey(subCategoryId)) {
            throw new IllegalArgumentException("Category ID does not exist. " + mainCategoryId + " " + subCategoryId);
        }
        subCategoryMapping.get(mainCategoryId).add(subCategoryId);
    }

    // Get category name
	public String getCategoryName(int categoryId) {
		if (!categories.containsKey(categoryId)) {
			throw new IllegalArgumentException("Category ID does not exist . " + categoryId);
		}
		return categories.get(categoryId);
	}
	
    // Get subcategories of a category
    public Set<String> getSubCategories(int categoryId) {
        Set<String> subCategories = new HashSet<>();
        if (subCategoryMapping.containsKey(categoryId)) {
            for (int subId : subCategoryMapping.get(categoryId)) {
                subCategories.add(categories.get(subId));
            }
        }
        return subCategories;
    }

    // List all categories
    public Map<Integer, String> listAllCategories() {
        return new HashMap<>(categories);
    }

    // Optional: Remove a category (also removes it from any subcategory mapping)
    public void removeCategory(int categoryId) {
        if (!categories.containsKey(categoryId)) {
            throw new IllegalArgumentException("Category ID does not exist. " + categoryId);
        }
        categories.remove(categoryId);
        subCategoryMapping.remove(categoryId);
        for (Set<Integer> subCategories : subCategoryMapping.values()) {
            subCategories.remove(categoryId);
        }
    }

    // Optional: Rename a category
    public void renameCategory(int categoryId, String newName) {
        if (!categories.containsKey(categoryId)) {
            throw new IllegalArgumentException("Category ID does not exist. " + categoryId);
        }
        categories.put(categoryId, newName);
    }
    
	public boolean hasCategory(int categoryId) {
		return categories.containsKey(categoryId);
	}
	
	public boolean removeSubCategory(int categoryId, int subCategoryId) {
		if (!categories.containsKey(categoryId) || !categories.containsKey(subCategoryId)) {
			return false;
		}
		return subCategoryMapping.get(categoryId).remove(subCategoryId);
	}
	
	public boolean removeCategoryByID(int categoryId) {
		if (!categories.containsKey(categoryId)) {
			return false;
		}
		categories.remove(categoryId);
		subCategoryMapping.remove(categoryId);
		for (Set<Integer> subCategories : subCategoryMapping.values()) {
			subCategories.remove(categoryId);
		}
		return true;
	}
}
