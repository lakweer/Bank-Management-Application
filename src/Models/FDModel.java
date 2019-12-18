package Models;

import Objects.FixedDepositAccount.FixedDepositAccount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FDModel {

    private Connection connection;

    /*
    Calls the fixedDepositOpen Stored Procedure
    Input Fields : FD Account Number, Linked Savings Account Number, Customer NIC
                    Deposit Amount, Date Deposit, matuarity Date , FD type
     */
    public String openFD(FixedDepositAccount account){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `fixedDepositOpen`(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, account.getFdAccountNumber());
            stmt.setString(2, account.getLinkedSavingsAccountNumber());
            stmt.setString(3, account.getCustomerId());
            stmt.setString(4, account.getDepositAmount().toString());
            stmt.setString(5, account.getDepositDate().toString());
            stmt.setString(6, account.getMatuarityDate().toString());
            stmt.setString(7, account.getFdType());
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
