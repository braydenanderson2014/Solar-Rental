package messageHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import InstallManager.ProgramController;

public class LogDump {
    public static LocalDateTime myDateObj = LocalDateTime.now();
    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH-mm-ss");
    public static String dTime  = myDateObj.format(myFormatObj);
    public static int EMT = 0;
    public static int WMT = 0;
    public static int UMT = 0;
    public static int SMT = 0;
    public static int AMT = 0;
    public static boolean DumpLog(String Mode){

        Console.getConsole();
        String path;
        path = ProgramController.SystemRunPath + "/Logs";
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        try {
            if(Mode.equals("All") || Mode.equals("all")){
                myDateObj = LocalDateTime.now();
                dTime  = myDateObj.format(myFormatObj);
                path = ProgramController.SystemRunPath + "\\Logs/[ALL MESSAGES][" + dTime + "]" + AMT + ".txt";
                AMT++;
                file = new File(path);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Solar Logs (TYPE: [ALL MESSAGES])\r\n");
                for(int i = 0; i< AllMessages.AllMessagesT.size();i++){
                    bw.write(AllMessages.AllMessagesT.get(i) + "\r\n");
                }
                bw.write("Report Generated at Time: " + AllMessages.getTime());
                bw.close();
                messageHandler.HandleMessage(2, "Log Generated at Time: " + AllMessages.getTime(), true);
                messageHandler.HandleMessage(1, "Log File Saved at: " + path, true);
                return true;
            }else if(Mode.equals("System") || Mode.equals("system")){
                myDateObj = LocalDateTime.now();
                dTime  = myDateObj.format(myFormatObj);
                path = ProgramController.SystemRunPath + "\\Logs/[SYSTEM][" + dTime + "]" + SMT + ".txt";
                SMT++;
                file = new File(path);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Solar Logs (TYPE: [System Messages Only])\r\n");
                for(int i = 0; i< SystemMessages.SystemMessagesT.size();i++){
                    bw.write(SystemMessages.SystemMessagesT.get(i) + "\r\n");
                }
                bw.write("Report Generated at Time: " + AllMessages.getTime());
                bw.close();
                messageHandler.HandleMessage(2, "Log Generated at Time: " + AllMessages.getTime(), true);
                messageHandler.HandleMessage(1, "Log File Saved at: " + path, true);
                return true;
            }else if(Mode.equals("User") || Mode.equals("user")){
                myDateObj = LocalDateTime.now();
                dTime  = myDateObj.format(myFormatObj);
                path = ProgramController.SystemRunPath + "\\Logs/[NOTIFICATION][" + dTime + "]" + UMT + ".txt";
                UMT++;
                file = new File(path);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Solar Logs (TYPE: [UserNotification Messages Only])\r\n");
                for(int i = 0; i< NotificationMessages.NotificationMessagesT.size();i++){
                    bw.write(NotificationMessages.NotificationMessagesT.get(i) + "\r\n");
                }
                bw.write("Report Generated at Time: " + AllMessages.getTime());
                bw.close();
                messageHandler.HandleMessage(2, "Log Generated at Time: " + AllMessages.getTime(), true);
                messageHandler.HandleMessage(1, "Log File Saved at: " + path, true);
                return true;
            }else if(Mode.equals("Warning") || Mode.equals("warning")){
                myDateObj = LocalDateTime.now();
                dTime  = myDateObj.format(myFormatObj);
                path = ProgramController.SystemRunPath + "\\Logs/[WARNING][" + dTime + "]" + WMT + ".txt";
                WMT++;
                file = new File(path);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Solar Logs (TYPE: [WARNING Messages Only])\r\n");
                for(int i = 0; i< WarningMessages.WarningMessagesT.size();i++){
                    bw.write(WarningMessages.WarningMessagesT.get(i) + "\r\n");
                }
                bw.write("Report Generated at Time: " + AllMessages.getTime());
                bw.close();
                messageHandler.HandleMessage(2, "Log Generated at Time: " + AllMessages.getTime(), true);
                messageHandler.HandleMessage(1, "Log File Saved at: " + path, true);
                return true;
            }else if(Mode.equals("Error") || Mode.equals("error")){
                myDateObj = LocalDateTime.now();
                dTime  = myDateObj.format(myFormatObj);
                path = ProgramController.SystemRunPath + "\\Logs/[ERROR][" + dTime + "]" + EMT + ".txt";
                EMT++;
                file = new File(path);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Solar Logs (TYPE: [ERROR Messages Only])\r\n");
                for(int i = 0; i< ErrorMessages.ErrorMessagesT.size();i++){
                    bw.write(ErrorMessages.ErrorMessagesT.get(i) + "\r\n");
                }
                bw.write("Report Generated at Time: " + AllMessages.getTime());
                bw.close();
                messageHandler.HandleMessage(2, "Log Generated at Time: " + AllMessages.getTime(), true);
                messageHandler.HandleMessage(1, "Log File Saved at: " + path, true);
                return true;
            }else if(Mode.equals("debug")){
                for(int i = 0; i < AllMessages.AllMessagesT.size();i++){
                    System.out.println(AllMessages.AllMessagesT.get(i));
                }
                System.exit(3);  
                return false;              
            }else{
                messageHandler.HandleMessage(-2, "Invalid Mode For Log Dump... Dumping all Messages", true);
                DumpLog("All");
                return false;
            }
        } catch (IOException e) {
            messageHandler.HandleMessage(-2, "A Failure Creating the Log FILE Occured!!!", true);
            messageHandler.HandleMessage(-1, "Now Entering [DEBUG] Mode... Log Type Shown [ALL]", true);
            messageHandler.HandleMessage(1, "Attempted Path of File: " + path, true);
            DumpLog("debug");
            return false;
        }
    }

}
