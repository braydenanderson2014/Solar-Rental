package UserController;

import java.io.*;
import java.util.Properties;

import com.google.common.base.Optional;

import assets.CustomScanner;
import assets.Logo;
import assets.Notebook;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import InstallManager.ProgramController;
import MainSystem.Main;
import MainSystem.SettingsController;
import messageHandler.AllMessages;
import messageHandler.MessageProcessor;

public class LoginUserController {
    static String UserProperties = ProgramController.userRunPath + "\\Users/";
    static String UserProperties2 = ProgramController.userRunPath + "\\Users/";
    static int FailedAttemptsLOMG = 0;
    static byte passChangeAttempts = 0;
    public static boolean passFlag = false;
    public static Properties userprop = new Properties();

    private static boolean loadUserlist() {
        return UserListController.loadUserList();
    }

    public static boolean loadUserProperties(String User) {
        loadUserlist();
        if (UserListController.SearchForUser(User)) {
            UserProperties = UserProperties2 + User + ".properties";
            try (InputStream input = new FileInputStream(UserProperties)) {
                userprop.load(input);
                MessageProcessor.processMessage(1, "User Profile Loaded, Ready for Login Functions", false);
                return true;
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, e.toString(), true);
                MessageProcessor.processMessage(-1, "Unable to load User Profile", true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
                return false;
            }
        }
        MessageProcessor.processMessage(-1, "Unable to find User on Userlist.", true);
        return false;
    }

    public static boolean saveUserProperties(String User) {
        loadUserlist();
        boolean success = UserListController.SearchForUser(User);
        if (success) {
            UserProperties = UserProperties2 + User + ".properties";
            try (OutputStream output = new FileOutputStream(UserProperties)) {
                userprop.store(output, null);
                MessageProcessor.processMessage(1, "User Profile Saved! LoginUserController", false);
                return true;
            } catch (IOException e) {
                MessageProcessor.processMessage(-2, e.toString(), true);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();

                MessageProcessor.processMessage(2, stackTrace, true);
                return false;
            }
        }
        MessageProcessor.processMessage(-1, "User Not Found: LoginUserController: SaveUserProperties", false);
        return false;
    }

    public static boolean checkPassword(String User, String Pass) {
        loadUserlist();
        if (UserListController.SearchForUser(User)) {
            if (checkUserProfileFile(User)) {
                loadUserProperties(User);
                if (SearchForKey("Password")) {
                    if (getProperty("Password").equals(Pass)) {
                        if (SearchForKey("Account")) {
                            if (getProperty("Account").equals("Enabled")) {
                                int logins = Integer.parseInt(getProperty("SuccessfulLogins"));
                                logins++;
                                setValue(User, "SuccessfulLogins", String.valueOf(logins));
                                setValue(User, "LastLogin", AllMessages.dTime);

                                if (Boolean.parseBoolean(getProperty("PassFlag"))) {
                                    if (SettingsController.getSetting("UI").equals("Enabled")) {
                                        Notebook.currentNoteName = null;
                                        Notebook.currentNotePath = null;
                                        Notebook.currentNote.clear();
                                        if (changePassword(new Stage(), User)) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        ChangePass(User);
                                        Notebook.currentNoteName = null;
                                        Notebook.currentNotePath = null;
                                        Notebook.currentNote.clear();
                                        return true;
                                    }
                                } else if (!Boolean.parseBoolean(getProperty("PassFlag"))) {
                                    Notebook.currentNoteName = null;
                                    Notebook.currentNotePath = null;
                                    Notebook.currentNote.clear();
                                    return true;
                                }
                            } else if (getProperty("Account").equals("Disabled")) {
                                MessageProcessor.processMessage(-1, "Account is Disabled", true);
                                return false;
                            }
                        } else if (!SearchForKey("Account")) {
                            MessageProcessor.processMessage(-1,
                                    "Unable to locate Account Property, Repair needed for profile, Auto Account Disable Activating...",
                                    true);
                            return false;
                        }
                    } else {
                        MessageProcessor.processMessage(-1, "Invalid Username or Password.", true);
                        FailedAttemptsLOMG = Integer.parseInt(getProperty("FailedLoginAttempts"));
                        FailedAttemptsLOMG = FailedAttemptsLOMG++;
                        setValue(User, "FailedLoginAttempts", String.valueOf(FailedAttemptsLOMG));
                        setValue(User, "AllTimeFailedLoginAttempts",
                                String.valueOf(Integer.parseInt(getProperty("AllTimeFailedLoginAttempts")) + 1));
                        int FailedAttempts = Integer.parseInt(getProperty("FailedLoginAttempts"));
                        if (FailedAttempts >= Integer.parseInt(SettingsController.getSetting("FailedAttempts"))) {
                            setValue(User, "Account", "Disabled");
                        }
                        return false;
                    }
                } else if (!SearchForKey("Password")) {
                    MessageProcessor.processMessage(-1,
                            "Unable to locate Password Property, Repair needed for profile... Auto Account Disable Activating...",
                            true);
                    return false;
                    // Auto Disable.
                }
            } else if (!checkUserProfileFile(User)) {
                MessageProcessor.processMessage(-1, "Unable to locate User Profile.", true);
                MessageProcessor.processMessage(-2, "User is on Userlist, But the Profile is not able to found.", true);
                return false;
            }
        } else if (!UserListController.SearchForUser(User)) {
            MessageProcessor.processMessage(-1, "User Not Found: LoginUserController: CheckPassword", true);
            return false;
        }
        return true;
    }

    public static boolean ChangePass(String User) {
        MessageProcessor.processMessage(1, "Password Change Initiated: ChangePass for Account: " + User, false);
        loadUserProperties(User);
        Logo.displayLogo();
        System.out.println("Old Password: ");
        String oldPass = CustomScanner.nextLine();
        if (oldPass.equals("back") || oldPass.equals("Back")) {
            return false;
        }
        System.out.println("New Password: ");
        String newPass = CustomScanner.nextLine();
        if (newPass.equals("back") || newPass.equals("Back")) {
            return false;
        }
        System.out.println("Confirm New Password: ");
        String cNewPass = CustomScanner.nextLine();
        if (cNewPass.equals("back") || cNewPass.equals("Back")) {
            return false;
        }
        if (oldPass.equals(getProperty("Password"))) {
            if (cNewPass.equals(newPass)) {
                setValue(User, "Password", newPass);
                setValue(User, "PassFlag", "false");
                setValue(User, "LastPassChange", AllMessages.getTime());
                MessageProcessor.processMessage(1, "Password Changed Sucessfully: ChangePass", true);
                return true;
            }
            MessageProcessor.processMessage(-1, "Passwords do not match, Try Again. Type \"Back\" to cancel", true);
            ChangePass(User);
        } else {
            passChangeAttempts++;
            if (passChangeAttempts >= 3) {
                setValue(User, "Account", "Disabled");
                MessageProcessor.processMessage(-1, "Account was Disabled due to too many Attempts", true);
                return false;
            }
            MessageProcessor.processMessage(-1, "Old Password was incorrect, Password Change Failed", true);
            return false;
        }
        return true;
    }

    public static boolean changePassword(Stage primaryStage, String User) {

        if (primaryStage == null || primaryStage.getScene() == null) {
            primaryStage = new Stage();
            primaryStage.setScene(new Scene(new Group(), 450, 250)); // Set a basic scene
        }
        // Create a new dialog
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.initOwner(primaryStage);
        dialog.setTitle("Change Password for " + User);
        dialog.initModality(Modality.WINDOW_MODAL);

        // Set up the GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField oldPassField = new PasswordField();
        oldPassField.setPromptText("Old Password");
        PasswordField newPassField = new PasswordField();
        newPassField.setPromptText("New Password");
        PasswordField confirmNewPassField = new PasswordField();
        confirmNewPassField.setPromptText("Confirm New Password");

        grid.add(new Label("Old Password:"), 0, 0);
        grid.add(oldPassField, 1, 0);
        grid.add(new Label("New Password:"), 0, 1);
        grid.add(newPassField, 1, 1);
        grid.add(new Label("Confirm New Password:"), 0, 2);
        grid.add(confirmNewPassField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType changeButtonType = new ButtonType("Change Password", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(changeButtonType, ButtonType.CANCEL);

        // Handle the result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == changeButtonType) {
                String oldPass = oldPassField.getText();
                String newPass = newPassField.getText();
                String confirmNewPass = confirmNewPassField.getText();

                if (!oldPass.equals(getProperty(User, "Password"))) {
                    // Old password doesn't match
                    showAlert(Alert.AlertType.ERROR, "Error", "Old Password is incorrect.");
                    return false;
                }
                if (!newPass.equals(confirmNewPass)) {
                    // New passwords don't match
                    showAlert(Alert.AlertType.ERROR, "Error", "New Passwords do not match.");
                    return false;
                }
                if (newPass.equals(oldPass)) {
                    // New password is same as old
                    showAlert(Alert.AlertType.ERROR, "Error", "New Password cannot be same as old password.");
                    return false;
                }

                // Here, actually change the password
                setValue(User, "Password", newPass);
                setValue(User, "PassFlag", "false");
                setValue(User, "LastPassChange", AllMessages.getTime());

                showAlert(Alert.AlertType.INFORMATION, "Success", "Password Changed Successfully");
                return true;
            }
            return false;
        });

        // Show the dialog and capture the result
        java.util.Optional<Boolean> result = dialog.showAndWait();
        return result.orElse(false);
    }

    private static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean adminUpdateUserPass(String user) {
        setValue(user, "Password", "Solar");
        setValue(user, "PassFlag", "true");
        MessageProcessor.processMessage(1, "Password Update for User: " + user + "; Password: Solar", true);
        return true;
    }

    public static boolean setValue(String user, String key, String value) {
        loadUserProperties(user);
        userprop.setProperty(key, value);
        saveUserProperties(user);
        return true;
    }

    private static boolean SearchForKey(String key) {
        return userprop.containsKey(key);

    }

    private static String getProperty(String key) {
        return userprop.getProperty(key);
    }

    public static String getProperty(String user, String key) {
        loadUserProperties(user);
        return userprop.getProperty(key);
    }

    private static boolean checkUserProfileFile(String user) {
        UserProperties = UserProperties2 + user + ".properties";
        File file = new File(UserProperties);
        return file.exists();
    }
}