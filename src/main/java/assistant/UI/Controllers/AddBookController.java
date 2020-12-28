package assistant.UI.Controllers;

import assistant.UI.Controllers.BookListController.Book;
import assistant.Utils.Utils;
import assistant.database.DatabaseHandler;
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
    @FXML
    private JFXButton saveAndCloseButton;
    @FXML
    private BorderPane mainBorderPane;

    //object of DatabaseHandler
    DatabaseHandler databaseHandler;
    private boolean isInEditMode = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
        setTitleBar();
    }

    public void setTitleBar() {
        mainBorderPane.setTop(Utils.fxmlLoader("/fxml/BaseTitleBar.fxml"));
    }

    @FXML
    private void executeSaveAction(ActionEvent event) {
        // retrieving user input of the book data from JFXTextField fields
        String id = bookID.getText();
        String authorName = bookAuthorName.getText();
        String title = bookTitle.getText();
        String publisher = publishingCompanyName.getText();

        // checking if any of the fields is empty
        if (id.isEmpty() || authorName.isEmpty() || title.isEmpty() || publisher.isEmpty()) {
            showSimpleAlert("error", "", "", "Please enter data in all fields");
        } else {
            if (isInEditMode) {
                Book book = new Book(title, id, authorName, publisher, true);
                if (databaseHandler.updateBook(book)) {
                    showSimpleAlert("information", "Success", "", "Book:" + book.getTitleProperty() + " was successfully updated");
                    ((Stage) bookID.getScene().getWindow()).close();
                } else {
                    showSimpleAlert("error", "Error", "", "Something went wrong");
                }
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
                    try {
                        Button button = (Button) event.getSource();
                        if (button.getId().equals("saveAndCloseButton"))
                            ((Stage) bookID.getScene().getWindow()).close();
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
        // closing current window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void inflateUI(Book book) {
        bookTitle.setText(book.getTitleProperty());
        bookID.setText(book.getIdProperty());
        bookAuthorName.setText(book.getAuthorProperty());
        publishingCompanyName.setText(book.getPublisherProperty());
        bookID.setEditable(false);
        isInEditMode = true;
    }
}

