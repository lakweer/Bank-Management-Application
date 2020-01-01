package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DebitCardModel {

    private Connection connection;

    public String issueNewDebitCard(String CardNumber, String PinNumber, String SavingsAccountNumber, String IssuedDate,
                                    String ExpiryDate){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `issueNewDebitCard`(?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, CardNumber);
            stmt.setString(2, PinNumber);
            stmt.setString(3, SavingsAccountNumber);
            stmt.setString(4, IssuedDate);
            stmt.setString(5, ExpiryDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                result = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public String cancelDebitCard(String AccountNumber){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `cancelDebitCard`(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, AccountNumber);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                result = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
