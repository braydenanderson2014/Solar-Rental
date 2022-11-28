package messageHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
public class SystemMessages{
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static ArrayList<String> SystemMessages = new ArrayList<String>();
    public static ArrayList<String> SystemMessagesT = new ArrayList<String>();
    public static ArrayList<Boolean> visibleToConsole = new ArrayList<Boolean>();
    public static String addMessage(String message, Boolean VisibleToConsole){
        myDateObj = LocalDateTime.now();
        myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        dTime  = myDateObj.format(myFormatObj);
        SystemMessages.add("[System]: " + message);
        SystemMessagesT.add("[" + dTime + "][System]: " + message);
        visibleToConsole.add(VisibleToConsole);
        AllMessages.addMessage("[System]: " + message, VisibleToConsole);
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
        if(ConsoleSettings.SystemSet == true){
            if(size > 0){
                if(ConsoleSettings.timeSet == true){
                    return SystemMessagesT.get(size);
                }else if(ConsoleSettings.timeSet == false){
                    return SystemMessages.get(size);
                }else {
                    messageHandler.HandleMessage(-2, "An Error Occured While Getting Time Setting (E)", true);
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