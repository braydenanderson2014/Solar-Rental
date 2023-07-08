package MainSystem;

import java.util.Properties;

import messageHandler.MessageProcessor;

import java.util.Map.Entry;

public class SettingsWatchDog {
    private static Properties previousSettings = new Properties();

    public static void checkAndLogChanges() {
        Properties currentSettings = new Properties();
        currentSettings.putAll(SettingsController.prop);
        for(Entry<Object, Object> entry : currentSettings.entrySet()) {
            String key = (String) entry.getKey();
            String newValue = (String) entry.getValue();
            String oldValue = previousSettings.getProperty(key);
            if(key == "UI" && newValue == "Enabled"){
                MessageProcessor.processMessage(-1, "UI MODE IS NOW ENABLED!, RELOADING PROGRAM!", true);
                if (Main.watchdogThread != null) {
                	Main.watchdogThread.interrupt(); // Close thread here
                	String[] args = {};  // or you can put specific arguments
                    Main.main(args);  // calling the main method from Main class
                }
                
            }else if(key == "UI" && newValue == "Disabled") {
                MessageProcessor.processMessage(-1, "CONSOLE MODE IS NOW ENABLED! RELOADING PROGRAM!", true);
                if (Main.watchdogThread != null) {
                	Main.watchdogThread.interrupt(); // Close thread here
                	String[] args = {};  // or you can put specific arguments
                    Main.main(args);  // calling the main method from Main class
                }
                
            }
            if(oldValue == null || !oldValue.equals(newValue)) {
            	MessageProcessor.processMessage(-1, "A Setting Was Changed!", true);
                MessageProcessor.processMessage(2, "Setting " + key + " has changed. Old value: " + oldValue + ", New value: " + newValue, true);
                if(key == "UI" && newValue == "Enabled"){
                    MessageProcessor.processMessage(-1, "UI MODE IS NOW ENABLED!, RELOADING PROGRAM!", true);
                    if (Main.watchdogThread != null) {
                    	Main.watchdogThread.interrupt(); // Close thread here
                    	String[] args = {};  // or you can put specific arguments
                        Main.main(args);  // calling the main method from Main class
                    }
                    
                }else if(key == "UI" && newValue == "Disabled") {
                    MessageProcessor.processMessage(-1, "CONSOLE MODE IS NOW ENABLED! RELOADING PROGRAM!", true);
                    if (Main.watchdogThread != null) {
                    	Main.watchdogThread.interrupt(); // Close thread here
                    	String[] args = {};  // or you can put specific arguments
                        Main.main(args);  // calling the main method from Main class
                    }
                    
                }
            }
        }
        previousSettings = currentSettings;
    }
    

    public static void mainLoop() {
        while (!Thread.currentThread().isInterrupted()) { // Check the interrupt status
            if(SettingsController.loadSettings()) {
            	checkAndLogChanges();
            }

            try {
                Thread.sleep(1000); // sleep for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Propagate the interrupt
                break; // And break the loop
            }
        }
    }

}
