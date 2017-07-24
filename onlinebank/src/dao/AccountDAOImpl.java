package dao;

import java.sql.*;
import java.util.*;
import java.math.*;

import javax.sql.RowSet;

import exceptions.NotFoundException;

import models.Account;


 /**
  * Account Data Access Object (DAO).
  * This class contains all database handling that is needed to 
  * permanently store and retrieve Account object instances. 
  */

 


public class AccountDAOImpl implements AccountDAO{


	public int getLastInsertedRowID(Connection conn) throws SQLException{
		String sql = "SELECT * from account ORDER BY account_id DESC LIMIT 1";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		
		while(rs.next()){
			return rs.getInt("account_id");
		}
		return 0;
	}
 
    public Account createValueObject() {
          return new Account();
    }


   
    public Account getObject(Connection conn, int account_id) throws NotFoundException, SQLException {

          Account valueObject = createValueObject();
          valueObject.setAccount_id(account_id);
          load(conn, valueObject);
          return valueObject;
    }


    
    public void load(Connection conn, Account valueObject) throws NotFoundException, SQLException {

          String sql = "SELECT * FROM account WHERE (account_id = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setInt(1, valueObject.getAccount_id()); 

               singleQuery(conn, stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    public List loadAll(Connection conn) throws SQLException {

          String sql = "SELECT * FROM account ORDER BY account_id ASC ";
          List searchResults = listQuery(conn, conn.prepareStatement(sql));

          return searchResults;
    }



    public synchronized void create(Connection conn, Account valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;

          try {
               sql = "INSERT INTO account ( account_id, pin, amount, "
               + "account_type, bank_branch_id) VALUES (?, ?, ?, ?, ?) ";
               stmt = conn.prepareStatement(sql);

               stmt.setInt(1, valueObject.getAccount_id()); 
               stmt.setInt(2, valueObject.getPin()); 
               stmt.setDouble(3, valueObject.getAmount()); 
               stmt.setInt(4, valueObject.getAccount_type()); 
               stmt.setInt(5, valueObject.getBank_branch_id()); 

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


    public void save(Connection conn, Account valueObject) 
          throws NotFoundException, SQLException {

          String sql = "UPDATE account SET pin = ?, amount = ?, account_type = ?, "
               + "bank_branch_id = ? WHERE (account_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getPin()); 
              stmt.setDouble(2, valueObject.getAmount()); 
              stmt.setInt(3, valueObject.getAccount_type()); 
              stmt.setInt(4, valueObject.getBank_branch_id()); 

              stmt.setInt(5, valueObject.getAccount_id()); 

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


    
    public void delete(Connection conn, Account valueObject) 
          throws NotFoundException, SQLException {

          String sql = "DELETE FROM account WHERE (account_id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getAccount_id()); 

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

          String sql = "DELETE FROM account";
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

          String sql = "SELECT count(*) FROM account";
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


    
    public List searchMatching(Connection conn, Account valueObject) throws SQLException {

          List searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM account WHERE 1=1 ");

          if (valueObject.getAccount_id() != 0) {
              if (first) { first = false; }
              sql.append("AND account_id = ").append(valueObject.getAccount_id()).append(" ");
          }

          if (valueObject.getPin() != 0) {
              if (first) { first = false; }
              sql.append("AND pin = ").append(valueObject.getPin()).append(" ");
          }

          if (valueObject.getAmount() != 0) {
              if (first) { first = false; }
              sql.append("AND amount = ").append(valueObject.getAmount()).append(" ");
          }

          if (valueObject.getAccount_type() != 0) {
              if (first) { first = false; }
              sql.append("AND account_type = ").append(valueObject.getAccount_type()).append(" ");
          }

          if (valueObject.getBank_branch_id() != 0) {
              if (first) { first = false; }
              sql.append("AND bank_branch_id = ").append(valueObject.getBank_branch_id()).append(" ");
          }


          sql.append("ORDER BY account_id ASC ");

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



    public void singleQuery(Connection conn, PreparedStatement stmt, Account valueObject) 
          throws NotFoundException, SQLException {

          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              if (result.next()) {

                   valueObject.setAccount_id(result.getInt("account_id")); 
                   valueObject.setPin(result.getInt("pin")); 
                   valueObject.setAmount(result.getLong("amount")); 
                   valueObject.setAccount_type(result.getInt("account_type")); 
                   valueObject.setBank_branch_id(result.getInt("bank_branch_id")); 

              } else {
                    //System.out.println("Account Object Not Found!");
                    throw new NotFoundException("Account Object Not Found!");
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
                   Account temp = createValueObject();

                   temp.setAccount_id(result.getInt("account_id")); 
                   temp.setPin(result.getInt("pin")); 
                   temp.setAmount(result.getLong("amount")); 
                   temp.setAccount_type(result.getInt("account_type")); 
                   temp.setBank_branch_id(result.getInt("bank_branch_id")); 

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