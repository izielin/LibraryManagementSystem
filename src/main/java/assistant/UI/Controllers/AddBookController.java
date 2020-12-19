package assistant.UI.Controllers;

import assistant.database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
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
        checkData();
    }

    @FXML
    private void executeSaveAction() {
        String id = bookID.getText();
        String authorName = bookAuthorName.getText();
        String title = bookTitle.getText();
        String publisher = publishingCompanyName.getText();

        if (id.isEmpty() || authorName.isEmpty() || title.isEmpty() || publisher.isEmpty()) {
            showSimpleAlert("error", "", "", "Please enter data in all fields");
        } else {
            String action = "INSERT INTO BOOK VALUES (" +
                    "'" + id + "'," +
                    "'" + title + "'," +
                    "'" + authorName + "'," +
                    "'" + publisher + "'," +
                    "" + true + "" +
                    ")";

            System.out.println(action);
            if (databaseHandler.execAction(action)) {
                showSimpleAlert("information", "", "", "Book: " + title + "was successfully added to database");
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
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData() {
        String query = "SELECT title FROM BOOK";
        ResultSet resultSet = databaseHandler.execQuery(query);
        try {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                System.out.println(title);
            }
        } catch (SQLException e) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
