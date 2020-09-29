package app.objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.time.LocalDate;

public class Worker {
    private int id;
    private StringProperty table;
    private StringProperty secondName;
    private StringProperty firstName;
    private StringProperty surname;
    private ObjectProperty<LocalDate> dateAcceptance;
    private StringProperty dateAcceptanceS;
    private StringProperty position;

    public Worker(int id, String table, String secondName, String firstName, String surname, String dateAcceptanceS, String position) {
        this.id = id;
        this.table = new SimpleStringProperty(table);
        this.secondName = new SimpleStringProperty(secondName);
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
        this.dateAcceptance = new SimpleObjectProperty<>(LocalDate.now());
        //this.dateAcceptanceS = new SimpleStringProperty(dateAcceptanceS);
        this.position = new SimpleStringProperty(position);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTable() {
        return table.get();
    }

    public StringProperty tableProperty() {
        return table;
    }

    public void setTable(String table) {
        this.table.set(table);
    }

    public String getSecondName() {
        return secondName.get();
    }

    public StringProperty secondNameProperty() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public LocalDate getDateAcceptance() {
        return dateAcceptance.get();
    }

    public ObjectProperty<LocalDate> dateAcceptanceProperty() {
        return dateAcceptance;
    }

    public void setDateAcceptance(LocalDate dateAcceptance) {
        this.dateAcceptance.set(dateAcceptance);
    }

    public String getDateAcceptanceS() {
        return dateAcceptanceS.get();
    }

    public StringProperty dateAcceptanceSProperty() {
        return dateAcceptanceS;
    }

    public void setDateAcceptanceS(String dateAcceptanceS) {
        this.dateAcceptanceS.set(dateAcceptanceS);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }
}

