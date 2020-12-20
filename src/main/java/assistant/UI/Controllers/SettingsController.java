package assistant.UI.Controllers;

import assistant.settings.Settings;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static assistant.alert.AlertMaker.showSimpleAlert;


public class SettingsController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField daysWithoutFee;
    @FXML
    private JFXTextField feePerDay;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }

    private void initDefaultValues() {
        Settings settings = Settings.getSettings();
        daysWithoutFee.setText(String.valueOf(settings.getDaysWithoutFee()));
        feePerDay.setText(String.valueOf(settings.getFeePerDay()));
        username.setText(String.valueOf(settings.getUsername()));
        password.setText(String.valueOf(settings.getPassword()));
    }

    @FXML
    private void executeSaveAction() {
        Settings newSettings = Settings.getSettings();
        newSettings.setDaysWithoutFee(Integer.parseInt(daysWithoutFee.getText()));
        newSettings.setFeePerDay(Float.parseFloat(feePerDay.getText()));
        newSettings.setUsername(username.getText());
        newSettings.setPassword(password.getText());

        Settings.overWriteSettings(newSettings);
    }

    @FXML
    private void executeCancelAction() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
}
