package messageHandler;

import MainSystem.Settings;
import assets.CustomScanner;
import assets.Logo;

import java.util.List;

public class ViewLogManager {
    public static void viewMessages(List<String> messages, List<String> messagesWithTimestamp) {
        for (int i = 0; i < messages.size(); i++) {
            if (ConsoleSettings.timeSet) {
                System.out.println(messagesWithTimestamp.get(i) + "[" + AllMessages.visibleToConsole.get(i) + "]");
            } else {
                System.out.println(messages.get(i) + "[" + AllMessages.visibleToConsole.get(i) + "]");
            }
        }
        MessageProcessor.processMessage(1, "Press Enter to continue", true);
        System.out.println(SystemMessages.getLastMessage());
        String enter = CustomScanner.nextLine();
        MessageProcessor.processMessage(-3, enter, false);
    }

    public static void viewWarnings() {
        viewMessages(WarningMessages.WarningMessages, WarningMessages.WarningMessagesT);
    }

    public static void viewSystemMessages() {
        viewMessages(SystemMessages.SystemMessages, SystemMessages.SystemMessagesT);
    }

    public static void viewErrorMessages() {
        viewMessages(ErrorMessages.ErrorMessages, ErrorMessages.ErrorMessagesT);
    }

    public static void viewUserMessages() {
        viewMessages(NotificationMessages.NotificationMessages, NotificationMessages.NotificationMessagesT);
    }

    public static void viewAllMessages() {
        viewMessages(AllMessages.allMessages, AllMessages.allMessagesT);
    }

    public static void viewDebugMessages() {
        viewMessages(DebugMessages.DebugMessages, DebugMessages.DebugMessagesT);
    }
    public static void viewMenu(int mode) {
        Logo.displayLogo();
        System.out.println("[All]: View All Messages");
        System.out.println("[Warning]: View All Warning Messages");
        System.out.println("[Error]: View All Error Messages");
        System.out.println("[User]: View All User Notification Messages");
        System.out.println("[Debug]: View All Debug Messages");
        System.out.println("[System]: View All System Messages");
        System.out.println("[Return]: Return");
        ConsoleHandler.getConsole();
        String option = CustomScanner.nextLine().toLowerCase();
        switch (option) {
            case "all":
                viewAllMessages();
                viewMenu(mode);
                break;
            case "warning":
                viewWarnings();
                viewMenu(mode);
                break;
            case "error":
                viewErrorMessages();
                viewMenu(mode);
                break;
            case "user":
                viewUserMessages();
                viewMenu(mode);
                break;
            case "system":
                viewSystemMessages();
                viewMenu(mode);
                break;
            case "debug":
            	viewDebugMessages();
            	viewMenu(mode);
            	break;
            case "return":
                try {
                    Settings.settingsMenu();
                } catch (Exception e) {
                    MessageProcessor.processMessage(-2, "Failed to access Settings Menu, Reattempting to access Settings Menu", true);
                    Settings.settingsMenu();
                }
                break;
            default:
                MessageProcessor.processMessage(-1, "Invalid option, try again", true);
                viewMenu(mode);
        }
    }
}
