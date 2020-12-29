package assistant.UI.Controllers;

import assistant.Utils.Utils;
import assistant.database.db;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static assistant.alert.AlertMaker.showSimpleAlert;

public class AddMemberController implements Initializable {

    private boolean isInEditMode = false;
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
    @FXML
    private JFXButton saveAndCloseButton;

    @FXML
    private BorderPane mainBorderPane;

    //object of DatabaseHandler
    db databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = db.getInstance();
        setTitleBar();
    }

    public void setTitleBar() {
        System.out.println("work");
        mainBorderPane.setTop(Utils.fxmlLoader("/fxml/BaseTitleBar.fxml"));
    }

    public void executeSaveAction(ActionEvent event) {
        String name = memberName.getText();
        String id = memberID.getText();
        String mobile = memberMobile.getText();
        String email = memberEmail.getText();

        if (id.isEmpty() || name.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
            showSimpleAlert("error", "", "", "Please enter data in all fields");
        } else {
            if (isInEditMode) {
                MemberListController.Member member = new MemberListController.Member(name, id, mobile, email);
                if (databaseHandler.updateMember(member)) {
                    showSimpleAlert("information", "Success", "", "Member:" + member.getNameProperty() + " was successfully updated");
                    ((Stage) memberID.getScene().getWindow()).close();
                } else {
                    showSimpleAlert("error", "Error", "", "Something went wrong");
                }
            } else {
                String action = "INSERT INTO MEMBER VALUES (" +
                        "'" + id + "'," +
                        "'" + name + "'," +
                        "'" + mobile + "'," +
                        "'" + email + "'" +
                        ")";
                System.out.println(action);
                if (databaseHandler.execAction(action)) {
                    showSimpleAlert("information", "", "", "Member: " + name + "was successfully added to database");
                    memberEmail.clear();
                    memberID.clear();
                    memberName.clear();
                    memberMobile.clear();
                    try {
                        Button button = (Button) event.getSource();
                        if (button.getId().equals("saveAndCloseButton"))
                            ((Stage) memberID.getScene().getWindow()).close();
                    } catch (ClassCastException ignored) {
                    }
                } else {
                    showSimpleAlert("error", "", "", "Something went wrong");
                }
            }
        }
    }

    @FXML
    private void executeCancelAction() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void inflateUI(MemberListController.Member member) {
        memberName.setText(member.getNameProperty());
        memberID.setText(member.getIdProperty());
        memberMobile.setText(member.getMobileProperty());
        memberEmail.setText(member.getEmailProperty());
        memberID.setEditable(false);
        isInEditMode = true;
    }
}
