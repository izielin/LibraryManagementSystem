package assistant.UI.Controllers;

import assistant.FXModels.*;
import assistant.Utils.Converters;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListsController implements Initializable {
    @FXML
    private TableView<AuthorFXModel> authorTable;
    @FXML
    private TableColumn<AuthorFXModel, String> authorFistName;
    @FXML
    private TableColumn<AuthorFXModel, String> authorMiddleName;
    @FXML
    private TableColumn<AuthorFXModel, String> authorLastName;
    @FXML
    private TableColumn<AuthorFXModel, CountryFXModel> authorCountry;
    @FXML
    private TableView<CategoryFXModel> categoryTable;
    @FXML
    private TableColumn<CategoryFXModel, String> categoryName;
    @FXML
    private TableView<CityFXModel> cityTable;
    @FXML
    private TableColumn<CityFXModel, String> cityName;
    @FXML
    private TableColumn<CityFXModel, CountryFXModel> cityCountry;
    @FXML
    private TableView<CountryFXModel> countryTable;
    @FXML
    private TableColumn<CountryFXModel, String> countryName;
    @FXML
    private TableView<PublishingCompanyFXModel> publisherTable;
    @FXML
    private TableColumn<PublishingCompanyFXModel, String> publisherName;

    CommonDao dao = new CommonDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initAuthorTable();
            initCategoryTable();
            initCityTable();
            initCountryTable();
            initPublisherTable();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    private void initPublisherTable() throws ApplicationException {
        ObservableList<PublishingCompanyFXModel> observableArrayList = FXCollections.observableArrayList();
        List<PublishingCompany> publishers = dao.queryForAll(PublishingCompany.class);
        observableArrayList.clear();
        publishers.forEach(publisher -> {
            PublishingCompanyFXModel fxModel = Converters.convertToPublisherFX(publisher);
            observableArrayList.add(fxModel);
        });

        publisherTable.setItems(observableArrayList);
        publisherName.setCellValueFactory(cellData->cellData.getValue().nameProperty());
    }

    private void initCountryTable() throws ApplicationException {
        ObservableList<CountryFXModel> observableArrayList = FXCollections.observableArrayList();
        List<Country> countries = dao.queryForAll(Country.class);
        observableArrayList.clear();
        countries.forEach(country -> {
            CountryFXModel fxModel = Converters.convertToCountryFX(country);
            observableArrayList.add(fxModel);
        });

        countryTable.setItems(observableArrayList);
        countryName.setCellValueFactory(cellData->cellData.getValue().nameProperty());
    }

    private void initCityTable() throws ApplicationException {
        ObservableList<CityFXModel> observableArrayList = FXCollections.observableArrayList();
        List<City> cities = dao.queryForAll(City.class);
        observableArrayList.clear();
        cities.forEach(city -> {
            CityFXModel fxModel = Converters.convertToCityFXModel(city);
            observableArrayList.add(fxModel);
        });

        cityTable.setItems(observableArrayList);
        cityName.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        cityCountry.setCellValueFactory(cellData->cellData.getValue().countryProperty());
    }

    private void initCategoryTable() throws ApplicationException {
        ObservableList<CategoryFXModel> observableArrayList = FXCollections.observableArrayList();
        List<Category> categories = dao.queryForAll(Category.class);
        observableArrayList.clear();
        categories.forEach(category -> {
            CategoryFXModel fxModel = Converters.convertToCategoryFx(category);
            observableArrayList.add(fxModel);
        });

        categoryTable.setItems(observableArrayList);
        categoryName.setCellValueFactory(cellData->cellData.getValue().nameProperty());
    }

    private void initAuthorTable() throws ApplicationException {
        ObservableList<AuthorFXModel> observableArrayList = FXCollections.observableArrayList();
        List<Author> authors = dao.queryForAll(Author.class);
        observableArrayList.clear();
        authors.forEach(author -> {
            AuthorFXModel fxModel = Converters.convertToAuthorFXModel(author);
            observableArrayList.add(fxModel);
        });

        authorTable.setItems(observableArrayList);
        authorFistName.setCellValueFactory(cellData->cellData.getValue().fistNameProperty());
        authorMiddleName.setCellValueFactory(cellData->cellData.getValue().middleNameProperty());
        authorLastName.setCellValueFactory(cellData->cellData.getValue().lastNameProperty());
        authorCountry.setCellValueFactory(cellData->cellData.getValue().countryProperty());
    }
}
