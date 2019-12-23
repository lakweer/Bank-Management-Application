package Models;

import Controllers.Employee.EmployeeHome;
import Objects.CurrentAccount.CurrentAccount;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrentAccountModel {

    private Connection connection;

    /*
    Calls the currentAccountOpen Stored Procedure
    Input Fields : current Account Number, customer NIC or business reg. number (depends on the customer type),
                    Current Account Open Date, Customer Type, Open Employee Id,	Branch Id
     */
    public String openCurrentAccount(CurrentAccount account, String open_date, String type, EmployeeHome parent){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `currentAccountOpen`(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getCustomerId());
            stmt.setString(3, open_date);
            stmt.setString(4, type);
            stmt.setString(5, parent.getEmployeeID());
            stmt.setString(6, account.getBranchId());

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
