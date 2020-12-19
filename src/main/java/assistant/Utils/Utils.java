package assistant.Utils;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Utils {
    private static final String ICON_LOCATION = "/icons/icon.png";

    public static void setIcon(Stage stage){
        stage.getIcons().add(new Image(ICON_LOCATION));

    }
}
