package app.objects.print;

import app.ConnectDataBase;
import app.objects.NormalDocument;
import app.objects.Position;
import app.objects.Worker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void setClothesList() {
        Statement stmt;
        ResultSet result;

        try {
            ConnectDataBase CDB = new ConnectDataBase();
            stmt = CDB.getStmt();

            result = stmt.executeQuery("select w.TABLE_PERSONAL, w.FIRST_NAME, w.SECOND_NAME, w.SURNAME, p.NAME from WORKER w, POSITION p\n" +
                    "where w.ID_POSITION = p.ID_POSITION");
            int i = 0;
            while (result.next()) {
                clothesList.add(new Clothes(
                        result.getString("TABLE_PERSONAL"),
                        result.getString("SECOND_NAME"),
                        result.getString("FIRST_NAME")));
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
