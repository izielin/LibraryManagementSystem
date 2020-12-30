package assistant.UI.Controllers;

import assistant.Utils.Utils;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.Library;
import assistant.database.models.User;
import assistant.settings.Settings;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static assistant.Utils.Utils.loadWindow;

public class LoginController implements Initializable {
    static Library library;

    @FXML
    private BorderPane borderPane;
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
        borderPane.setTop(Utils.fxmlLoader("/fxml/BaseTitleBar.fxml"));
    }

    @FXML
    private void LoginAction() throws IOException {
        String username = usernameInput.getText();
        String password = DigestUtils.sha1Hex(passwordInput.getText());
        CommonDao dao = new CommonDao();

        try {
            User user = dao.findById(User.class, dao.isLogin(username, password)); // creating object of logged user
            if (user != null) {
                // calling up different windows depending on the type of user
                if (user.getUserType().equals("EMPLOYEE")) {
                    library = user.getLibrary(); // creating an object of library in which a logged in employee works
                    closeStage();
                    loadWindow("/fxml/UserList.fxml");
                } else {
                    System.out.println("TO DO");
                }
            } else {
                alertText.setText("Invalid username or password");
                usernameInput.getStyleClass().add("wrong-login-data");
                passwordInput.getStyleClass().add("wrong-login-data");
                passwordInput.clear();
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
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