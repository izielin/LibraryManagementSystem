package assistant.UI;

import assistant.Utils.Utils;
import assistant.database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

import static assistant.UI.Controllers.MainController.getResourceBundle;

public class Main extends Application {
    public static final String FXML_FILE = "/fxml/Login.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("en"));

        Parent root = FXMLLoader.load(getClass().getResource(FXML_FILE), getResourceBundle());
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        Utils.setIcon(primaryStage);

        new Thread(DatabaseHandler::getInstance).start();

    }


}
