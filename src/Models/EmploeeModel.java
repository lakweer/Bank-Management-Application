package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.Database;
import Objects.Employee;



public class EmploeeModel {

    private Connection connection;
    private String  employee_id;

    public boolean login(String[] params) throws SQLException {
        connection = Database.getConnection();

        try{
            String sql = "SELECT employee_id FROM employee_users WHERE username = ? AND password = ? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, params[0]);
            stmt.setString(2, params[1]);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                employee_id = rs.getString("employee_id");
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

    public String getEmployeeID(){
        return this.employee_id;
    }

    public boolean addNewEmployee(Employee employee) throws SQLException{
        connection = Database.getConnection();
        try {
            String sql = "INSERT INTO employee VALUES (UUID(), ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, employee.getFirst_name());
            stmt.setString(2, employee.getLast_name());
            stmt.setString(3, employee.get_nic());
            int result = stmt.executeUpdate();
            if(result>0) {
                String sql_1 = "SELECT employee_id FROM employee WHERE nic = ? ";
                PreparedStatement statement_1 = connection.prepareStatement(sql_1);
                statement_1.setString(1, employee.get_nic());
                ResultSet rs = statement_1.executeQuery();
                while (rs.next()){
                    this.employee_id = rs.getString("employee_id");
                }

                if(this.getEmployeeID() != null){
                    return addEmployeeToLogInTable(employee_id, employee);
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

    private boolean addEmployeeToLogInTable(String emp_id, Employee employee) throws SQLException{
        connection = Database.getConnection();
        try{
            String sql = "INSERT INTO employee_users VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, emp_id);
            stmt.setString(2,employee.getUsername());
            stmt.setString(3, employee.getPassword());
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
