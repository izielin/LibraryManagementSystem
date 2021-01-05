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
    CommonDao dao = new CommonDao();

    public ObservableList<CityFXModel> initCityList(ObservableList<CityFXModel> cityFXModelObservableList) throws ApplicationException {
        List<City> cityList = dao.queryForAll(City.class);
        cityFXModelObservableList.clear();
        cityList.forEach(city -> {
            CityFXModel cityFX = Converters.convertToCityFXModel(city);
            cityFXModelObservableList.add(cityFX);
        });
        return cityFXModelObservableList;
    }

    public ObservableList<CountryFXModel> initCountryList(ObservableList<CountryFXModel> countryFXModelObservableList) throws ApplicationException {
        List<Country> countryList = dao.queryForAll(Country.class);
        countryFXModelObservableList.clear();
        countryList.forEach(country -> {
            CountryFXModel countryFXModel = Converters.convertToCountryFX(country);
            countryFXModelObservableList.add(countryFXModel);
        });
        return countryFXModelObservableList;
    }
}
