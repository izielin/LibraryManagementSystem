package assistant.Utils;

import assistant.FXModels.*;
import assistant.database.models.*;

public class Converters {
    public static UserFXModel convertToUserFx(User user) {
        UserFXModel model = new UserFXModel();
        model.setId(user.getId());
        model.setUsername(user.getUsername());
        model.setPassword(user.getPassword());
        model.setProfilePicture(user.getProfilePicture());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setGender(user.getGender());
        model.setMobile(user.getMobile());
        model.setEmail(user.getEmail());
        model.setRegistrationDate(user.getRegistrationDate());
        model.setStreet(user.getStreet());
        model.setZipCode(user.getZipCode());
        model.setUserType(user.getUserType());
        model.setLibraryID(user.getLibraryID());
        return model;
    }

    public static User convertToUser(UserFXModel user) {
        User model = new User();
        model.setId(user.getId());
        model.setUsername(user.getUsername());
        model.setPassword(user.getPassword());
        model.setProfilePicture(user.getProfilePicture());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setGender(user.getGender());
        model.setMobile(user.getMobile());
        model.setEmail(user.getEmail());
        model.setRegistrationDate(user.getRegistrationDate());
        model.setStreet(user.getStreet());
        model.setZipCode(user.getZipCode());
        model.setUserType(user.getUserType());
        model.setLibraryID(user.getLibraryID());
        return model;
    }

    public static PublishingCompanyFXModel convertToPublisherFX(PublishingCompany publisher) {
        PublishingCompanyFXModel model = new PublishingCompanyFXModel();
        model.setId(publisher.getId());
        model.setName(publisher.getName());
        return model;
    }

    public static CityFXModel convertToCityFXModel(City city) {
        CityFXModel model = new CityFXModel();
        model.setId(city.getId());
        model.setName(city.getName());
        return model;
    }

    public static CategoryFXModel convertToCategoryFx(Category category) {
        CategoryFXModel model = new CategoryFXModel();
        model.setId(category.getId());
        model.setName(category.getName());
        return model;
    }

    public static AuthorFXModel convertToAuthorFXModel(Author author) {
        AuthorFXModel model = new AuthorFXModel();
        model.setId(author.getId());
        model.setFistName(author.getFistName());
        model.setLastName(author.getLastName());
        model.setMiddleName(author.getMiddleName());
        return model;
    }


    public static LibraryFXModel convertToLibraryFXModel(Library library) {
        LibraryFXModel model = new LibraryFXModel();
        model.setId(library.getId());
        model.setName(library.getName());
        model.setStreet(library.getStreet());
        model.setZipCode(library.getZipCode());
        model.setCityID(library.getCityID());
        return model;
    }

    public static BookFXModel convertToBookFx(Book book) {
        BookFXModel model = new BookFXModel();
        model.setId(book.getId());
        model.setIsbn10(book.getIsbn10());
        model.setIsbn13(book.getIsbn13());
        model.setTitle(book.getTitle());
        model.setAddedDate(book.getAddedDate());
        model.setDescription(book.getDescription());
        model.setPublicationDate(book.getPublicationDate());
        model.setBookCover(book.getBookCover());
        model.setAvailability(book.getAvailability());
        model.setAuthorID(book.getAuthor());
        model.setCategoryID(book.getCategory());
        model.setPublishingCompanyID(book.getPublishingCompany());
        model.setLibraryID(book.getLibrary());
        return model;
    }
}
