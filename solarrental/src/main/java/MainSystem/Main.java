package MainSystem;

import InstallManager.AutoSetup;
import InstallManager.FirstTimeManager;
import InstallManager.ManualSetup;
import InstallManager.ProgramController;
import InstallManager.SystemSetLoader;
import Login.Login;
import assets.CustomScanner;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import messageHandler.MessageProcessor;
import javafx.util.Duration;

public class Main extends Application {
    private static Stage mainStage;

    public static Stage getStage() {
        return mainStage;
    }

    public static void hideUI(Stage stage) {
        stage.hide();
    }

    public static void showUI(Stage stage) {
        stage.show();
    }

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static boolean isUIVisible() {
        final boolean[] result = { false };
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            if (mainStage != null) {
                // The stage has been initialized, so check if it is visible.
                result[0] = mainStage.isShowing();
            }
            // If S is null, result[0] will remain false.
        });
        delay.play();
        return result[0];
    }

    public static void reloadUI(Stage stage) {
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
    }

    private static void showSetupMenu(Stage stage) {
        mainStage = stage;

        // Create buttons for each setup option
        Button autoSetupBtn = new Button("Auto Setup Program");
        Button manualSetupBtn = new Button("Manually Setup Program");
        Button quitBtn = new Button("Quit Program");

        // Set actions for each button
        autoSetupBtn.setOnAction(e -> AutoSetup.startAutoSetup());
        manualSetupBtn.setOnAction(e -> ManualSetup.configureSetup());
        quitBtn.setOnAction(e -> System.exit(0));
        // 0 = success; 1 = Error; 2 <= Program Specific
        // Add buttons to layout
        VBox vbox = new VBox(autoSetupBtn, manualSetupBtn, quitBtn);

        // Create a scene and add layout to scene
        Scene scene = new Scene(vbox, 300, 200);

        // Add scene to stage
        stage.setScene(scene);
        stage.setTitle("Solar-Rental Setup");
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        if (SettingsController.searchForSet("UI")) {
            MessageProcessor.processMessage(2, "Does UI Setting Exist: " + SettingsController.searchForSet("UI"), true);
            if (!SettingsController.getSetting("UI").equals("Enabled")) {
                MessageProcessor.processMessage(2, "Starting Program in Console Mode", true);
                if (Platform.isFxApplicationThread()) {
                    setStage(primaryStage);
                    primaryStage.hide();
                }
                ProgramController.Start();
            } else {
                MessageProcessor.processMessage(2, "Starting Program in UI Mode", true);
                if (Platform.isFxApplicationThread()) { // if it's the JavaFX thread
                    // setStage(primaryStage);
                    if (FirstTimeManager.checkFirstTime()) {
                        showSetupMenu(primaryStage);
                    } else {
                        SystemSetLoader.loadSystems();
                        Login.showLoginScreen();
                    }
                } else { // it's not the JavaFX thread
                    String[] args = null;
                    launch(args);
                }
            }

        } else {
            MessageProcessor.processMessage(-1, "Failed to find UI Setting!", true);
            MessageProcessor.processMessage(2, "Starting Program in UI Mode", true);
            if (Platform.isFxApplicationThread()) { // if it's the JavaFX thread

            } else { // it's not the JavaFX thread
                String[] args = null;
                launch(args);
            }
        }
        setStage(primaryStage);
        if (FirstTimeManager.checkFirstTime()) {
            showSetupMenu(primaryStage);
        } else {
            SystemSetLoader.loadSystems();
            Login.showLoginScreen();
        }
    }

    public static void main(String[] args) {
        boolean uiEnabled = false;
        boolean firstTimeSetup = false;

        for (String arg : args) {
            if (arg.equals("--ui")) {
                uiEnabled = true;
            } else if (arg.equals("--first-time-setup")) {
                firstTimeSetup = true;
            }
        }

        if (firstTimeSetup) {
            FirstTimeManager.firstTime = true;
            // Perform first time setup
        }

        SettingsController.loadSettings();
        new CustomScanner();

        if (uiEnabled && Platform.isFxApplicationThread()) { // if it's the JavaFX thread and UI is enabled
            try {
                new Main().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { // it's not the JavaFX thread or UI is not enabled
            launch(args);
        }
    }
}
