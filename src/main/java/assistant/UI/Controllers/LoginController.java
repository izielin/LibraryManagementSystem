package assistant.UI.Controllers;

import assistant.settings.Settings;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static assistant.Utils.Utils.loadWindow;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField usernameInput;
    @FXML
    private JFXPasswordField passwordInput;

    double x, y;
    Settings settings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settings = Settings.getSettings();
    }

    @FXML
    private void LoginAction() throws IOException {
        String username = usernameInput.getText();
        String password = DigestUtils.sha1Hex(passwordInput.getText());

        if (username.equals(settings.getUsername()) && password.equals(settings.getPassword())) {
            closeStage();
            loadWindow("/fxml/Main.fxml", "Library Assistant Login");
        } else {
            usernameInput.getStyleClass().add("wrong-credentials");
            passwordInput.getStyleClass().add("wrong-credentials");
        }
    }

    @FXML
    private void CancelAction() {
        System.exit(0);
    }

    private void closeStage() {
        ((Stage) usernameInput.getScene().getWindow()).close();
    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void fullScreen(MouseEvent event) {
        Stage stage = (Stage) usernameInput.getScene().getWindow();
        stage.setFullScreen(true);

    }

    @FXML
    private void minimize(MouseEvent event) {
        Stage stage = (Stage) usernameInput.getScene().getWindow();
        stage.setIconified(true);

    }

    public void dragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() + x);
        stage.setY(mouseEvent.getScreenY() + y);

    }

    public void pressed(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        x = stage.getX() - mouseEvent.getScreenX();
        y = stage.getY() - mouseEvent.getScreenY();
    }
}