package com.chainsys.ebfusion.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.chainsys.ebfusion.model.Bill;

public class BillMapper implements RowMapper<Bill> {

	@Override
	public Bill mapRow(ResultSet rs,int rowNum) throws SQLException {
		Bill bill=new Bill();
		int id=rs.getInt("id");
		String emailId=rs.getString("email_id");
		long serviceNumber=rs.getLong("service_number");
		String serviceType=rs.getString("service_type");
		String address=rs.getString("address");
		double readingUnits=rs.getDouble("reading_units");
	    String readingTakenDate=rs.getString("reading_taken_date");
	    String dueDate=rs.getString("due_date");
	    double amount=rs.getDouble("amount");
	    String billStatus=rs.getString("bill_status");
	    
	    bill.setId(id);
	    bill.setEmailId(emailId);
	    bill.setServiceNumber(serviceNumber);
	    bill.setServiceType(serviceType);
	    bill.setAddress(address);
	    bill.setReadingUnits(readingUnits);
	    bill.setReadingTakenDate(readingTakenDate);
	    bill.setDueDate(dueDate);
	    bill.setAmount(amount);
	    bill.setBillStatus(billStatus);
		return bill;
	}
}
