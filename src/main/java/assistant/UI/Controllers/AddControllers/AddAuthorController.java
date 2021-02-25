package assistant.UI.Controllers.AddControllers;

import assistant.Utils.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.Author;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddAuthorController {
    @FXML
    private JFXTextField firstNameInput;

    @FXML
    private JFXTextField middleNameInput;

    @FXML
    private JFXTextField lastNameInput;

    @FXML
    void cancelAction(ActionEvent event) {

    }

    @FXML
    void executeSaveAction(ActionEvent event) throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();

        Author author = new Author();
        author.setFistName(firstNameInput.getText());
        author.setMiddleName(middleNameInput.getText());
        author.setLastName(lastNameInput.getText());
        dao.createOrUpdate(author);
        Stage stage = ((Stage) firstNameInput.getScene().getWindow());
        stage.close();
    }

}
