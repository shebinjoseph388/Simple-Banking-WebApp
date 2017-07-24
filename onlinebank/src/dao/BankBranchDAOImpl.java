package dao;

import java.sql.*;
import java.util.*;
import java.math.*;

import exceptions.NotFoundException;

import models.BankBranch;


 /**
  * BankBranch Data Access Object (DAO).
  * This class contains all database handling that is needed to 
  * permanently store and retrieve BankBranch object instances. 
  */

 



public class BankBranchDAOImpl implements BankBranchDAO{



   
    public BankBranch createValueObject() {
          return new BankBranch();
    }


   
    public BankBranch getObject(Connection conn, int bank_branch_id) throws NotFoundException, SQLException {

          BankBranch valueObject = createValueObject();
          valueObject.setBank_branch_id(bank_branch_id);
          load(conn, valueObject);
          return valueObject;
    }


    
    public void load(Connection conn, BankBranch valueObject) throws NotFoundException, SQLException {

          String sql = "SELECT * FROM bank_branch WHERE (bank_branch_id = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setInt(1, valueObject.getBank_branch_id()); 

               singleQuery(conn, stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    
    public List loadAll(Connection conn) throws SQLException {

          String sql = "SELECT * FROM bank_branch ORDER BY bank_branch_id ASC ";
          List searchResults = listQuery(conn, conn.prepareStatement(sql));

          return searchResults;
    }



    public synchronized void create(Connection conn, BankBranch valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;

          try {
               sql = "INSERT INTO bank_branch ( bank_branch_id, name, location, "
               + "description) VALUES (?, ?, ?, ?) ";
               stmt = conn.prepareStatement(sql);

               stmt.setInt(1, valueObject.getBank_branch_id()); 
               stmt.setString(2, valueObject.getName()); 
               stmt.setString(3, valueObject.getLocation()); 
               stmt.setString(4, valueObject.getDescription()); 

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


    
    public void save(Connection conn, BankBranch valueObject) 
          throws NotFoundException, SQLException {

          String sql = "UPDATE bank_branch SET name = ?, location = ?, description = ? WHERE (bank_branch_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setString(1, valueObject.getName()); 
              stmt.setString(2, valueObject.getLocation()); 
              stmt.setString(3, valueObject.getDescription()); 

              stmt.setInt(4, valueObject.getBank_branch_id()); 

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


    public void delete(Connection conn, BankBranch valueObject) 
          throws NotFoundException, SQLException {

          String sql = "DELETE FROM bank_branch WHERE (bank_branch_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getBank_branch_id()); 

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

          String sql = "DELETE FROM bank_branch";
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

          String sql = "SELECT count(*) FROM bank_branch";
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


    
    public List searchMatching(Connection conn, BankBranch valueObject) throws SQLException {

          List searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM bank_branch WHERE 1=1 ");

          if (valueObject.getBank_branch_id() != 0) {
              if (first) { first = false; }
              sql.append("AND bank_branch_id = ").append(valueObject.getBank_branch_id()).append(" ");
          }

          if (valueObject.getName() != null) {
              if (first) { first = false; }
              sql.append("AND name LIKE '").append(valueObject.getName()).append("%' ");
          }

          if (valueObject.getLocation() != null) {
              if (first) { first = false; }
              sql.append("AND location LIKE '").append(valueObject.getLocation()).append("%' ");
          }

          if (valueObject.getDescription() != null) {
              if (first) { first = false; }
              sql.append("AND description LIKE '").append(valueObject.getDescription()).append("%' ");
          }


          sql.append("ORDER BY bank_branch_id ASC ");

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



    
    public void singleQuery(Connection conn, PreparedStatement stmt, BankBranch valueObject) 
          throws NotFoundException, SQLException {

          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              if (result.next()) {

                   valueObject.setBank_branch_id(result.getInt("bank_branch_id")); 
                   valueObject.setName(result.getString("name")); 
                   valueObject.setLocation(result.getString("location")); 
                   valueObject.setDescription(result.getString("description")); 

              } else {
                    //System.out.println("BankBranch Object Not Found!");
                    throw new NotFoundException("BankBranch Object Not Found!");
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
                   BankBranch temp = createValueObject();

                   temp.setBank_branch_id(result.getInt("bank_branch_id")); 
                   temp.setName(result.getString("name")); 
                   temp.setLocation(result.getString("location")); 
                   temp.setDescription(result.getString("description")); 

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

