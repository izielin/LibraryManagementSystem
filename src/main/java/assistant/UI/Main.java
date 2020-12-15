package assistant.UI;

import assistant.database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static assistant.UI.Controllers.MainController.getResourceBundle;

public class Main  extends Application {
    public static final String FXML_MAIN = "/fxml/Main.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("en"));

        Parent root = FXMLLoader.load(getClass().getResource(FXML_MAIN), getResourceBundle());
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(DatabaseHandler::getInstance).start();

    }


}
