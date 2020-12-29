package assistant.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "CITIES")
public class City {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "NAME", canBeNull = false, unique = true)
    private String name;

    @DatabaseField(columnName = "COUNTRY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Country country;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<District> districts;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Author> authors;

    public City() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public ForeignCollection<District> getDistricts() {
        return districts;
    }

    public void setDistricts(ForeignCollection<District> districts) {
        this.districts = districts;
    }

    public ForeignCollection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ForeignCollection<Author> authors) {
        this.authors = authors;
    }
}
