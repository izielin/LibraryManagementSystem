package assistant.Utils;

import assistant.UI.Controllers.ListControllers.ListsController;
import assistant.UI.Controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectTools {
    private static final String ICON_LOCATION = "/icons/icon.png";

    public static void setIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_LOCATION));

    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("MainBundle");
    }

    public static void loadWindow(String path) {

        try {
            Locale.setDefault(new Locale("en")); // setting local zone
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource(path), getResourceBundle());
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UNDECORATED);

            if (!path.equals("/fxml/Login.fxml")) {
                ResizeHelper.addResizeListener(stage);
            }
            System.out.println(stage.getScene().getWidth());
            stage.show();
            ProjectTools.setIcon(stage);

        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public static Pane fxmlLoader(String path) {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource(path), getResourceBundle());
        try {
            return loader.load();
        } catch (Exception e) {
            Logger.getLogger(ProjectTools.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    public static void loadAddWindow(String path, ListsController controller) {

        try {
            FXMLLoader loader = new FXMLLoader(ProjectTools.class.getResource(path), getResourceBundle());
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();

            stage.setOnHidden((e)->{
                System.out.println("work1");
                controller.executeRefresh();
            }); //refresh table

        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }

    }


}
