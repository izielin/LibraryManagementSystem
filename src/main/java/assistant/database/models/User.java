package assistant.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "MEMBERS")
public class User {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "USERNAME", canBeNull = false)
    private String username;

    @DatabaseField(columnName = "PASSWORD", canBeNull = false)
    private String password;

    @DatabaseField(columnName = "EMAIL", canBeNull = false)
    private String email;

    @DatabaseField(columnName = "PRIVILEGE", canBeNull = false, defaultValue = "false")
    private boolean privilege;

    @DatabaseField(columnName = "LIBRARY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Library library;

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

    public boolean isPrivilege() {
        return privilege;
    }

    public void setPrivilege(boolean privilege) {
        this.privilege = privilege;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}