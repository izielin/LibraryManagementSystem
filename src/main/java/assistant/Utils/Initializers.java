package assistant.Utils;

import assistant.FXModels.CityFXModel;
import assistant.FXModels.CountryFXModel;
import assistant.FXModels.LibraryFXModel;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.City;
import assistant.database.models.Country;
import assistant.database.models.Library;
import javafx.collections.ObservableList;

import java.util.List;

public class Initializers {

    public static ObservableList<CityFXModel> initCityList(ObservableList<CityFXModel> cityFXModelObservableList) throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        List<City> cityList = dao.queryForAll(City.class);
        cityFXModelObservableList.clear();
        cityList.forEach(city -> {
            CityFXModel cityFX = Converters.convertToCityFXModel(city);
            cityFXModelObservableList.add(cityFX);
        });
        return cityFXModelObservableList;
    }

    public static ObservableList<CountryFXModel> initCountryList(ObservableList<CountryFXModel> countryFXModelObservableList) throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        List<Country> countryList = dao.queryForAll(Country.class);
        countryFXModelObservableList.clear();
        countryList.forEach(country -> {
            CountryFXModel countryFXModel = Converters.convertToCountryFX(country);
            countryFXModelObservableList.add(countryFXModel);
        });
        return countryFXModelObservableList;
    }

    public static ObservableList<LibraryFXModel> initLibraryList(ObservableList<LibraryFXModel> libraryFXModelObservableList) throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        List<Library> countryList = dao.queryForAll(Library.class);
        libraryFXModelObservableList.clear();
        countryList.forEach(library -> {
            LibraryFXModel libraryFXModel = Converters.convertToLibraryFXModel(library);
            libraryFXModelObservableList.add(libraryFXModel);
        });
        return libraryFXModelObservableList;

    }
}
