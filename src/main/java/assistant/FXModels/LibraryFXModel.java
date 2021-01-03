package assistant.FXModels;

import javafx.beans.property.*;

public class LibraryFXModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty street = new SimpleStringProperty();
    private final StringProperty zipCode = new SimpleStringProperty();
    private final ObjectProperty<CityFXModel> cityFX = new SimpleObjectProperty<>();
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

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getZipCode() {
        return zipCode.get();
    }

    public StringProperty zipCodeProperty() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode.set(zipCode);
    }

    public CityFXModel getCityFX() {
        return cityFX.get();
    }

    public ObjectProperty<CityFXModel> cityFXProperty() {
        return cityFX;
    }

    public void setCityFX(CityFXModel cityFX) {
        this.cityFX.set(cityFX);
    }

    @Override
    public String toString() {
        return name.getValue();
    }
}
