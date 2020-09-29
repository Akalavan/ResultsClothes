package app.managementDB;

import app.ConnectDataBase;
import app.unload.CreateExcelDemo;

import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {
    Statement stmt;
    ResultSet result;

    public void testSql() {
        setStmt();
        //result =

    }

    public void setStmt() {
        ConnectDataBase CDB = new ConnectDataBase();
        stmt = CDB.getStmt();
    }
}
