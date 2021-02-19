package assistant.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.awt.*;


@DatabaseTable(tableName = "BOOKS")
public class Book implements BaseModel {
    @DatabaseField(columnName ="ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "ISBN_10", unique = true)
    private String isbn10;

    @DatabaseField(columnName = "ISBN_13", unique = true)
    private String isbn13;

    @DatabaseField(columnName = "TITLE", canBeNull = false)
    private String title;

    @DatabaseField(columnName = "ADDED_DATE")
    private String addedDate;

    @DatabaseField(columnName = "LAST_SUBMISSION")
    private String lastSubmission;

    @DatabaseField(columnName = "DESCRIPTION")
    private String description;

    @DatabaseField(columnName = "PUBLICATION_DATE")
    private String publicationDate;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private byte[] bookCover;

    @DatabaseField(columnName = "AVAILABILITY", defaultValue = "true")
    private Boolean availability;

    @DatabaseField(columnName = "AUTHOR_ID", foreign = true, foreignAutoRefresh = true,  foreignAutoCreate = true, canBeNull = false)
    private Author author;

    @DatabaseField(columnName = "CATEGORY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Category category;

    @DatabaseField(columnName = "PUBLISHER_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private PublishingCompany publishingCompany;

    @DatabaseField(columnName = "LIBRARY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Library library;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<BorrowedBook> borrowedBooks;

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

    public String getLastSubmission() {
        return lastSubmission;
    }

    public void setLastSubmission(String lastSubmission) {
        this.lastSubmission = lastSubmission;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public PublishingCompany getPublishingCompany() {
        return publishingCompany;
    }

    public void setPublishingCompany(PublishingCompany publishingCompany) {
        this.publishingCompany = publishingCompany;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public ForeignCollection<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ForeignCollection<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public byte[] getBookCover() {
        return bookCover;
    }

    public void setBookCover(byte[] bookCover) {
        this.bookCover = bookCover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
