package assistant.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DISTRICTS")
public class District {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "NAME", canBeNull = false, unique = true)
    private String name;

    @DatabaseField(columnName = "ZIP_CODE", canBeNull = false, unique = true)
    private String zipCode;

    @DatabaseField(columnName = "CITY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private City city;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Library> libraries;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Member> members;

    public District() {
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

    public ForeignCollection<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(ForeignCollection<Library> libraries) {
        this.libraries = libraries;
    }

    public ForeignCollection<Member> getMembers() {
        return members;
    }

    public void setMembers(ForeignCollection<Member> members) {
        this.members = members;
    }
}