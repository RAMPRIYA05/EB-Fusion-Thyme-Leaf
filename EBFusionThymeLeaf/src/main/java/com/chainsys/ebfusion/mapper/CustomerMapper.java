package com.chainsys.ebfusion.mapper;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.chainsys.ebfusion.model.Customer;



public class CustomerMapper implements RowMapper<Customer> {
	@Override
	public Customer mapRow(ResultSet rs,int rowNum) throws SQLException {
		Customer customer=new Customer();
		String emailId=rs.getString("email_id");
		long serviceNumber=rs.getLong("service_number");
		String serviceType=rs.getString("service_type");
		String address=rs.getString("address");
		String district=rs.getString("district");
		String state=rs.getString("state");
		
		 Blob document = rs.getBlob("address_proof");
	        if (document != null) 
	        {
	            int blobLength = (int) document.length();
	            byte[] blobAsBytes = document.getBytes(1, blobLength);
	            customer.setAddressProof(blobAsBytes);
	        }
	        
		String connectionStatus=rs.getString("connection_status");
		customer.setEmailId(emailId);
		customer.setServiceNumber(serviceNumber);
		customer.setServiceType(serviceType);
		customer.setAddress(address);
		customer.setDistrict(district);
		customer.setState(state);
		
		customer.setConnectionStatus(connectionStatus);
		
		return customer;
}
}