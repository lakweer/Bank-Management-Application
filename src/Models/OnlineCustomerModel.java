package Models;

import Objects.OnlineCustomerAccount.OnlineCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OnlineCustomerModel {
    private Connection connection;

    public String addCustomer(OnlineCustomer customer){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `addOnlineCustomer`(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,customer.getUsername());

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
