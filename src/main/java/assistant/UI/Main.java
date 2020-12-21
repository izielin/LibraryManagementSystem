package assistant.UI;

import assistant.database.DatabaseHandler;
import javafx.application.Application;
import javafx.stage.Stage;

import static assistant.Utils.Utils.loadWindow;

public class Main extends Application {
    public static final String FXML_FILE = "/fxml/Login.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadWindow(FXML_FILE, "Library Management System");
        new Thread(DatabaseHandler::getInstance).start();

    }


}
