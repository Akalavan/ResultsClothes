package app.objects.print;


import app.ConnectDataBase;
import app.objects.NormalDocument;
import app.objects.Position;
import app.objects.Worker;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintChecklist {
    private String tableNumber;
    private String name;
    private String surname;
    private String patronymic;
    private NormalDocument document;
    private Position position;
    private List<Clothes> clothesList;

    private Statement stmt;
    private ResultSet result;


    public PrintChecklist(String tableNumber, String name, String surname, String patronymic, Position position) {
        this.tableNumber = tableNumber;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.position = position;
        ConnectDataBase CDB = new ConnectDataBase();
        stmt = CDB.getStmt();
        setClothesList();
        setDocument();
    }

    public PrintChecklist() {
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

    public void setDocument() {
        try {
            result = stmt.executeQuery(
                    "select * from NORMAL_DOCUMENT nd\n" +
                            "join CHECKLIST C on nd.ID_NORMAL_DOCUMENT = C.ID_NORMAL_DOCUMENT\n" +
                            "WHERE C.ID_POSITION = " + position.getId() +" limit 1;");

            while (result.next()) {
                document = new NormalDocument(result.getInt(1), result.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public List<Clothes> getClothesList() {
        return clothesList;
    }

    public void setClothesList() {
        clothesList = new ArrayList<>();
        try {

            result = stmt.executeQuery(
                    "SELECT P.NAME, P.UNIT, C.QUANTITY_YEAR FROM PERSONAL_PROTECTIVE P " +
                    "JOIN CHECKLIST C on P.ID_PERSONAL_PROTECTIVE = C.ID_PERSONAL_PROTECTIVE " +
                    "WHERE C.ID_POSITION = 1;");
            int i = 0;
            while (result.next()) {
                clothesList.add(new Clothes(
                        result.getString("NAME"),
                        result.getString("UNIT"),
                        result.getString("QUANTITY_YEAR")));
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(clothesList);
    }

    public static void main(String[] args) {
        PrintChecklist p = new PrintChecklist();
        p.setClothesList();
    }
}
