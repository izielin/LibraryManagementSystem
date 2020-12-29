package assistant.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "BORROWED_BOOKS")
public class BorrowedBook {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "BORROW_TIME")
    private Date borrowTime;

    @DatabaseField(columnName = "RENEW_COUNT")
    private int renewCount;

    @DatabaseField(columnName = "BOOK_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Book book;

    @DatabaseField(columnName = "LIBRARY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Member member;

    public BorrowedBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Integer getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(Integer renewCount) {
        this.renewCount = renewCount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}