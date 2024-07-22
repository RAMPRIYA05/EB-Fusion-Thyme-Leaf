package com.chainsys.ebfusion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.ebfusion.model.User;

public class UserMapper implements RowMapper<User> {

	
	@Override
	public User mapRow(ResultSet rs,int rowNum) throws SQLException {
		User user=new User();
		String name=rs.getString("name");
				String emailId=rs.getString("email_id");
				String password=rs.getString("password");
				long phoneNumber=rs.getLong("phone_number");
				long aadhaarNumber=rs.getLong("aadhaar_number");
				user.setName(name);
				user.setEmailId(emailId);
				user.setPassword(password);
				user.setPhoneNumber(phoneNumber);
				user.setAadhaarNumber(aadhaarNumber);
				return user;
	}
}
