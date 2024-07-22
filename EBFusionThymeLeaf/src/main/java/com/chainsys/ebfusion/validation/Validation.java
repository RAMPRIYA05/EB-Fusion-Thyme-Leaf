package com.chainsys.ebfusion.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
@Repository
public class Validation {

	
		public Boolean nameValidation(String name, Model model) {
			String regex1 = "^[A-Za-z]+$";
			Pattern p1 = Pattern.compile(regex1);
			Matcher valid1 = p1.matcher(name);
			Boolean ans1 = valid1.matches();
			if (Boolean.FALSE.equals(ans1)) {
				
				String errorMessage = name+" is a invalid name use only alphabetic sequence";
				model.addAttribute(errorMessage,model);
				return false;
			}
			return ans1;

	
}

		public Boolean emailIdValidation(String emailId, Model model) {
			String regex2 = "[a-z0-9._%+-]+@[a-z0-9.-]+[/.][a-z]{2,}$";
			Pattern p2 = Pattern.compile(regex2);
			Matcher valid2 = p2.matcher(emailId);
			Boolean ans2 = valid2.matches();
			if (Boolean.FALSE.equals(ans2)) {
				
				String errorMessage = emailId+" is a invalid emailId use only small case letters,0-9 numbers,@,.,_ ";
				model.addAttribute(errorMessage,model);
				return false;
			}
			return ans2;
			
			
		}

		public Boolean passwordValidation(String password, Model model) {
			
			String regex3 = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}";
			Pattern p3 = Pattern.compile(regex3);
			Matcher valid3 = p3.matcher(password);
			Boolean ans3 = valid3.matches();
			if (Boolean.FALSE.equals(ans3)) {
				
				String errorMessage = password+" is a invalid password use only one uppercase,one lowercase,one numbers,one special characters,and length 8 ";
				model.addAttribute(errorMessage,model);
				return false;
			}
			return ans3;
		}

		public Boolean phoneNumberValidation(long phoneNumber, Model model) {
			String regex4 = "[0-9]{10}";
			String phonenumber=Long.toString(phoneNumber);
			Pattern p4 = Pattern.compile(regex4);
			
			Matcher valid4 = p4.matcher(phonenumber);
			Boolean ans4 = valid4.matches();
			if (Boolean.FALSE.equals(ans4)) {
				
				String errorMessage = phonenumber+" is a invalid phone number use only 10 digit numbers ";
				model.addAttribute(errorMessage,model);
				return false;
			}
			return ans4;
		}

		public Boolean aadhaarNumberValidation(long aadhaarNumber, Model model) {
			String regex5 = "^[2-9][0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
			String aadhaarnumber=Long.toString(aadhaarNumber);
			Pattern p5 = Pattern.compile(regex5);
			Matcher valid5 = p5.matcher(aadhaarnumber);
			Boolean ans5 = valid5.matches();
			if (Boolean.FALSE.equals(ans5)) {
				
				String errorMessage = aadhaarnumber+" is a invalid aadhaar number use only 12 digit numbers";
				model.addAttribute(errorMessage,model);
				return false;
			}
			return ans5;
			
		}

		}