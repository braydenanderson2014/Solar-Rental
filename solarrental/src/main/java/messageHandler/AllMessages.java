package messageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AllMessages {
    private static final String MM_DD_YYYY_HH_MM_SS = "MM-dd-yyyy HH:mm:ss";
	public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(MM_DD_YYYY_HH_MM_SS);
	private static DateTimeFormatter myFormatOBJ1 = myFormatObj;
    public static String dTime  = myDateObj.format(myFormatOBJ1);
    public static List<String> allMessages = new ArrayList<>();
    public static List<String> allMessagesT = new ArrayList<>();
    public static List<Boolean> visibleToConsole = new ArrayList<>();
    public static String addMessage(String message, boolean visibleToConsoles){
        allMessages.add(message);
        allMessagesT.add("[" + getTime() + "]" + message);
        visibleToConsole.add(visibleToConsoles);
        return message;
    }

    public static boolean clearMessages(){
        allMessages.clear();
        allMessagesT.clear();
        visibleToConsole.clear();
        return true;
    }

    public static int size(){// Normal array (Get) size
        return allMessages.size();
    }

    public static String getLastMessage() {
        int size = allMessages.size();
        size --;
        if(size > 0){
            if(ConsoleSettings.timeSet){
                return allMessagesT.get(size);
            }else if(!ConsoleSettings.timeSet){
                return allMessages.get(size);
            }else {
                MessageProcessor.processMessage(-2, "An Error Occured While Getting Time Setting (E)", true);
                return "";
            }
        }else {
            return "No Messages";
        }
    }

    public static boolean setLastVisibleSet(boolean b) {
        int size = visibleToConsole.size();
        size --;
        visibleToConsole.set(size, b);
        return true;
    }

    public static String getTime(){
        myDateObj = LocalDateTime.now();
        myFormatOBJ1 = DateTimeFormatter.ofPattern(MM_DD_YYYY_HH_MM_SS);
        dTime  = myDateObj.format(myFormatOBJ1);
        return dTime;
    }
}
