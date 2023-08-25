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
    private static String defaultPath;
    //private static RequestData requestData = new RequestData();

    public static class RequestData {
        public List<String> administrativeRequests = new ArrayList<>();
        public List<String> administrativeRequestKeyWord = new ArrayList<>();
        public List<String> administrativeRequestUser = new ArrayList<>();
        public List<String> administrativeRequestedName = new ArrayList<>();
        public List<String> administrativeRequestFull = new ArrayList<>();
        public List<Integer> administrativeRequestID = new ArrayList<>();
        public List<String> accountRequestNamePool = new ArrayList<>();
    }

    private static RequestData requestData = new RequestData();

    public static boolean createRequestFile() {
    	File file = new File(requestFilePath);
    	if(file.exists()) {
    		MessageProcessor.processMessage(2, "Request File already exists!", true);
    		return true;
    	}
		try {
			file.createNewFile();
			return true;
		}catch(Exception e) {
			MessageProcessor.processMessage(-2, e.toString(), true);
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
		    String stackTrace = sw.toString();
		    MessageProcessor.processMessage(2, stackTrace, true);
		    return false;
		}
    }

    @SuppressWarnings("unlikely-arg-type")
	public static boolean loadJson() {
        try {
//        	if(RequestData.class.equals(null)) {
//            	return false;
//            }
//            if (requestData.administrativeRequestedName.size() == 0) {
//                return false;
//            }
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(requestFilePath));
            requestData = gson.fromJson(reader, RequestData.class);
            MessageProcessor.processMessage(2, requestData.toString(), true);
            reader.close();            
            AdministrativeFunctions.administrativeRequestedName.clear();
            AdministrativeFunctions.administrativeRequestID.clear();
            AdministrativeFunctions.administrativeRequestUser.clear();
            AdministrativeFunctions.administrativeRequestKeyWord.clear();
            AdministrativeFunctions.administrativeRequests.clear();
            AdministrativeFunctions.administrativeRequestFull.clear();
            for (int i = 0; i < requestData.administrativeRequestID.size(); i++) {
            	if(!AdministrativeFunctions.administrativeRequestedName.contains(requestData.administrativeRequestedName)) {
                    AdministrativeFunctions.administrativeRequestedName.add(requestData.administrativeRequestedName.get(i));
            	}else {
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
            }
            MessageProcessor.processMessage(2, AdministrativeFunctions.administrativeRequestedName.toString(), false);
            MessageProcessor.processMessage(2, requestData.toString(), false);
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

    public static boolean saveJson() {
        try {
            createRequestFile();
            Gson gson = new Gson();
            String json = gson.toJson(requestData);

            BufferedWriter writer = new BufferedWriter(new FileWriter(requestFilePath));
            writer.write(json);
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
        loadJson();
        
        requestData.administrativeRequestedName.add(newAccountName);
        requestData.administrativeRequestID.add(id);
        requestData.administrativeRequestUser.add(SwitchController.focusUser);
        requestData.administrativeRequestKeyWord.add(request);
        requestData.administrativeRequests.add(description);
        requestData.administrativeRequestFull.add(temp);
        
        MessageProcessor.processMessage(2, id + " was added to Properties list Successfully!", true);
        MessageProcessor.processMessage(1, id + " " + path + " Successfully added to request list!", true);
        saveJson();
        
        return true;
    }

    

    public static boolean removeRequest(int id) {
        // Add your logic to remove request here
        return false;
    }
}
