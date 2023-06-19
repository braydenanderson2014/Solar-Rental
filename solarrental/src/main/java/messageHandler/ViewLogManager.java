package messageHandler;

import MainSystem.Settings;
import MainSystem.SettingsController;

import java.util.List;
import java.util.stream.Collectors;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;

public class ViewLogManager {
    public static void viewMessages(List<String> messages, List<String> messagesWithTimestamp) {
        for (int i = 0; i < messages.size(); i++) {
            if (Boolean.parseBoolean(SettingsController.getSetting("Date/TimeSet"))) {
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

    public List<MessageProcessor.Message> filterMessagesByType(int typeOfInterest,
            List<MessageProcessor.Message> allMessages) {
        return allMessages.stream()
                .filter(msg -> msg.messageType == typeOfInterest)
                .collect(Collectors.toList());
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
        List<MessageProcessor.Message> allMessages = MessageProcessor.getMessages();
        int typeOfInterest = -1;
        ViewLogManager viewLogManager = new ViewLogManager();
        String Enter;
        switch (option) {
            case "all":
                for (MessageProcessor.Message message : allMessages) {
                    System.out.println(message);
                }
                Enter = CustomScanner.nextLine();
                viewMenu(mode);
                break;
            case "warning":
                typeOfInterest = -1 /* the type of message you're interested in */;
                List<MessageProcessor.Message> filteredMessages = viewLogManager.filterMessagesByType(typeOfInterest,
                        MessageProcessor.getMessages());

                // filteredMessages now contains only the messages of the type you're interested
                // in.
                System.out.println(filteredMessages);
                Enter = CustomScanner.nextLine();

                viewMenu(mode);
                break;
            case "error":
                typeOfInterest = -2 /* the type of message you're interested in */;
                filteredMessages = viewLogManager.filterMessagesByType(typeOfInterest, MessageProcessor.getMessages());

                // filteredMessages now contains only the messages of the type you're interested
                // in.
                System.out.println(filteredMessages);
                Enter = CustomScanner.nextLine();

                viewMenu(mode);
                break;
            case "user":
                typeOfInterest = -0 /* the type of message you're interested in */;
                filteredMessages = viewLogManager.filterMessagesByType(typeOfInterest, MessageProcessor.getMessages());

                // filteredMessages now contains only the messages of the type you're interested
                // in.
                System.out.println(filteredMessages);
                Enter = CustomScanner.nextLine();

                viewMenu(mode);
                break;
            case "system":
                typeOfInterest = 1 /* the type of message you're interested in */;
                filteredMessages = viewLogManager.filterMessagesByType(typeOfInterest, MessageProcessor.getMessages());

                // filteredMessages now contains only the messages of the type you're interested
                // in.
                System.out.println(filteredMessages);
                Enter = CustomScanner.nextLine();

                viewMenu(mode);
                break;
            case "debug":
                typeOfInterest = 2 /* the type of message you're interested in */;
                filteredMessages = viewLogManager.filterMessagesByType(typeOfInterest, MessageProcessor.getMessages());
                // filteredMessages now contains only the messages of the type you're interested
                // in.
                System.out.println(filteredMessages);
                Enter = CustomScanner.nextLine();

                viewMenu(mode);
                break;
            case "return":
                try {
                    Settings.settingsMenu();
                } catch (Exception e) {
                    MessageProcessor.processMessage(-2,
                            "Failed to access Settings Menu, Reattempting to access Settings Menu", true);
                    Settings.settingsMenu();
                }
                break;
            default:
                MessageProcessor.processMessage(-1, "Invalid option, try again", true);
                viewMenu(mode);
        }
    }
}
