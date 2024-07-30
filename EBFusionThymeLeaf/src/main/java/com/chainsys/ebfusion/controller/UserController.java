package com.chainsys.ebfusion.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.chainsys.ebfusion.dao.UserDAO;

import com.chainsys.ebfusion.model.User;
import com.chainsys.ebfusion.service.UserService;
import com.chainsys.ebfusion.validation.Validation;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
    UserDAO userDAO;
	JdbcTemplate jdbcTemplate;
	@Autowired
	UserService userService;
	
	@Autowired
	Validation validate;
	@RequestMapping("/home")
	public String home()
	{
		return "home";
	}
	
	@RequestMapping("/LogIn")
	public String logIn() {
		return "logIn";
	}
	
	@RequestMapping("/About")
	public String about() {
		return "about";
	}
	
	@RequestMapping("/AdminAbout")
	public String adminAbout() {
		return "adminAbout";
	}
	
	@RequestMapping("/AdminWelcomePage")
	public String adminWelcome() {
		return "adminWelcomePage";
	}
	
	@RequestMapping("/UserWelcomePage")
	public String userWelcome() {
		return "userWelcomePage";
	}
	
	@RequestMapping("/UserRegistration")
	public String userRegistration() {
		return "userRegistration";
	}
	@PostMapping("/UserServlet")
	public String saveDetails(@RequestParam("name")String name,@RequestParam("emailId")String emailId,@RequestParam("password")String password,@RequestParam("phoneNumber")long phoneNumber,@RequestParam("aadhaarNumber")long aadhaarNumber,Model model)
	{
		
		/*
		 * BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder(); String
		 * encryptedPassword=bcrypt.encode(password);
		 */
		
		User user=new User();
		Validation validate = new Validation();
		    user.setName(name);
			user.setEmailId(emailId);
			user.setPassword(password);
			user.setPhoneNumber(phoneNumber);	
			user.setAadhaarNumber(aadhaarNumber);	
			user.setUserType("User");
			
			
		if(Boolean.FALSE.equals(validate.nameValidation(name, model)) || Boolean.FALSE.equals(validate.emailIdValidation(emailId, model)) || Boolean.FALSE.equals(validate.passwordValidation(password, model)) || Boolean.FALSE.equals(validate.phoneNumberValidation(phoneNumber, model)) || Boolean.FALSE.equals(validate.aadhaarNumberValidation(aadhaarNumber, model))){   
		userService.saveUser(user);
		return "logIn";
		}
		else {
			return "userRegistration";
		}
		
	}
	
	
	
	@PostMapping("/Login")
	public String login(@RequestParam("emailId") String emailId, @RequestParam("password") String password,
			HttpSession session) {
		try {
			if (emailId.equals("ram5@eb.com")) {
				String adminPassword = userService.getPassword(emailId);
				if (adminPassword != null && adminPassword.equals(password)) {
					session.setAttribute("AdminEmailId", emailId);
					return "adminWelcomePage";
				} else {
					return "home";
				}
			} else {
				String userEmail = userService.getUserEmailId(emailId);
				if (userEmail != null) {
					String userPassword = userService.getPassword(emailId);
					if (userPassword != null && userPassword.equals(password)) {
						session.setAttribute("UserEmailId", emailId);
						return "userWelcomePage";
					} else {
						return "home";
					}
				} else {
					return "home";
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
			return "logIn";
		}
	}

	 @GetMapping("/listOfUsers")
	    public String getAllUser(Model model) {        
	         userService.getAllUsers(model);
	     
	        return "registerTable";
	    }
	
	 @GetMapping("/UserProfile")
	    public String getUser(Model model, HttpSession session) { 
	        
	        String email = (String) session.getAttribute("UserEmailId");
	        if (email != null) {
	           userService.getUserProfile(email,model);
	           
	        } else {
	            model.addAttribute("error", "No user found in session.");
	        }
	        return "userProfile";    
	    }
	
	@GetMapping("/AdminProfile")
	public String getAdmin(Model model,HttpSession session)
	{		
		String emailID=(String)session.getAttribute("AdminEmailId");
		userService.getAdminByEmail(emailID,model);
		
		return "adminProfile";	
	}
	
	  @GetMapping("/UpdateAdminProfile") 
	  public String updateAdminProfile() 
	  {
	  return "updateAdminProfile";
	  }
	  @GetMapping("/UpdateUserProfile") 
	  public String updateUserProfile() 
	  {
	  return "updateUserProfile";
	  }
	 
	  
	  
	  @GetMapping("/UpdateCustomerProfile") 
	  public String updateCustomerProfile() 
	  {
	  return "updateCustomerProfile";
	  }
	  
	  @GetMapping("/AfterAdminUpdateUserProfile")
		public String AdminUpdateUserProfile(@RequestParam("name")String name,@RequestParam("phoneNumber")long phoneNumber,@RequestParam("aadhaarNumber")long aadhaarNumber,@RequestParam("emailId")String emailId,Model model) {
		    User user=new User();
		    
		    user.setName(name);				
			user.setPhoneNumber(phoneNumber);
			user.setAadhaarNumber(aadhaarNumber);
			user.setEmailId(emailId);
			
			userService.updateUserDetails(user);
			userService.listUsers(model);
			
			return "registerTable";
		}
	@GetMapping("/UpdateAdmin")
	public String updateAdmin(@RequestParam("name")String name,@RequestParam("phoneNumber")long phoneNumber,@RequestParam("aadhaarNumber")long aadhaarNumber,@RequestParam("emailId")String emailId,Model model) {
		User user=new User();
		user.setName(name);	
		user.setPhoneNumber(phoneNumber);
		user.setAadhaarNumber(aadhaarNumber);
		user.setEmailId(emailId);
		userService.updateUser(user);
		userService.getAdmin(emailId,model);
		
		return "adminProfile";
	}
	
	@GetMapping("/deleteAdmin")
	public String deleteAdmin(@RequestParam("emailId")String emailId,Model model)
	{
		User user=new User();
		user.setEmailId(emailId);
		 userService.deleteUser(user);
		userService.getAdmin(emailId,model);
		
		return "adminProfile";
	}
	
	@GetMapping("/deleteParticularUser")
	public String deleteDetails(@RequestParam("emailId")String emailId,Model model)
	{
		User user=new User();
		user.setEmailId(emailId);
		
		userService.deleteUser(user);
		userService.listUsers(model);
	
		return "registerTable";
	}
	
	@GetMapping("/updateUser")
	public String updateUser(@RequestParam("name")String name,@RequestParam("phoneNumber")long phoneNumber,@RequestParam("aadhaarNumber")long aadhaarNumber,@RequestParam("emailId")String emailId,Model model) {
		User user=new User();
		user.setName(name);				
		user.setPhoneNumber(phoneNumber);
		user.setAadhaarNumber(aadhaarNumber);
		user.setEmailId(emailId);
		userService.updateUser(user);
		userService.getUserProfile(emailId,model);
	
		return "userProfile";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(@RequestParam("emailId")String emailId,Model model,HttpSession session)
	{
		User user=new User();
		user.setEmailId(emailId);
		userService.deleteUser(user);
		userService.getUserProfile(emailId,model);
		
		return "userProfile";
	}
	
	@GetMapping("/searchUser")
	public String searchDetails(@RequestParam("emailId")String emailId,Model model)
	{		
		userService.searchUser(emailId,model);
	
		return "registerTable";
	}
	
	@GetMapping("/logOut")
	public String logOut(HttpSession session)
	{
		session.invalidate();
		return "home";
	}
	
	
	
}
