package assistant.Utils;

import assistant.FXModels.CategoryFXModel;
import assistant.FXModels.CityFXModel;
import assistant.Utils.converters.CityConverter;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.City;
import javafx.collections.ObservableList;

import java.util.List;

public class Initialize {
    public static ObservableList<CityFXModel> initCityList(ObservableList<CityFXModel> cityFXModelObservableList) throws ApplicationException {
        CommonDao dao = new CommonDao();
        List<City> cityList = dao.queryForAll(City.class);
        cityFXModelObservableList.clear();
        cityList.forEach(city -> {
            CityFXModel cityFX = CityConverter.convertToCityFXModel(city);
            cityFXModelObservableList.add(cityFX);
        });
        return cityFXModelObservableList;
    }
}
