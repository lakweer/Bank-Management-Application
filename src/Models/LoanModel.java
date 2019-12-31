package Models;


import Objects.loan.IndividualLoanRequest;
import Objects.loan.OrgLoanRequest;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class LoanModel {
    private Connection connection;

    public ArrayList<HashMap<String, String>> viewIndividulaLoanRequests(String branchId){
        ArrayList<HashMap<String, String>> ar = new ArrayList<HashMap<String, String>>();
        connection = DB.Database.getConnection();
        String sql =
        "SELECT `RequestId`, `CustomerId`, `Amount`, `ApprovedStatus`, `EmployeeId`, `GrossSalary`, `NetSalary`, `EmploymentSector`, " +
                "`EmploymentType`, `ApprovedStatus`, `Profession`, `requestDate`, `FirstName`, `LastName`, `Nic`, `LoanTypeName`, `LoanTypeId` FROM `loan_request` " +
                "INNER JOIN `individual` USING(`CustomerId`) INNER JOIN `loan_type` USING(`LoanTypeId`) " +
                "WHERE `ApprovedStatus` = 'PENDING' AND `BranchId` =?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,branchId);
            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
            int counter = md.getColumnCount();

            while (rs.next()){
                HashMap<String, String> map = new HashMap<String, String>();
                for (int loop = 1; loop <= counter; loop++) {
                    map.put(md.getColumnLabel(loop), rs.getString(loop));
                }
                ar.add(map);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ar;
    }

    public ArrayList<HashMap<String, String>> viewOrganizationLoanRequests(String branchId){
        ArrayList<HashMap<String, String>> ar = new ArrayList<HashMap<String, String>>();
        connection = DB.Database.getConnection();
        String sql = "SELECT `RequestId`, `CustomerId`, `BranchId`, `Amount`, `ApprovedStatus`, `EmployeeId`, `ProjectGrossValue`, " +
                " `LoanReason`, `OrganizationType`, `RequestDate` FROM `org_loan_request`"
                + "WHERE `ApprovedStatus` = 'PENDING' AND `BranchId` =?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,branchId);
            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
            int counter = md.getColumnCount();

            while (rs.next()){
                HashMap<String, String> map = new HashMap<String, String>();
                for (int loop = 1; loop <= counter; loop++) {
                    map.put(md.getColumnLabel(loop), rs.getString(loop));
                }
                ar.add(map);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ar;
    }

    public boolean rejectIndividualLoanRequest(String reqId){
        connection = DB.Database.getConnection();
        String sql = "UPDATE `loan_request` SET `ApprovedStatus`= 'REJECTED' WHERE `RequestId` = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, reqId);
            int i = stmt.executeUpdate();
            if(i > 0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String approveLoanRequest(String reqId, String amount,String loanType, String period, String numOfSettlements){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        String sql =" CALL `approveLoanRequest`(?, ?, ?, ?, ?)";
       try {
           PreparedStatement stmt =  connection.prepareStatement(sql);
           stmt.setString(1,reqId);
           stmt.setString(2,amount);
           stmt.setString(3,loanType);
           stmt.setString(4,period);
           stmt.setString(5,numOfSettlements);
           ResultSet rs = stmt.executeQuery();
           while (rs.next()) {
               result = rs.getString(1);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
        return result;
    }

    /*
   Calls the createIndividualLoanRequest Stored Procedure
   Input Fields : `CustomerId`, `BranchId`, `Amount`, `EmployeeId`, `GrossSalary`, `NetSalary`,
                `EmploymentSector`, `EmploymentType`, `Profession`, `LoanTypeId`, `requestDate`
    */
    public String createIndividualLoanRequest(IndividualLoanRequest inLoanRequest) {
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `createIndividualLoanRequest`(?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, inLoanRequest.getCustomerId());
            stmt.setString(2, inLoanRequest.getBranchId());
            stmt.setString(3, inLoanRequest.getAmount().toString());
            stmt.setString(4, inLoanRequest.getEmployeeId());
            stmt.setString(5, inLoanRequest.getGrossSalary().toString());
            stmt.setString(6, inLoanRequest.getNetSalary().toString());
            stmt.setString(7, inLoanRequest.getEmploymentSector());
            stmt.setString(8, inLoanRequest.getEmploymentType());
            stmt.setString(9, inLoanRequest.getProfession());
            stmt.setString(10, inLoanRequest.getLoanTypeId());
            stmt.setString(11, inLoanRequest.getRequestDate().toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String createOrgLoanRequest(OrgLoanRequest orgLoanRequest) {
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `createOrgLoanRequest`(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, orgLoanRequest.getRegisterId());
            stmt.setString(2, orgLoanRequest.getBranchId());
            stmt.setString(3, orgLoanRequest.getAmount().toString());
            stmt.setString(4, orgLoanRequest.getEmployeeId());
            stmt.setString(5, orgLoanRequest.getProjectGrossValue().toString());
            stmt.setString(6, orgLoanRequest.getLoanReason());
            stmt.setString(7, orgLoanRequest.getOrganizationType());
            stmt.setString(8, orgLoanRequest.getRequestDate().toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String checkLoanStatus(String loanType, Integer requestId) {
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `checkLoanStatus`(?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, loanType);
            stmt.setString(2, requestId.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public String updateSettlements(String loanTID, LocalDate date , Double settleAmount) {
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `updateSettlements`(?, ?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, loanTID);
            stmt.setString(2, date.toString());
            stmt.setString(2, settleAmount.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}