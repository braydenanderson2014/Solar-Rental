package messageHandler;
public class MessageHandler{
    public static String handleMessage(int mode, String message, boolean visibleToConsole){
        if(!message.equals("")){
            if(mode == -2){
                return ErrorMessages.addMessage(message, visibleToConsole);
               
            }else if(mode == -1){
                return WarningMessages.addMessage(message, visibleToConsole);
 
            }else if(mode == 1){
                return SystemMessages.addMessage(message, visibleToConsole);
              
            }else if(mode == 2){
                return NotificationMessages.addMessage(message, visibleToConsole);
               
            }else{
                handleMessage(-1, "Invalid Message Route... [" + mode + "]", visibleToConsole);
                return WarningMessages.getLastMessage();
            }
        }else{
            handleMessage(-2, "Received Improper message on mode channel " + mode, visibleToConsole);
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
            handleMessage(-1, "Invalid Message Clear Route... [" + mode + "]", visibleToConsole);
            return false;
        }
    }

    public static boolean dumpAll(){
        LogDump.DumpLog("all");
        return true;
    }
}