package assistant.UI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import static assistant.Utils.ProjectTools.fxmlLoader;


public class MainController {

    private static final String FXML_ADD_BOOK = "/fxml/addViews/AddBook.fxml";
    private static final String FXML_LIST_BOOK = "/fxml/BookList.fxml";
    private static final String FXML_ADD_MEMBER = "/fxml/AddMember.fxml";
    private static final String FXML_LIST_MEMBER = "/fxml/UserList.fxml";
    private static final String FXML_TOOLBAR = "/fxml/ToolBar.fxml";

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        setTitleBar();
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
                        case "userList" -> setCenter("/fxml/UserList.fxml");
                        case "addView" -> setCenter("/fxml/Lists.fxml");
                    }
                });
            }
        }
    }

    public void setTitleBar() {
        String TITLE_BAR_FXML = "/fxml/BaseTitleBar.fxml";
        mainBorderPane.setTop(fxmlLoader(TITLE_BAR_FXML));
    }

    public void setCenter(String fxmlPath) {
        mainBorderPane.setCenter(fxmlLoader(fxmlPath));
    }

    // menu actions
//    @FXML
//    private void MenuCloseApplication() {
//        showExitDialog(rootPane, mainBorderPane);
//    }
//
//    @FXML
//    private void MenuAddBook() {
//        loadWindow(FXML_ADD_BOOK);
//    }
//
//    @FXML
//    private void MenuAddMember() {
//        loadWindow(FXML_ADD_MEMBER);
//    }
//
//    @FXML
//    private void MenuViewBook() {
//        loadWindow(FXML_LIST_BOOK);
//    }
//
//    @FXML
//    private void MenuViewMember() {
//        loadWindow(FXML_LIST_MEMBER);
//    }
//
//    @FXML
//    private void MenuFullScreen() {
//        Stage stage = ((Stage) bookTitle.getScene().getWindow());
//        stage.setFullScreen(!stage.isFullScreen());
//    }

//    @FXML
//    private void loadMemberInformation() {
//        String memberID = memberIDInput.getText();
//        String query = "SELECT * FROM MEMBER WHERE id = '" + memberID + "'";
//        ResultSet resultSet = databaseHandler.execQuery(query);
//        boolean flag = false;
//        try {
//            while (resultSet.next()) {
//                // get data from db
//                String mName = resultSet.getString("name");
//                String mobile = resultSet.getString("mobile");
//                String email = resultSet.getString("email");
//
//                // set data to TextField
//                memberName.setText(mName);
//                memberContact.setText(mobile + ", " + email);
//                flag = true;
//            }
//            if (!flag) {
//                memberName.setText("Any member is associated with this id");
//                memberContact.setText("");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void loadBookInformation() {
//        String bookID = bookIDInput.getText();
//        String query = "SELECT * FROM BOOK WHERE id = '" + bookID + "'";
//        ResultSet resultSet = databaseHandler.execQuery(query);
//        boolean flag = false;
//        try {
//            while (resultSet.next()) {
//                // get data from db
//                String bName = resultSet.getString("title");
//                String bAuthor = resultSet.getString("author");
//                boolean bStatus = resultSet.getBoolean("isAvailable");
//
//                // set data to TextField
//                bookTitle.setText(bName);
//                bookAuthor.setText(bAuthor);
//                String status = bStatus ? "available" : "nAvailable";
//                bookStatus.setText(getResourceBundle().getString(status));
//
//                flag = true;
//            }
//            if (!flag) {
//                bookTitle.setText("Any book is associated with this id");
//                bookAuthor.setText("");
//                bookStatus.setText("");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void executeCheckOutOperation() {
//        String memberID = memberIDInput.getText();
//        String bookID = bookIDInput.getText();
//
//        JFXButton yesButton = new JFXButton("YES");
//        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
//            String action = "INSERT INTO CHECK_OUT(memberID, bookID) VALUES( " +
//                    "'" + memberID + "'," +
//                    "'" + bookID + "')";
//            String action2 = "UPDATE BOOK SET isAvailable = false WHERE id = '" + bookID + "'";
//
//            if (databaseHandler.execAction(action) && databaseHandler.execAction(action2)) {
//                showJFXButton(rootPane, mainBorderPane, new ArrayList<>(), " Success", "Operation ended successfully");
//                clearCheckOutEntries();
//            }
//        });
//
//        JFXButton cancelButton = new JFXButton("Cancel");
//        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->
//                showJFXButton(rootPane, mainBorderPane, new ArrayList<>(), "Failed", "Operation ended unsuccessfully"));
//
//        showJFXButton(rootPane, mainBorderPane, Arrays.asList(yesButton, cancelButton), "Confirm chek book out operation",
//                "Are you sure you want to lend '" + bookTitle.getText() + "' to " + memberName.getText() + "?");
//    }
//
//    private void clearCheckOutEntries() {
//        memberIDInput.clear();
//        bookIDInput.clear();
//        bookTitle.setText(getResourceBundle().getString("bookTitle"));
//        bookAuthor.setText(getResourceBundle().getString("bookAuthor"));
//        bookStatus.setText(getResourceBundle().getString("availability"));
//        memberName.setText(getResourceBundle().getString("memberName"));
//        memberContact.setText(getResourceBundle().getString("memberContact"));
//    }
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
