package messageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DebugMessages{
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static List<String> DebugMessages = new ArrayList<>();
    public static List<String> DebugMessagesT = new ArrayList<>();
    public static List<Boolean> visibleToConsole = new ArrayList<>();
    public static String addMessage(String message, boolean VisibleToConsole){
        myDateObj = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        DebugMessages.add("[Debug]: " + message);
        DebugMessagesT.add("[" + dTime + "][Warning]: " + message);
        visibleToConsole.add(VisibleToConsole);
        AllMessages.addMessage("[Debug]: " + message, VisibleToConsole);
        return message;
    }

    public static boolean clearMessages(){
        DebugMessages.clear();
        DebugMessagesT.clear();
        visibleToConsole.clear();
        return true;
    }

    public static int size(){
        return DebugMessages.size();
    }

    public static int tSize(){
        return DebugMessagesT.size();
    }

    public static String getLastMessage() {
        int size = DebugMessages.size();
        size --;
        if(ConsoleSettings.DebugSet){
            if(size > 0){
                if(ConsoleSettings.timeSet){
                    return DebugMessagesT.get(size);
                }else if(!ConsoleSettings.timeSet){
                    return DebugMessages.get(size);
                }else {
                    MessageProcessor.processMessage(-2, "An Error Occured While Getting Time Setting (E)", true);
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