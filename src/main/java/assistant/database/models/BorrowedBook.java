package assistant.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@DatabaseTable(tableName = "BORROWED_BOOKS")
public class BorrowedBook implements BaseModel{
    @DatabaseField(columnName ="ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "BORROW_TIME")
    private String borrowTime;

    @DatabaseField(columnName = "RETURN_TIME")
    private String returnTime;

    @DatabaseField(columnName = "IS_RETURNED", defaultValue = "false")
    private Boolean isReturned;

    @DatabaseField(columnName = "BOOK_ID")
    private int bookID;

    @DatabaseField(columnName = "USER_ID")
    private int userID;

    @DatabaseField(columnName = "LIBRARY_ID")
    private int libraryID;

    public BorrowedBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(int libraryID) {
        this.libraryID = libraryID;
    }
}