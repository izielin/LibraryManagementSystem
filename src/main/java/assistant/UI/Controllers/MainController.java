package assistant.UI.Controllers;

import assistant.database.DatabaseHandler;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {

    private static final String FXML_ADD_BOOK = "/fxml/AddBook.fxml";
    private static final String FXML_LIST_BOOK = "/fxml/BookList.fxml";
    private static final String FXML_ADD_MEMBER = "/fxml/AddMember.fxml";
    private static final String FXML_LIST_MEMBER = "/fxml/MemberList.fxml";

    @FXML
    private ListView<String> checkOutDataList;
    @FXML
    private TextField bookIDInput;
    @FXML
    private JFXTextField bookIdInput;
    @FXML
    private TextField memberIDInput;
    @FXML
    private Text bookStatus;
    @FXML
    private Text memberName;
    @FXML
    private Text memberContact;
    @FXML
    private Text bookTitle;
    @FXML
    private Text bookAuthor;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("MainBundle");
    }

    @FXML
    private void loadAddMember(ActionEvent actionEvent) {
        loadWindow(FXML_ADD_MEMBER);
    }

    @FXML
    private void loadAddBook(ActionEvent actionEvent) {
        loadWindow(FXML_ADD_BOOK);
    }

    @FXML
    private void loadMemberTable(ActionEvent actionEvent) {
        loadWindow(FXML_LIST_MEMBER);
    }

    @FXML
    private void loadBookTable(ActionEvent actionEvent) {
        loadWindow(FXML_LIST_BOOK);
    }

    void loadWindow(String path) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(path), getResourceBundle());
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    private void loadMemberInformation(ActionEvent actionEvent) {
        String id = memberIDInput.getText();
        String query = "SELECT * FROM MEMBER WHERE id = '" + id + "'";
        ResultSet resultSet = databaseHandler.execQuery(query);
        boolean flag = false;
        try {
            while (resultSet.next()) {
                // get data from db
                String mName = resultSet.getString("name");
                String mobile = resultSet.getString("mobile");
                String email = resultSet.getString("email");

                // set data to TextField
                memberName.setText(mName);
                memberContact.setText(mobile + ", " + email);
                flag = true;
            }
            if (!flag) {
                memberName.setText("Any member is associated with this id");
                memberContact.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void loadBookInformation(ActionEvent actionEvent) {
        String id = bookIDInput.getText();
        String query = "SELECT * FROM BOOK WHERE id = '" + id + "'";
        ResultSet resultSet = databaseHandler.execQuery(query);
        boolean flag = false;
        try {
            while (resultSet.next()) {
                // get data from db
                String bName = resultSet.getString("title");
                String bAuthor = resultSet.getString("author");
                boolean bStatus = resultSet.getBoolean("isAvailable");

                // set data to TextField
                bookTitle.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = bStatus ? "available" : "nAvailable";
                bookStatus.setText(getResourceBundle().getString(status));

                flag = true;
            }
            if (!flag) {
                bookTitle.setText("Any book is associated with this id");
                bookAuthor.setText("");
                bookStatus.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void executeCheckOutOperation(ActionEvent actionEvent) {
        String memberID = memberIDInput.getText();
        String bookID = bookIDInput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm chek out Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to lend '" + bookTitle.getText() + "' to " + memberName.getText() + "?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String action = "INSERT INTO CHECK_OUT(memberID, bookID) VALUES( " +
                    "'" + memberID + "'," +
                    "'" + bookID + "')";
            String action2 = "UPDATE BOOK SET isAvailable = false WHERE id = '" + bookID + "'";

            if (databaseHandler.execAction(action) && databaseHandler.execAction(action2)) {
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setTitle("Success");
                alertInfo.setHeaderText(null);
                alertInfo.setContentText("Operation ended successfully");
                alertInfo.showAndWait();
            } else {
                Alert alertError = new Alert(Alert.AlertType.INFORMATION);
                alertError.setTitle("Failed");
                alertError.setHeaderText(null);
                alertError.setContentText("Operation ended unsuccessfully");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void loadBookCheckOut(ActionEvent actionEvent) {
        ObservableList<String> checkOutData = FXCollections.observableArrayList();

        String id = bookIdInput.getText();
        String query = "SELECT * FROM CHECK_OUT WHERE bookID = '" + id + "'";
        ResultSet resultSet = databaseHandler.execQuery(query);

        try {
            while (resultSet.next()) {
                String memberID = resultSet.getString("memberID");
                Timestamp timestamp = resultSet.getTimestamp("checkOut");
                int renewCount = resultSet.getInt("renew_count");

                checkOutData.add(getResourceBundle().getString("checkOutDataList.timestamp") +": " + timestamp.toGMTString());
                checkOutData.add(getResourceBundle().getString("checkOutDataList.renew") +": " + renewCount);

                checkOutData.add("");
                checkOutData.add(getResourceBundle().getString("checkOutDataList.book")+": ");
                query = "SELECT * FROM BOOK WHERE id = '" + id + "'";
                ResultSet bookResultSet = databaseHandler.execQuery(query);

                while (bookResultSet.next()) {
                    checkOutData.add(getResourceBundle().getString("bookTitle") + ": " + bookResultSet.getString("title"));
                    checkOutData.add(getResourceBundle().getString("bookID") + ": " + bookResultSet.getString("id"));
                    checkOutData.add(getResourceBundle().getString("bookAuthor") + ": " + bookResultSet.getString("author"));
                    checkOutData.add(getResourceBundle().getString("publisher") + ": " + bookResultSet.getString("publisher"));
                }

                checkOutData.add("");
                checkOutData.add(getResourceBundle().getString("checkOutDataList.member")+": ");
                query = "SELECT * FROM MEMBER WHERE id = '" + memberID + "'";
                ResultSet memberResultSet = databaseHandler.execQuery(query);
                while (memberResultSet.next()) {
                    checkOutData.add(getResourceBundle().getString("memberName") + ": " + memberResultSet.getString("name"));
                    checkOutData.add(getResourceBundle().getString("memberMobile") + ": " + memberResultSet.getString("mobile"));
                    checkOutData.add(getResourceBundle().getString("memberEmail") + ": " + memberResultSet.getString("email"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        checkOutDataList.getItems().setAll(checkOutData);

    }
}
