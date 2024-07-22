package com.chainsys.ebfusion.model;



public class User {
  public User() {
	  
  }
	String name;
	String emailId;
	String password;
	long phoneNumber;
	long aadhaarNumber;
	String userType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public long getAadhaarNumber() {
		return aadhaarNumber;
	}
	public void setAadhaarNumber(long aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", emailId=" + emailId + ", password=" + password + ", phoneNumber=" + phoneNumber
				+ ", aadhaarNumber=" + aadhaarNumber + ", userType=" + userType + "]";
	}
	
	
	
	
}
