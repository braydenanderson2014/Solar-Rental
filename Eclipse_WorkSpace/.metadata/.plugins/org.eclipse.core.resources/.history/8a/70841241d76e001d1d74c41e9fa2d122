package messageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ErrorMessages {
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static List<String> ErrorMessages = new ArrayList<>();
    public static List<String> ErrorMessagesT = new ArrayList<>();
    public static List<Boolean> visibleToConsole = new ArrayList<>();
    public static String addMessage(String message, boolean VisibleToConsole){
        myDateObj = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        ErrorMessages.add("[Error]: " + message);
        ErrorMessagesT.add("[" + dTime + "][Error]: " + message);
        visibleToConsole.add(VisibleToConsole);
        AllMessages.addMessage("[Error]: " + message, VisibleToConsole);
        return message;
    }

    public static int size(){// Normal array (Get) size
        return ErrorMessages.size();
    }

    public static int tSize(){ //Time array (Get) size
        return ErrorMessagesT.size();
    }

    public static boolean clearMessages(){
        ErrorMessages.clear();
        ErrorMessagesT.clear();
        visibleToConsole.clear();
        return true;
    }

    public static boolean getLastVisibleSet(){
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

    public static String getLastMessage() {
        int size = ErrorMessages.size();
        size --;
        if(ConsoleSettings.ErrorSet){
            if(size > 0){
                if(ConsoleSettings.timeSet){
                    return ErrorMessagesT.get(size);
                }else if(!ConsoleSettings.timeSet){
                    return ErrorMessages.get(size);
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
}
