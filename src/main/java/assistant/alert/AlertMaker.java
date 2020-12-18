package assistant.alert;

import javafx.scene.control.Alert;

public class AlertMaker {
    public static  void showSimpleAlert(String type,String title, String header, String content){
        Alert alert;
        if (type.equals("information"))
             alert = new Alert(Alert.AlertType.INFORMATION);
        else if (type.equals("error"))
             alert = new Alert(Alert.AlertType.ERROR);
        else
            alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
