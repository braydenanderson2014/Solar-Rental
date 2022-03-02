package messageHandler;

public class Console {
    public static String getConsole(){
        System.out.println();
        System.out.println("Console: ");
        if(!NotificationMessages.getLastMessage().contains("")){
            System.out.println(NotificationMessages.getLastMessage());
        }
        if(!WarningMessages.getLastMessage().equals("")){
            System.out.println(WarningMessages.getLastMessage());
        }
        if(!SystemMessages.getLastMessage().equals("")){
            System.out.println(SystemMessages.getLastMessage());
        }
        if(!ErrorMessages.getLastMessage().equals("")){
            System.out.println(ErrorMessages.getLastMessage());
        }
        return "";
    }
}
