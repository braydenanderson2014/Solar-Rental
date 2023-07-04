package messageHandler;

import java.util.Arrays;
import java.util.List;

import com.solarrental.assets.CustomScanner;
import com.solarrental.assets.Logo;
import com.solarrental.assets.TxtColorTable;

import MainSystem.Settings;
import MainSystem.SettingsController;

public class ConsoleHandler {
    public static boolean isErrorEnabled = true;
    public static boolean isWarningEnabled = true;
    public static boolean isInfoEnabled = true;
    public static boolean isSystemEnabled = true;
    public static boolean isDebugEnabled = true;
    public static String ErrorColor = "Red";
    public static String WarningColor = "Yellow";
    public static String InfoColor = "Blue";
    public static String SystemColor = "Green";
    public static String DebugColor = "Purple";
    private static final List<String> COLORS = Arrays.asList(
            "Black", "Red", "Green", "Yellow", "Blue", "Purple", "Cyan", "White",
            "Bright Black", "Bright Red", "Bright Green", "Bright Yellow",
            "Bright Blue", "Bright Purple", "Bright Cyan", "Bright White");

    public static boolean changeColor(String messageType, String color) {
        if (messageType.equals("Error")) {
            ErrorColor = color;
            SettingsController.setSetting(messageType + " Color", color);
            SettingsController.saveSettings();
            return true;
        } else if (messageType.equals("Warning")) {
            WarningColor = color;
            SettingsController.setSetting(messageType + " Color", color);
            SettingsController.saveSettings();
            return true;
        } else if (messageType.equals("Info")) {
            InfoColor = color;
            SettingsController.setSetting(messageType + " Color", color);
            SettingsController.saveSettings();
            return true;
        } else if (messageType.equals("System")) {
            SystemColor = color;
            SettingsController.setSetting(messageType + " Color", color);
            SettingsController.saveSettings();
            return true;
        } else if (messageType.equals("Debug")) {
            DebugColor = color;
            SettingsController.setSetting(messageType + " Color", color);
            SettingsController.saveSettings();
            return true;
        } else {
            MessageProcessor.processMessage(-1, "MessageType not found: " + messageType, true);
            MessageProcessor.processMessage(2, "MessageType was not found " + messageType, false);
            return false;
        }
    }

    public static void colorChanger(String messageType) {
        String currentColor = getColorByMessageType(messageType);
        System.out.println("Current Color: " + currentColor);
        int index = COLORS.indexOf(currentColor);

        // If the color is not found or it is the last one, reset to the first color
        if (index == -1 || index == COLORS.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        String newColor = COLORS.get(index);
        changeColor(messageType, newColor);
    }

    public static String getConsole() {
    	System.out.println("Console: ");
        MessageProcessor.displayMessages();
        return null;
    }

    public static String getColorByMessageType(String messageType) {
        switch (messageType) {
            case "[Error]: ":
                return TxtColorTable.getColor("Error");
            case "[Warning]: ":
                return TxtColorTable.getColor("Warning");
            case "[Info]: ":
                return TxtColorTable.getColor("Info");
            case "[System]: ":
                return TxtColorTable.getColor("System");
            case "[Debug]: ":
                return TxtColorTable.getColor("Debug");
            default:
                MessageProcessor.processMessage(-1, "Invalid Message Type: ", true);
                MessageProcessor.processMessage(2,
                        "Invalid Message Type: " + messageType + "(CONSOLE HANDLER; GETCONSOLE())", false);
                return "";
        }
    }

    public static void ConsoleSettings() {
        Logo.displayLogo();
        System.out.println("Console Settings");
        System.out.println("[Error]: Error Messages: " + isErrorEnabled);
        System.out.println("[WARNING]: Warning Messages: " + isWarningEnabled);
        System.out.println("[INFO]: Info Messages: " + isInfoEnabled);
        System.out.println("[System]: System Messages: " + isSystemEnabled);
        System.out.println("[Debug]: Debug Messages: " + isDebugEnabled);
        System.out.println("[ErrorColor]: Error Color: " + ErrorColor);
        System.out.println("[WarningColor]: Warning Color: " + WarningColor);
        System.out.println("[InfoColor]: Info Color: " + InfoColor);
        System.out.println("[SystemColor]: System Color: " + SystemColor);
        System.out.println("[DebugColor]: Debug Color: " + DebugColor);
        System.out.println("[Back]: Go Back");
        System.out.println("Type the name of the setting you want to change, or type 'back' to go back.");
        String settingToChange = CustomScanner.nextLine().toLowerCase().trim();
        switch (settingToChange) {
            case "error":
                if (isErrorEnabled) {
                    isErrorEnabled = !isErrorEnabled;
                } else {
                    isErrorEnabled = true;
                }
                break;
            case "warning":
                if (isWarningEnabled) {
                    isWarningEnabled = !isWarningEnabled;
                } else {
                    isWarningEnabled = true;
                }
                break;
            case "info":
                if (isInfoEnabled) {
                    isInfoEnabled = !isInfoEnabled;
                } else {
                    isInfoEnabled = true;
                }
                break;
            case "system":
                if (isSystemEnabled) {
                    isSystemEnabled = !isSystemEnabled;
                } else {
                    isSystemEnabled = true;
                }
                break;
            case "debug":
                if (isDebugEnabled) {
                    isDebugEnabled = !isDebugEnabled;
                } else {
                    isDebugEnabled = true;
                }
                break;
            case "errorcolor":
                colorChanger("[Error]: ]");
                break;
            case "warningcolor":
                colorChanger("[Warning]: ");
                break;
            case "infocolor":
                colorChanger("[Info]: ");
                break;
            case "systemcolor":
                colorChanger("[System]: ");
                break;
            case "debugcolor":
                colorChanger("[Debug]: ");
                break;
            case "back":
                Settings.settingsMenu();
                break;
            default:
                MessageProcessor.processMessage(-1, "Invalid setting name. Please try again.", true);
                ConsoleSettings();
                break;
        }

    }

    public static boolean saveConsoleSettings() {
        return SettingsController.saveSettings();

    }

}
