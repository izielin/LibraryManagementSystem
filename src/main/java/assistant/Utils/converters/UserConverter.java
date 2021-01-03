package assistant.Utils.converters;

import assistant.FXModels.UserFXModel;
import assistant.database.models.User;

public class UserConverter {
    public static UserFXModel convertToUserFx(User user) {
        UserFXModel model = new UserFXModel();
        model.setId(user.getId());
        model.setUsername(user.getUsername());
        model.setPassword(user.getPassword());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setMobile(user.getMobile());
        model.setEmail(user.getEmail());
        model.setStreet(user.getStreet());
        model.setZipCode(user.getZipCode());
        model.setCity(user.getCity());
        model.setUserType(user.getUserType());
        model.setLibrary(user.getLibrary());
        return model;
    }

    public static User convertToUser(UserFXModel userFXModel){
        User model = new User();
        model.setId(userFXModel.getId());
        model.setUsername(userFXModel.getUsername());
        model.setPassword(userFXModel.getPassword());
        model.setFirstName(userFXModel.getFirstName());
        model.setLastName(userFXModel.getLastName());
        model.setMobile(userFXModel.getMobile());
        model.setEmail(userFXModel.getEmail());
        model.setStreet(userFXModel.getStreet());
        model.setZipCode(userFXModel.getZipCode());
        model.setCity(userFXModel.getCity());
        model.setUserType(userFXModel.getUserType());
        model.setLibrary(userFXModel.getLibrary());
        return model;
    }
}
