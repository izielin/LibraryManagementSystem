package assistant.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LIBRARIES")
public class Library {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "NAME", canBeNull = false, unique = true)
    private String name;

    @DatabaseField(columnName = "STREET", canBeNull = false)
    private String street;

    @DatabaseField(columnName = "DISTRICT_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private District district;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Book> books;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Member> members;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<User> users;

    public Library() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ForeignCollection<Book> getBooks() {
        return books;
    }

    public void setBooks(ForeignCollection<Book> books) {
        this.books = books;
    }

    public ForeignCollection<Member> getMembers() {
        return members;
    }

    public void setMembers(ForeignCollection<Member> members) {
        this.members = members;
    }

    public ForeignCollection<User> getUsers() {
        return users;
    }

    public void setUsers(ForeignCollection<User> users) {
        this.users = users;
    }
}
