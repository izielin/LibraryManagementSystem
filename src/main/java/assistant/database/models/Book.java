package assistant.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "BOOKS")
public class Book {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "ISBN_10")
    private String isbn10;

    @DatabaseField(columnName = "ISBN_13")
    private String isbn13;

    @DatabaseField(columnName = "TITTLE", canBeNull = false)
    private String title;

    @DatabaseField(columnName = "ADDED_DATE")
    private Date addedDate;

    @DatabaseField(columnName = "PUBLICATION_DATE")
    private Date publicationDate;

    @DatabaseField(columnName = "AUTHOR_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Author author;

    @DatabaseField(columnName = "CATEGORY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Category category;

    @DatabaseField(columnName = "PUBLISHER_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private PublishingCompany publishingCompany;

    @DatabaseField(columnName = "LIBRARY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Library library;

    @ForeignCollectionField(eager=true)
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

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
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
}
