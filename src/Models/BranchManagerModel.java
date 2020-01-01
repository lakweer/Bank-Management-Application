package Models;

import Hashing.GFG;
import Objects.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchManagerModel extends  EmployeeModel{
    private Connection connection;

    public String getBranchMangerId(){
        return "ID";
    }

    /*
   Calls the addNewEmployee Stored Procedure
   Input Fields : first name, last name, branch id, nic, email, date of birth,
                    employee type, user name, password
    */
    public String addNewEmployee(Employee employee, String empType){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `addNewEmployee`(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getBranchId());
            stmt.setString(4, employee.getNic());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getDateOfBirth().toString());
            stmt.setString(7, empType);
            stmt.setString(8, employee.getUserName());
            stmt.setString(9, GFG.encryptThisString(employee.getPassword()));
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
      Calls the viewCurrentEmployees Stored Procedure
      Input Fields : branchId
    */
    public ResultSet getCurrentEmployeeDetails(){
        connection = DB.Database.getConnection();
//        ArrayList<Employee> employees = new ArrayList<Employee>();
        ResultSet rs = null;
        try {
            String sql = "CALL `viewCurrentEmployees`(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, this.getBranchID());
            rs = stmt.executeQuery();
//            while (rs.next()){
//                Employee employee = new Employee(rs.getString(1), rs.getString(2),
//                        rs.getString(3), rs.getString(4), rs.getString(6), rs.getString(5));
//            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }


    /*
      Calls the addExistingEmployeeToBranch Stored Procedure
      Input Fields : nic , BranchId
      No return value in this procedure
   */
    public String addExistingEmployeeToBranch(String  employeeNic){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `addExistingEmployeeToBranch`(?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, employeeNic);
            stmt.setString(2, this.getBranchID());
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
     Calls the removeNormalEmployee Stored Procedure
     Input Fields : nic , BranchId
    */
    public String removeNormalEmployee(String  employeeNic){
        connection = DB.Database.getConnection();
        String result = "Error! Try again.";
        try {
            String sql = "CALL `removeNormalEmployee`(?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, employeeNic);
            stmt.setString(2, this.getBranchID());
            stmt.executeQuery();
            result = "Success";
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

}
