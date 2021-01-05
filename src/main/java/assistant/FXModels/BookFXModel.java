package assistant.FXModels;

import javafx.beans.property.*;

import java.time.LocalDate;

public class BookFXModel {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty isbn10 = new SimpleStringProperty();
    private StringProperty isbn13 = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty addedDate = new SimpleStringProperty();
    private StringProperty lastSubmissionDate = new SimpleStringProperty();
    private StringProperty publicationDate = new SimpleStringProperty();
    private ObjectProperty<AuthorFXModel> authorFX = new SimpleObjectProperty<>();
    private ObjectProperty<CategoryFXModel> categoryFX = new SimpleObjectProperty<>();
    private ObjectProperty<PublishingCompanyFXModel> publishingCompanyFX = new SimpleObjectProperty<>();
    private ObjectProperty<LibraryFXModel> libraryFX = new SimpleObjectProperty<>();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getIsbn10() {
        return isbn10.get();
    }

    public StringProperty isbn10Property() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10.set(isbn10);
    }

    public String getIsbn13() {
        return isbn13.get();
    }

    public StringProperty isbn13Property() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13.set(isbn13);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAddedDate() {
        return addedDate.get();
    }

    public StringProperty addedDateProperty() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate.set(addedDate);
    }

    public String getLastSubmissionDate() {
        return lastSubmissionDate.get();
    }

    public StringProperty lastSubmissionDateProperty() {
        return lastSubmissionDate;
    }

    public void setLastSubmissionDate(String lastSubmissionDate) {
        this.lastSubmissionDate.set(lastSubmissionDate);
    }

    public String getPublicationDate() {
        return publicationDate.get();
    }

    public StringProperty publicationDateProperty() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate.set(publicationDate);
    }

    public AuthorFXModel getAuthorFX() {
        return authorFX.get();
    }

    public ObjectProperty<AuthorFXModel> authorFXProperty() {
        return authorFX;
    }

    public void setAuthorFX(AuthorFXModel authorFX) {
        this.authorFX.set(authorFX);
    }

    public CategoryFXModel getCategoryFX() {
        return categoryFX.get();
    }

    public ObjectProperty<CategoryFXModel> categoryFXProperty() {
        return categoryFX;
    }

    public void setCategoryFX(CategoryFXModel categoryFX) {
        this.categoryFX.set(categoryFX);
    }

    public PublishingCompanyFXModel getPublishingCompanyFX() {
        return publishingCompanyFX.get();
    }

    public ObjectProperty<PublishingCompanyFXModel> publishingCompanyFXProperty() {
        return publishingCompanyFX;
    }

    public void setPublishingCompanyFX(PublishingCompanyFXModel publishingCompanyFX) {
        this.publishingCompanyFX.set(publishingCompanyFX);
    }

    public LibraryFXModel getLibraryFX() {
        return libraryFX.get();
    }

    public ObjectProperty<LibraryFXModel> libraryFXProperty() {
        return libraryFX;
    }

    public void setLibraryFX(LibraryFXModel libraryFX) {
        this.libraryFX.set(libraryFX);
    }

    @Override
    public String toString() {
        return "BookFXModel{" + "title=" + title + '}';
    }
}
