package app.objects.print;

import app.objects.NormalDocument;
import app.objects.Position;

import java.util.List;

public class PrintChecklist {
    private String name;
    private String surname;
    private String patronymic;
    private NormalDocument document;
    private Position position;
    private List<Clothes> clothesList;


    public PrintChecklist(String name, String surname, String patronymic,
                          NormalDocument document, Position position, List<Clothes> clothesList) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.document = document;
        this.position = position;
        this.clothesList = clothesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public NormalDocument getDocument() {
        return document;
    }

    public void setDocument(NormalDocument document) {
        this.document = document;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Clothes> getClothesList() {
        return clothesList;
    }

    public void setClothesList(List<Clothes> clothesList) {
        this.clothesList = clothesList;
    }
}
