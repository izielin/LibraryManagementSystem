package assistant.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "USERS")
public class User implements BaseModel {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "USERNAME", canBeNull = false, unique = true)
    private String username;

    @DatabaseField(columnName = "PASSWORD", canBeNull = false)
    private String password;

    @DatabaseField(columnName = "FIRST_NAME", canBeNull = false)
    private String firstName;

    @DatabaseField(columnName = "LAST_NAME", canBeNull = false)
    private String lastName;

    @DatabaseField(columnName = "MOBILE", canBeNull = false)
    private String mobile;

    @DatabaseField(columnName = "EMAIL", canBeNull = false)
    private String email;

    @DatabaseField(columnName = "REGISTRATION_DATE")
    private String registrationDate;

    @DatabaseField(columnName = "STREET", canBeNull = false)
    private String street;

    @DatabaseField(columnName = "ZIP_CODE", canBeNull = false)
    private String zipCode;

    @DatabaseField(columnName = "CITY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private City city;

    @DatabaseField(columnName = "USER_TYPE", canBeNull = false, defaultValue = "MEMBER")
    private String userType;

    @DatabaseField(columnName = "LIBRARY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Library library;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<BorrowedBook> borrowedBooks;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registration_date) {
        this.registrationDate = registration_date;
    }
}