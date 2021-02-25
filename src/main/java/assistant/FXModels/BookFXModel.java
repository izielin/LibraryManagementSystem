package assistant.FXModels;

import javafx.beans.property.*;

import java.time.LocalDate;

public class BookFXModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty isbn10 = new SimpleStringProperty();
    private final StringProperty isbn13 = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty addedDate = new SimpleStringProperty();
    private final StringProperty lastSubmissionDate = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty publicationDate = new SimpleStringProperty();
    private final BooleanProperty availability = new SimpleBooleanProperty();
    private final ObjectProperty<byte[]> bookCover = new SimpleObjectProperty<>();
    private final IntegerProperty authorID = new SimpleIntegerProperty();
    private final IntegerProperty categoryID = new SimpleIntegerProperty();
    private final IntegerProperty publishingCompanyID = new SimpleIntegerProperty();
    private final IntegerProperty libraryID = new SimpleIntegerProperty();

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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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

    public boolean isAvailability() {
        return availability.get();
    }

    public BooleanProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability.set(availability);
    }

    public byte[] getBookCover() {
        return bookCover.get();
    }

    public ObjectProperty<byte[]> bookCoverProperty() {
        return bookCover;
    }

    public void setBookCover(byte[] bookCover) {
        this.bookCover.set(bookCover);
    }

    public int getAuthorID() {
        return authorID.get();
    }

    public IntegerProperty authorIDProperty() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID.set(authorID);
    }

    public int getCategoryID() {
        return categoryID.get();
    }

    public IntegerProperty categoryIDProperty() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID.set(categoryID);
    }

    public int getPublishingCompanyID() {
        return publishingCompanyID.get();
    }

    public IntegerProperty publishingCompanyIDProperty() {
        return publishingCompanyID;
    }

    public void setPublishingCompanyID(int publishingCompanyID) {
        this.publishingCompanyID.set(publishingCompanyID);
    }

    public int getLibraryID() {
        return libraryID.get();
    }

    public IntegerProperty libraryIDProperty() {
        return libraryID;
    }

    public void setLibraryID(int libraryID) {
        this.libraryID.set(libraryID);
    }
}
