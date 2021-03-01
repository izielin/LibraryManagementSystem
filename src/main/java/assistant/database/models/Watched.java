package assistant.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "WATCHED")
public class Watched implements BaseModel{
    @DatabaseField(columnName ="ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "BOOK_TITLE")
    private int bookTitle;

    @DatabaseField(columnName = "USER_ID")
    private int userID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(int bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
