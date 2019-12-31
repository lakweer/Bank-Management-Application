package Models;


import Objects.loan.IndividualLoanRequest;
import Objects.loan.OrgLoanRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanModel {
    private Connection connection;
    private Connection connection1;

//    public String getBranchMangerId() {
//        return "ID";
//    }

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
        connection1 = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `createOrgLoanRequest`(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection1.prepareStatement(sql);
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
        connection1 = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `checkLoanStatus`(?, ?)";
            PreparedStatement stmt = connection1.prepareStatement(sql);
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
}