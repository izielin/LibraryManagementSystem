package assistant.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "REPORTS")
public class Report implements BaseModel {
    @DatabaseField(columnName = "ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "LIBRARY_ID")
    private int libraryID;

    @DatabaseField(columnName = "DATE")
    private String date;

    @DatabaseField(columnName = "BOOKS_AMOUNT")
    private int booksAmount;

    @DatabaseField(columnName = "USERS_NUMBER")
    private int usersNumber;

    @DatabaseField(columnName = "WOMAN_NUMBER")
    private int womanNumber;

    // total amount of borrowed books in a given month
    @DatabaseField(columnName = "B_BOOKS_AMOUNT")
    private int bBooksAmount;

    //total amount of returned books in a given month
    @DatabaseField(columnName = "R_BOOKS_AMOUNT")
    private int rBooksAmount;

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(int libraryID) {
        this.libraryID = libraryID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBooksAmount() {
        return booksAmount;
    }

    public void setBooksAmount(int booksAmount) {
        this.booksAmount = booksAmount;
    }

    public int getbBooksAmount() {
        return bBooksAmount;
    }

    public void setbBooksAmount(int bBooksAmount) {
        this.bBooksAmount = bBooksAmount;
    }

    public int getUsersNumber() {
        return usersNumber;
    }

    public void setUsersNumber(int usersNumber) {
        this.usersNumber = usersNumber;
    }

    public int getWomanNumber() {
        return womanNumber;
    }

    public void setWomanNumber(int womanNumber) {
        this.womanNumber = womanNumber;
    }

    public int getrBooksAmount() {
        return rBooksAmount;
    }

    public void setrBooksAmount(int rBooksAmount) {
        this.rBooksAmount = rBooksAmount;
    }
}
