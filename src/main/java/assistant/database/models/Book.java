package assistant.database.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "BOOKS")
public class Book implements BaseModel {
    @DatabaseField(columnName = "ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "ISBN_10", unique = true)
    private String isbn10;

    @DatabaseField(columnName = "ISBN_13", unique = true)
    private String isbn13;

    @DatabaseField(columnName = "TITLE", canBeNull = false)
    private String title;

    @DatabaseField(columnName = "ADDED_DATE")
    private String addedDate;

    @DatabaseField(columnName = "DESCRIPTION")
    private String description;

    @DatabaseField(columnName = "PUBLICATION_DATE")
    private String publicationDate;

    @DatabaseField(columnName = "BOOK_COVER",dataType = DataType.SERIALIZABLE)
    private byte[] bookCover;

    @DatabaseField(columnName = "AVAILABILITY", defaultValue = "true")
    private Boolean availability;

    @DatabaseField(columnName = "AUTHOR_ID")
    private int authorID;

    @DatabaseField(columnName = "CATEGORY_ID")
    private int categoryID;

    @DatabaseField(columnName = "PUBLISHER_ID")
    private int publishingCompanyID;

    @DatabaseField(columnName = "LIBRARY_ID")
    private int libraryID;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public byte[] getBookCover() {
        return bookCover;
    }

    public void setBookCover(byte[] bookCover) {
        this.bookCover = bookCover;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public int getAuthor() {
        return authorID;
    }

    public void setAuthor(int authorID) {
        this.authorID = authorID;
    }

    public int getCategory() {
        return categoryID;
    }

    public void setCategory(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getPublishingCompany() {
        return publishingCompanyID;
    }

    public void setPublishingCompany(int publishingCompanyID) {
        this.publishingCompanyID = publishingCompanyID;
    }

    public int getLibrary() {
        return libraryID;
    }

    public void setLibrary(int libraryID) {
        this.libraryID = libraryID;
    }
}
