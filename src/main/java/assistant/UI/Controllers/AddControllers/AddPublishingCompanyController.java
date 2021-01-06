package assistant.UI.Controllers.AddControllers;

import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
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
        CommonDao dao = new CommonDao();
        PublishingCompany publishingCompany = new PublishingCompany();
        publishingCompany.setName(nameInput.getText());
        dao.createOrUpdate(publishingCompany);
        Stage stage = ((Stage) nameInput.getScene().getWindow());
        ;
        stage.close();
    }
}