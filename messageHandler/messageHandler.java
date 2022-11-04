package messageHandler;
public class messageHandler{
    public static String HandleMessage(int mode, String message, boolean visibleToConsole){
        if(!message.equals("")){
            if(mode == -2){
                ErrorMessages.addMessage(message, visibleToConsole);
                return message;
            }else if(mode == -1){
                WarningMessages.addMessage(message, visibleToConsole);
                return message;
            }else if(mode == 1){
                SystemMessages.addMessage(message, visibleToConsole);
                return message;
            }else if(mode == 2){
                NotificationMessages.addMessage(message, visibleToConsole);
                return message;
            }else{
                HandleMessage(-1, "Invalid Message Route... [" + mode + "]", visibleToConsole);
                return WarningMessages.getLastMessage();
            }
        }else{
            HandleMessage(-2, "Received Improper message on mode channel " + mode, visibleToConsole);
            return ErrorMessages.getLastMessage();
        } 
    }

    public static boolean clearMessages(int mode, boolean visibleToConsole){
        if(mode == -2){
            ErrorMessages.clearMessages();
            return true;
        }else if(mode == -1){
            WarningMessages.clearMessages();        
            return true;
        }else if(mode == 0){
            AllMessages.clearMessages();
            return true;
        }else if(mode == 1){
            SystemMessages.clearMessages();
            return true;
        }else if(mode == 2){
            NotificationMessages.clearMessages();
            return true;
        }else{
            HandleMessage(-1, "Invalid Message Clear Route... [" + mode + "]", visibleToConsole);
            return false;
        }
    }

    public static boolean dumpAll(){
        LogDump.DumpLog("all");
        return true;
    }
}