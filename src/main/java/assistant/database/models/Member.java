package assistant.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "MEMBERS")
public class Member {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "FIRST_NAME", canBeNull = false)
    private String first_name;

    @DatabaseField(columnName = "LAST_NAME", canBeNull = false)
    private String last_name;

    @DatabaseField(columnName = "MOBILE", canBeNull = false)
    private String mobile;

    @DatabaseField(columnName = "EMAIL", canBeNull = false)
    private String email;

    @DatabaseField(columnName = "STREET", canBeNull = false)
    private String street;

    @DatabaseField(columnName = "DISTRICT_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private District district;

    @DatabaseField(columnName = "LIBRARY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Library library;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<BorrowedBook> borrowedBooks;

    public Member(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
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