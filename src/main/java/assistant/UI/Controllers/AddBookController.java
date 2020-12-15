package assistant.UI.Controllers;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book: " + title + "was successfully added to database");
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
