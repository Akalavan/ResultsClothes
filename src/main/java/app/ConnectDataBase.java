package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDataBase {
    private Statement stmt = null;
    public static void main(String[] args) {

//        Connection con = null;
//
//        int result = 0;
//
//        try {
//            //Registering the HSQLDB JDBC drive
//            Class.forName("org.hsqldb.jdbc.JDBCDriver");
//            //Creating the connecting with HSQLDB
//            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
//
//            if (con == null) {
//                System.out.println("Problem with creating connection");
//            } else {
//                System.out.println("Connection created successfully");
//
//                stmt = con.createStatement();
//
//                // result = createTable(stmt);
//                //  System.out.println("Table created successfully");
//                result = dropTable(stmt, "tutorial_tbl");
//                System.out.println("Table dropped successfully");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }

    }

    private static int createTable(Statement stmt) throws SQLException {
        return stmt.executeUpdate(
                "CREATE TABLE tutorial_tbl (" +
                        "id INT NOT NULL, " +
                        "title VARCHAR(50) NOT NULL, " +
                        "author VARCHAR(20) NOT NULL, " +
                        "date DATE, PRIMARY KEY(id) )"
        );
    }

    private static int dropTable(Statement stmt, String name) throws SQLException {
        return stmt.executeUpdate("DROP TABLE "+ name);
    }

    public Statement getStmt() {
        Connection con = null;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
            if (con == null) {
                System.out.println("Problem with creating connection");
                return null;
            }
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return stmt;
    }
}
