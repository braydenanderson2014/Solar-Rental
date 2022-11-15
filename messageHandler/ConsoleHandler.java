package messageHandler;

import java.util.ArrayList;
import java.util.List;

import MainSystem.SettingsController;

public class ConsoleHandler{
	public static int showHowMany = 5;
	public static int howManyTimes = 0;
	public static List<String>Messages = new ArrayList<>();
	public static List<String>MessagesT = new ArrayList<>();
	public static List<Integer> indexesToRemove = new ArrayList<>();
	public static String getConsole() {
		Messages.clear();
		MessagesT.clear();
		Messages = AllMessages.AllMessages;
		MessagesT = AllMessages.AllMessagesT;
		howManyTimes = 0;
		checkSettings();
		int size = Messages.size();
		if(size < showHowMany) {
			showHowMany = Messages.size();
		}
		size --;
		size = size - showHowMany;
		System.out.println("CONSOLE: ");
		while(howManyTimes < showHowMany) {
			if(SettingsController.getSetting("Date/TimeSet").equals("true")) {
				System.out.println(MessagesT.get(size));
			}else {
				System.out.println(Messages.get(size));
			}
			howManyTimes++;
			size++;
		}
		return "";
	}
	private static Boolean checkSettings() {
		if(!SettingsController.getSetting("SystemSet").equals("true")) {
			for(int i = 0; i < Messages.size(); i++) {
				if(Messages.contains("[System]:")) {
					indexesToRemove.add(i);
				}
			}
			for(int i = 0; i < indexesToRemove.size(); i++) {
				Messages.remove(indexesToRemove.get(i));
			}
			indexesToRemove.clear();
		}
		
		if(!SettingsController.getSetting("ErrorSet").equals("true")) {
			for(int i = 0; i < Messages.size(); i++) {
				if(Messages.contains("[Error]:")) {
					indexesToRemove.add(i);
				}
			}
			for(int i = 0; i < indexesToRemove.size(); i++) {
				Messages.remove(indexesToRemove.get(i));
			}
			indexesToRemove.clear();
		}
		
		if(!SettingsController.getSetting("WarningSet").equals("true")) {
			for(int i = 0; i < Messages.size(); i++) {
				if(Messages.contains("[Warning]:")) {
					indexesToRemove.add(i);
				}
			}
			for(int i = 0; i < indexesToRemove.size(); i++) {
				Messages.remove(indexesToRemove.get(i));
			}
			indexesToRemove.clear();
		}
		
		if(!SettingsController.getSetting("UserNotifySet").equals("true")) {
			for(int i = 0; i < Messages.size(); i++) {
				if(Messages.contains("[Notification]:")) {
					indexesToRemove.add(i);
				}
			}
			for(int i = 0; i < indexesToRemove.size(); i++) {
				Messages.remove(indexesToRemove.get(i));
			}
			indexesToRemove.clear();
		}
		
		return true;
	}
}