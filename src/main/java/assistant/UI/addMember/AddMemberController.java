package assistant.UI.addMember;

import assistant.database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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
        databaseHandler = new DatabaseHandler();
    }

    public void addMember(ActionEvent actionEvent) {
        String name = memberName.getText();
        String id = memberID.getText();
        String mobile = memberMobile.getText();
        String email = memberEmail.getText();

        if (id.isEmpty() || name.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter data in all fields");
            alert.showAndWait();
        } else {
            String action = "INSERT INTO MEMBER VALUES (" +
                    "'" + id + "'," +
                    "'" + name + "'," +
                    "'" + mobile + "'," +
                    "'" + email + "'" +
                    ")";

            System.out.println(action);
            if (databaseHandler.execAction(action)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Member: " + name + "was successfully added to database");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong");
                alert.showAndWait();

            }
        }
    }

    @FXML
    private void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
