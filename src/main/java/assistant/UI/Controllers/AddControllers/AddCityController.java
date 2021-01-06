package assistant.UI.Controllers.AddControllers;

import assistant.FXModels.CountryFXModel;
import assistant.Utils.Initializers;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.BaseModel;
import assistant.database.models.Category;
import assistant.database.models.City;
import assistant.database.models.Country;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddCityController {
    @FXML
    private JFXComboBox<CountryFXModel> countryCombobox;
    @FXML
    private JFXTextField nameInput;

    private static final ObservableList<CountryFXModel> countryFXModelObservableList = FXCollections.observableArrayList();


    public void initialize() throws ApplicationException {
        Initializers.initCountryList(countryFXModelObservableList);
        countryCombobox.setItems(countryFXModelObservableList);
    }

    public void cancelAction(ActionEvent event) {
    }

    public void executeSaveAction() throws ApplicationException {
        CommonDao dao = new CommonDao();
        Country country = dao.findById(Country.class, countryCombobox.getValue().getId());

        City city = new City();
        city.setName(nameInput.getText());
        city.setCountry(country);
        dao.createOrUpdate(city);
        Stage stage = ((Stage) nameInput.getScene().getWindow());;
        stage.close();
    }
}
