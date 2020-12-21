package assistant.Utils;

import assistant.UI.Controllers.MainController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    private static final String ICON_LOCATION = "/icons/icon.png";

    public static void setIcon(Stage stage){
        stage.getIcons().add(new Image(ICON_LOCATION));

    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("MainBundle");
    }

    public static void loadWindow(String path, String title) {

        try {
            Locale.setDefault(new Locale("en")); // setting local zone
            Parent parent = FXMLLoader.load(MainController.class.getResource(path), getResourceBundle());
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UNDECORATED);

            if(!path.equals("/fxml/Login.fxml")) {
                ResizeHelper.addResizeListener(stage);
            }

            stage.setTitle(title);
            stage.show();
            Utils.setIcon(stage);
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
