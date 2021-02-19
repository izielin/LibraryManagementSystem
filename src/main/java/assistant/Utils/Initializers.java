package assistant.Utils;

import assistant.FXModels.CityFXModel;
import assistant.FXModels.LibraryFXModel;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.City;
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

    public static void initLibraryList(ObservableList<LibraryFXModel> libraryFXModelObservableList) throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        List<Library> libraries = dao.queryForAll(Library.class);
        libraryFXModelObservableList.clear();
        libraries.forEach(library -> {
            LibraryFXModel libraryFXModel = Converters.convertToLibraryFXModel(library);
            libraryFXModelObservableList.add(libraryFXModel);
        });

    }
}
