package com.chainsys.ebfusion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.ebfusion.model.Complaint;


public class ComplaintMapper implements RowMapper<Complaint>  {

	@Override
	public Complaint mapRow(ResultSet rs, int rowNum) throws SQLException {
		Complaint complaint=new Complaint();
		int complaintId=rs.getInt("complaint_id");
		long serviceNumber=rs.getLong("service_number");
		String emailId=rs.getString("email_id");
		String description=rs.getString("description");
		String complaintStatus=rs.getString("complaint_status");
		
		complaint.setComplaintId(complaintId);
		complaint.setEmailId(emailId);
		complaint.setServiceNumber(serviceNumber);
		complaint.setDescription(description);
		complaint.setComplaintStatus(complaintStatus);
		
		return complaint;
	}

}
