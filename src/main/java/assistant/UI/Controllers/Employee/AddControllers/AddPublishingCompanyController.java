package assistant.UI.Controllers.Employee.AddControllers;

import assistant.Utils.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.PublishingCompany;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddPublishingCompanyController {
    @FXML
    private JFXTextField nameInput;

    public void cancelAction(ActionEvent event) {
    }

    public void executeSaveAction() throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        PublishingCompany publishingCompany = new PublishingCompany();
        publishingCompany.setName(nameInput.getText());
        dao.createOrUpdate(publishingCompany);
        Stage stage = ((Stage) nameInput.getScene().getWindow());
        ;
        stage.close();
    }
}
