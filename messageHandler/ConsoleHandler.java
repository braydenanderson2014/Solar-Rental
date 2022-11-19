package messageHandler;

import java.util.ArrayList;
import java.util.List;

import MainSystem.SettingsController;

public class ConsoleHandler{
	public static int showHowMany = ConsoleSettings.max;
	public static int howManyTimes = 0;
	public static int size;
	public static List<String>messages = new ArrayList<>();
	public static List<String>messagesT = new ArrayList<>();
	public static List<Integer> indexesToRemove = new ArrayList<>();
	public static String getConsole() throws IndexOutOfBoundsException{
		MessageProcessor.gatherVisible();
		size = MessageProcessor.gatheredMessages.size();
		howManyTimes = 0;
		showHowMany = Integer.parseInt(SettingsController.getSetting("MaxConsole"));
		if(size == 0) {
			System.out.println("NO MESSAGES ON THE CONSOLE, CHECK YOUR SETTINGS");
			return "";
		}else if(size < showHowMany) {
			showHowMany = MessageProcessor.gatheredMessages.size();
			showHowMany --;
		}
		size --;
		size = size - showHowMany;
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
	private static Boolean checkSettings() {
		SettingsController.loadSettings();
		MessageProcessor.processMessage(1, "Checking Console Settings", true);
		String anObject = "false";
		if(SettingsController.getSetting("SystemSet").equals(anObject) || !ConsoleSettings.SystemSet) {
			MessageProcessor.processMessage(1, "Removing System Messages", true);
			for(int i = 0; i < MessageProcessor.gatheredMessages.size(); i++) {
				if(MessageProcessor.gatheredMessages.get(i).contains("[System]: ")) {
					MessageProcessor.processMessage(1, "Found System messages, Removing now", true);
					indexesToRemove.add(i);
					MessageProcessor.processMessage(1, String.valueOf(i), true);
				}
			}
			int temp = 0;
			for(int i = 0; i < indexesToRemove.size(); i++) {
				MessageProcessor.processMessage(1, String.valueOf(i), true);
				MessageProcessor.processMessage(1, MessageProcessor.gatheredMessages.get(indexesToRemove.get(i)), true);
				temp = indexesToRemove.get(i);
				MessageProcessor.gatheredMessages.remove(temp);
				MessageProcessor.gatheredMessages.remove(temp);
				
			}
			indexesToRemove.clear();
		}else {
			MessageProcessor.processMessage(1, "System Messages Enabled", false);
		}
		
		if(SettingsController.getSetting("ErrorSet").equals(anObject) || !ConsoleSettings.ErrorSet) {
			for(int i = 0; i < MessageProcessor.gatheredMessages.size(); i++) {
				if(MessageProcessor.gatheredMessages.get(i).contains("[Error]:")) {
					indexesToRemove.add(i);
					MessageProcessor.processMessage(1, String.valueOf(i), true);
				}
			}
			int temp = 0;
			for(int i = 0; i < indexesToRemove.size(); i++) {
				MessageProcessor.processMessage(1, String.valueOf(i), true);
				MessageProcessor.processMessage(1, MessageProcessor.gatheredMessages.get(indexesToRemove.get(i)), true);
				temp = indexesToRemove.get(i);
				MessageProcessor.gatheredMessages.remove(temp);
				MessageProcessor.gatheredMessages.remove(temp);
				
			}
			indexesToRemove.clear();
		}else {
			MessageProcessor.processMessage(1, "Error Messages Enabled", false);
		}
		
		if(SettingsController.getSetting("WarningSet").equals(anObject) || !ConsoleSettings.WarningSet) {
			for(int i = 0; i < MessageProcessor.gatheredMessages.size(); i++) {
				if(MessageProcessor.gatheredMessages.get(i).contains("[Warning]:")) {
					indexesToRemove.add(i);
					MessageProcessor.processMessage(1, String.valueOf(i), true);
				}
			}
			int temp = 0;
			for(int i = 0; i < indexesToRemove.size(); i++) {
				MessageProcessor.processMessage(1, String.valueOf(i), true);
				MessageProcessor.processMessage(1, MessageProcessor.gatheredMessages.get(indexesToRemove.get(i)), true);
				temp = indexesToRemove.get(i);
				MessageProcessor.gatheredMessages.remove(temp);
				MessageProcessor.gatheredMessages.remove(temp);
				
			}
			indexesToRemove.clear();
		}else {
			MessageProcessor.processMessage(1, "Warning Messages Enabled", false);
		}
		
		if(SettingsController.getSetting("UserNotifySet").equals(anObject) || !ConsoleSettings.UserNotifySet) {
			for(int i = 0; i < MessageProcessor.gatheredMessages.size(); i++) {
				if(MessageProcessor.gatheredMessages.get(i).contains("[Notification]:")) {
					indexesToRemove.add(i);
					MessageProcessor.processMessage(1, String.valueOf(i), true);
				}
			}
			int temp = 0;
			for(int i = 0; i < indexesToRemove.size(); i++) {
				MessageProcessor.processMessage(1, String.valueOf(i), true);
				MessageProcessor.processMessage(1,MessageProcessor.gatheredMessages.get(indexesToRemove.get(i)), true);
				temp = indexesToRemove.get(i);
				MessageProcessor.gatheredMessages.remove(temp);
				MessageProcessor.gatheredMessages.remove(temp);
				
			}
			indexesToRemove.clear();
		}else {
			MessageProcessor.processMessage(1, "User Notification Messages Enabled", false);
		}
		
		return true;
	}
}