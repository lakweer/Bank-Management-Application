package Models;

import Objects.SavingsAccounts.SavingsAccount;
import Objects.SavingsAccounts.SavingsTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SavingsAccountModel {
    private Connection connection;

    public ResultSet getAccountDetails(){
        ResultSet rs = null;
        connection = DB.Database.getConnection();
        String sql = "SELECT * FROM savings_accounts_type";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public boolean openIndividualSavingsAccount(SavingsAccount account,String empID){
        connection = DB.Database.getConnection();
        try {
            connection.setAutoCommit(false);
            String sql1 = " INSERT INTO `savings_account` VALUES (?,?,?,?,?)";
            PreparedStatement stmt1 = connection.prepareStatement(sql1);
            stmt1.setString(1,account.getAccountNumber());
            stmt1.setString(2,account.getBranchId());
            stmt1.setString(3,account.getAccountType());
            stmt1.setDouble(4,account.getBalance());
            stmt1.setString(5,account.getStatus());
            int result = stmt1.executeUpdate();

            if(result>0){
                String sql2 = "INSERT INTO `savings_customers` VALUES (?,?)";
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setString(1,account.getAccountNumber());
                stmt2.setString(2,account.getCustomerId());
                int result2 = stmt2.executeUpdate();

                if(result2>0){
                    String sql3 = "INSERT INTO `savings_open`(`AccountNumber`, `OpenEmployeeId`, `OpenDate`) VALUES (?,?,?)";
                    PreparedStatement stmt3 = connection.prepareStatement(sql3);
                    stmt3.setString(1,account.getAccountNumber());
                    stmt3.setString(2, empID);
                    stmt3.setString(3, LocalDate.now().toString());
                    int result3 = stmt3.executeUpdate();

                    if(result3>0){
                        connection.commit();
                        return true;
                    }
                    else {
                        connection.rollback();
                    }
                }
                else{
                    connection.rollback();
                }
            }
            else {
                connection.rollback();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    /*
    Calls the savingsWithdrawal Stored Procedure
    Input Fields : Account Number, Withdraw Amount, Date , Teller, EmployeeId
     */
    public String withdrawal(SavingsTransaction transaction, String empId){
        connection = DB.Database.getConnection();
        String result = "";
        try {
            String sql = "CALL `savingsWithdraw`(?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,transaction.getAccountNumber());
            stmt.setString(2,transaction.getAmount().toString());
            stmt.setString(3,transaction.getTransactionDate().toString());
            stmt.setString(4,transaction.getTellerType());
            stmt.setString(5, empId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                result = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }


    /*
    Calls the savingsDeposit Stored Procedure
    Input Fields : Account Number, Withdraw Amount, Date , Teller, EmployeeId
     */
    public String deposit(SavingsTransaction transaction, String empId){
        connection = DB.Database.getConnection();
        String result = "";
        try {
            String sql = "CALL `savingsDeposit`(?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,transaction.getAccountNumber());
            stmt.setString(2,transaction.getAmount().toString());
            stmt.setString(3,transaction.getTransactionDate().toString());
            stmt.setString(4,transaction.getTellerType());
            stmt.setString(5, empId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                result = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }


    /*
    Calls the savingsClose Stored Procedure
    Input Fields : Account Number, Date of Closing , EmployeeId
     */
    public String close(String accountNumber, String closingDate, String empId){
        connection = DB.Database.getConnection();
        String result = "";
        try {
            String sql = "CALL `savingsClose`(?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,accountNumber);
            stmt.setString(2,closingDate);
            stmt.setString(3, empId);
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
