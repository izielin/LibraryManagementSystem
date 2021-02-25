package assistant.UI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static assistant.Utils.AlertMaker.showExitDialog;
import static assistant.Utils.ProjectTools.fxmlLoader;



public class MainController extends TitleBarController {
    private static final String FXML_TOOLBAR = "/fxml/ToolBar.fxml";
    private static final String FXML_ADD_BOOK = "/fxml/addViews/AddBook.fxml";
    private static final String FXML_LIST_BOOK = "/fxml/listViews/BookList.fxml";
    private static final String FXML_ADD_MEMBER = "/fxml/addViews/AddUser.fxml";
    private static final String FXML_LIST_MEMBER = "/fxml/listViews/UserList.fxml";
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private StackPane rootPane;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        insertMenu();
        setCenter("/fxml/DashBoard.fxml");
    }

    private void insertMenu() {
        AnchorPane toolbar = (AnchorPane) fxmlLoader(FXML_TOOLBAR);
        mainBorderPane.setLeft(toolbar);

        VBox vbox = (VBox) toolbar.lookup("#menuVbox");
        for (Node node : vbox.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    switch (node.getAccessibleText()) {
                        case "HomeView" -> setCenter("/fxml/DashBoard.fxml");
                        case "AddBook" -> setCenter("/fxml/addViews/AddBook.fxml");
                        case "AddUser" -> setCenter("/fxml/addViews/AddUser.fxml");
                        case "AddView" -> setCenter("/fxml/listViews/Lists.fxml");
                        case "BookList" -> setCenter("/fxml/listViews/BookList.fxml");
                        case "UserList" -> setCenter("/fxml/listViews/UserList.fxml");
//                        case "BorrowedList" -> setCenter("/fxml/UserList.fxml");
                        case "LendBookView" -> setCenter("/fxml/LendBook.fxml");
                        case "SubmissionBook" -> setCenter("/fxml/SubmitBook.fxml");
//                        case "Settings" -> setCenter("/fxml/UserList.fxml");
                    }
                });
            }
        }
    }


    public void setCenter(String fxmlPath) {
        mainBorderPane.setCenter(fxmlLoader(fxmlPath));
    }


    // menu actions
    public MainController() {
        super();
    }

    @FXML
    private void MenuCloseApplication() {
        showExitDialog(rootPane, mainAnchorPane);
    }

    @FXML
    private void MenuAddBook() {
        setCenter(FXML_ADD_BOOK);
    }

    @FXML
    private void MenuAddMember() {
        setCenter(FXML_ADD_MEMBER);
    }

    @FXML
    private void MenuViewBook() {
        setCenter(FXML_LIST_BOOK);
    }

    @FXML
    private void MenuViewMember() {
        setCenter(FXML_LIST_MEMBER);
    }

//
//
//
//
//    @FXML
//    private void loadBookCheckOut() {
//        clearEntries();
//        isReadyForSubmission = false;
//
//        try {
//            String id = bookIdInput.getText();
//            String query = "SELECT CHECK_OUT.bookID, CHECK_OUT.memberID, CHECK_OUT.checkOut, CHECK_OUT.renew_count, " +
//                    "MEMBER.name, MEMBER.mobile, MEMBER.email, " +
//                    "BOOK.title, BOOK.author, BOOK.publisher, BOOK.isAvailable " +
//                    "FROM CHECK_OUT INNER JOIN MEMBER " +
//                    "ON CHECK_OUT.memberID = MEMBER.id " +
//                    "INNER JOIN BOOK " +
//                    "ON CHECK_OUT.bookID = BOOK.id " +
//                    "WHERE bookID = '" + id + "'";
//
//            ResultSet resultSet = databaseHandler.execQuery(query);
//
//            if (resultSet.next()) {
//                memberNameHolder.setText(resultSet.getString("name"));
//                memberEmailHolder.setText(resultSet.getString("mobile"));
//                memberContactHolder.setText(resultSet.getString("email"));
//
//                bookTitleHolder.setText(resultSet.getString("title"));
//                bookAuthorHolder.setText(resultSet.getString("author"));
//                bookPublisherHolder.setText(resultSet.getString("publisher"));
//
//                Timestamp chekOutTime = resultSet.getTimestamp("checkOut");
//                Date dateOfChekOut = new Date(chekOutTime.getTime());
//
//                long timeElapsed = System.currentTimeMillis() - chekOutTime.getTime();
//                long daysElapsed = TimeUnit.DAYS.convert(timeElapsed, TimeUnit.MILLISECONDS);
//
//                checkOutHolder.setText(dateOfChekOut.toString());
//                dayHolder.setText(Long.toString(daysElapsed));
//                feeHolder.setText("Not Supported Yet");
//
//                isReadyForSubmission = true;
//                toggleControls(false);
//            } else {
//                JFXButton button = new JFXButton("OK");
//                showJFXButton(rootPane, mainBorderPane, Arrays.asList(button), "No such Book exist in Check Out Records", "Try type different id");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void clearEntries() {
//        memberNameHolder.setText("");
//        memberEmailHolder.setText("");
//        memberContactHolder.setText("");
//
//        bookTitleHolder.setText("");
//        bookAuthorHolder.setText("");
//        bookPublisherHolder.setText("");
//
//        checkOutHolder.setText("");
//        dayHolder.setText("");
//        feeHolder.setText("");
//
//        toggleControls(true);
//    }
//
//    private void toggleControls(boolean enableFlag) {
//        renewButton.setDisable(enableFlag);
//        submissionButton.setDisable(enableFlag);
//        if (enableFlag) {
//            submissionDataContainer.setOpacity(0);
//        } else {
//            submissionDataContainer.setOpacity(1);
//        }
//    }
//
//    @FXML
//    private void loadSubmissionOperation() {
//        if (!isReadyForSubmission) {
//            showJFXButton(rootPane, mainBorderPane, new ArrayList<>(), "Failed", "Please select book to submit");
//        } else {
//            Optional<ButtonType> response = alertConfirm("Confirm submission operation", "", "Are you sure you want to return the book?");
//            if (response.orElse(null) == ButtonType.OK) {
//                String bookID = bookIdInput.getText();
//                String actionDelete = "DELETE FROM CHECK_OUT WHERE bookID = '" + bookID + "'";
//                String actionUpdate = "UPDATE BOOK SET isAvailable = true WHERE id = '" + bookID + "'";
//
//                if (databaseHandler.execAction(actionDelete) && databaseHandler.execAction(actionUpdate)) {
//                    showJFXButton(rootPane, mainBorderPane, new ArrayList<>(), "Book has been Submitted", "Operation ended successfully");
//                    bookIdInput.clear();
//                    toggleControls(true);
//                } else {
//                    showJFXButton(rootPane, mainBorderPane, new ArrayList<>(), "Failed", "Operation ended unsuccessfully");
//                }
//            }
//        }
//    }
//
//    @FXML
//    private void loadRenewOperation() {
//        if (!isReadyForSubmission) {
//            showJFXButton(rootPane, mainBorderPane, new ArrayList<>(), "Failed", "Please select book to renew");
//        } else {
//            Optional<ButtonType> response = alertConfirm("Confirm renew operation", "", "Are you sure you want to renew the book?");
//            if (response.orElse(null) == ButtonType.OK) {
//                String action = "UPDATE CHECK_OUT SET checkOut = CURRENT_TIMESTAMP, renew_count = renew_count+1 where bookID = '" + bookIdInput.getText() + "'";
//
//                if (databaseHandler.execAction(action)) {
//                    showJFXButton(rootPane, mainBorderPane, new ArrayList<>(), "Book has been successfully renewed", "Operation ended successfully");
//                    loadBookCheckOut();
//                } else {
//                    showJFXButton(rootPane, mainBorderPane, new ArrayList<>(), "Failed", "Operation ended unsuccessfully");
//                }
//            }
//        }
//    }

}