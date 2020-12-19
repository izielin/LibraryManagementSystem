package assistant.UI.Controllers;

import assistant.database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static assistant.alert.AlertMaker.showSimpleAlert;

public class AddMemberController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField memberName;
    @FXML
    private JFXTextField memberID;
    @FXML
    private JFXTextField memberMobile;
    @FXML
    private JFXTextField memberEmail;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    //object of DatabaseHandler
    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    public void executeSaveAction() {
        String name = memberName.getText();
        String id = memberID.getText();
        String mobile = memberMobile.getText();
        String email = memberEmail.getText();

        if (id.isEmpty() || name.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
            showSimpleAlert("error", "", "", "Please enter data in all fields");
        } else {
            String action = "INSERT INTO MEMBER VALUES (" +
                    "'" + id + "'," +
                    "'" + name + "'," +
                    "'" + mobile + "'," +
                    "'" + email + "'" +
                    ")";
            System.out.println(action);
            if (databaseHandler.execAction(action)) {
                showSimpleAlert("information", "", "", "\"Member: \" + name + \"was successfully added to database\"");
                memberEmail.clear();
                memberID.clear();
                memberName.clear();
                memberMobile.clear();
            } else {
                showSimpleAlert("error", "", "", "Something went wrong");
            }
        }
    }

    @FXML
    private void executeCancelAction() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
