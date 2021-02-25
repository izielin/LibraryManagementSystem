package assistant.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "AUTHORS")
public class Author implements BaseModel {
    @DatabaseField(columnName = "ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "FIRST_NAME", canBeNull = false)
    private String fistName;

    @DatabaseField(columnName = "MIDDLE_NAME")
    private String middleName;

    @DatabaseField(columnName = "LAST_NAME", canBeNull = false)
    private String lastName;

    // ORMLite needs a no-arg constructor
    public Author() {
    }

    // setters & getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fistName + " " + middleName + " " + lastName;
    }
}