package models;

import java.io.*;


import java.sql.*;
import java.util.*;
import java.math.*;




public class Customer implements Cloneable, Serializable {

	
	private int customer_id;
	private String nric;
	private String username;
	private String password;
	private String givenname;
	private String address;
	private String gender;
	private String nationality;
	private Timestamp date_of_birth;
	private Timestamp date_of_join;

	private ArrayList<Account> accounts;

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = (ArrayList<Account>) accounts;
	}

	

	public Customer() {
		accounts = new ArrayList<Account>();
	}

	public Customer(int customer_idIn) {

		this.customer_id = customer_idIn;

	}

	
	public int getCustomer_id() {
		return this.customer_id;
	}

	public void setCustomer_id(int customer_idIn) {
		this.customer_id = customer_idIn;
	}

	public String getNric() {
		return this.nric;
	}

	public void setNric(String nricIn) {
		this.nric = nricIn;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String usernameIn) {
		this.username = usernameIn;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String passwordIn) {
		this.password = passwordIn;
	}

	public String getGivenname() {
		return this.givenname;
	}

	public void setGivenname(String givennameIn) {
		this.givenname = givennameIn;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String addressIn) {
		this.address = addressIn;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String genderIn) {
		this.gender = genderIn;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationalityIn) {
		this.nationality = nationalityIn;
	}

	public Timestamp getDate_of_birth() {
		return this.date_of_birth;
	}

	public void setDate_of_birth(Timestamp date_of_birthIn) {
		this.date_of_birth = date_of_birthIn;
	}

	public Timestamp getDate_of_join() {
		return this.date_of_join;
	}

	public void setDate_of_join(Timestamp date_of_joinIn) {
		this.date_of_join = date_of_joinIn;
	}

	
	public void setAll(int customer_idIn, String nricIn, String usernameIn,
			String passwordIn, String givennameIn, String addressIn,
			String genderIn, String nationalityIn, Timestamp date_of_birthIn,
			Timestamp date_of_joinIn) {
		this.customer_id = customer_idIn;
		this.nric = nricIn;
		this.username = usernameIn;
		this.password = passwordIn;
		this.givenname = givennameIn;
		this.address = addressIn;
		this.gender = genderIn;
		this.nationality = nationalityIn;
		this.date_of_birth = date_of_birthIn;
		this.date_of_join = date_of_joinIn;
	}

	
	public boolean hasEqualMapping(Customer valueObject) {

		if (valueObject.getCustomer_id() != this.customer_id) {
			return (false);
		}
		if (this.nric == null) {
			if (valueObject.getNric() != null)
				return (false);
		} else if (!this.nric.equals(valueObject.getNric())) {
			return (false);
		}
		if (this.username == null) {
			if (valueObject.getUsername() != null)
				return (false);
		} else if (!this.username.equals(valueObject.getUsername())) {
			return (false);
		}
		if (this.password == null) {
			if (valueObject.getPassword() != null)
				return (false);
		} else if (!this.password.equals(valueObject.getPassword())) {
			return (false);
		}
		if (this.givenname == null) {
			if (valueObject.getGivenname() != null)
				return (false);
		} else if (!this.givenname.equals(valueObject.getGivenname())) {
			return (false);
		}
		if (this.address == null) {
			if (valueObject.getAddress() != null)
				return (false);
		} else if (!this.address.equals(valueObject.getAddress())) {
			return (false);
		}
		if (this.gender == null) {
			if (valueObject.getGender() != null)
				return (false);
		} else if (!this.gender.equals(valueObject.getGender())) {
			return (false);
		}
		if (this.nationality == null) {
			if (valueObject.getNationality() != null)
				return (false);
		} else if (!this.nationality.equals(valueObject.getNationality())) {
			return (false);
		}
		if (this.date_of_birth == null) {
			if (valueObject.getDate_of_birth() != null)
				return (false);
		} else if (!this.date_of_birth.equals(valueObject.getDate_of_birth())) {
			return (false);
		}
		if (this.date_of_join == null) {
			if (valueObject.getDate_of_join() != null)
				return (false);
		} else if (!this.date_of_join.equals(valueObject.getDate_of_join())) {
			return (false);
		}

		return true;
	}

	
	public String toString() {
		StringBuffer out = new StringBuffer(this.getDaogenVersion());
		out.append("\nclass Customer, mapping to table customer\n");
		out.append("Persistent attributes: \n");
		out.append("customer_id = " + this.customer_id + "\n");
		out.append("nric = " + this.nric + "\n");
		out.append("username = " + this.username + "\n");
		out.append("password = " + this.password + "\n");
		out.append("givenname = " + this.givenname + "\n");
		out.append("address = " + this.address + "\n");
		out.append("gender = " + this.gender + "\n");
		out.append("nationality = " + this.nationality + "\n");
		out.append("date_of_birth = " + this.date_of_birth + "\n");
		out.append("date_of_join = " + this.date_of_join + "\n");
		return out.toString();
	}

	
	public Object clone() {
		Customer cloned = new Customer();

		cloned.setCustomer_id(this.customer_id);
		if (this.nric != null)
			cloned.setNric(new String(this.nric));
		if (this.username != null)
			cloned.setUsername(new String(this.username));
		if (this.password != null)
			cloned.setPassword(new String(this.password));
		if (this.givenname != null)
			cloned.setGivenname(new String(this.givenname));
		if (this.address != null)
			cloned.setAddress(new String(this.address));
		if (this.gender != null)
			cloned.setGender(new String(this.gender));
		if (this.nationality != null)
			cloned.setNationality(new String(this.nationality));
		if (this.date_of_birth != null)
			cloned.setDate_of_birth((Timestamp) this.date_of_birth.clone());
		if (this.date_of_join != null)
			cloned.setDate_of_join((Timestamp) this.date_of_join.clone());
		return cloned;
	}

	
	public String getDaogenVersion() {
		return "DaoGen version 2.4.1";
	}

}
