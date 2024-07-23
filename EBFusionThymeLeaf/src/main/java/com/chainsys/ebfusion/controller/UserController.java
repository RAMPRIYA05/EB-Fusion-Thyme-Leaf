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
import org.springframework.web.bind.annotation.RestController;

import com.chainsys.ebfusion.dao.UserDAO;

import com.chainsys.ebfusion.model.User;
import com.chainsys.ebfusion.validation.Validation;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
    UserDAO userDAO;
	JdbcTemplate jdbcTemplate;
	
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
		userDAO.saveDetails(user);
		return "logIn";
		}
		else {
			return "userRegistration";
		}
		
	}
	
	
	
	@PostMapping("/Login")
    public String login(@RequestParam("emailId") String emailId, @RequestParam("password") String password,HttpSession session)
    {		
        try
        {
            if(emailId.equals("ram5@eb.com"))
            {
                if(password.equals(userDAO.getPassword(emailId)))
                {                    
                	session.setAttribute("AdminEmailId", emailId);
                	
                    return "adminWelcomePage";
                }
                else
                {
                    return "logIn";
                }
            }
            else if(emailId.equals(userDAO.getUserEmailId(emailId)))
            {
                if(password.equals(userDAO.getPassword(emailId)))
                {
                	session.setAttribute("UserEmailId", emailId);
                  return "userWelcomePage";
                }
                else
                {
                    return "logIn";
                }
            }

            else
            {
                return "logIn";
            }   
        }  
        catch (Exception e) 
        {
            System.out.println(e);
        }
        return emailId;
    }
	@GetMapping("/listOfUsers")
	public String getAllUser(Model model)
	{		
		List<User> list=userDAO.listUsers();
		model.addAttribute("list",list);
		return "registerTable";	
	}
	
	@GetMapping("/UserProfile")
	public String getUser(Model model,HttpSession session)
	{ 
		
		String email=(String)session.getAttribute("UserEmailId");
		
		List<User> list=userDAO.getUser(email);
		model.addAttribute("list",list);
		return "userProfile";	
	}
	
	@GetMapping("/AdminProfile")
	public String getAdmin(Model model,HttpSession session)
	{		
		String emailID=(String)session.getAttribute("AdminEmailId");
		List<User> list=userDAO.getAdmin(emailID);
		model.addAttribute("list",list);
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
	
		
	@GetMapping("/UpdateAdmin")
	public String updateAdmin(@RequestParam("name")String name,@RequestParam("phoneNumber")long phoneNumber,@RequestParam("aadhaarNumber")long aadhaarNumber,@RequestParam("emailId")String emailId,Model model) {
		User user=new User();
		user.setName(name);	
		user.setPhoneNumber(phoneNumber);
		user.setAadhaarNumber(aadhaarNumber);
		user.setEmailId(emailId);
		userDAO.update(user);
		List<User> list=userDAO.getAdmin(emailId);
		model.addAttribute("list",list);
		return "adminProfile";
	}
	
	@GetMapping("/deleteAdmin")
	public String deleteAdmin(@RequestParam("emailId")String emailId,Model model)
	{
		User user=new User();
		user.setEmailId(emailId);
		userDAO.delete(user);
		List<User> list=userDAO.getAdmin(emailId);
		model.addAttribute("list",list);
		return "adminProfile";
	}
	
	@GetMapping("/delete")
	public String deleteDetails(@RequestParam("emailId")String emailId,Model model)
	{
		User user=new User();
		user.setEmailId(emailId);
		userDAO.delete(user);
		List<User> list=userDAO.listUsers();
		model.addAttribute("list",list);
		return "registerTable";
	}
	
	@GetMapping("/updateUser")
	public String updateUser(@RequestParam("name")String name,@RequestParam("phoneNumber")long phoneNumber,@RequestParam("aadhaarNumber")long aadhaarNumber,@RequestParam("emailId")String emailId,Model model) {
		User user=new User();
		user.setName(name);				
		user.setPhoneNumber(phoneNumber);
		user.setAadhaarNumber(aadhaarNumber);
		user.setEmailId(emailId);
		userDAO.update(user);
		List<User> list=userDAO.getUser(emailId);
		model.addAttribute("list",list);
		return "userProfile";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(@RequestParam("emailId")String emailId,Model model,HttpSession session)
	{
		User user=new User();
		user.setEmailId(emailId);
		userDAO.delete(user);
		List<User> list=userDAO.getUser(emailId);
		model.addAttribute("list",list);
		return "userProfile";
	}
	
	@GetMapping("/searchUser")
	public String searchDetails(@RequestParam("emailId")String emailId,Model model)
	{		
		List<User> list=userDAO.searchUser(emailId);
		model.addAttribute("list",list);
		return "registerTable";
	}
	
	@GetMapping("/logOut")
	public String logOut(HttpSession session)
	{
		session.invalidate();
		return "home";
	}
}
