package messageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NotificationMessages{
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static List<String> NotificationMessages = new ArrayList<>();
    public static List<String> NotificationMessagesT = new ArrayList<>();
    public static List<Boolean> visibleToConsole = new ArrayList<>();
    public static String addMessage(String message, boolean VisibleToConsole){
        myDateObj = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        NotificationMessages.add(message);
        NotificationMessagesT.add("[" + dTime + "]" + message);
        visibleToConsole.add(VisibleToConsole);
        return message;
    }

    public static boolean clearMessages(){
        NotificationMessages.clear();
        NotificationMessagesT.clear();
        visibleToConsole.clear();
        return true;
    }

    public static int size(){
        return NotificationMessages.size();
    }

    public static int tSize(){
        return NotificationMessagesT.size();
    }

    public static String getLastMessage() {
        int size = NotificationMessages.size();
        size --;
        if(ConsoleSettings.UserNotifySet){
            if(size > 0){
                if(ConsoleSettings.timeSet){
                    return NotificationMessagesT.get(size);
                }else if(!ConsoleSettings.timeSet){
                    return NotificationMessages.get(size);
                }else {
                    MessageProcessor.processMessage(-2, "An Error Occured While Getting Time Setting (E)", true);
                    return "";
                }
            }else {
                return "";
            }
        }else{
            return "";
        }
    }

    public static boolean getLastVisibleSet() {
        int size = visibleToConsole.size();
        size --;
        return visibleToConsole.get(size);
    }

    public static boolean setLastVisibleSet(boolean b) {
        int size = visibleToConsole.size();
        size --;
        visibleToConsole.set(size, b);
        return true;
    }
}