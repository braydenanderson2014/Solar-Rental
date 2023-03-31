package messageHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class SystemMessages{
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static List<String> SystemMessages = new ArrayList<>();
    public static List<String> SystemMessagesT = new ArrayList<>();
    public static List<Boolean> visibleToConsole = new ArrayList<>();
    public static String addMessage(String message, Boolean VisibleToConsole){
        myDateObj = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        SystemMessages.add(message);
        SystemMessagesT.add("[" + dTime + "]" + message);
        visibleToConsole.add(VisibleToConsole);
        return message;
    }

    public static boolean clearMessages(){
        SystemMessages.clear();
        SystemMessagesT.clear();
        visibleToConsole.clear();
        return true;
    }

    public static int size(){
        return SystemMessages.size();
    }

    public static int tSize(){
        return SystemMessagesT.size();
    }

    public static String getLastMessage() {
        int size = SystemMessages.size();
        size --;
        if(ConsoleSettings.SystemSet){
            if(size > 0){
                if(ConsoleSettings.timeSet){
                    return SystemMessagesT.get(size);
                }else if(!ConsoleSettings.timeSet){
                    return SystemMessages.get(size);
                }else {
                    MessageProcessor.processMessage(-2, "An Error Occured While Getting Time Setting (E)", true);
                    return "";
                }
            }else {
                return "";
            }
        }else {
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
        size--;
        return visibleToConsole.get(size);
    }
}