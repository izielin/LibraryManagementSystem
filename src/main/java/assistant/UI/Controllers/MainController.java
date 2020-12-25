package assistant.UI.Controllers;

import assistant.Utils.Utils;
import assistant.database.DatabaseHandler;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

import static assistant.Utils.Utils.getResourceBundle;
import static assistant.Utils.Utils.loadWindow;
import static assistant.alert.AlertMaker.alertConfirm;
import static assistant.alert.AlertMaker.showSimpleAlert;


public class MainController implements Initializable {

    private static final String FXML_ADD_BOOK = "/fxml/AddBook.fxml";
    private static final String FXML_LIST_BOOK = "/fxml/BookList.fxml";
    private static final String FXML_ADD_MEMBER = "/fxml/AddMember.fxml";
    private static final String FXML_LIST_MEMBER = "/fxml/MemberList.fxml";
    private static final String FXML_TOOLBAR = "/fxml/ToolBar.fxml";


    @FXML
    private BorderPane rootPane;
    @FXML
    private ListView<String> checkOutDataList;
    @FXML
    private JFXTextField bookIDInput;
    @FXML
    private JFXTextField bookIdInput;
    @FXML
    private JFXTextField memberIDInput;
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

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;

    DatabaseHandler databaseHandler;
    boolean isReadyForSubmission = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
        setTitleBar();

        initDrawer();
    }

    private void initDrawer() {
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource(FXML_TOOLBAR), getResourceBundle());
            drawer.setSidePane(toolbar);
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });

    }

    public void setTitleBar() {
        rootPane.setTop(Utils.fxmlLoader("/fxml/CustomTitleBar.fxml"));
    }

    // menu actions
    @FXML
    private void MenuCloseApplication() {
        Optional<ButtonType> result = alertConfirm("Close Application", "", "Are you sure you want to close app?");
        if (result.orElse(null) == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void MenuAddBook() {
        loadWindow(FXML_ADD_BOOK);
    }

    @FXML
    private void MenuAddMember() {
        loadWindow(FXML_ADD_MEMBER);
    }

    @FXML
    private void MenuViewBook() {
    loadWindow(FXML_LIST_BOOK);
    }

    @FXML
    private void MenuViewMember() {
        loadWindow(FXML_LIST_MEMBER);
    }

    @FXML
    private void MenuFullScreen() {
        Stage stage = ((Stage) bookTitle.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    private void loadMemberInformation() {
        String memberID = memberIDInput.getText();
        String query = "SELECT * FROM MEMBER WHERE id = '" + memberID + "'";
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
    private void loadBookInformation() {
        String bookID = bookIDInput.getText();
        String query = "SELECT * FROM BOOK WHERE id = '" + bookID + "'";
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
    private void executeCheckOutOperation() {
        String memberID = memberIDInput.getText();
        String bookID = bookIDInput.getText();

        Optional<ButtonType> response = alertConfirm("Confirm chek book out operation", "",
                "Are you sure you want to lend '" + bookTitle.getText() + "' to " + memberName.getText() + "?");

        if (response.orElse(null) == ButtonType.OK) {
            String action = "INSERT INTO CHECK_OUT(memberID, bookID) VALUES( " +
                    "'" + memberID + "'," +
                    "'" + bookID + "')";
            String action2 = "UPDATE BOOK SET isAvailable = false WHERE id = '" + bookID + "'";

            if (databaseHandler.execAction(action) && databaseHandler.execAction(action2)) {
                showSimpleAlert("information", "Success", "", "Operation ended successfully");
                memberIDInput.clear();
                bookIDInput.clear();
                bookTitle.setText(getResourceBundle().getString("bookTitle"));
                bookAuthor.setText(getResourceBundle().getString("bookAuthor"));
                bookStatus.setText(getResourceBundle().getString("availability"));
                memberName.setText(getResourceBundle().getString("memberName"));
                memberContact.setText(getResourceBundle().getString("memberContact"));
            } else {
                showSimpleAlert("error", "Failed", "", "Operation ended unsuccessfully");
            }
        } else {
            showSimpleAlert("information", "Cancelled", "", "Operation was cancelled");
        }
    }

    @FXML
    private void loadBookCheckOut() {
        ObservableList<String> checkOutData = FXCollections.observableArrayList();
        isReadyForSubmission = false;

        String bookID = bookIdInput.getText();
        String query = "SELECT * FROM CHECK_OUT WHERE bookID = '" + bookID + "'";
        ResultSet resultSet = databaseHandler.execQuery(query);

        try {
            while (resultSet.next()) {
                String memberID = resultSet.getString("memberID");
                Timestamp timestamp = resultSet.getTimestamp("checkOut");
                int renewCount = resultSet.getInt("renew_count");

                checkOutData.add(getResourceBundle().getString("checkOutDataList.timestamp") + ": " + timestamp.toString());
                checkOutData.add(getResourceBundle().getString("checkOutDataList.renew") + ": " + renewCount);

                checkOutData.add("");
                checkOutData.add(getResourceBundle().getString("checkOutDataList.book") + ": ");
                query = "SELECT * FROM BOOK WHERE id = '" + bookID + "'";
                ResultSet bookResultSet = databaseHandler.execQuery(query);

                while (bookResultSet.next()) {
                    checkOutData.add(getResourceBundle().getString("bookTitle") + ": " + bookResultSet.getString("title"));
                    checkOutData.add(getResourceBundle().getString("bookID") + ": " + bookResultSet.getString("id"));
                    checkOutData.add(getResourceBundle().getString("bookAuthor") + ": " + bookResultSet.getString("author"));
                    checkOutData.add(getResourceBundle().getString("publisher") + ": " + bookResultSet.getString("publisher"));
                }

                checkOutData.add("");
                checkOutData.add(getResourceBundle().getString("checkOutDataList.member") + ": ");
                query = "SELECT * FROM MEMBER WHERE id = '" + memberID + "'";
                ResultSet memberResultSet = databaseHandler.execQuery(query);
                while (memberResultSet.next()) {
                    checkOutData.add(getResourceBundle().getString("memberName") + ": " + memberResultSet.getString("name"));
                    checkOutData.add(getResourceBundle().getString("memberMobile") + ": " + memberResultSet.getString("mobile"));
                    checkOutData.add(getResourceBundle().getString("memberEmail") + ": " + memberResultSet.getString("email"));
                }
                isReadyForSubmission = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkOutDataList.getItems().setAll(checkOutData);
    }

    @FXML
    private void loadSubmissionOperation() {
        if (!isReadyForSubmission) {
            showSimpleAlert("error", "Failed", "", "Please select book to submit");
        } else {
            Optional<ButtonType> response = alertConfirm("Confirm submission operation", "", "Are you sure you want to return the book?");
            if (response.orElse(null) == ButtonType.OK) {
                String bookID = bookIdInput.getText();
                String actionDelete = "DELETE FROM CHECK_OUT WHERE bookID = '" + bookID + "'";
                String actionUpdate = "UPDATE BOOK SET isAvailable = true WHERE id = '" + bookID + "'";

                if (databaseHandler.execAction(actionDelete) && databaseHandler.execAction(actionUpdate)) {
                    showSimpleAlert("information", "Success", "Book has been Submitted", "Operation ended successfully");
                    bookIdInput.clear();
                    checkOutDataList.getItems().clear();
                } else {
                    showSimpleAlert("error", "Failed", "", "Operation ended unsuccessfully");
                }
            } else {
                showSimpleAlert("information", "Cancelled", "", "Operation was cancelled");
            }
        }
    }

    @FXML
    private void loadRenewOperation() {
        if (!isReadyForSubmission) {
            showSimpleAlert("error", "Failed", "", "Please select book to renew");
        } else {
            Optional<ButtonType> response = alertConfirm("Confirm renew operation", "", "Are you sure you want to renew the book?");
            if (response.orElse(null) == ButtonType.OK) {
                String action = "UPDATE CHECK_OUT SET checkOut = CURRENT_TIMESTAMP, renew_count = renew_count+1 where bookID = '" + bookIdInput.getText() + "'";

                if (databaseHandler.execAction(action)) {
                    showSimpleAlert("information", "Success", "Book has been successfully renewed", "Operation ended successfully");
                    checkOutDataList.getItems().clear();
                    loadBookCheckOut();
                } else {
                    showSimpleAlert("error", "Failed", "", "Operation ended unsuccessfully");
                }
            } else {
                showSimpleAlert("information", "Cancelled", "", "Operation was cancelled");
            }
        }
    }

}
