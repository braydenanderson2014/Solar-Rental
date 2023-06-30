package Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import InstallManager.FirstTimeManager;
import InstallManager.ProgramController;
import MainSystem.AdministrativeFunctions;
import MainSystem.MainMenu;

import MainSystem.SettingsController;
import UserController.LoginUserController;
import messageHandler.MessageProcessor;

public class Login {
    private static String currentUser = "Null";
    private static TextField usernameField;
    private static PasswordField passwordField;

    public static void showLoginScreen(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        TextArea consoleOutput = new TextArea();
        gridPane.add(consoleOutput, 0, 4, 2, 1);
        consoleOutput.setEditable(false);

        Label usernameLabel = new Label("Username:");
        gridPane.add(usernameLabel, 0, 1);
        usernameField = new TextField();
        gridPane.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 2);
        passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> handleLogin());
        gridPane.add(loginBtn, 1, 3);

        Scene scene = new Scene(gridPane, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Login Screen");
        stage.show();
    }
    public static void showLoginScreen(Stage stage, String User) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        TextArea consoleOutput = new TextArea();
        gridPane.add(consoleOutput, 0, 4, 2, 1);
        consoleOutput.setEditable(false);

        Label usernameLabel = new Label("Username:");
        gridPane.add(usernameLabel, 0, 1);
        usernameField = new TextField(User);
        gridPane.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 2);
        passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> handleLogin());
        gridPane.add(loginBtn, 1, 3);

        Scene scene = new Scene(gridPane, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Login Screen");
        stage.show();
    }
    private static void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (LoginUserController.checkPassword(username, password)) {
            SwitchController.updateCurrentUser(username);
            MainMenu.mainMenu();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }

}