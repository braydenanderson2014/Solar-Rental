package messageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AllMessages {
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static ArrayList<String> AllMessages = new ArrayList<String>();
    public static ArrayList<String> AllMessagesT = new ArrayList<String>();
    public static ArrayList<Boolean> visibleToConsole = new ArrayList<Boolean>();
    public static String addMessage(String message, boolean VisibleToConsole){
        AllMessages.add(message);
        myDateObj = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        AllMessagesT.add("[" + dTime + "]" + message);
        visibleToConsole.add(VisibleToConsole);
        return message;
    }
    
    public static boolean clearMessages(){
        AllMessages.clear();
        AllMessagesT.clear();
        visibleToConsole.clear();
        return true;
    }
    public static int size(){// Normal array (Get) size
        return AllMessages.size();
    }
    public static String getLastMessage() {
        int size = AllMessages.size();
        size --;
        if(size > 0){
            if(ConsoleSettings.timeSet == true){
                return AllMessagesT.get(size);
            }else if(ConsoleSettings.timeSet == false){
                return AllMessages.get(size);
            }else {
                messageHandler.HandleMessage(-2, "An Error Occured While Getting Time Setting (E)", true);
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
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        return dTime;
    }
}
