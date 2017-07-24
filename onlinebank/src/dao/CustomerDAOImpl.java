package dao;

import java.sql.*;


import java.util.*;
import java.math.*;

import exceptions.NotFoundException;

import models.Customer;


 /**
  * Customer Data Access Object (DAO).
  * This class contains all database handling that is needed to 
  * permanently store and retrieve Customer object instances. 
  */




public class CustomerDAOImpl implements CustomerDAO{



    public Customer createValueObject() {
          return new Customer();
    }


   
    public Customer getObject(Connection conn, int customer_id) throws NotFoundException, SQLException {

          Customer valueObject = createValueObject();
          valueObject.setCustomer_id(customer_id);
          load(conn, valueObject);
          return valueObject;
    }


    
    public void load(Connection conn, Customer valueObject) throws NotFoundException, SQLException {

          String sql = "SELECT * FROM customer WHERE (customer_id = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setInt(1, valueObject.getCustomer_id()); 

               singleQuery(conn, stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


   
    public List loadAll(Connection conn) throws SQLException {

          String sql = "SELECT * FROM customer ORDER BY customer_id ASC ";
          List searchResults = listQuery(conn, conn.prepareStatement(sql));

          return searchResults;
    }



   
    public synchronized void create(Connection conn, Customer valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;

          try {
               sql = "INSERT INTO customer ( customer_id, nric, username, "
               + "password, givenname, address, "
               + "gender, nationality, date_of_birth, "
               + "date_of_join) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
               stmt = conn.prepareStatement(sql);

               stmt.setInt(1, valueObject.getCustomer_id()); 
               stmt.setString(2, valueObject.getNric()); 
               stmt.setString(3, valueObject.getUsername()); 
               stmt.setString(4, valueObject.getPassword()); 
               stmt.setString(5, valueObject.getGivenname()); 
               stmt.setString(6, valueObject.getAddress()); 
               stmt.setString(7, valueObject.getGender()); 
               stmt.setString(8, valueObject.getNationality()); 
               stmt.setTimestamp(9, valueObject.getDate_of_birth()); 
               stmt.setTimestamp(10, valueObject.getDate_of_join()); 

               int rowcount = databaseUpdate(conn, stmt);
               if (rowcount != 1) {
                    //System.out.println("PrimaryKey Error when updating DB!");
                    throw new SQLException("PrimaryKey Error when updating DB!");
               }

          } finally {
              if (stmt != null)
                  stmt.close();
          }


    }


    
    public void save(Connection conn, Customer valueObject) 
          throws NotFoundException, SQLException {

          String sql = "UPDATE customer SET nric = ?, username = ?, password = ?, "
               + "givenname = ?, address = ?, gender = ?, "
               + "nationality = ?, date_of_birth = ?, date_of_join = ? WHERE (customer_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setString(1, valueObject.getNric()); 
              stmt.setString(2, valueObject.getUsername()); 
              stmt.setString(3, valueObject.getPassword()); 
              stmt.setString(4, valueObject.getGivenname()); 
              stmt.setString(5, valueObject.getAddress()); 
              stmt.setString(6, valueObject.getGender()); 
              stmt.setString(7, valueObject.getNationality()); 
              stmt.setTimestamp(8, valueObject.getDate_of_birth()); 
              stmt.setTimestamp(9, valueObject.getDate_of_join()); 

              stmt.setInt(10, valueObject.getCustomer_id()); 

              int rowcount = databaseUpdate(conn, stmt);
              if (rowcount == 0) {
                   //System.out.println("Object could not be saved! (PrimaryKey not found)");
                   throw new NotFoundException("Object could not be saved! (PrimaryKey not found)");
              }
              if (rowcount > 1) {
                   //System.out.println("PrimaryKey Error when updating DB! (Many objects were affected!)");
                   throw new SQLException("PrimaryKey Error when updating DB! (Many objects were affected!)");
              }
          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    
    public void delete(Connection conn, Customer valueObject) 
          throws NotFoundException, SQLException {

          String sql = "DELETE FROM customer WHERE (customer_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getCustomer_id()); 

              int rowcount = databaseUpdate(conn, stmt);
              if (rowcount == 0) {
                   //System.out.println("Object could not be deleted (PrimaryKey not found)");
                   throw new NotFoundException("Object could not be deleted! (PrimaryKey not found)");
              }
              if (rowcount > 1) {
                   //System.out.println("PrimaryKey Error when updating DB! (Many objects were deleted!)");
                   throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");
              }
          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


   
    public void deleteAll(Connection conn) throws SQLException {

          String sql = "DELETE FROM customer";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              int rowcount = databaseUpdate(conn, stmt);
          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


   
    public int countAll(Connection conn) throws SQLException {

          String sql = "SELECT count(*) FROM customer";
          PreparedStatement stmt = null;
          ResultSet result = null;
          int allRows = 0;

          try {
              stmt = conn.prepareStatement(sql);
              result = stmt.executeQuery();

              if (result.next())
                  allRows = result.getInt(1);
          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }
          return allRows;
    }


    
    public List searchMatching(Connection conn, Customer valueObject) throws SQLException {

          List searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM customer WHERE 1=1 ");

          if (valueObject.getCustomer_id() != 0) {
              if (first) { first = false; }
              sql.append("AND customer_id = ").append(valueObject.getCustomer_id()).append(" ");
          }

          if (valueObject.getNric() != null) {
              if (first) { first = false; }
              sql.append("AND nric LIKE '").append(valueObject.getNric()).append("%' ");
          }

          if (valueObject.getUsername() != null) {
              if (first) { first = false; }
              sql.append("AND username LIKE '").append(valueObject.getUsername()).append("%' ");
          }

          if (valueObject.getPassword() != null) {
              if (first) { first = false; }
              sql.append("AND password LIKE '").append(valueObject.getPassword()).append("%' ");
          }

          if (valueObject.getGivenname() != null) {
              if (first) { first = false; }
              sql.append("AND givenname LIKE '").append(valueObject.getGivenname()).append("%' ");
          }

          if (valueObject.getAddress() != null) {
              if (first) { first = false; }
              sql.append("AND address LIKE '").append(valueObject.getAddress()).append("%' ");
          }

          if (valueObject.getGender() != null) {
              if (first) { first = false; }
              sql.append("AND gender LIKE '").append(valueObject.getGender()).append("%' ");
          }

          if (valueObject.getNationality() != null) {
              if (first) { first = false; }
              sql.append("AND nationality LIKE '").append(valueObject.getNationality()).append("%' ");
          }

          if (valueObject.getDate_of_birth() != null) {
              if (first) { first = false; }
              sql.append("AND date_of_birth = '").append(valueObject.getDate_of_birth()).append("' ");
          }

          if (valueObject.getDate_of_join() != null) {
              if (first) { first = false; }
              sql.append("AND date_of_join = '").append(valueObject.getDate_of_join()).append("' ");
          }


          sql.append("ORDER BY customer_id ASC ");

          // Prevent accidential full table results.
          // Use loadAll if all rows must be returned.
          if (first)
               searchResults = new ArrayList();
          else
               searchResults = listQuery(conn, conn.prepareStatement(sql.toString()));

          return searchResults;
    }


    
    public String getDaogenVersion() {
        return "DaoGen version 2.4.1";
    }


   
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {

          int result = stmt.executeUpdate();

          return result;
    }



    public void singleQuery(Connection conn, PreparedStatement stmt, Customer valueObject) 
          throws NotFoundException, SQLException {

          ResultSet result = null;

          try {
              result = stmt.executeQuery();
              
              if (result.next()) {
                   valueObject.setCustomer_id(result.getInt("customer_id"));
                   valueObject.setNric(result.getString("nric")); 
                   valueObject.setUsername(result.getString("username")); 
                   valueObject.setPassword(result.getString("password")); 
                   valueObject.setGivenname(result.getString("givenname")); 
                   valueObject.setAddress(result.getString("address")); 
                   valueObject.setGender(result.getString("gender")); 
                   valueObject.setNationality(result.getString("nationality")); 
                   valueObject.setDate_of_birth(result.getTimestamp("date_of_birth")); 
                   valueObject.setDate_of_join(result.getTimestamp("date_of_join")); 
                   
              } else {
                    //System.out.println("Customer Object Not Found!");
                    throw new NotFoundException("Customer Object Not Found!");
              }
          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }
    }


   
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {

          ArrayList searchResults = new ArrayList();
          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              while (result.next()) {
                   Customer temp = createValueObject();

                   temp.setCustomer_id(result.getInt("customer_id")); 
                   temp.setNric(result.getString("nric")); 
                   temp.setUsername(result.getString("username")); 
                   temp.setPassword(result.getString("password")); 
                   temp.setGivenname(result.getString("givenname")); 
                   temp.setAddress(result.getString("address")); 
                   temp.setGender(result.getString("gender")); 
                   temp.setNationality(result.getString("nationality")); 
                   temp.setDate_of_birth(result.getTimestamp("date_of_birth")); 
                   temp.setDate_of_join(result.getTimestamp("date_of_join")); 

                   searchResults.add(temp);
              }

          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }

          return (List)searchResults;
    }


}

