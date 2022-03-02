package messageHandler;
public class messageHandler{
    public static String HandleMessage(int mode, String message){
        if(!message.equals("")){
            if(mode == -2){
                ErrorMessages.addMessage(message);
                return message;
            }else if(mode == -1){
                WarningMessages.addMessage(message);
                return message;
            }else if(mode == 1){
                SystemMessages.addMessage(message);
                return message;
            }else if(mode == 2){
                NotificationMessages.addMessage(message);
                return message;
            }else{
                HandleMessage(-1, "Invalid Message Route... [" + mode + "]");
                return WarningMessages.getLastMessage();
            }
        }else{
            HandleMessage(-2, "Received Improper message on mode channel " + mode);
            return ErrorMessages.getLastMessage();
        } 
    }
    public static boolean clearMessages(int mode){
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
            HandleMessage(-1, "Invalid Message Clear Route... [" + mode + "]");
            return false;
        }
    }
    public static boolean dumpAll(){
        LogDump.DumpLog("ALL");
        return true;
    }
}