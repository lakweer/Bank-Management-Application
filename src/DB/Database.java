package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static Connection connection;

    private Database(){}

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bank_system", "root", "");
            System.out.print("Database is connected !\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
