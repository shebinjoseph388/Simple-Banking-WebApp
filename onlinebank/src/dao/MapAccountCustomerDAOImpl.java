package dao;

import java.sql.*;

import java.util.*;
import java.math.*;

import exceptions.NotFoundException;

import models.MapAccountCustomer;






public class MapAccountCustomerDAOImpl implements MapAccountCustomerDAO{



    
    public MapAccountCustomer createValueObject() {
          return new MapAccountCustomer();
    }


    public MapAccountCustomer getObject(Connection conn, int map_id) throws NotFoundException, SQLException {

          MapAccountCustomer valueObject = createValueObject();
          valueObject.setMap_id(map_id);
          load(conn, valueObject);
          return valueObject;
    }


   
    public void load(Connection conn, MapAccountCustomer valueObject) throws NotFoundException, SQLException {

          String sql = "SELECT * FROM map_account_customer WHERE (map_id = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setInt(1, valueObject.getMap_id()); 

               singleQuery(conn, stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    public List loadAll(Connection conn) throws SQLException {

          String sql = "SELECT * FROM map_account_customer ORDER BY map_id ASC ";
          List searchResults = listQuery(conn, conn.prepareStatement(sql));

          return searchResults;
    }



    public synchronized void create(Connection conn, MapAccountCustomer valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;

          try {
               sql = "INSERT INTO map_account_customer ( map_id, account_id, customer_id) VALUES (?, ?, ?) ";
               stmt = conn.prepareStatement(sql);

               stmt.setInt(1, valueObject.getMap_id()); 
               stmt.setInt(2, valueObject.getAccount_id()); 
               stmt.setInt(3, valueObject.getCustomer_id()); 

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


 
    public void save(Connection conn, MapAccountCustomer valueObject) 
          throws NotFoundException, SQLException {

          String sql = "UPDATE map_account_customer SET account_id = ?, customer_id = ? WHERE (map_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getAccount_id()); 
              stmt.setInt(2, valueObject.getCustomer_id()); 

              stmt.setInt(3, valueObject.getMap_id()); 

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


    public void delete(Connection conn, MapAccountCustomer valueObject) 
          throws NotFoundException, SQLException {

          String sql = "DELETE FROM map_account_customer WHERE (map_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getMap_id()); 

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

          String sql = "DELETE FROM map_account_customer";
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

          String sql = "SELECT count(*) FROM map_account_customer";
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


    public List searchMatching(Connection conn, MapAccountCustomer valueObject) throws SQLException {

          List searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM map_account_customer WHERE 1=1 ");

          if (valueObject.getMap_id() != 0) {
              if (first) { first = false; }
              sql.append("AND map_id = ").append(valueObject.getMap_id()).append(" ");
          }

          if (valueObject.getAccount_id() != 0) {
              if (first) { first = false; }
              sql.append("AND account_id = ").append(valueObject.getAccount_id()).append(" ");
          }

          if (valueObject.getCustomer_id() != 0) {
              if (first) { first = false; }
              sql.append("AND customer_id = ").append(valueObject.getCustomer_id()).append(" ");
          }


          sql.append("ORDER BY map_id ASC ");

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



    public void singleQuery(Connection conn, PreparedStatement stmt, MapAccountCustomer valueObject) 
          throws NotFoundException, SQLException {

          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              if (result.next()) {

                   valueObject.setMap_id(result.getInt("map_id")); 
                   valueObject.setAccount_id(result.getInt("account_id")); 
                   valueObject.setCustomer_id(result.getInt("customer_id")); 

              } else {
                    //System.out.println("MapAccountCustomer Object Not Found!");
                    throw new NotFoundException("MapAccountCustomer Object Not Found!");
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
                   MapAccountCustomer temp = createValueObject();

                   temp.setMap_id(result.getInt("map_id")); 
                   temp.setAccount_id(result.getInt("account_id")); 
                   temp.setCustomer_id(result.getInt("customer_id")); 

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

//-------------------------------------------------------------------------
    /*public List<MapAccountCustomer> getAccountsByCustomerID(Connection conn, int customer_id){
    	
    	
    }*/
}
