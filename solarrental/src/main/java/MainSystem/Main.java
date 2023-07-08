package MainSystem;

import com.solarrental.assets.CustomScanner;

import InstallManager.AutoSetup;
import InstallManager.FirstTimeManager;
import InstallManager.ManualSetup;
import InstallManager.ProgramController;
import InstallManager.SystemSetLoader;
import Login.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import messageHandler.MessageProcessor;

public class Main extends Application {
	private static Stage S;
	public static Thread watchdogThread;
    public static Stage getS() {
		return S;
	}


	@Override
    public void start(Stage primaryStage) {
    	new CustomScanner();
        if (FirstTimeManager.checkFirstTime()) {
            showSetupMenu(primaryStage);
        } else {
            SystemSetLoader.loadSystems();
            Login.showLoginScreen(primaryStage);
        }
    }
	public static void reloadUI(Stage stage) {
		VBox vbox = new VBox();
	    Scene scene = new Scene(vbox, 400, 400);
	    stage.setScene(scene);
	}

    private static void showSetupMenu(Stage stage) {
    	S = stage;

        // Create buttons for each setup option
        Button autoSetupBtn = new Button("Auto Setup Program");
        Button manualSetupBtn = new Button("Manually Setup Program");
        Button quitBtn = new Button("Quit Program");

        // Set actions for each button
        autoSetupBtn.setOnAction(e -> AutoSetup.startAutoSetup());
        manualSetupBtn.setOnAction(e -> ManualSetup.configureSetup());
        quitBtn.setOnAction(e -> System.exit(0));
        //0 = success; 1 = Error; 2 <= Program Specific
        // Add buttons to layout
        VBox vbox = new VBox(autoSetupBtn, manualSetupBtn, quitBtn);

        // Create a scene and add layout to scene
        Scene scene = new Scene(vbox, 300, 200);

        // Add scene to stage
        stage.setScene(scene);
        stage.setTitle("Solar-Rental Setup");
        stage.show();
    }
    public static void main(String[] args) {
    	SettingsController.loadSettings();
    	MessageProcessor.processMessage(2, "Starting WatchDog Thread!", true);
    	watchdogThread = new Thread(SettingsWatchDog::mainLoop); // Modify this line
        watchdogThread.start();
    	
    	if(SettingsController.searchForSet("UI")) {
    		MessageProcessor.processMessage(2, "Does UI Setting Exist: " + SettingsController.searchForSet("UI"), true);
    		if(!SettingsController.getSetting("UI").equals("Enabled")) {
    			new CustomScanner();
    			MessageProcessor.processMessage(2, "Starting Program in Console Mode", true);
    			ProgramController.Start();
    		}else {
    			MessageProcessor.processMessage(2, "Starting Program in UI Mode", true);
                launch(args);
    		}
    	}else {
    		MessageProcessor.processMessage(-1, "Failed to find UI Setting!", true);
    		MessageProcessor.processMessage(2, "Starting Program in UI Mode", true);
            launch(args);
    	}
    }
}
