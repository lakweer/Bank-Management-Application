package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Objects.IndividualCustomer;
import Objects.OrganizationCustomer;
import DB.Database;

public class CustomerModel {
    private Connection connection;

    public boolean addIndividualCustomer(IndividualCustomer customer){
        connection = Database.getConnection();
        String customerID = "";
        try{
            connection.setAutoCommit(false);
            String customerSQL = " INSERT INTO `customer` VALUES (uuid(),?,?)";
            PreparedStatement stmt1 = connection.prepareStatement(customerSQL);
            stmt1.setString(1,customer.getCustomerType());
            stmt1.setString(2,customer.getEmail());
            int rs1 = stmt1.executeUpdate();
            if(rs1 >0){
                customerID = getCustomerId(connection, customer.getCustomerType(), customer.getEmail());
                if(!customerID.equals("")){
                    String individualSQL = "INSERT INTO `individual` VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement stmt2= connection.prepareStatement(individualSQL);
                    stmt2.setString(1,customerID);
                    stmt2.setString(2,customer.getNic());
                    stmt2.setString(3,customer.getFirstName());
                    stmt2.setString(4,customer.getLastName());
                    stmt2.setString(5,customer.getHouseNumber());
                    stmt2.setString(6,customer.getStreetOne());
                    stmt2.setString(7,customer.getStreetTwo());
                    stmt2.setString(8,customer.getTown());
                    stmt2.setString(9,customer.getDistrict());
                    stmt2.setString(10,customer.getPostalCode());
                    stmt2.setString(11,customer.getGender());
                    stmt2.setString(12,customer.getDob().toString());
                    int rs2 = stmt2.executeUpdate();
                    if(rs2 >0){
                        connection.commit();
                        return true;
                    }else{
                       connection.rollback();
                       return false;
                    }
                }else{
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    private String getIndividualCustomerId(String nic){
        connection = Database.getConnection();
        String id = "";
        try{
            String sql = "SELECT `CustomerId` FROM `individual` WHERE `Nic`=? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,nic);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                id = rs.getString("CustomerId");
            }
            connection.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }

    private String getCustomerId(Connection connection, String type, String email){
        String id = "";
        try{
            connection.setAutoCommit(false);
            String sql = "SELECT `CustomerId` FROM `customer` WHERE `CustomerType`=? AND `Email`=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,type);
            stmt.setString(2,email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                id = rs.getString("CustomerId");
                return id;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }

    public boolean addOrganizationCustomer(OrganizationCustomer customer){
        connection = Database.getConnection();
        String customerID = "";
        try{
            connection.setAutoCommit(false);
            String customerSQL = " INSERT INTO `customer` VALUES (uuid(),?,?)";
            PreparedStatement stmt1 = connection.prepareStatement(customerSQL);
            stmt1.setString(1,customer.getCustomerType());
            stmt1.setString(2,customer.getEmail());
            int rs1 = stmt1.executeUpdate();
            if(rs1 >0){
                customerID = getCustomerId(connection, customer.getCustomerType(), customer.getEmail());
                if(!customerID.equals("")){
                    String individualSQL = "INSERT INTO `organization` VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement stmt2= connection.prepareStatement(individualSQL);
                    stmt2.setString(1,customerID);
                    stmt2.setString(2,customer.getOrganizationName());
                    stmt2.setString(3,customer.getAuthorizedPerson());
                    stmt2.setString(4,customer.getRegisterNumber());
                    stmt2.setString(5,customer.getBuildingNumber());
                    stmt2.setString(6,customer.getStreetOne());
                    stmt2.setString(7,customer.getStreetTwo());
                    stmt2.setString(8,customer.getTown());
                    stmt2.setString(9,customer.getPostalCode());
                    stmt2.setString(10,customer.getTelephoneNumber());
                    stmt2.setString(11,customer.getAuthorizedNicNumber());
                    int rs2 = stmt2.executeUpdate();
                    if(rs2 >0){
                        connection.commit();
                        return true;
                    }else{
                        connection.rollback();
                        return false;
                    }
                }else{
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
