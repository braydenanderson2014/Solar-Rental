package messageHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import MainSystem.SettingsController;
import messageHandler.MessageProcessor.Message;

public class LogDump {
    private static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static boolean DumpLog(String Mode) {
        String path = SettingsController.getSetting("SystemPathLetter")+ SettingsController.getSetting("UserPath") + "Logs/" + Mode.toLowerCase() + "Log"  + LocalDateTime.now().format(myFormatObj) + ".txt";
        MessageProcessor.processMessage(2, "Dumping log to " + path, true);
        try {
            int messageType;

            switch (Mode.toLowerCase()) {
                case "all":
                    messageType = Integer.MIN_VALUE; // signifies all messages
                    break;
                case "system":
                    messageType = 1;
                    break;
                case "user":
                case "notification":
                    messageType = 0; // assuming "user" and "notification" are type "info"
                    break;
                case "warning":
                    messageType = -1;
                    break;
                case "error":
                    messageType = -2;
                    break;
                case "debug":
                    messageType = 2;
                    break;
                default:
                    System.err.println("Invalid log dump mode: " + Mode);
                    return false;
            }

            List<Message> messages = MessageProcessor.getMessages();
            if (messageType != Integer.MIN_VALUE) {
                // If not all messages, filter by messageType
                messages = messages.stream()
                        .filter(message -> message.messageType == messageType)
                        .collect(Collectors.toList());
            }

            writeLogFile(path, Mode.toUpperCase(), messages);
            MessageProcessor.processMessage(1, "Log successfully dumped to " + path, true);
            return true;
        } catch (IOException e) {
            MessageProcessor.processMessage(-2, "Error writing log file: " + e.getMessage(), true);
            MessageProcessor.processMessage(2, "Error writing log file: " + e.getMessage() + " :" + path, true);
            return false;
        }
    }

    private static void writeLogFile(String path, String messageType, List<MessageProcessor.Message> messages) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile());
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("Solar Logs (TYPE: " + messageType + ")\r\n");
            for (MessageProcessor.Message message : messages) {
                bw.write(message + "\r\n");
            }
            bw.write("Report Generated at Time: " + LocalDateTime.now().format(myFormatObj));
        }
            MessageProcessor.processMessage(1, "Log successfully dumped to " + path, true);

    }
}
