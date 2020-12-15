package assistant.UI;

import assistant.database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main  extends Application {
    public static final String FXML_MAIN = "/fxml/Main.fxml";

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("de"));
        ResourceBundle bundle = ResourceBundle.getBundle("MainBundle");

        Parent root = FXMLLoader.load(getClass().getResource(FXML_MAIN), bundle);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        DatabaseHandler.getInstance();

    }


}
