package Models;

import DB.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Objects.Employee;


public class EmployeeModel {
        private Connection connection;
        private String  employeeId;
        private String branchId;

        public boolean login(String[] params) throws SQLException {
            connection = Database.getConnection();
            try{
                String sql = "SELECT EmployeeID FROM users WHERE UserName = ? AND Password = ? AND Status = 1";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, params[0]);
                stmt.setString(2, params[1]);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    employeeId = rs.getString("EmployeeID");
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
                String sql = "SELECT BranchID FROM employeebranch WHERE EmployeeID= ? AND Leaved is null";
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

        public void setEmployeeID(String employeeId){
            this.employeeId=employeeId;
        }

        public boolean addNewEmployee(Employee employee) throws SQLException{
        connection = Database.getConnection();
        String empID = null;
        try {
            String sql = "INSERT INTO employee VALUES (UUID(), ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getNic());
            stmt.setString(4,employee.getEmail());
            stmt.setString(5,employee.getDateOfBirth().toString());
            int result = stmt.executeUpdate(); //returns a integer
            if(result>0) {
                String sql_1 = "SELECT EmployeeID FROM employee WHERE Nic = ? ";
                PreparedStatement statement_1 = connection.prepareStatement(sql_1);
                statement_1.setString(1, employee.getNic());
                ResultSet rs = statement_1.executeQuery();
                while (rs.next()) {
                    empID = rs.getString("EmployeeID");
                }
                if(empID!= null){
                    return addEmployeeToLogInTable(empID, employee.getNic()) && employeeSetToBranch(empID);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

        private boolean addEmployeeToLogInTable(String empID, String nic){
            connection = Database.getConnection();
            try{
                String sql = "INSERT INTO users VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, empID);
                stmt.setString(2,nic);
                stmt.setString(3,"SLBC");
                stmt.setString(4,"1");
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

        public boolean isUniqueUsername(String username){
            connection = Database.getConnection();
            try {
                String sql = "SELECT UserName FROM users WHERE UserName = ?";
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
                String sql = "UPDATE users  SET UserName = ? WHERE EmployeeID =? ";
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

        public boolean employeeSetToBranch(String empID){
            connection = Database.getConnection();
            try{
                String sql = "INSERT INTO employeebranch (`BranchID`, `EmployeeID`, `JoinedDate`) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, getBranchID());
                stmt.setString(2,empID);
                stmt.setString(3, LocalDate.now().toString());
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
                String sql = "SELECT Password FROM users WHERE EmployeeID = ?";
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
                String sql = "UPDATE users  SET Password = ? WHERE EmployeeID =? ";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, password);
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


