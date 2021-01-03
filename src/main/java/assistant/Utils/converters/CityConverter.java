package assistant.Utils.converters;

import assistant.FXModels.CategoryFXModel;
import assistant.FXModels.CityFXModel;
import assistant.database.models.Category;
import assistant.database.models.City;

public class CityConverter {
    public static CityFXModel convertToCityFXModel(City city){
        CityFXModel model = new CityFXModel();
        model.setId(city.getId());
        model.setName(city.getName());
        model.setCountry(city.getCountry());
        return model;
    }
}
