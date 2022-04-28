package app.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonalProtective {

    private int id;
    private StringProperty name;
    private StringProperty unit;

    public PersonalProtective(int id, String name, String unit) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.unit = new SimpleStringProperty(unit);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }
}
