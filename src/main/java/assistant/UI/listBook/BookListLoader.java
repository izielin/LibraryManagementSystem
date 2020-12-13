package assistant.UI.listBook;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


public class BookListLoader extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Locale.setDefault(new Locale("de"));
        ResourceBundle bundle = ResourceBundle.getBundle("MainBundle");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/BookList.fxml"), bundle);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}