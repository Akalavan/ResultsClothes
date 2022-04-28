package app;

import java.sql.*;

public class ConnectDataBase {
    private static Statement stmt = null;

    public static void main(String[] args) {

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

    public static ResultSet query(String query) {
        ResultSet resultSet = null;
        try {
            resultSet = stmt.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public static void queryInsert(String query) {
        try {
            stmt.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
