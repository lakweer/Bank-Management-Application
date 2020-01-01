package Models;

import DB.Database;
import Hashing.GFG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {
        private Connection connection;
        private String  employeeId;
        private String branchId;
        private String employeeType;

        public boolean login(String[] params) throws SQLException {
            connection = Database.getConnection();
            try{
                String sql = "SELECT `EmployeeID`, `Type` FROM employee_login WHERE UserName = ? AND Password = ? AND Status = 1";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, params[0]);
                stmt.setString(2, params[1]);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    employeeId = rs.getString("EmployeeId");
                    employeeType = rs.getString("Type");
                }
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            if(this.getEmployeeID() != null){
                return true;
            }
            return false;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchID(){
            connection = Database.getConnection();
            try{
                String sql = "SELECT BranchId FROM employee WHERE EmployeeId= ? ";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1,getEmployeeID());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    branchId = rs.getString("BranchID");
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
            return branchId;
        }

        public String getEmployeeID(){
            return this.employeeId;
        }

        public String getEmployeeType(){
            return  this.employeeType;
        }

        public void setEmployeeID(String employeeId){
            this.employeeId=employeeId;
        }

        public boolean isUniqueUsername(String username){
            connection = Database.getConnection();
            try {
                String sql = "SELECT UserName FROM employee_login WHERE UserName = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()==false){
                    return true;
                }
                return false;
            }catch (SQLException ex){
                ex.printStackTrace();
            }
            return false;
        }

        public boolean updateEmployeeUsername(String username){
            connection = Database.getConnection();
            try{
                String sql = "UPDATE employee_login  SET UserName = ? WHERE EmployeeId =? ";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2,employeeId);
                int result = stmt.executeUpdate();
                if(result>0){
                    return true;
                }else{
                    return false;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }


        public String getEmployeePassword(){
            connection = Database.getConnection();
            try{
                String sql = "SELECT Password FROM employee_login WHERE EmployeeId = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, employeeId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    return rs.getString("Password");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            return "";
        }

        public boolean updateEmployeePassword(String password){
            connection = Database.getConnection();
            try{
                String sql = "UPDATE employee_login  SET Password = ? WHERE EmployeeId =? ";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, GFG.encryptThisString(password));
                stmt.setString(2,employeeId);
                int result = stmt.executeUpdate();
                if(result>0){
                    return true;
                }else{
                    return false;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }

    }


