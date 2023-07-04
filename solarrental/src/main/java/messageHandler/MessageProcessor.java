package messageHandler;

import java.time.LocalDateTime;

import java.util.*;
import java.util.stream.Collectors;
import java.lang.reflect.Field;

import InstallManager.ProgramController;
import MainSystem.SettingsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MessageProcessor {

	private static int maxMessages = 5;

	private static List<Message> messages = new LinkedList<>();
	private static List<Message> log = new LinkedList<>();
	public static Map<Integer, String> messageTypes = Map.of(-2, "[Error]: ", -1, "[Warning]: ", 0, "[Info]: ", 1,
			"[System]: ", 2, "[Debug]: ");
	private static List<String> colorCodes = List.of("\u001B[30m", // BLACK
			"\u001B[31m", // RED
			"\u001B[32m", // GREEN
			"\u001B[33m", // YELLOW
			"\u001B[34m", // BLUE
			"\u001B[35m", // PURPLE
			"\u001B[36m", // CYAN
			"\u001B[37m", // WHITE
			"\u001B[90m", // BRIGHT_BLACK
			"\u001B[91m", // BRIGHT_RED
			"\u001B[92m", // BRIGHT_GREEN
			"\u001B[93m", // BRIGHT_YELLOW
			"\u001B[94m", // BRIGHT_BLUE
			"\u001B[95m", // BRIGHT_PURPLE
			"\u001B[96m", // BRIGHT_CYAN
			"\u001B[97m" // BRIGHT_WHITE
	);

	private static Map<Integer, Integer> messageTypeColorIndex = new HashMap<>();
	private static Map<Integer, String> messageColors = new HashMap<>() {
		{
			put(-2, "\033[0;31m"); // RED
			put(-1, "\033[0;33m"); // YELLOW
			put(0, "\033[0;32m"); // GREEN
			put(1, "\033[0;34m"); // BLUE
			put(2, "\033[0;36m"); // CYAN
		}
	};
	private static Map<Integer, Color> UImessageColors = new HashMap<>() {
		{
			put(-2, Color.RED);
			put(-1, Color.YELLOW);
			put(0, Color.GREEN);
			put(1, Color.BLUE);
			put(2, Color.CYAN);
		}
	};

	private static Map<Integer, Boolean> messageTypeVisibility = new HashMap<>() {
		{
			put(-2, true);
			put(-1, true);
			put(0, true);
			put(1, true);
			put(2, true);
		}
	};

	static class Message {
		public int messageType;
		String message;
		Boolean visibleToConsole;
		LocalDateTime dateTime;

		Message(int messageType, String message, Boolean visibleToConsole) {
			this.messageType = messageType;
			this.message = message;
			this.visibleToConsole = visibleToConsole;
			this.dateTime = LocalDateTime.now();
		}

		@Override
		public String toString() {
			if (SettingsController.getSetting("UI").equals("Enabled")) {
				return messageTypes.get(messageType) + " [" + dateTime + "] " + message;
			}
			return messageColors.get(messageType) + messageTypes.get(messageType) + " [" + dateTime + "] " + message
					+ "\033[0m"; // RESET color after printing the message
		}
	}

	public static void processMessage(int messageType, String message, Boolean visibleToConsole) {
		if (!messageTypes.containsKey(messageType)) {
			processMessage(-1, "Invalid Message Type sent to Processor: " + messageType + " Message: " + message,
					visibleToConsole);
			return;
		}

		Message newMessage = new Message(messageType, message, visibleToConsole);
		messages.add(newMessage);
		log.add(newMessage);
		// AutoGenerateLog.log.add(newMessage.toString());

		while (messages.size() > maxMessages) {
			messages.remove(0);
		}
	}

	public static TextFlow getUIConsole(Stage stage) {
	    TextFlow textFlow = new TextFlow();
	    List<Message> messagesToDisplay = getMessagesforUI();
	    
	    // Calculate the start index to show only maxMessages number of messages.
	    int startIndex = Math.max(0, messagesToDisplay.size() - maxMessages);

	    // Create a sublist from the startIndex to the end of the list.
	    List<Message> messagesToShow = messagesToDisplay.subList(startIndex, messagesToDisplay.size());

	    for (Message message : messagesToShow) {
	        // Ensure that the message is visible and the type is enabled for visibility.
	        if (messageTypeVisibility.get(message.messageType) && message.visibleToConsole) {
	            Text text = new Text();
	            text.setText(message.toString() + "\n");

	            // Get the color associated with the message type from the map.
	            Color color = UImessageColors.getOrDefault(message.messageType, Color.BLACK);
	            text.setFill(color);

	            textFlow.getChildren().add(text);

	            // Once a message is shown, it's marked as not visible to avoid duplication.
	            message.visibleToConsole = false;
	        }
	    }
	    return textFlow;
	}


	public static void displayMessages() {
		for (Message message : messages) {
			if (messageTypeVisibility.get(message.messageType) && message.visibleToConsole) {
				System.out.println(message);
				message.visibleToConsole = false;
			}
		}
	}

	public static List<Message> getMessagesforUI() {
		return messages;
	}

	public static String getMessageColor(int messageType) {
		return messageColors.getOrDefault(messageType, "\u001B[37m"); // Default to white if messageType is not found
	}

	public static List<Message> getMessages() {
		return new ArrayList<>(log);
	}

	public static void setMessageTypeVisibility(int messageType, boolean isVisible) {
		if (messageTypes.containsKey(messageType)) {
			messageTypeVisibility.put(messageType, isVisible);
			String tempString;
			if (messageType == -2) {
				tempString = "Display Error Messages";
			} else if (messageType == -1) {
				tempString = "Display Warning Messages";
			} else if (messageType == 0) {
				tempString = "Display Info Messages";
			} else if (messageType == 1) {
				tempString = "Display System Messages";
			} else {
				tempString = "Display Debug Messages";
			}
			SettingsController.setSetting(tempString, String.valueOf(isVisible));
		}
	}

	public static String getJavaFXColorNameByAnsiCode(String ansiCode) {
		switch (ansiCode) {
		case "\u001B[30m":
			return "BLACK";
		case "\u001B[31m":
			return "RED";
		case "\u001B[32m":
			return "GREEN";
		case "\u001B[33m":
			return "YELLOW";
		case "\u001B[34m":
			return "BLUE";
		case "\u001B[35m":
			return "PURPLE";
		case "\u001B[36m":
			return "CYAN";
		case "\u001B[37m":
			return "WHITE";
		case "\u001B[90m":
			return "DARKGRAY"; // There is no BRIGHT_BLACK in JavaFX
		case "\u001B[91m":
			return "RED"; // There is no BRIGHT_RED in JavaFX
		case "\u001B[92m":
			return "LIGHTGREEN"; // There is no BRIGHT_GREEN in JavaFX
		case "\u001B[93m":
			return "LIGHTYELLOW"; // There is no BRIGHT_YELLOW in JavaFX
		case "\u001B[94m":
			return "LIGHTBLUE"; // There is no BRIGHT_BLUE in JavaFX
		case "\u001B[95m":
			return "MAGENTA"; // There is no BRIGHT_PURPLE in JavaFX
		case "\u001B[96m":
			return "LIGHTCYAN"; // There is no BRIGHT_CYAN in JavaFX
		case "\u001B[97m":
			return "WHITE"; // There is no BRIGHT_WHITE in JavaFX
		default:
			return "BLACK";
		}
	}

	public static void setMessageTypeColor(int messageType, String color) {
		if (messageTypes.containsKey(messageType)) {
			messageColors.put(messageType, color);
			String tempString;
			if (messageType == -2) {
				tempString = "Error Color";
			} else if (messageType == -1) {
				tempString = "Warning Color";
			} else if (messageType == 0) {
				tempString = "Info Color";
			} else if (messageType == 1) {
				tempString = "System Color";
			} else {
				tempString = "Debug Color";
			}
			SettingsController.setSetting(tempString, color);
		}
	}

	public static void setMaxMessages(int newMax) {
		maxMessages = newMax;
		SettingsController.setSetting("Console Length", String.valueOf(newMax));
	}

	public static void cycleMessageTypeColor(int messageType) {
		if (!messageTypes.containsKey(messageType)) {
			processMessage(-1, "Invalid Message Type sent to Processor: " + messageType, true);
			return;
		}

		// Initialize color index if it's not set yet
		messageTypeColorIndex.putIfAbsent(messageType, 0);

		// Increment the color index for the message type
		int nextColorIndex = messageTypeColorIndex.get(messageType) + 1;

		// If the index exceeds the color list size, reset it to 0
		if (nextColorIndex >= colorCodes.size()) {
			nextColorIndex = 0;
		}

		// Update the color for the message type
		messageColors.put(messageType, colorCodes.get(nextColorIndex));
		String tempString;
		if (messageType == -2) {
			tempString = "Error Color";
		} else if (messageType == -1) {
			tempString = "Warning Color";
		} else if (messageType == 0) {
			tempString = "Info Color";
		} else if (messageType == 1) {
			tempString = "System Color";
		} else {
			tempString = "Debug Color";
		}
		SettingsController.setSetting(tempString, colorCodes.get(nextColorIndex));
		// Update the index for next time
		messageTypeColorIndex.put(messageType, nextColorIndex);
	}

	private static Map<String, String> colorNameToCodeMap = Map.ofEntries(Map.entry("BLACK", "\u001B[30m"),
			Map.entry("RED", "\u001B[31m"), Map.entry("GREEN", "\u001B[32m"), Map.entry("YELLOW", "\u001B[33m"),
			Map.entry("BLUE", "\u001B[34m"), Map.entry("PURPLE", "\u001B[35m"), Map.entry("CYAN", "\u001B[36m"),
			Map.entry("WHITE", "\u001B[37m"), Map.entry("BRIGHT_BLACK", "\u001B[90m"),
			Map.entry("BRIGHT_RED", "\u001B[91m"), Map.entry("BRIGHT_GREEN", "\u001B[92m"),
			Map.entry("BRIGHT_YELLOW", "\u001B[93m"), Map.entry("BRIGHT_BLUE", "\u001B[94m"),
			Map.entry("BRIGHT_PURPLE", "\u001B[95m"), Map.entry("BRIGHT_CYAN", "\u001B[96m"),
			Map.entry("BRIGHT_WHITE", "\u001B[97m"));

	public static String getColorCodeByName(String colorName) {
		return colorNameToCodeMap.getOrDefault(colorName.toUpperCase(), "\u001B[37m"); // Default to WHITE if name is
		// not found
	}

	public static void clearAll() {
		// TODO Auto-generated method stub

	}

	static class PublicMessage {
		public final int messageType;
		public final String messageText;
		public final LocalDateTime dateTime;

		public PublicMessage(int messageType, String messageText, LocalDateTime dateTime) {
			this.messageType = messageType;
			this.messageText = messageText;
			this.dateTime = dateTime;
		}
	}

	public static List<PublicMessage> getPublicMessages() {
		return messages.stream().map(m -> new PublicMessage(m.messageType, m.message, m.dateTime))
				.collect(Collectors.toList());
	}

}
