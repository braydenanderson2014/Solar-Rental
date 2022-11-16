package messageHandler;

import java.util.ArrayList;
import java.util.List;

import MainSystem.SettingsController;

public class ConsoleHandler{
	public static int showHowMany = ConsoleSettings.max;
	public static int howManyTimes = 0;
	public static int size;
	public static List<String>Messages = new ArrayList<>();
	public static List<String>MessagesT = new ArrayList<>();
	public static List<Integer> indexesToRemove = new ArrayList<>();
	public static String getConsole() throws IndexOutOfBoundsException{
		Messages.clear();
		MessagesT.clear();
		for(int i = 0; i < AllMessages.AllMessages.size(); i++) {
			Messages.add(AllMessages.AllMessages.get(i));
			MessagesT.add(AllMessages.AllMessagesT.get(i));
		}
		howManyTimes = 0;
		checkSettings();
		size = Messages.size();
		if(size < showHowMany) {
			showHowMany = Messages.size();
		}
		size --;
		size = size - showHowMany;
		System.out.println("CONSOLE: ");
		while(howManyTimes < showHowMany) {
			if(SettingsController.getSetting("Date/TimeSet").equals("true")) {
				if(Boolean.TRUE.equals(AllMessages.visibleToConsole.get(size))) {
					System.out.println(MessagesT.get(size));
				}
			}else {
				if(Boolean.TRUE.equals(AllMessages.visibleToConsole.get(size))) {
					System.out.println(Messages.get(size));
				}
			}
			howManyTimes++;
			size++;
		}
		return "";
	}
	private static Boolean checkSettings() {
		SettingsController.loadSettings();
		messageHandler.HandleMessage(1, "Checking Console Settings", true);
		String anObject = "false";
		if(SettingsController.getSetting("SystemSet").equals(anObject) || !ConsoleSettings.SystemSet) {
			for(int i = 0; i < Messages.size(); i++) {
				if(Messages.contains("[System]: ")) {
					indexesToRemove.add(i);
					messageHandler.HandleMessage(1, String.valueOf(i), true);
				}
			}
			for(int i = 0; i < indexesToRemove.size(); i++) {
				messageHandler.HandleMessage(1, String.valueOf(i), true);
				messageHandler.HandleMessage(1, Messages.get(indexesToRemove.get(i)), true);
				Messages.remove(indexesToRemove.get(i));
				MessagesT.remove(indexesToRemove.get(i));
				
			}
			indexesToRemove.clear();
		}else {
			messageHandler.HandleMessage(1, "System Messages Enabled", false);
		}
		
		if(SettingsController.getSetting("ErrorSet").equals(anObject) || !ConsoleSettings.ErrorSet) {
			for(int i = 0; i < Messages.size(); i++) {
				if(Messages.contains("[Error]:")) {
					indexesToRemove.add(i);
					messageHandler.HandleMessage(1, String.valueOf(i), true);
				}
			}
			for(int i = 0; i < indexesToRemove.size(); i++) {
				messageHandler.HandleMessage(1, String.valueOf(i), true);
				messageHandler.HandleMessage(1, Messages.get(indexesToRemove.get(i)), true);
				Messages.remove(indexesToRemove.get(i));
				MessagesT.remove(indexesToRemove.get(i));
			}
			indexesToRemove.clear();
		}else {
			messageHandler.HandleMessage(1, "Error Messages Enabled", false);
		}
		
		if(SettingsController.getSetting("WarningSet").equals(anObject) || !ConsoleSettings.WarningSet) {
			for(int i = 0; i < Messages.size(); i++) {
				if(Messages.contains("[Warning]:")) {
					indexesToRemove.add(i);
					messageHandler.HandleMessage(1, String.valueOf(i), true);
				}
			}
			for(int i = 0; i < indexesToRemove.size(); i++) {
				messageHandler.HandleMessage(1, String.valueOf(i), true);
				messageHandler.HandleMessage(1, Messages.get(indexesToRemove.get(i)), true);
				Messages.remove(indexesToRemove.get(i));
				MessagesT.remove(indexesToRemove.get(i));
			}
			indexesToRemove.clear();
		}else {
			messageHandler.HandleMessage(1, "Warning Messages Enabled", false);
		}
		
		if(SettingsController.getSetting("UserNotifySet").equals(anObject) || !ConsoleSettings.UserNotifySet) {
			for(int i = 0; i < Messages.size(); i++) {
				if(Messages.contains("[Notification]:")) {
					indexesToRemove.add(i);
					messageHandler.HandleMessage(1, String.valueOf(i), true);
				}
			}
			for(int i = 0; i < indexesToRemove.size(); i++) {
				messageHandler.HandleMessage(1, String.valueOf(i), true);
				messageHandler.HandleMessage(1, Messages.get(indexesToRemove.get(i)), true);
				Messages.remove(indexesToRemove.get(i));
				MessagesT.remove(indexesToRemove.get(i));
			}
			indexesToRemove.clear();
		}else {
			messageHandler.HandleMessage(1, "User Notificatoin Messages Enabled", false);
		}
		
		return true;
	}
}