/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package assistant.FXModels;

import assistant.database.models.Country;
import javafx.beans.property.*;

public class AuthorFXModel {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty fistName = new SimpleStringProperty();
    private StringProperty middleName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
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

    public String getFistName() {
        return fistName.get();
    }

    public StringProperty fistNameProperty() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName.set(fistName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
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
        return fistName.getValue() + " " + lastName.get();
    }
}
