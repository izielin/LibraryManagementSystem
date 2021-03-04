package assistant.UI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static assistant.Utils.AlertMaker.showExitDialog;
import static assistant.Utils.ProjectTools.fxmlLoader;



public class MainController extends TitleBarController {
    private static final String FXML_TOOLBAR = "/fxml/Employee/EmployeeToolBar.fxml";
    private static final String FXML_MEMBER_TOOLBAR = "/fxml/Member/MemberToolBar.fxml";
    private static final String FXML_ADD_BOOK = "/fxml/Employee/addViews/AddBook.fxml";
    private static final String FXML_LIST_BOOK = "/fxml/Employee/listViews/BookList.fxml";
    private static final String FXML_ADD_MEMBER = "/fxml/Employee/addViews/AddUser.fxml";
    private static final String FXML_LIST_MEMBER = "/fxml/Employee/listViews/UserList.fxml";
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private StackPane rootPane;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        if(!LoginController.currentlyLoggedUser.getUserType().equals("MEMBER")) {
            insertAdvancedMenu();
            setCenter("/fxml/Employee/DashBoard.fxml");
        }
        else {
            insertMenu();
            setCenter("/fxml/Member/MemberBookList.fxml");
        }
    }

    private void insertAdvancedMenu() {
        AnchorPane toolbar = (AnchorPane) fxmlLoader(FXML_TOOLBAR);
        mainBorderPane.setLeft(toolbar);

        VBox vbox = (VBox) toolbar.lookup("#menuVbox");
        for (Node node : vbox.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    switch (node.getAccessibleText()) {
                        case "HomeView" -> setCenter("/fxml/Employee/DashBoard.fxml");
                        case "AddBook" -> setCenter("/fxml/Employee/addViews/AddBook.fxml");
                        case "AddUser" -> setCenter("/fxml/Employee/addViews/AddUser.fxml");
                        case "AddView" -> setCenter("/fxml/Employee/listViews/Lists.fxml");
                        case "BookList" -> setCenter("/fxml/Employee/listViews/BookList.fxml");
                        case "UserList" -> setCenter("/fxml/Employee/listViews/UserList.fxml");
                        case "LendBookView" -> setCenter("/fxml/Employee/LendBook.fxml");
                        case "SubmissionBook" -> setCenter("/fxml/Employee/SubmitBook.fxml");
                        case "UserProfile" -> setCenter("/fxml/UserProfile.fxml");
//                        case "Settings" -> setCenter("/fxml/UserList.fxml");
                    }
                });
            }
        }
    }

    private void insertMenu() {
        AnchorPane toolbar = (AnchorPane) fxmlLoader(FXML_MEMBER_TOOLBAR);
        mainBorderPane.setLeft(toolbar);

        VBox vbox = (VBox) toolbar.lookup("#menuVbox");
        for (Node node : vbox.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    switch (node.getAccessibleText()) {
                        case "HomeView" -> setCenter("/fxml/Member/MemberBookList.fxml");
                        case "UserProfile" -> setCenter("/fxml/UserProfile.fxml");
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
}