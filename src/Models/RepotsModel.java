package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RepotsModel {
    private Connection connection;

    public HashMap<String, ArrayList<String>> getMonthlyTransactionReport(String branchId, String date){

        HashMap<String, ArrayList<String> > result = new HashMap<>();

        connection = DB.Database.getConnection();

        String sql = "SELECT `TransactionType`, COUNT(*), SUM(`Amount`)  " +
                "FROM `savings_transaction_report` WHERE MONTH (`TransactionDate`) = MONTH(?) AND `BranchId` = ? " +
                "GROUP BY `TransactionType`";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, date);
            stmt.setString(2, branchId);
            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
            int counter = md.getColumnCount();

            while (rs.next()) {
                ArrayList<String> values =  new ArrayList<>();

                if(rs.getString(1).equals("Withdrawal")){
                    for (int loop = 1; loop <= counter; loop++) {
                        values.add(rs.getString(loop));
                    }
                    result.put("Withdrawal", values);
                }else {
                    for (int loop = 1; loop <= counter; loop++) {
                        values.add(rs.getString(loop));
                    }
                    result.put("Deposits", values);
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public HashMap<String, String > getNumberOfActiveAccountsInBranch(String branchId){
        connection = DB.Database.getConnection();

        HashMap<String, String > result = new HashMap<>();

        String sql = " SELECT `AccountTypeName`, COUNT(*) FROM `savings_details` WHERE `BranchId` = ?  GROUP BY `AccountTypeName`";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, branchId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                result.put(rs.getString(1), rs.getString(2));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    public String[]  getLateLoanReports(String branchId){
        connection = DB.Database.getConnection();

        String[] result = new String[4];

        String notRecivedOrgCharges = "0.00";
        String sql1 = "SELECT SUM(`LateCharges`) FROM `organization_loan_latecharge` WHERE `InstallmentDate` IS NULL AND `BranchId` = ? ";
        try {
            PreparedStatement stmt1 = connection.prepareStatement(sql1);
            stmt1.setString(1, branchId);
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()){
                notRecivedOrgCharges = rs.getString(1);

                if(notRecivedOrgCharges == null){
                    notRecivedOrgCharges = "0.00";
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String recivedOrgCharges = "0.00";
        String sql2 = "SELECT SUM(`LateCharges`) FROM `organization_loan_latecharge` WHERE `InstallmentDate` IS NOT NULL AND `BranchId` = ? ";
        try {
            PreparedStatement stmt1 = connection.prepareStatement(sql2);
            stmt1.setString(1, branchId);
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()){
                recivedOrgCharges = rs.getString(1);
                if(recivedOrgCharges == null ){
                    recivedOrgCharges = "0.00";
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String notRecivedIndiCharges = "0.00";
        String sql3 = "SELECT SUM(`LateCharges`) FROM `individual_loan_latecharge` WHERE `InstallmentDate` IS NULL AND `BranchId` = ? ";
        try {
            PreparedStatement stmt1 = connection.prepareStatement(sql3);
            stmt1.setString(1, branchId);
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()){
                notRecivedIndiCharges = rs.getString(1);
                if(notRecivedIndiCharges == null){
                    notRecivedIndiCharges = "0.00";
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String recivedIndiCharges = "0.00";
        String sql4 = "SELECT SUM(`LateCharges`) FROM `individual_loan_latecharge` WHERE `InstallmentDate` IS NOT NULL AND `BranchId` = ? ";
        try {
            PreparedStatement stmt1 = connection.prepareStatement(sql4);
            stmt1.setString(1, branchId);
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()){
                recivedIndiCharges = rs.getString(1);
                if(recivedIndiCharges == null){
                    recivedIndiCharges = "0.00";
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        result[0] = notRecivedOrgCharges;
        result[1] = recivedOrgCharges;
        result[2] = notRecivedIndiCharges;
        result[3] = recivedIndiCharges;

        return result;
    }
}
