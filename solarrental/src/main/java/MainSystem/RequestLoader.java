package MainSystem;

import com.google.gson.Gson;

import InstallManager.ProgramController;
import Login.SwitchController;
import messageHandler.LogDump;
import messageHandler.MessageProcessor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RequestLoader {
    private static String requestFilePath = ProgramController.userRunPath + "\\Users\\Admin/AdministrativeRequests.json";
    //private static RequestData requestData = new RequestData();

    public static class RequestData {
    	//Arrays correspond directly to AdministrativeFunctions Class Arrays for Requests
        public List<String> administrativeRequests = new ArrayList<>();
        public List<String> administrativeRequestKeyWord = new ArrayList<>();
        public List<String> administrativeRequestUser = new ArrayList<>();
        public List<String> administrativeRequestedName = new ArrayList<>();
        public List<String> administrativeRequestFull = new ArrayList<>();
        public List<Integer> administrativeRequestID = new ArrayList<>();
        public List<String> accountRequestNamePool = new ArrayList<>();
    }

    private static RequestData requestData = new RequestData(); //Instantiate class as object

    public static boolean createRequestFile() {
        // Create a File object with the specified file path
        File file = new File(requestFilePath);
        
        // Check if the file already exists
        if(file.exists()) {
            // Print a message indicating that the file already exists
            MessageProcessor.processMessage(2, "Request File already exists!", true);
            // Return true to indicate that the file exists
            return true;
        }
        
        try {
            // Attempt to create a new file
            file.createNewFile();
            // Return true to indicate that the file creation was successful
            return true;
        } catch (Exception e) {
            // Handle any exceptions that occur during file creation
            
            // Process an error message indicating the exception
            MessageProcessor.processMessage(-2, e.toString(), true);
            
            // Create a StringWriter to capture the exception's stack trace
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            // Write the exception's stack trace to the StringWriter
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            
            // Process the captured stack trace
            MessageProcessor.processMessage(2, stackTrace, true);
            
            // Return false to indicate that file creation encountered an error
            return false;
        }
    }


    @SuppressWarnings("unlikely-arg-type")
	public static boolean loadJson() {
        try {
            Gson gson = new Gson(); // Create a Gson object for JSON deserialization
            BufferedReader reader = new BufferedReader(new FileReader(requestFilePath)); // Open a reader to read from the JSON file
            requestData = gson.fromJson(reader, RequestData.class);         // Deserialize JSON data into requestData object
            reader.close(); // Close the reader after deserialization (Memory management)
            // Check if requestData is not null
            if(requestData != null) {
                // Print the deserialized requestData for debugging
                MessageProcessor.processMessage(2, requestData.toString(), true);
            }
         // Clear existing data from various lists before updating
            AdministrativeFunctions.administrativeRequestedName.clear();
            AdministrativeFunctions.administrativeRequestID.clear();
            AdministrativeFunctions.administrativeRequestUser.clear();
            AdministrativeFunctions.administrativeRequestKeyWord.clear();
            AdministrativeFunctions.administrativeRequests.clear();
            AdministrativeFunctions.administrativeRequestFull.clear();
            AdministrativeFunctions.accountRequestNamePool.clear();
         // Check if requestData is null
            if (requestData == null) {
                // If null, return false to indicate failure
                return false;
            }
            // Loop through administrative request data and update lists
            for (int i = 0; i < requestData.administrativeRequestID.size(); i++) {
                // Check if data is already contained in various lists
            	if(!AdministrativeFunctions.administrativeRequestedName.contains(requestData.administrativeRequestedName)) {
                    AdministrativeFunctions.administrativeRequestedName.add(requestData.administrativeRequestedName.get(i));
            	}else {
                    // If already contained, log and return false
            		MessageProcessor.processMessage(-1, "AdministrativeRequestedName already contained inside requestData", true);
            		return false;
            	}
            	if(!AdministrativeFunctions.administrativeRequestID.contains(requestData.administrativeRequestID)) {
            		AdministrativeFunctions.administrativeRequestID.add(requestData.administrativeRequestID.get(i));
            	}else {
            		MessageProcessor.processMessage(-1, "AdministrativeRequestedID already contained inside requestData", true);
            		return false;
            	}
            	if(!AdministrativeFunctions.administrativeRequestUser.contains(requestData.administrativeRequestUser)) {
            		AdministrativeFunctions.administrativeRequestUser.add(requestData.administrativeRequestUser.get(i));
            	}else {
            		MessageProcessor.processMessage(-1, "AdministrativeRequestedUser already contained inside requestData", true);
            		return false;
            	}
            	if(!AdministrativeFunctions.administrativeRequestKeyWord.contains(requestData.administrativeRequestKeyWord)) {
            		AdministrativeFunctions.administrativeRequestKeyWord.add(requestData.administrativeRequestKeyWord.get(i));
            	}else {
            		MessageProcessor.processMessage(-1, "AdministrativeRequesteKeyWord already contained inside requestData", true);
            		return false;
            	}
            	if(!AdministrativeFunctions.administrativeRequests.contains(requestData.administrativeRequests)) {
            		AdministrativeFunctions.administrativeRequests.add(requestData.administrativeRequests.get(i));
            	}else {
            		MessageProcessor.processMessage(-1, "AdministrativeRequests already contained inside requestData", true);
            		return false;
            	}
            	if(!AdministrativeFunctions.administrativeRequestFull.contains(requestData.administrativeRequestFull)) {
            		AdministrativeFunctions.administrativeRequestFull.add(requestData.administrativeRequestFull.get(i));
            	}else {
            		MessageProcessor.processMessage(-1, "AdministrativeRequestedFull already contained inside requestData", true);
            		return false;
            	}
            	if(!AdministrativeFunctions.accountRequestNamePool.contains(requestData.accountRequestNamePool)) {
            		AdministrativeFunctions.accountRequestNamePool.add(requestData.accountRequestNamePool.get(i));
            	}else {
            		MessageProcessor.processMessage(-1, "AccountRequestNamePool already contained inside requestData", true);
            		return false;
            	}
            }
            // Print updated lists and requestData for debugging
            MessageProcessor.processMessage(2, AdministrativeFunctions.administrativeRequestedName.toString(), false);
            MessageProcessor.processMessage(2, requestData.toString(), false);
            return true;        // Return true to indicate successful loading of JSON
        } catch (IOException e) {
        	// Handle IOException if file reading fails
            // Process error messages and stack trace
        	MessageProcessor.processMessage(2, "Error saving JSON: " + e.getMessage(), true);
            MessageProcessor.processMessage(-2, e.toString(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            MessageProcessor.processMessage(2, stackTrace, true);
            // Dump log and return false to indicate failure
            LogDump.DumpLog("ALL");
            return false;
        }
    }

    public static boolean saveJson() {
        try {
            createRequestFile();
            Gson gson = new Gson();
            String json = gson.toJson(requestData);
            BufferedWriter writer = new BufferedWriter(new FileWriter(requestFilePath));
            writer.write(json + "\n");
            writer.close();
            return true;
        } catch (IOException e) {
            MessageProcessor.processMessage(2, "Error saving JSON: " + e.getMessage(), true);
            MessageProcessor.processMessage(-2, e.toString(), true);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            MessageProcessor.processMessage(2, stackTrace, true);
            LogDump.DumpLog("ALL");
            return false;
        }
    }


    public static boolean addRequest(String newAccountName, int id, String path, String request, String description, String temp) {
        loadJson(); //Load Json Data (Requests back into System)
        requestData = new RequestData();
        requestData.administrativeRequestedName.add(newAccountName);
        requestData.administrativeRequestID.add(id);
        requestData.administrativeRequestUser.add(SwitchController.focusUser);
        requestData.administrativeRequestKeyWord.add(request);
        requestData.administrativeRequests.add(description);
        requestData.administrativeRequestFull.add(temp);
        MessageProcessor.processMessage(2, id + " was added to JSON list Successfully!", true);
        MessageProcessor.processMessage(1, id + " " + path + " Successfully added to request list!", true);
        saveJson(); //Save Requests as JsonData (requests to file)
        return true;
    }

    

    public static boolean removeRequestByID(int id) {
    	if(requestData.administrativeRequestID.size() == 0) {
    		MessageProcessor.processMessage(-1, "Failed to find any Messages to remove", true);
    		return false;
    	}
    	int index;
    	if(requestData.administrativeRequestID.contains(id)) {
    		index = requestData.administrativeRequestID.indexOf(id);
    		if(requestData.accountRequestNamePool.size() > 0) 
        		requestData.accountRequestNamePool.remove(index);
    		
    		if(requestData.administrativeRequestedName.size() > 0)
    			requestData.administrativeRequestedName.remove(index);
    		
    		if(requestData.administrativeRequestFull.size() > 0)
    			requestData.administrativeRequestFull.remove(index);
    		
    		if(requestData.administrativeRequestID.size() > 0)
    			requestData.administrativeRequestID.remove(index);
    		
    		if(requestData.administrativeRequestKeyWord.size() > 0)
    			requestData.administrativeRequestKeyWord.remove(index);
    		
    		if(requestData.administrativeRequestUser.size() > 0)
    			requestData.administrativeRequestUser.remove(index);
    		
    		saveJson();
    		loadJson();
    		return true;
    	}
        return false; //fallback if unable requestData (id Array) did not contain the (id) 
    }
}
