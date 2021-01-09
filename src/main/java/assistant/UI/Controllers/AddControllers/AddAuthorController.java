package assistant.UI.Controllers.AddControllers;

import assistant.FXModels.CountryFXModel;
import assistant.Utils.Initializers;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private JFXComboBox<CountryFXModel> countryCombobox;

    private static final ObservableList<CountryFXModel> countryFXModelObservableList = FXCollections.observableArrayList();

    public void initialize() throws ApplicationException {
        Initializers.initCountryList(countryFXModelObservableList);
        countryCombobox.setItems(countryFXModelObservableList);
    }

    @FXML
    void cancelAction(ActionEvent event) {

    }

    @FXML
    void executeSaveAction(ActionEvent event) throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        Country country = dao.findById(Country.class, countryCombobox.getValue().getId());

        Author author = new Author();
        author.setFistName(firstNameInput.getText());
        author.setMiddleName(middleNameInput.getText());
        author.setLastName(lastNameInput.getText());
        author.setCountry(country);
        dao.createOrUpdate(author);
        Stage stage = ((Stage) firstNameInput.getScene().getWindow());;
        stage.close();
    }

}
