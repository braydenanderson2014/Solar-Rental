package messageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WarningMessages{
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static ArrayList<String> WarningMessages = new ArrayList<String>();
    public static ArrayList<String> WarningMessagesT = new ArrayList<String>();
    public static ArrayList<Boolean> visibleToConsole = new ArrayList<Boolean>();
    public static String addMessage(String message, boolean VisibleToConsole){
        myDateObj = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        WarningMessages.add("[Warning]: " + message);
        WarningMessagesT.add("[" + dTime + "][Warning]: " + message);
        visibleToConsole.add(VisibleToConsole);
        AllMessages.addMessage("[Warning]: " + message, VisibleToConsole);
        return message;
    }
    public static boolean clearMessages(){
        WarningMessages.clear();
        WarningMessagesT.clear();
        visibleToConsole.clear();
        return true;
    }
    public static int size(){
        return WarningMessages.size();
    }
    public static int tSize(){
        return WarningMessagesT.size();
    }
    public static String getLastMessage() {
        int size = WarningMessages.size();
        size --;
        if(ConsoleSettings.WarningSet == true){
            if(size > 0){
                if(ConsoleSettings.timeSet == true){
                    return WarningMessagesT.get(size);
                }else if(ConsoleSettings.timeSet == false){
                    return WarningMessages.get(size);
                }else {
                    messageHandler.HandleMessage(-2, "An Error Occured While Getting Time Setting (E)", true);
                    return "";
                }
            }else {
                return "";
            }
        } else {
            return "";
        }
    }
    public static boolean setLastVisibleSet(boolean b) {
        int size = visibleToConsole.size();
        size --;
        visibleToConsole.set(size, b);
        return true;
    }
    public static boolean getLastVisibleSet() {
        int size = visibleToConsole.size();
        size --;
        return visibleToConsole.get(size);
    }
}