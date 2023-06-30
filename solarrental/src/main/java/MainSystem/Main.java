package MainSystem;

import InstallManager.AutoSetup;
import InstallManager.FirstTimeManager;
import InstallManager.ManualSetup;
import Login.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        if (FirstTimeManager.checkFirstTime()) {
            showSetupMenu(primaryStage);
        } else {
            Login.showLoginScreen(primaryStage);
        }
    }

    private void showSetupMenu(Stage stage) {
        // Create buttons for each setup option
        Button autoSetupBtn = new Button("Auto Setup Program");
        Button manualSetupBtn = new Button("Manually Setup Program");
        Button quitBtn = new Button("Quit Program");

        // Set actions for each button
        autoSetupBtn.setOnAction(e -> AutoSetup.startAutoSetup());
        manualSetupBtn.setOnAction(e -> ManualSetup.configureSetup());
        quitBtn.setOnAction(e -> System.exit(1));

        // Add buttons to layout
        VBox vbox = new VBox(autoSetupBtn, manualSetupBtn, quitBtn);

        // Create a scene and add layout to scene
        Scene scene = new Scene(vbox, 300, 200);

        // Add scene to stage
        stage.setScene(scene);
        stage.setTitle("Solar Setup");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
