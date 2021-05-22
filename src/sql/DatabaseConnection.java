package sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection conn;

    public Connection getConnection() {
        Connection conn;
        String url = "jdbc:mysql://127.0.0.1:3306/COMP1020";
        String username = "root";
        String password = "VuphuC2462001.";

        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully Connected");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }
}
