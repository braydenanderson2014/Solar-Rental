package messageHandler;

public class Console {
    public static String getConsole(){
        System.out.println();
        System.out.println("Console: ");
        if(!NotificationMessages.getLastMessage().equals("")){
            if(NotificationMessages.getLastVisibleSet() == true){
                System.out.println(NotificationMessages.getLastMessage());
                //NotificationMessages.setLastVisibleSet(false);
            }
        }
        if(!WarningMessages.getLastMessage().equals("")){
            if(WarningMessages.getLastVisibleSet() == true){
                System.out.println(WarningMessages.getLastMessage());
                //WarningMessages.setLastVisibleSet(false);
            }
        }
        if(!SystemMessages.getLastMessage().equals("")){
            if(SystemMessages.getLastVisibleSet() == true){
                System.out.println(SystemMessages.getLastMessage());
                //SystemMessages.setLastVisibleSet(false);
            }
        }
        if(!ErrorMessages.getLastMessage().equals("")){
            if(ErrorMessages.getLastVisibleSet() == true){
                System.out.println(ErrorMessages.getLastMessage());
                //ErrorMessages.setLastVisibleSet(false);
            }
        }
        return "";
    }
}
