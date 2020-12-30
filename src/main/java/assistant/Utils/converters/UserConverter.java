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
}
