package assistant.UI.Controllers;

import assistant.Utils.FillDatabase;
import assistant.Utils.GenerateReport;
import assistant.Utils.ProjectTools;
import assistant.Utils.ApplicationException;
import assistant.database.DatabaseHandler;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.Report;
import assistant.database.models.User;
import assistant.settings.Settings;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static assistant.Utils.ProjectTools.loadWindow;

public class LoginController implements Initializable {
    public static User currentlyLoggedUser;
    public JFXCheckBox rebuildDatabase;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Text alertText;
    @FXML
    private JFXTextField usernameInput;
    @FXML
    private JFXPasswordField passwordInput;

    Settings settings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        settings = Settings.getSettings();
        setTitleBar();
    }

    public void setTitleBar() {
        borderPane.setTop(ProjectTools.fxmlLoader("/fxml/BaseTitleBar.fxml"));
    }

    @FXML
    private void LoginAction() throws IOException {
        String username = usernameInput.getText();
        String password = DigestUtils.sha1Hex(passwordInput.getText());
        DataAccessObject dao = new DataAccessObject();
        if (rebuildDatabase.isSelected()) {
            DatabaseHandler.initDatabase(true);
            FillDatabase.fillDatabase();
        }
        try {
            User user = dao.isLogin(username, password); // creating object of logged user
            if (user != null) {
                // calling up different windows depending on the type of user
                if (user.getUserType().equals("EMPLOYEE")) {
                    currentlyLoggedUser = user; // creating an object of currently logged user
                    System.out.println(currentlyLoggedUser.getLibraryID());
                    closeStage();
                    LocalDate date = LocalDate.now();
                    LocalDate firstDay = date.withDayOfMonth(1);
                    LocalDate lastDay = firstDay.minusDays(1);
                    int noOfReports = Integer.parseInt(dao.countRecords(Report.class,
                            "select count(*) from REPORTS where LIBRARY_ID = " + currentlyLoggedUser.getLibraryID() + " and date = \"" + lastDay + "\""));
                    if (date.equals(firstDay) && noOfReports == 0) {
                        GenerateReport.generateNewRaport();
                    }
                    loadWindow("/fxml/Main.fxml");

                } else {
                    System.out.println("TO DO");
                }
            } else {
                alertText.setText("Invalid username or password");
                usernameInput.getStyleClass().add("wrong-login-data");
                passwordInput.getStyleClass().add("wrong-login-data");
                passwordInput.clear();
            }
        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeStage() {
        ((Stage) usernameInput.getScene().getWindow()).close();
    }

}