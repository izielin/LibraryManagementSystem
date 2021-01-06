package assistant.UI.Controllers.AddControllers;

import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.Category;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddCategoryController {
    @FXML
    private JFXTextField nameInput;

    public void cancelAction(ActionEvent event) {
    }

    public void executeSaveAction() throws ApplicationException {
        CommonDao dao = new CommonDao();
        Category category = new Category();
        category.setName(nameInput.getText());
        dao.createOrUpdate(category);
        Stage stage = ((Stage) nameInput.getScene().getWindow());
        stage.close();
    }
}
