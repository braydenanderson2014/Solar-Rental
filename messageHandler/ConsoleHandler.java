package messageHandler;

import java.util.ArrayList;
import java.util.List;

import MainSystem.SettingsController;

public class ConsoleHandler{
	public static int showHowMany;
	public static int howManyTimes = 0;
	public static int size;
	public static List<String>messages = new ArrayList<String>();
	public static List<String>messagesT = new ArrayList<String>();
	public static List<Integer> indexesToRemove = new ArrayList<Integer>();
	public static String getConsole(){
		SettingsController.loadSettings();
		MessageProcessor.gatherVisible();
		size = MessageProcessor.gatheredMessages.size();
		checkSettings();
		howManyTimes = 0;
		if(SettingsController.getSetting("FirstTime").equals("true")) {
			showHowMany = 5;
		}else {
			showHowMany = Integer.parseInt(SettingsController.getSetting("MaxConsole"));
		}
		if(size == 0) {
			System.out.println("NO MESSAGES ON THE CONSOLE, CHECK YOUR SETTINGS");
			return "";
		}else if(size < showHowMany) {
			showHowMany = MessageProcessor.gatheredMessages.size();
			showHowMany --;
		}
		size --;
		size = size - showHowMany;
		if(size < 1) {
			return "";//Helps avoid an Index out of Bounds
		}
		System.out.println("CONSOLE: ");
		while(howManyTimes < showHowMany) {
			if(SettingsController.getSetting("Date/TimeSet").equals("true")) {
				System.out.println(MessageProcessor.gatheredMessagesT.get(size));
				MessageProcessor.processMessage(1, MessageProcessor.gatheredMessagesT.get(size), false);
			}else {
				System.out.println(MessageProcessor.gatheredMessages.get(size));
				MessageProcessor.processMessage(1, MessageProcessor.gatheredMessages.get(size), false);
			}
			howManyTimes++;
			size++;
		}
		MessageProcessor.gatheredMessages.clear();
		MessageProcessor.gatheredMessagesT.clear();
		return "";
	}
	public static String getAltConsole() {
		return "";
	}
	
	private static void removeMessagesByType(String messageType) {
	    for (int i = MessageProcessor.gatheredMessages.size() - 1; i >= 0; i--) {
	        if (MessageProcessor.gatheredMessages.get(i).contains(messageType)) {
	            MessageProcessor.gatheredMessages.remove(i);
	            MessageProcessor.gatheredMessagesT.remove(i);
	        }
	    }
	}

	private static Boolean checkSettings() {
	    SettingsController.loadSettings();
	    if(SettingsController.getSetting("FirstTime").equals("false") || SettingsController.getSetting("FirstTime")== null) {
	    	return false;
	    }
	    if (SettingsController.getSetting("SystemSet").equals("false") || !ConsoleSettings.SystemSet) {
	        removeMessagesByType("[System]: ");
	    }

	    if (SettingsController.getSetting("ErrorSet").equals("false") || !ConsoleSettings.ErrorSet) {
	        removeMessagesByType("[Error]:");
	    }

	    if (SettingsController.getSetting("WarningSet").equals("false") || !ConsoleSettings.WarningSet) {
	        removeMessagesByType("[Warning]:");
	    }

	    if (SettingsController.getSetting("UserNotifySet").equals("false") || !ConsoleSettings.UserNotifySet) {
	        removeMessagesByType("[Notification]:");
	    }
	    if(SettingsController.getSetting("DebugSet").equals("false") || !ConsoleSettings.DebugSet) {
	    	removeMessagesByType("[Debug]: ");
	    }

	    return true;
	}

}