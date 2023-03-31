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
	public static String getConsole() {
		// Load settings and gather visible messages
		SettingsController.loadSettings();
		MessageProcessor.gatherVisible();
		int totalMessages = MessageProcessor.gatheredMessages.size();
		checkSettings();
	
		// Determine how many messages to show
		int messagesToShow;
		if (SettingsController.getSetting("FirstTime").equals("true")) {
			messagesToShow = 5;
		} else {
			messagesToShow = Integer.parseInt(SettingsController.getSetting("MaxConsole"));
		}
	
		// If there are no messages
		if (totalMessages == 0) {
			System.out.println("NO MESSAGES ON THE CONSOLE, CHECK YOUR SETTINGS");
			return "";
		}
	
		// Calculate starting index for messages to display
		int startIndex = Math.max(0, totalMessages - messagesToShow);
	
		// Display messages
		System.out.println("CONSOLE: ");
		for (int i = startIndex; i < totalMessages; i++) {
			if (SettingsController.getSetting("Date/TimeSet").equals("true")) {
				System.out.println(MessageProcessor.gatheredMessagesT.get(i));
				MessageProcessor.processMessage(1, MessageProcessor.gatheredMessagesT.get(i), false);
			} else {
				System.out.println(MessageProcessor.gatheredMessages.get(i));
				MessageProcessor.processMessage(1, MessageProcessor.gatheredMessages.get(i), false);
			}
		}
	
		// Clear gathered messages
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
	
		String systemSet = SettingsController.getSetting("SystemSet");
		if (systemSet != null && systemSet.equals("false")) {
			removeMessagesByType("[System]: ");
		}
	
		String errorSet = SettingsController.getSetting("ErrorSet");
		if (errorSet != null && errorSet.equals("false")) {
			removeMessagesByType("[Error]:");
		}
	
		String warningSet = SettingsController.getSetting("WarningSet");
		if (warningSet != null && warningSet.equals("false")) {
			removeMessagesByType("[Warning]:");
		}
	
		String userNotifySet = SettingsController.getSetting("UserNotifySet");
		if (userNotifySet != null && userNotifySet.equals("false")) {
			removeMessagesByType("[Notification]:");
		}
	
		String debugSet = SettingsController.getSetting("DebugSet");
		if (debugSet != null && debugSet.equals("false")) {
			removeMessagesByType("[Debug]: ");
		}
	
		return true;
	}
	
	

}