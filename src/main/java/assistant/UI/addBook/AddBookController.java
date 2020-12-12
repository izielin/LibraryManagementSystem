package assistant.UI.addBook;

import assistant.database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

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

    //object of DatabaseHandler
    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = new DatabaseHandler();
    }

    @FXML
    private void addBook(ActionEvent actionEvent) {
        String id = bookID.getText();
        String authorName = bookAuthorName.getText();
        String title = bookTitle.getText();
        String publisher = publishingCompanyName.getText();

        if (id.isEmpty() || authorName.isEmpty() || title.isEmpty() || publisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter data in all fields");
            alert.showAndWait();
        }
        String action = "INSERT INTO BOOK VALUES (" +
                "'" + id + "'," +
                "'" + title + "'," +
                "'" + authorName + "'," +
                "'" + publisher + "'," +
                "" + true + "" +
                ")";

        System.out.println(action);
        if ( databaseHandler.execAction(action)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Book: " + title + "was successfully added to database");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong");
            alert.showAndWait();

        }
    }

    @FXML
    private void cancelAction(ActionEvent actionEvent) {
    }


}
