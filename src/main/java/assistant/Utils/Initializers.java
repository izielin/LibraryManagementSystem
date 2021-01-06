package assistant.Utils;

import assistant.FXModels.CityFXModel;
import assistant.FXModels.CountryFXModel;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.City;
import assistant.database.models.Country;
import javafx.collections.ObservableList;

import java.util.List;

public class Initializers {

    public static ObservableList<CityFXModel> initCityList(ObservableList<CityFXModel> cityFXModelObservableList) throws ApplicationException {
        CommonDao dao = new CommonDao();
        List<City> cityList = dao.queryForAll(City.class);
        cityFXModelObservableList.clear();
        cityList.forEach(city -> {
            CityFXModel cityFX = Converters.convertToCityFXModel(city);
            cityFXModelObservableList.add(cityFX);
        });
        return cityFXModelObservableList;
    }

    public static ObservableList<CountryFXModel> initCountryList(ObservableList<CountryFXModel> countryFXModelObservableList) throws ApplicationException {
        CommonDao dao = new CommonDao();
        List<Country> countryList = dao.queryForAll(Country.class);
        countryFXModelObservableList.clear();
        countryList.forEach(country -> {
            CountryFXModel countryFXModel = Converters.convertToCountryFX(country);
            countryFXModelObservableList.add(countryFXModel);
        });
        return countryFXModelObservableList;
    }
}
