/**
 * 
 */
package messageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brayden Anderson
 *
 */
public class MessageProcessor {
	public static List<String> allMessages = new ArrayList<>();
	public static List<String> allMessagesT = new ArrayList<>();
	public static List<Boolean> visibleToConsoles = new ArrayList<>();
	public static List<String> gatheredMessages = new ArrayList<>();
	public static List<String> gatheredMessagesT = new ArrayList<>();
 	private static final String MM_DD_YYYY_HH_MM_SS = "MM-dd-yyyy HH:mm:ss";
	public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(MM_DD_YYYY_HH_MM_SS);
	private static DateTimeFormatter myFormatOBJ1 = myFormatObj;
    public static String dTime  = myDateObj.format(myFormatOBJ1);
	public static String processMessage(int messageType, String message, boolean visibleToConsole) {
		String messageTypeProcessed;
		if(messageType == -2) {
			messageTypeProcessed = "Error";
			ErrorMessages.addMessage("[" + messageTypeProcessed + "]" + message, visibleToConsole);
		}else if(messageType == -1) {
			messageTypeProcessed = "Warning";
			WarningMessages.addMessage("[" + messageTypeProcessed + "]" + message, visibleToConsole);
		}else if(messageType == 1) {
			messageTypeProcessed = "System";
			SystemMessages.addMessage("[" + messageTypeProcessed + "]" + message, visibleToConsole);
		}else if(messageType == 2) {
			messageTypeProcessed = "Notification";
			NotificationMessages.addMessage("[" + messageTypeProcessed + "]" + message, visibleToConsole);
		}else {
			messageTypeProcessed = "Invalid Message Type";
			return messageTypeProcessed;
		}
		String getTime = getTime();
		allMessages.add("[" + messageTypeProcessed + "]" + message);
		allMessagesT.add("[" + getTime + "][" + messageTypeProcessed + "]" + message);
		AllMessages.addMessage("[" + messageTypeProcessed + "]" + message, visibleToConsole);
		visibleToConsoles.add(visibleToConsole);
		return "";
	}
	public static String getTime(){
        myDateObj = LocalDateTime.now();
        myFormatOBJ1 = DateTimeFormatter.ofPattern(MM_DD_YYYY_HH_MM_SS);
        dTime  = myDateObj.format(myFormatOBJ1);
        return dTime;
    }
	public static String gatherVisible() {
		gatheredMessages.clear();
		for(int i = 0; i < allMessages.size(); i++) {
			if(Boolean.TRUE.equals(visibleToConsoles.get(i))) {
				gatheredMessages.add(allMessages.get(i));
				gatheredMessagesT.add(allMessagesT.get(i));
			}
		}
		return "";
	}
	public static boolean dumpAll(){
        LogDump.DumpLog("all");
        return true;
    }
}