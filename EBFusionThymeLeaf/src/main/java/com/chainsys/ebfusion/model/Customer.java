package com.chainsys.ebfusion.model;

import java.util.Arrays;

public class Customer {

	
	
	public Customer() {
		
	}
	String emailId;
	long serviceNumber;
	String serviceType;
	String address;
	String district;
	String state;
	byte[] addressProof;
	String connectionStatus;
	String customerAddressProof;
	boolean canEnterBill;
	
	
	
	public boolean isCanEnterBill() {
		return canEnterBill;
	}
	public void setCanEnterBill(boolean canEnterBill) {
		this.canEnterBill = canEnterBill;
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
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public byte[] getAddressProof() {
		return addressProof;
	}
	public void setAddressProof(byte[] addressProof) {
		this.addressProof = addressProof;
	}
	public String getConnectionStatus() {
		return connectionStatus;
	}
	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}
	
	
	
	public String getCustomerAddressProof() {
		return customerAddressProof;
	}
	public void setCustomerAddressProof(String customerAddressProof) {
		this.customerAddressProof = customerAddressProof;
	}
	@Override
	public String toString() {
		return "Customer [emailId=" + emailId + ", serviceNumber=" + serviceNumber + ", serviceType=" + serviceType
				+ ", address=" + address + ", district=" + district + ", state=" + state + ", addressProof="
				+ Arrays.toString(addressProof) + ", connectionStatus=" + connectionStatus + ", customerAddressProof="
				+ customerAddressProof + ", canEnterBill=" + canEnterBill + "]";
	}
	
		
	
}
