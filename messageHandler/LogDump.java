package messageHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import InstallManager.ProgramController;

public class LogDump {
    private static LocalDateTime myDateObj = LocalDateTime.now();
    private static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH-mm-ss");
    private static String dTime = myDateObj.format(myFormatObj);
    private static int EMT = 0;
    private static int WMT = 0;
    private static int UMT = 0;
    private static int SMT = 0;
    private static int AMT = 0;
    private static int DMT = 0;

    public static boolean DumpLog(String Mode) {
        ConsoleHandler.getConsole();
        String path;
        path = ProgramController.systemRunPath + "/Logs";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            String messageType;
            List<String> messages;
            int index;

            switch (Mode.toLowerCase()) {
                case "all":
                    messageType = "[ALL MESSAGES]";
                    messages = AllMessages.allMessagesT;
                    index = AMT++;
                    break;
                case "system":
                    messageType = "[SYSTEM]";
                    messages = SystemMessages.SystemMessagesT;
                    index = SMT++;
                    break;
                case "user":
                case "notification":
                    messageType = "[NOTIFICATION]";
                    messages = NotificationMessages.NotificationMessagesT;
                    index = UMT++;
                    break;
                case "warning":
                    messageType = "[WARNING]";
                    messages = WarningMessages.WarningMessagesT;
                    index = WMT++;
                    break;
                case "error":
                    messageType = "[ERROR]";
                    messages = ErrorMessages.ErrorMessagesT;
                    index = EMT++;
                    break;
                case "debugmt":
                	messageType = "[DEBUG]:";
                	messages = DebugMessages.DebugMessagesT;
                	index = DMT++;
                	break;
                case "debug":
                    for (int i = 0; i < AllMessages.allMessagesT.size(); i++) {
                        System.out.println(AllMessages.allMessagesT.get(i));
                    }
                    System.exit(3);
                    return false;
                default:
                    MessageProcessor.processMessage(-2, "Invalid Mode For Log Dump... Dumping all Messages", true);
                    return DumpLog("All");
            }

            myDateObj = LocalDateTime.now();
            dTime = myDateObj.format(myFormatObj);
            path = ProgramController.systemRunPath + "\\Logs/" + messageType + "[" + dTime + "]" + index + ".txt";

            writeLogFile(path, messageType, messages);
            MessageProcessor.processMessage(2, "Log Generated at Time: " + AllMessages.getTime(), true);
            MessageProcessor.processMessage(1, "Log File Saved at: " + path, true);
            return true;
        } catch (IOException e) {
            MessageProcessor.processMessage(-2, "A Failure Creating the Log FILE Occured!!!", true);
            MessageProcessor.processMessage(-1, "Now Entering [DEBUG] Mode... Log Type Shown [ALL]", true);
            MessageProcessor.processMessage(1, "Attempted Path of File: " + path, true);
            return DumpLog("debug");
        }
    }

    private static void writeLogFile(String path, String messageType, List<String> messages) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile());
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("Solar Logs (TYPE: " + messageType + ")\r\n");
            for (String message : messages) {
                bw.write(message + "\r\n");
            }
            bw.write("Report Generated at Time: " + AllMessages.getTime());
        }
    }
}