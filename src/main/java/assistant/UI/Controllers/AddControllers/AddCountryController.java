package assistant.UI.Controllers.AddControllers;

import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.BaseModel;
import assistant.database.models.Category;
import assistant.database.models.Country;
import assistant.database.models.PublishingCompany;
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
        CommonDao dao = new CommonDao();
        Country country = new Country();
        country.setName(nameInput.getText());
        dao.createOrUpdate(country);
        Stage stage = ((Stage) nameInput.getScene().getWindow());;
        stage.close();
    }
}
