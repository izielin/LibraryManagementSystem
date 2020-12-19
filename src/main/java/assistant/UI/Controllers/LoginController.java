package assistant.UI.Controllers;

import assistant.settings.Settings;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static assistant.UI.Controllers.MainController.loadWindow;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField usernameInput;
    @FXML
    private JFXPasswordField passwordInput;

    Settings settings;
    @FXML
    private Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settings = Settings.getSettings();
    }

    @FXML
    private void LoginAction() {
        titleLabel.setText("Library Assistant Login");
        titleLabel.setStyle("-fx-background-color:#1976D2;-fx-text-fikll:white");

        String username = usernameInput.getText();
        String password = DigestUtils.sha1Hex(passwordInput.getText());

        if (username.equals(settings.getUsername()) && password.equals(settings.getPassword())) {
            closeStage();
            loadWindow("/fxml/Main.fxml");
        } else {
            titleLabel.setText("Invalid username or password");
            titleLabel.setStyle("-fx-background-color:#d32f2f;-fx-text-fill:white");
        }

    }

    @FXML
    private void CancelAction() {
        System.exit(0);
    }

    private void closeStage() {
        ((Stage) usernameInput.getScene().getWindow()).close();
    }
}