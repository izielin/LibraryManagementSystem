package assistant.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class SettingsController implements Initializable {

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
        Preferences preferences = Preferences.getPreferences();
        daysWithoutFee.setText(String.valueOf(preferences.getDaysWithoutFee()));
        feePerDay.setText(String.valueOf(preferences.getFeePerDay()));
        username.setText(String.valueOf(preferences.getUsername()));
        password.setText(String.valueOf(preferences.getPassword()));
    }

    @FXML
    private void executeSaveButtonAction(ActionEvent event) {
    }

    @FXML
    private void executeCancelButtonAction(ActionEvent event) {
    }
    
}
