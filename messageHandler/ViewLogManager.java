package messageHandler;
import Assets.Logo;
import Assets.customScanner;
import MainSystem.Settings;
public class ViewLogManager{
    public static String ViewWarnings(){
        for(int i = 0; i < WarningMessages.size(); i++){
            if(ConsoleSettings.timeSet = true){
                System.out.println(WarningMessages.WarningMessagesT.get(i));
            }else {
                System.out.println(WarningMessages.WarningMessages.get(i));
            }
        }
        messageHandler.HandleMessage(1, "Press Enter to continue", true);
        System.out.println(SystemMessages.getLastMessage());
        String enter = customScanner.nextLine();
        return "";
    }
    public static String ViewSystemMessages(){
        for(int i = 0; i < SystemMessages.size(); i++){
            if(ConsoleSettings.timeSet = true){
                System.out.println(SystemMessages.SystemMessagesT.get(i));
            }else {
                System.out.println(SystemMessages.SystemMessages.get(i));
            }
        }
        messageHandler.HandleMessage(1, "Press Enter to continue", true);
        System.out.println(SystemMessages.getLastMessage());
        String enter = customScanner.nextLine();
        return "";
    }
    public static String ViewErrorMessages(){
        for(int i = 0; i < ErrorMessages.size(); i++){
            if(ConsoleSettings.timeSet = true){
                System.out.println(ErrorMessages.ErrorMessagesT.get(i));
            }else {
                System.out.println(ErrorMessages.ErrorMessages.get(i));
            }
        }
        messageHandler.HandleMessage(1, "Press Enter to continue", true);
        System.out.println(SystemMessages.getLastMessage());
        String enter = customScanner.nextLine();
        return "";
    }
    public static String ViewUserMessages(){
        for(int i = 0; i < NotificationMessages.size(); i++){
            if(ConsoleSettings.timeSet = true){
                System.out.println(NotificationMessages.NotificationMessagesT.get(i));
            }else {
                System.out.println(NotificationMessages.NotificationMessages.get(i));
            }
        }
        messageHandler.HandleMessage(1, "Press Enter to continue", true);
        System.out.println(SystemMessages.getLastMessage());
        String enter = customScanner.nextLine();
        return "";
    }
    public static String ViewAllMessages(){
        for(int i = 0; i < AllMessages.size(); i++){
            if(ConsoleSettings.timeSet = true){
                System.out.println(AllMessages.AllMessagesT.get(i));
            }else {
                System.out.println(AllMessages.AllMessages.get(i));
            }
        }
        messageHandler.HandleMessage(1, "Press Enter to continue", true);
        System.out.println(SystemMessages.getLastMessage());
        String enter = customScanner.nextLine();
        return "";
    }
    public static void ViewMenu(int Mode){
        Logo.displayLogo();
        System.out.println("[All]: View All Messages");
        System.out.println("[Warning]: View All Warning Messages");
        System.out.println("[Error]: View All Error Messages");
        System.out.println("[User]: View All User Notification Messages");
        System.out.println("[System]: View All System Messages");
        System.out.println("[Return]: Return");
        Console.getConsole();
        String option = customScanner.nextLine().toLowerCase();
        if(option.equals("all")){
            ViewAllMessages();
            ViewMenu(Mode);
        }else if(option.equals("warning")){
            ViewWarnings();
            ViewMenu(Mode);
        }else if(option.equals("error")){
            ViewErrorMessages();
            ViewMenu(Mode);
        }else if(option.equals("user")){
            ViewUserMessages();
            ViewMenu(Mode);
        }else if(option.equals("system")){
            ViewSystemMessages();
            ViewMenu(Mode);
        }else if(option.equals("return")){
                Settings.SettingsMenu();
        }else{
            messageHandler.HandleMessage(-1, "Invalid option, try again", true);
            ViewMenu(Mode);
        }
    }
}