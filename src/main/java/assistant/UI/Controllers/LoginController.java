package assistant.UI.Controllers;

import assistant.Utils.Utils;
import assistant.settings.Settings;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static assistant.Utils.Utils.loadWindow;

public class LoginController implements Initializable {

    @FXML
    private  BorderPane borderPane;
    @FXML
    private Text alertText;
    @FXML
    private JFXTextField usernameInput;
    @FXML
    private JFXPasswordField passwordInput;

    Settings settings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settings = Settings.getSettings();
        setTitleBar();
    }

    public void setTitleBar() {
        borderPane.setTop(Utils.fxmlLoader("/fxml/CustomTitleBar.fxml"));
    }

    @FXML
    private void LoginAction() throws IOException {
        String username = usernameInput.getText();
        String password = DigestUtils.sha1Hex(passwordInput.getText());

        if (username.equals(settings.getUsername()) && password.equals(settings.getPassword())) {
            closeStage();
            loadWindow("/fxml/Main.fxml");
        } else {
            alertText.setText("Invalid username or password");
            usernameInput.getStyleClass().add("wrong-login-data");
            passwordInput.getStyleClass().add("wrong-login-data");
            passwordInput.clear();
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