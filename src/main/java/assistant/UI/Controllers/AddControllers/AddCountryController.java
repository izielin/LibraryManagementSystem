package assistant.UI.Controllers.AddControllers;

import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.Country;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddCountryController {
    @FXML
    private JFXTextField nameInput;

    public void cancelAction(ActionEvent event) {
    }

    public void executeSaveAction() throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        Country country = new Country();
        country.setName(nameInput.getText());
        dao.createOrUpdate(country);
        Stage stage = ((Stage) nameInput.getScene().getWindow());;
        stage.close();
    }
}
