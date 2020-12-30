package assistant.UI.Controllers;

import javafx.fxml.FXML;

import static assistant.Utils.Utils.loadWindow;


public class ToolBarController {
    private static final String FXML_ADD_BOOK = "/fxml/AddBook.fxml";
    private static final String FXML_LIST_BOOK = "/fxml/BookList.fxml";
    private static final String FXML_ADD_MEMBER = "/fxml/AddMember.fxml";
    private static final String FXML_LIST_MEMBER = "/fxml/UserList.fxml";
    private static final String FXML_SETTINGS = "/fxml/Settings.fxml";

    // open other windows
    @FXML
    private void executeAddMember() {
        loadWindow(FXML_ADD_MEMBER);
    }

    @FXML
    private void executeAddBook() {
        loadWindow(FXML_ADD_BOOK);
    }

    @FXML
    private void loadMemberTable() {
        loadWindow(FXML_LIST_MEMBER);
    }

    @FXML
    private void loadBookTable() {
        loadWindow(FXML_LIST_BOOK);
    }

    @FXML
    private void loadSettings() {
        loadWindow(FXML_SETTINGS);
    }

}
