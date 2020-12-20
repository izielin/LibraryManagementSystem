package assistant.UI.Controllers;

import assistant.database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static assistant.alert.AlertMaker.showSimpleAlert;

public class AddBookController implements Initializable {
    @FXML
    private AnchorPane rootPane;
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
        databaseHandler = DatabaseHandler.getInstance();
    }

    @FXML
    private void executeSaveAction() {
        // retrieving user input of the book data from JFXTextField fields
        String id = bookID.getText();
        String authorName = bookAuthorName.getText();
        String title = bookTitle.getText();
        String publisher = publishingCompanyName.getText();

        // checking if any of the fields is empty
        if (id.isEmpty() || authorName.isEmpty() || title.isEmpty() || publisher.isEmpty()) {
            showSimpleAlert("error", "", "", "Please enter data in all fields");
        } else {
            // creating a command to add data to a table
            String action = "INSERT INTO BOOK VALUES (" +
                    "'" + id + "'," +
                    "'" + title + "'," +
                    "'" + authorName + "'," +
                    "'" + publisher + "'," +
                    "" + true + "" +
                    ")";

            if (databaseHandler.execAction(action)) { // performing the data adding operation
                showSimpleAlert("information", "", "", "Book: " + title + "was successfully added to database");
                // clearing the input fields
                bookID.clear();
                bookAuthorName.clear();
                bookTitle.clear();
                publishingCompanyName.clear();
            } else {
                showSimpleAlert("error", "", "", "Something went wrong");
            }
        }
    }

    @FXML
    private void executeCancelAction() {
        // closing current window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}
