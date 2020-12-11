package UI.addBook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {
    @FXML
    private JFXTextField bookAuthorName;
    @FXML
    private JFXTextField bookTitle;
    @FXML
    private JFXTextField bookID;
    @FXML
    private JFXTextField publishingCompanyName;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addBook(ActionEvent actionEvent) {
    }

    @FXML
    private void cancelAction(ActionEvent actionEvent) {
    }


}
