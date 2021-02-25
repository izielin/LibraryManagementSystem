package assistant.database.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "USERS")
public class User implements BaseModel {
    @DatabaseField(columnName = "ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "USERNAME", canBeNull = false, unique = true)
    private String username;

    @DatabaseField(columnName = "PASSWORD", canBeNull = false)
    private String password;

    @DatabaseField(columnName = "PROFILE_PICTURE",dataType = DataType.SERIALIZABLE)
    private byte[] profilePicture;

    @DatabaseField(columnName = "FIRST_NAME", canBeNull = false)
    private String firstName;

    @DatabaseField(columnName = "LAST_NAME", canBeNull = false)
    private String lastName;

    @DatabaseField(columnName = "GENDER", canBeNull = false)
    private String gender;

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

    @DatabaseField(columnName = "USER_TYPE", canBeNull = false, defaultValue = "MEMBER")
    private String userType;

    @DatabaseField(columnName = "LIBRARY_ID")
    private int libraryID;

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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(int libraryID) {
        this.libraryID = libraryID;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}