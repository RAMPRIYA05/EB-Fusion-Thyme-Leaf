package com.chainsys.ebfusion.dao;

import java.sql.Blob;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.ebfusion.mapper.BillMapper;
import com.chainsys.ebfusion.mapper.ComplaintMapper;
import com.chainsys.ebfusion.mapper.CustomerMapper;
import com.chainsys.ebfusion.mapper.ImageMapper;
import com.chainsys.ebfusion.mapper.PaymentMapper;
import com.chainsys.ebfusion.mapper.UserMapper;
import com.chainsys.ebfusion.model.Bill;
import com.chainsys.ebfusion.model.Complaint;
import com.chainsys.ebfusion.model.Customer;
import com.chainsys.ebfusion.model.Payment;
import com.chainsys.ebfusion.model.User;

@Repository
public class UserImpl implements UserDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	UserMapper mapper;
	CustomerMapper customerMapper;
	BillMapper billMapper;
	PaymentMapper paymentMapper;
	ComplaintMapper complaintMapper;
	ImageMapper imageMapper;

	@Override
	public void saveDetails(User user) {		
		String insert="insert into user(name,email_id,password,phone_number,aadhaar_number,user_type)values(?,?,?,?,?,?)";
		 Object[] params= {user.getName(),user.getEmailId(),user.getPassword(),user.getPhoneNumber(),user.getAadhaarNumber(),user.getUserType()};
  	   jdbcTemplate.update(insert,params);   	
	}

	@Override
	public String getPassword(String emailId) {
		String adminPassword = "select password from user where email_id=? and delete_user=0";
		String password = jdbcTemplate.queryForObject(adminPassword, String.class, emailId);
		return password;
	}

	@Override
	public String getUserEmailId(String emailId) {
		String userEmailId = "select email_id from user where email_id=? and (user_type='User') and delete_user=0";
		String email = jdbcTemplate.queryForObject(userEmailId, String.class, emailId);
		return email;

	}

	@Override
	public List<User> listUsers() {
		String read = "Select name,email_id,password,phone_number,aadhaar_number from user where delete_user=0 and (user_type='User')";
		List<User> list = jdbcTemplate.query(read, new UserMapper());
		return list;
	}

	@Override
	public List<User> getUser(String email) {
		String read = "Select name,email_id,password,phone_number,aadhaar_number from user where email_id=? and delete_user=0 and user_type='User'";
		List<User> list = jdbcTemplate.query(read, new UserMapper(), email);
		return list;
	}

	@Override
	public List<User> getAdmin(String emailID) {
		String read = "Select name,email_id,password,phone_number,aadhaar_number from user where email_id=? and delete_user=0 and user_type='admin'";
		List<User> list = jdbcTemplate.query(read, new UserMapper(), emailID);
		return list;
	}

	@Override
	public void update(User user) {
		String update = "update user set name=?,phone_number=?,aadhaar_number=? where email_id=?";
		Object[] params = { user.getName(), user.getPhoneNumber(), user.getAadhaarNumber(), user.getEmailId() };
		jdbcTemplate.update(update, params);
	}
	
	@Override
	public void delete(User user) {
		String delete = "update user set delete_user=1 where email_id=?";
		Object[] params = { user.getEmailId() };
		jdbcTemplate.update(delete, params);
	}

	@Override
	public List<User> searchUser(String emailId) {
		String retrive = String.format("SELECT name,email_id,password,phone_number,aadhaar_number FROM user "
				+ "WHERE (name LIKE '%%%s%%' OR email_id LIKE '%%%s%%' OR phone_number LIKE '%%%s%%' OR aadhaar_number LIKE '%%%s%%' ) "
				+ "AND delete_user=0", emailId, emailId, emailId, emailId);
		return jdbcTemplate.query(retrive, new UserMapper());
	}
	
	@Override
	public void adminUpdateUserDetails(String name, long phoneNumber, long aadhaarNumber, String emailId) {
		String update = "update user set name=?,phone_number=?,aadhaar_number=? where email_id=?";
		Object[] params = { name, phoneNumber, aadhaarNumber,emailId };
		jdbcTemplate.update(update, params);		
	}

	@Override
	public void applyConnection(Customer customer) {
		String insert = "insert into customer_details(email_id,service_number,service_type,address,district,state,connection_status,address_proof)values(?,?,?,?,?,?,?,?)";
		Object[] params = { customer.getEmailId(), customer.getServiceNumber(), customer.getServiceType(),
				customer.getAddress(), customer.getDistrict(), customer.getState(), customer.getConnectionStatus(),
				customer.getAddressProof() };
		jdbcTemplate.update(insert, params);
	}

	@Override
	public List<Customer> readApplyConnection(String email) {
		String read = "Select email_id,service_number,service_type,address,district,state,connection_status,address_proof from customer_details where email_id=? and connection_status='applied'";
		List<Customer> list = jdbcTemplate.query(read, new CustomerMapper(), email);
		return list;
	}

	@Override
	public List<Customer> readAllApplyConnection() {
		String read = "SELECT email_id,service_number,service_type,address,district,state,connection_status,address_proof from customer_details where connection_status='applied'";
		List<Customer> list = jdbcTemplate.query(read, new CustomerMapper());
		return list;
	}

	@Override
	public List<Customer> readApprovedConnection(String email) {
		String read = "Select email_id,service_number,service_type,address,district,state,connection_status,address_proof from customer_details where email_id=? and connection_status='approved'";
		List<Customer> list = jdbcTemplate.query(read, new CustomerMapper(), email);
		return list;
	}

	@Override
	public void adminApproveConnection(Customer customer) {
		String delete = "update customer_details set connection_status='approved' where service_number=?";
		Object[] params = { customer.getServiceNumber() };
		jdbcTemplate.update(delete, params);
	}

	@Override
	public List<Customer> allApprovedConnection() {
		String read = "Select email_id,service_number,service_type,address,district,state,connection_status,address_proof from customer_details where  connection_status='approved'";
		List<Customer> list = jdbcTemplate.query(read, new CustomerMapper());
		return list;
	}

	@Override
	public List<Customer> getImage(String email,long serviceNumber) {
		String read = "Select address_proof from customer_details where  email_id=? and service_number=?";
		List<Customer> list = jdbcTemplate.query(read, new ImageMapper(), email,serviceNumber);
		return list;
	}
	
	/*
	 * Blob document = rs.getBlob("address_proof"); if (document != null) { int
	 * blobLength = (int) document.length(); byte[] blobAsBytes =
	 * document.getBytes(1, blobLength); customer.setAddressProof(blobAsBytes); }
	 */
	
	@Override
	public void enterBill(Bill bill) {

		String insert = "insert into bill(id,email_id,service_number,address,reading_units,reading_taken_date,due_date,service_type,amount,bill_status)values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { bill.getId(), bill.getEmailId(), bill.getServiceNumber(), bill.getAddress(),
				bill.getReadingUnits(), bill.getReadingTakenDate(), bill.getDueDate(), bill.getServiceType(),
				bill.getAmount(), bill.getBillStatus() };
		jdbcTemplate.update(insert, params);
	}

	

	@Override
	public void updateReadingUnit() {
	    String update = "update bill set bill_status = 'Paid' where reading_units <= 100 and bill_status = 'Not Paid'";
	    jdbcTemplate.update(update);
	}

	
	@Override
	public List<Bill> viewBill() {
		String read = "Select id,email_id,service_number,address,reading_units,reading_taken_date,due_date,service_type,amount,bill_status from bill where bill_status='Not Paid'";
		List<Bill> list = jdbcTemplate.query(read, new BillMapper());
		return list;

	}

	@Override
	public List<Bill> readBill(String email) {
		String read = "Select id,email_id,service_number,address,reading_units,reading_taken_date,due_date,service_type,amount,bill_status from bill where email_id=? AND bill_status='Not Paid'";
		List<Bill> list = jdbcTemplate.query(read, new BillMapper(), email);
		return list;
	}

	@Override
	public String readReadingTakenDate(String email, Long service) {
		String read = "Select reading_taken_date from bill where email_id=? & service_number=?";
	    return jdbcTemplate.queryForObject(read, String.class, email, service);

	}

	@Override
	public List<Bill> readPaidBill(String email) {
		String read = "Select id,email_id,service_number,address,reading_units,reading_taken_date,due_date,service_type,amount,bill_status from bill where email_id=? AND bill_status='Paid'";
		List<Bill> list = jdbcTemplate.query(read, new BillMapper(), email);
		return list;
	}

	@Override
	public void payAmount(Payment payment) {
		String insert = "insert into payment(payment_id,email_id,service_number,amount,account_number,payment_date,total_amount,payed_amount,payed_status)values(?,?,?,?,?,?,?,?,?)";
		Object[] params = { payment.getPaymentId(), payment.getEmailId(), payment.getServiceNumber(),
				payment.getAmount(), payment.getAccountNumber(), payment.getPaymentDate(), payment.getTotalAmount(),
				payment.getPayedAmount(), payment.getPayedStatus() };
		jdbcTemplate.update(insert, params);

	}

	@Override
	public void updatePaidStatus(long serviceNumber) {
		String delete = "update payment set payed_status='Paid' where service_number=?";
		
		Object[] paymentParams = { serviceNumber };
		jdbcTemplate.update(delete, paymentParams);
		
		
		String update = "update bill set bill_status='Paid' where service_number=?";
		Object[] billParam = { serviceNumber };
		jdbcTemplate.update(update, billParam);

	}

	@Override
	public List<Payment> checkPayment(String email,long serviceNumber,String paymentDate,double amount) {

		String read = "Select payment_id,email_id,service_number,amount,account_number,payment_date,total_amount,payed_amount,payed_status from payment where email_id=? and service_number=? and payment_date=? and amount=?";
		List<Payment> list = jdbcTemplate.query(read, new PaymentMapper(), email,serviceNumber,paymentDate,amount);
		return list;

	}
	
	@Override
	public List<Payment> checkPaymentAll(String email) {

		String read = "Select payment_id,email_id,service_number,amount,account_number,payment_date,total_amount,payed_amount,payed_status from payment where email_id=?";
		List<Payment> list = jdbcTemplate.query(read, new PaymentMapper(), email);
		return list;

	}

	@Override
	public List<Payment> viewPayment() {
		String read = "Select payment_id,email_id,service_number,amount,account_number,payment_date,total_amount,payed_amount,payed_status from payment";
		List<Payment> list = jdbcTemplate.query(read, new PaymentMapper());
		return list;
	}

	@Override
	public void applyComplaint(Complaint complaint) {
		String insert = "insert into complaint_details(complaint_id,email_id,service_number,description,complaint_status)values(?,?,?,?,?)";
		Object[] params = { complaint.getComplaintId(), complaint.getEmailId(), complaint.getServiceNumber(),
				complaint.getDescription(), complaint.getComplaintStatus() };
		jdbcTemplate.update(insert, params);

	}

	@Override
	public List<Complaint> viewComplaint(String email) {
		String read = "Select complaint_id,email_id,service_number,description,complaint_status from complaint_details where email_id=? and (complaint_status='processed' || complaint_status='applied')";
		List<Complaint> list = jdbcTemplate.query(read, new ComplaintMapper(), email);
		return list;

	}

	@Override
	public List<Complaint> viewPendingComplaint() {

		String read = "Select complaint_id,email_id,service_number,description,complaint_status from complaint_details where (complaint_status='processed' || complaint_status='applied')";
		List<Complaint> list = jdbcTemplate.query(read, new ComplaintMapper());
		return list;
	}

	@Override
	public void updateComplaint(String complaintStatus, int complaintId) {
		String update = "update complaint_details set complaint_status=? where complaint_id=?";
		Object[] params = { complaintStatus, complaintId };
		jdbcTemplate.update(update, params);

	}

	@Override
	public List<Complaint> rectifiedComplaint(String email) {
		String read = "Select complaint_id,email_id,service_number,description,complaint_status from complaint_details where email_id=? and complaint_status='rectified'";
		List<Complaint> list = jdbcTemplate.query(read, new ComplaintMapper(), email);
		return list;

	}

	@Override
	public List<Complaint> adminRectifiedComplaint() {

		String read = "Select complaint_id,email_id,service_number,description,complaint_status from complaint_details where complaint_status='rectified'";
		List<Complaint> list = jdbcTemplate.query(read, new ComplaintMapper());
		return list;
	}

	@Override
	public List<Customer> searchConnection(String emailId) {
		String retrive = String.format(
				"SELECT email_id,service_number,service_type,address,district,state,connection_status,address_proof FROM customer_details "
						+ "WHERE (email_id LIKE '%%%s%%' OR service_number LIKE '%%%s%%' OR service_type LIKE '%%%s%%' OR address LIKE '%%%s%%' OR district LIKE '%%%s%%' OR state LIKE '%%%s%%') ",
				emailId, emailId, emailId, emailId, emailId, emailId);
		return jdbcTemplate.query(retrive, new CustomerMapper());
	}

	@Override
	public List<Bill> searchUnpaid(String emailId) {
		String retrive = String.format(
				"SELECT id,email_id,service_number,address,reading_units,reading_taken_date,due_date,service_type,amount,bill_status FROM bill "
						+ "WHERE (id LIKE '%%%s%%' OR email_id LIKE '%%%s%%' OR service_number LIKE '%%%s%%' OR address LIKE '%%%s%%' OR reading_units LIKE '%%%s%%' OR reading_taken_date LIKE '%%%s%%' OR due_date LIKE '%%%s%%' OR service_type LIKE '%%%s%%' OR amount LIKE '%%%s%%') ",
				emailId, emailId, emailId, emailId, emailId, emailId, emailId, emailId, emailId);
		return jdbcTemplate.query(retrive, new BillMapper());
	}

	@Override
	public List<Payment> searchPaid(String emailId) {
		String search=String.format(
				"SELECT payment_id,email_id,service_number,amount,account_number,payment_date,total_amount,payed_amount,payed_status FROM payment "
						+ "WHERE (payment_id LIKE '%%%s%%' OR email_id LIKE '%%%s%%' OR service_number LIKE '%%%s%%' OR amount LIKE '%%%s%%' OR payment_date LIKE '%%%s%%' OR total_amount LIKE '%%%s%%' OR payed_amount LIKE '%%%s%%' ) ",
				emailId, emailId, emailId, emailId, emailId, emailId, emailId);
		return jdbcTemplate.query(search, new PaymentMapper());
	}

	@Override
	public List<Complaint> searchPending(String emailId) {
		String search = String.format(
				"SELECT complaint_id,email_id,service_number,description,complaint_status FROM complaint_details "
						+ "WHERE (complaint_id LIKE '%%%s%%' OR email_id LIKE '%%%s%%' OR service_number LIKE '%%%s%%' OR description LIKE '%%%s%%' OR complaint_status LIKE '%%%s%%') "+"AND complaint_status='applied' || complaint_status='processed'",
				emailId, emailId, emailId, emailId, emailId);
		return jdbcTemplate.query(search, new ComplaintMapper());
	}

	@Override
	public List<Complaint> searchRectified(String emailId) {
		String search = String.format(
				"SELECT complaint_id,email_id,service_number,description,complaint_status FROM complaint_details "
						+ "WHERE (complaint_id LIKE '%%%s%%' OR email_id LIKE '%%%s%%' OR service_number LIKE '%%%s%%' OR description LIKE '%%%s%%' OR complaint_status LIKE '%%%s%%') "+"AND complaint_status='rectified' ",
				emailId, emailId, emailId, emailId, emailId);
		return jdbcTemplate.query(search, new ComplaintMapper());
	}

	
	
	@Override
    public List<Complaint> getComplaintById(int complaintId) {
        String getId= "SELECT complaint_id,email_id,service_number,description,complaint_status FROM complaint_details WHERE complaint_id = ?";
      
        return  jdbcTemplate.query(getId, new ComplaintMapper(),complaintId);
        
        
    }

	

	
	
}
