package app.objects.print;

import java.util.List;

public class Clothes {
    private String nameClothes;
    private String unit;
    private String quantity_year;

    public Clothes(String nameClothes, String unit, String quantity_year) {
        this.nameClothes = nameClothes;
        this.unit = unit;
        this.quantity_year = quantity_year;
    }

    public String getNameClothes() {
        return nameClothes;
    }

    public void setNameClothes(String nameClothes) {
        this.nameClothes = nameClothes;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity_year() {
        return quantity_year;
    }

    public void setQuantity_year(String quantity_year) {
        this.quantity_year = quantity_year;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "nameClothes='" + nameClothes + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity_year='" + quantity_year + '\'' +
                '}';
    }
}
