package Models;

import Controllers.Employee.EmployeeHome;
import DB.Database;
import Objects.CurrentAccount.CurrentAccount;


import java.sql.*;

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

    public String closeCurrentAccount(String account_number, String close_date, EmployeeHome parent){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `currentAccountClose`(?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, account_number);
            stmt.setString(2, close_date);
            stmt.setString(3, parent.getEmployeeID());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                result = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public String currentAccountTransaction(String account_number, String transaction_date, EmployeeHome parent, String amount,
                                            String cheque_number, String transaction_type, String transaction_mode){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `currentAccountTransaction`(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, parent.getEmployeeID());
            stmt.setString(2, account_number);
            stmt.setString(3, transaction_date);
            stmt.setString(4, amount);
            stmt.setString(5, cheque_number);
            stmt.setString(6, transaction_type);
            stmt.setString(7, transaction_mode);


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
