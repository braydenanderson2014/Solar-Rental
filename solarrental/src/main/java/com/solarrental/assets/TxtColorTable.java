package com.solarrental.assets;

import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;

public class TxtColorTable {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static String color;
    public static String getRESET() {
        return RESET;
    }
    public static String colorMatcher(String color){
        if(color == "Reset"){
            return RESET;
        }else if(color == "Black"){
            return BLACK;
        }else if(color == "Red"){
            return RED;
        }else if(color == "Green"){
            return GREEN;
        }else if(color == "Yellow"){
            return YELLOW;
        }else if(color == "Blue"){
            return BLUE;
        }else if(color == "Purple"){
            return PURPLE;
        }else if(color == "Cyan"){
            return CYAN;
        }else if(color == "White"){
            return WHITE;
        }else if(color == "Bright Black"){
            return BRIGHT_BLACK;
        }else if(color == "Bright Red"){
            return BRIGHT_RED;
        }else if(color == "Bright Green"){
            return BRIGHT_GREEN;
        }else if(color == "Bright Yellow"){
            return BRIGHT_YELLOW;
        }else if(color == "Bright Blue"){
            return BRIGHT_BLUE;
        }else if(color == "Bright Purple"){
            return BRIGHT_PURPLE;
        }else if(color == "Bright Cyan"){
            return BRIGHT_CYAN;
        }else if(color == "Bright White"){
            return BRIGHT_WHITE;
        }else{
            MessageProcessor.processMessage(-1, "Color not found: " + color, true);
            MessageProcessor.processMessage(2, "Color was not found in lookup table " + color, false);
            return "Null";
        }
    }
    public static String getColor(String MessageType) {
        switch (MessageType) {
            case "Error":
               color = ConsoleHandler.ErrorColor;
               return colorMatcher(color);
            case "Warning":
                color = ConsoleHandler.WarningColor;
                return colorMatcher(color);
            case "Info":
                color = ConsoleHandler.InfoColor;
                return colorMatcher(color);
            case "System":
                color = ConsoleHandler.SystemColor;
                return colorMatcher(color);
            case "Debug":
                color = ConsoleHandler.DebugColor;
                return colorMatcher(color);
            default:
                MessageProcessor.processMessage(-1, "MessageType not found: " + MessageType, true);
                MessageProcessor.processMessage(2, "MessageType was not found " + MessageType, false);
                return "Null";
        }
    }
}
