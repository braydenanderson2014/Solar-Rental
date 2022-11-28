package messageHandler;

public class Console {
    public static String getConsole(){
        System.out.println();
        System.out.println("Console: ");
        if (!NotificationMessages.getLastMessage().equals("") && NotificationMessages.getLastVisibleSet()) {
		    System.out.println(NotificationMessages.getLastMessage());
		    //NotificationMessages.setLastVisibleSet(false);
		}
        if (!WarningMessages.getLastMessage().equals("") && WarningMessages.getLastVisibleSet()) {
		    System.out.println(WarningMessages.getLastMessage());
		    //WarningMessages.setLastVisibleSet(false);
		}
        if (!SystemMessages.getLastMessage().equals("") && SystemMessages.getLastVisibleSet()) {
		    System.out.println(SystemMessages.getLastMessage());
		    //SystemMessages.setLastVisibleSet(false);
		}
        if (!ErrorMessages.getLastMessage().equals("") && ErrorMessages.getLastVisibleSet()) {
		    System.out.println(ErrorMessages.getLastMessage());
		    //ErrorMessages.setLastVisibleSet(false);
		}
        return "";
    }
}
