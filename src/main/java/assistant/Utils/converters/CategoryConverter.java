package assistant.Utils.converters;

import assistant.FXModels.CategoryFXModel;
import assistant.database.models.Category;

public class CategoryConverter {
    public static CategoryFXModel convertToCategoryFx(Category category){
        CategoryFXModel model = new CategoryFXModel();
        model.setId(category.getId());
        model.setName(category.getName());
        return model;
    }


}
