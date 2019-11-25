package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static Connection connection;

    private Database(){}

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "");
            System.out.print("Database is connected !\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
            connection = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
