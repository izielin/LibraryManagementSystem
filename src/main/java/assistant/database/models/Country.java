package assistant.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "COUNTRIES")
public class Country implements BaseModel{
    @DatabaseField(columnName ="ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "NAME", canBeNull = false, unique = true)
    private String name;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<City> cities;

    public Country() {
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

    public ForeignCollection<City> getCities() {
        return cities;
    }

    public void setCities(ForeignCollection<City> cities) {
        this.cities = cities;
    }
}
