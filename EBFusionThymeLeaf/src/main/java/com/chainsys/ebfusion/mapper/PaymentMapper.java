package com.chainsys.ebfusion.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.chainsys.ebfusion.model.Payment;


public class PaymentMapper implements RowMapper<Payment>{
	@Override
	public Payment mapRow(ResultSet rs,int rowNum) throws SQLException {
		Payment payment=new Payment();
		int paymentId=rs.getInt("payment_id");
		String emailId=rs.getString("email_id");
		long serviceNumber=rs.getLong("service_number");
		double amount=rs.getDouble("amount");
		long accountNumber=rs.getLong("account_number");
		
		String paymentDate=rs.getString("payment_date");
		double totalAmount=rs.getDouble("total_amount");
		double payedAmount=rs.getDouble("payed_amount");
		String payedStatus=rs.getString("payed_status");
		
		payment.setPaymentId(paymentId);
		payment.setEmailId(emailId);
		payment.setServiceNumber(serviceNumber);
		payment.setAmount(amount);
		payment.setAccountNumber(accountNumber);
		
		payment.setPaymentDate(paymentDate);
		payment.setTotalAmount(totalAmount);
		payment.setPayedAmount(payedAmount);
		payment.setPayedStatus(payedStatus);
		
		return payment;
	
	
	}
}
