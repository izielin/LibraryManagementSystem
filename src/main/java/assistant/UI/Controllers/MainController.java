package assistant.UI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final String FXML_ADD_BOOK = "/fxml/AddBook.fxml";
    private static final String FXML_LIST_BOOK = "/fxml/BookList.fxml";
    private static final String FXML_ADD_MEMBER = "/fxml/AddMember.fxml";
    private static final String FXML_LIST_MEMBER = "/fxml/MemberList.fxml";

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
        Locale.setDefault(new Locale("de"));
        ResourceBundle bundle = ResourceBundle.getBundle("MainBundle");
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(path), bundle);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
