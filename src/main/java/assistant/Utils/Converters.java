package assistant.Utils;

import assistant.FXModels.*;
import assistant.database.models.*;

public class Converters {
    public static UserFXModel convertToUserFx(User user) {
        UserFXModel model = new UserFXModel();
        model.setId(user.getId());
        model.setUsername(user.getUsername());
        model.setPassword(user.getPassword());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setMobile(user.getMobile());
        model.setEmail(user.getEmail());
        model.setRegistrationDate(user.getRegistrationDate());
        model.setStreet(user.getStreet());
        model.setZipCode(user.getZipCode());
        model.setUserType(user.getUserType());
        model.setLibrary(convertToLibraryFXModel(user.getLibrary()));
        return model;
    }

    public static User convertToUser(UserFXModel userFXModel) {
        User model = new User();
        model.setId(userFXModel.getId());
        model.setUsername(userFXModel.getUsername());
        model.setPassword(userFXModel.getPassword());
        model.setFirstName(userFXModel.getFirstName());
        model.setLastName(userFXModel.getLastName());
        model.setMobile(userFXModel.getMobile());
        model.setEmail(userFXModel.getEmail());
        model.setRegistrationDate(userFXModel.getRegistrationDate());
        model.setStreet(userFXModel.getStreet());
        model.setZipCode(userFXModel.getZipCode());
        model.setUserType(userFXModel.getUserType());
        model.setLibrary(convertToLibrary(userFXModel.getLibrary()));
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

    public static City convertToCity(CityFXModel city) {
        City model = new City();
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

    public static Author convertToAuthor(AuthorFXModel author) {
        Author model = new Author();
        model.setId(author.getId());
        model.setFistName(author.getFistName());
        model.setLastName(author.getLastName());
        model.setMiddleName(author.getMiddleName());
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
        model.setCityFX(convertToCityFXModel(library.getCity()));
        return model;
    }

    public static Library convertToLibrary(LibraryFXModel library) {
        Library model = new Library();
        model.setId(library.getId());
        model.setName(library.getName());
        model.setStreet(library.getStreet());
        model.setZipCode(library.getZipCode());
        model.setCity(convertToCity(library.getCityFX()));
        return model;
    }

    public static BookFXModel convertToBookFx(Book book) {
        BookFXModel model = new BookFXModel();
        model.setId(book.getId());
        model.setIsbn10(book.getIsbn10());
        model.setIsbn13(book.getIsbn13());
        model.setTitle(book.getTitle());
        model.setAddedDate(book.getAddedDate());
        model.setLastSubmissionDate(book.getLastSubmission());
        model.setDescription(book.getDescription());
        model.setPublicationDate(book.getPublicationDate());
        model.setBookCover(book.getBookCover());
        model.setAvailability(book.getAvailability());
        model.setAuthorFX(convertToAuthorFXModel(book.getAuthor()));
        model.setCategoryFX(convertToCategoryFx(book.getCategory()));
        model.setPublishingCompanyFX(convertToPublisherFX(book.getPublishingCompany()));
        model.setLibraryFX(convertToLibraryFXModel(book.getLibrary()));
        return model;
    }
}
