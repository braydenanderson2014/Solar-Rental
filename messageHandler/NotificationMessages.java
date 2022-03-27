package messageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NotificationMessages{
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static ArrayList<String> NotificationMessages = new ArrayList<String>();
    public static ArrayList<String> NotificationMessagesT = new ArrayList<String>();
    public static ArrayList<Boolean> visibleToConsole = new ArrayList<Boolean>();
    public static String addMessage(String message, boolean VisibleToConsole){
        myDateObj = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        NotificationMessages.add("[Notification]: " + message);
        NotificationMessagesT.add("[" + dTime + "][Notification]: " + message);
        visibleToConsole.add(VisibleToConsole);
        AllMessages.addMessage("[Notification]: " + message, VisibleToConsole);
        messageHandler.HandleMessage(1, "Notification Message Size: " + size(), false);
        messageHandler.HandleMessage(1, "Visible to Console Size: " + visibleToConsole.size(), false);
        messageHandler.HandleMessage(1, "Last Message: " + message , false);
        messageHandler.HandleMessage(1, "Last Visible Set: " + getLastVisibleSet(), false);
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
        if(ConsoleSettings.UserNotifySet == true){
            if(size > 0){
                if(ConsoleSettings.timeSet == true){
                    return NotificationMessagesT.get(size);
                }else if(ConsoleSettings.timeSet == false){
                    return NotificationMessages.get(size);
                }else {
                    messageHandler.HandleMessage(-2, "An Error Occured While Getting Time Setting (E)", true);
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