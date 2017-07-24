package dao;

import java.sql.*;
import java.util.*;
import java.math.*;

import exceptions.NotFoundException;

import models.Transaction;


 /**
  * Transaction Data Access Object (DAO).
  * This class contains all database handling that is needed to 
  * permanently store and retrieve Transaction object instances. 
  */

 


public class TransactionDAOImpl implements TransactionDAO{



   
    public Transaction createValueObject() {
          return new Transaction();
    }


    public Transaction getObject(Connection conn, int transaction_id) throws NotFoundException, SQLException {

          Transaction valueObject = createValueObject();
          valueObject.setTransaction_id(transaction_id);
          load(conn, valueObject);
          return valueObject;
    }


   
    public void load(Connection conn, Transaction valueObject) throws NotFoundException, SQLException {

          String sql = "SELECT * FROM transaction WHERE (transaction_id = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setInt(1, valueObject.getTransaction_id()); 

               singleQuery(conn, stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    
    public List loadAll(Connection conn) throws SQLException {

          String sql = "SELECT * FROM transaction ORDER BY transaction_id ASC ";
          List searchResults = listQuery(conn, conn.prepareStatement(sql));

          return searchResults;
    }



   
    public synchronized void create(Connection conn, Transaction valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;

          try {
               sql = "INSERT INTO transaction ( transaction_id, customer_id_by, account_id, "
               + "account_id_to, transaction_type, transaction_amount, "
               + "transaction_time) VALUES (?, ?, ?, ?, ?, ?, ?) ";
               stmt = conn.prepareStatement(sql);

               stmt.setInt(1, valueObject.getTransaction_id()); 
               stmt.setInt(2, valueObject.getCustomer_id_by()); 
               stmt.setInt(3, valueObject.getAccount_id()); 
               stmt.setInt(4, valueObject.getAccount_id_to()); 
               stmt.setInt(5, valueObject.getTransaction_type()); 
               stmt.setDouble(6, valueObject.getTransaction_amount()); 
               stmt.setTimestamp(7, valueObject.getTransaction_time()); 

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


    public void save(Connection conn, Transaction valueObject) 
          throws NotFoundException, SQLException {

          String sql = "UPDATE transaction SET customer_id_by = ?, account_id = ?, account_id_to = ?, "
               + "transaction_type = ?, transaction_amount = ?, transaction_time = ? WHERE (transaction_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getCustomer_id_by()); 
              stmt.setInt(2, valueObject.getAccount_id()); 
              stmt.setInt(3, valueObject.getAccount_id_to()); 
              stmt.setInt(4, valueObject.getTransaction_type()); 
              stmt.setDouble(5, valueObject.getTransaction_amount()); 
              stmt.setTimestamp(6, valueObject.getTransaction_time()); 

              stmt.setInt(7, valueObject.getTransaction_id()); 

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


    public void delete(Connection conn, Transaction valueObject) 
          throws NotFoundException, SQLException {

          String sql = "DELETE FROM transaction WHERE (transaction_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getTransaction_id()); 

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

          String sql = "DELETE FROM transaction";
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

          String sql = "SELECT count(*) FROM transaction";
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


    public List searchMatching(Connection conn, Transaction valueObject) throws SQLException {

          List searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM transaction WHERE 1=1 ");

          if (valueObject.getTransaction_id() != 0) {
              if (first) { first = false; }
              sql.append("AND transaction_id = ").append(valueObject.getTransaction_id()).append(" ");
          }

          if (valueObject.getCustomer_id_by() != 0) {
              if (first) { first = false; }
              sql.append("AND customer_id_by = ").append(valueObject.getCustomer_id_by()).append(" ");
          }

          if (valueObject.getAccount_id() != 0) {
              if (first) { first = false; }
              sql.append("AND account_id = ").append(valueObject.getAccount_id()).append(" ");
          }

          if (valueObject.getAccount_id_to() != 0) {
              if (first) { first = false; }
              sql.append("AND account_id_to = ").append(valueObject.getAccount_id_to()).append(" ");
          }

          if (valueObject.getTransaction_type() != 0) {
              if (first) { first = false; }
              sql.append("AND transaction_type = ").append(valueObject.getTransaction_type()).append(" ");
          }

          if (valueObject.getTransaction_amount() != 0) {
              if (first) { first = false; }
              sql.append("AND transaction_amount = ").append(valueObject.getTransaction_amount()).append(" ");
          }

          if (valueObject.getTransaction_time() != null) {
              if (first) { first = false; }
              sql.append("AND transaction_time = '").append(valueObject.getTransaction_time()).append("' ");
          }


          sql.append("ORDER BY transaction_id ASC ");

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



   
    public void singleQuery(Connection conn, PreparedStatement stmt, Transaction valueObject) 
          throws NotFoundException, SQLException {

          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              if (result.next()) {

                   valueObject.setTransaction_id(result.getInt("transaction_id")); 
                   valueObject.setCustomer_id_by(result.getInt("customer_id_by")); 
                   valueObject.setAccount_id(result.getInt("account_id")); 
                   valueObject.setAccount_id_to(result.getInt("account_id_to")); 
                   valueObject.setTransaction_type(result.getInt("transaction_type")); 
                   valueObject.setTransaction_amount(result.getDouble("transaction_amount")); 
                   valueObject.setTransaction_time(result.getTimestamp("transaction_time")); 

              } else {
                    //System.out.println("Transaction Object Not Found!");
                    throw new NotFoundException("Transaction Object Not Found!");
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
                   Transaction temp = createValueObject();

                   temp.setTransaction_id(result.getInt("transaction_id")); 
                   temp.setCustomer_id_by(result.getInt("customer_id_by")); 
                   temp.setAccount_id(result.getInt("account_id")); 
                   temp.setAccount_id_to(result.getInt("account_id_to")); 
                   temp.setTransaction_type(result.getInt("transaction_type")); 
                   temp.setTransaction_amount(result.getDouble("transaction_amount")); 
                   temp.setTransaction_time(result.getTimestamp("transaction_time")); 

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

