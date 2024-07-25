package com.chainsys.ebfusion.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.chainsys.ebfusion.model.Bill;
import com.chainsys.ebfusion.model.Complaint;
import com.chainsys.ebfusion.model.Customer;
import com.chainsys.ebfusion.model.Payment;
import com.chainsys.ebfusion.model.User;




@Repository
public interface UserDAO {
	public void saveDetails(User user);
    public String getPassword(String emailId);
	public String getUserEmailId(String emailId);
	public List<User> listUsers();
	public List<User> getUser(String email);
	public List<User> getAdmin(String emailID);
	public void update(User user);
	public void delete(User user);
	public List<User> searchUser(String emailId);
	
	public void applyConnection(Customer customer);
	public List<Customer> readApplyConnection(String email);
	public List<Customer> readAllApplyConnection();
	public List<Customer> readApprovedConnection(String email);
	public void adminApproveConnection(Customer customer);
	public List<Customer> allApprovedConnection();
	
	public void enterBill(Bill bill);
	public List<Bill> viewBill();
	public List<Bill> readBill(String email);
	List<Bill> readPaidBill(String email);
	public void updateReadingUnit();
	
	public void payAmount(Payment payment);
	List<Payment> checkPayment(String email, long serviceNumber, String paymentDate,double amount);
	public List<Payment> checkPaymentAll(String email);
	/* public List<Payment> checkPayment(String email); */
	public List<Payment> viewPayment();
	void updatePaidStatus(long serviceNumber);
	
	public void applyComplaint(Complaint complaint);
	public List<Complaint> viewComplaint(String email);
	public List<Complaint> viewPendingComplaint();
	public void updateComplaint(String complaintStatus, int complaintId);
	List<Complaint> rectifiedComplaint(String email);
	public List<Complaint> adminRectifiedComplaint();
	
	public List<Customer> searchConnection(String emailId);
	public List<Bill> searchUnpaid(String emailId);
	
	
	String readReadingTakenDate(String email, Long service);
	public List<Payment> searchPaid(String emailId);
	public List<Complaint> searchPending(String emailId);
	List<Complaint> searchRectified(String emailId);
	public List<Complaint> getComplaintById(int complaintId);
	
	public void adminUpdateUserDetails(String name, long phoneNumber, long aadhaarNumber, String emailId);
	public List<Customer> getImage( String email,long serviceNumber);
	
	
	
	


}
