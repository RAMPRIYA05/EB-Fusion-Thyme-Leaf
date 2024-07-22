package com.chainsys.ebfusion.model;

public class Bill {

	public Bill() {
		
	}
	int id;
	String emailId;
	long serviceNumber;
	String serviceType;
	String address;
	double readingUnits;
    String readingTakenDate;
    String dueDate;
    double amount;
    String billStatus;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	public long getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(long serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getReadingUnits() {
		return readingUnits;
	}
	public void setReadingUnits(double readingUnits) {
		this.readingUnits = readingUnits;
	}
	public String getReadingTakenDate() {
		return readingTakenDate;
	}
	public void setReadingTakenDate(String readingTakenDate) {
		this.readingTakenDate = readingTakenDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	@Override
	public String toString() {
		return "Bill [id=" + id + ", emailId=" + emailId + ", serviceNumber=" + serviceNumber + ", serviceType="
				+ serviceType + ", address=" + address + ", readingUnits=" + readingUnits + ", readingTakenDate="
				+ readingTakenDate + ", dueDate=" + dueDate + ", amount=" + amount + ", billStatus=" + billStatus + "]";
	}
	
	
}
