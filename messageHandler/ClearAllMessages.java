package messageHandler;

public class ClearAllMessages {
    public static boolean clearAll(){
        AllMessages.clearMessages();
        ErrorMessages.clearMessages();
        WarningMessages.clearMessages();
        NotificationMessages.clearMessages();
        SystemMessages.clearMessages();
        DebugMessages.clearMessages();
        return true;
    }
}
