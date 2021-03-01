package assistant.UI.Controllers.Member;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import static assistant.Utils.ProjectTools.loadWindow;


public class MemberToolBarController {
    @FXML
    private JFXButton logoutButton;

    // open other windows
    @FXML
    void logout() {
        ((Stage) logoutButton.getScene().getWindow()).close();
        loadWindow("/fxml/Login.fxml");
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }
}
