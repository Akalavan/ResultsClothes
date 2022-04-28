package app.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableIndividualClothing {
    private int id;
    private StringProperty nameClothing;
    private StringProperty normalDocument;
    private StringProperty unit;
    private StringProperty quantityYear;

    public TableIndividualClothing(String nameClothing, String normalDocument, String unit, String quantityYear) {
        this.nameClothing = new SimpleStringProperty(nameClothing);
        this.normalDocument = new SimpleStringProperty(normalDocument);
        this.unit = new SimpleStringProperty(unit);
        this.quantityYear = new SimpleStringProperty(quantityYear);
    }

    public String getNameClothing() {
        return nameClothing.get();
    }

    public StringProperty nameClothingProperty() {
        return nameClothing;
    }

    public void setNameClothing(String nameClothing) {
        this.nameClothing.set(nameClothing);
    }

    public String getNormalDocument() {
        return normalDocument.get();
    }

    public StringProperty normalDocumentProperty() {
        return normalDocument;
    }

    public void setNormalDocument(String normalDocument) {
        this.normalDocument.set(normalDocument);
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

    public String getQuantityYear() {
        return quantityYear.get();
    }

    public StringProperty quantityYearProperty() {
        return quantityYear;
    }

    public void setQuantityYear(String quantityYear) {
        this.quantityYear.set(quantityYear);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
