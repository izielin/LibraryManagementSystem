package assistant.UI.Controllers.AddControllers;

import assistant.Utils.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.City;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddCityController {
    @FXML
    private JFXTextField nameInput;

    public void cancelAction(ActionEvent event) {
    }

    public void executeSaveAction() throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();

        City city = new City();
        city.setName(nameInput.getText());
        dao.createOrUpdate(city);
        Stage stage = ((Stage) nameInput.getScene().getWindow());
        stage.close();
    }
}
