package assistant.FXModels;

import assistant.database.models.Country;
import javafx.beans.property.*;

public class CityFXModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private ObjectProperty<CountryFXModel> country = new SimpleObjectProperty<>();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public CountryFXModel getCountry() {
        return country.get();
    }

    public ObjectProperty<CountryFXModel> countryProperty() {
        return country;
    }

    public void setCountry(CountryFXModel country) {
        this.country.set(country);
    }

    @Override
    public String toString() {
        return name.getValue();
    }
}