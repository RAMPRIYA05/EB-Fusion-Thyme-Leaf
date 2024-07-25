package com.chainsys.ebfusion.controller;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.chainsys.ebfusion.dao.UserDAO;
import com.chainsys.ebfusion.model.Customer;
import com.chainsys.ebfusion.model.User;
import com.chainsys.ebfusion.validation.Validation;


import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
	@Autowired
    UserDAO userDAO;
	JdbcTemplate jdbcTemplate;
	@Autowired
	Validation validate;
	
	@PostMapping("/applyConnection")
	public String applyConnection(@RequestParam("emailId")String emailId,@RequestParam("serviceType")String serviceType,@RequestParam("address")String address,@RequestParam("district")String district,@RequestParam("state")String state,@RequestParam("addressProof") MultipartFile addressProof, Model model,HttpSession session) throws IOException
	{
		  if(!addressProof.isEmpty())
	        { 
		Customer customer=new Customer();
		byte[] documentImages=addressProof.getBytes();
		
		customer.setEmailId(emailId);		
		customer.setServiceType(serviceType);
		customer.setAddress(address);
		customer.setDistrict(district);
		customer.setState(state);
		customer.setAddressProof(documentImages);
		
		Random random=new Random();
		long serviceNumber=random.nextLong(10000);
		customer.setServiceNumber(serviceNumber);
		customer.setConnectionStatus("applied");
		
		
		userDAO.applyConnection(customer);
	
        String email=(String)session.getAttribute("UserEmailId");
		List<Customer> list=userDAO.readApplyConnection(email);
		for(Customer customer1:list)
		{ 
			String base64AddressProof = Base64.getEncoder().encodeToString(customer1.getAddressProof());
             customer1.setCustomerAddressProof(base64AddressProof);
		}
		model.addAttribute("list",list);	
		  return "applyConnectionTable";
	        }
	        else
	        {
	            return "applyNewConnection";
	        }
		
	}
	
	@RequestMapping("/ApplyNewConnection")
	public String home()
	{
		return "applyNewConnection";
	}
	
	@GetMapping("/readAppliedConnection")
	public String readAppliedConnection(Model model,HttpSession session)
	{ 		
		String email=(String)session.getAttribute("UserEmailId");		
		List<Customer> list=userDAO.readApplyConnection(email);
		for(Customer customer:list)
		{ 
			String base64AddressProof = Base64.getEncoder().encodeToString(customer.getAddressProof());
             customer.setCustomerAddressProof(base64AddressProof);
		}
		model.addAttribute("list",list);
		return "applyConnectionTable";
	}
	
	
	
	
	@GetMapping("/ImageAddress")
	public String imageAddressProof(Model model,HttpSession session,String emailId,Long serviceNumber) {
       
		List<Customer> list=userDAO.getImage(emailId,serviceNumber);
		for(Customer customer:list)
		{ 
			String base64AddressProof = Base64.getEncoder().encodeToString(customer.getAddressProof());
             customer.setCustomerAddressProof(base64AddressProof);
		}
		model.addAttribute("list",list);
		return "addressProofPicture";	
	}
	
	
	@GetMapping("/readAllConnection")
	public String readAllAppliedConnection(Model model)
	{ 
		List<Customer> list=userDAO.readAllApplyConnection();
		for(Customer customer:list)
		{ 
			String base64AddressProof = Base64.getEncoder().encodeToString(customer.getAddressProof());
             customer.setCustomerAddressProof(base64AddressProof);
		}
		model.addAttribute("list",list);
		return "approveConnection";	
	}
	
	@GetMapping("/approvedConnection")
	public String approvedConnection(Model model,HttpSession session)
	{ 
		String email=(String)session.getAttribute("UserEmailId");
		List<Customer> list=userDAO.readApprovedConnection(email);
		for(Customer customer:list)
		{ 
			String base64AddressProof = Base64.getEncoder().encodeToString(customer.getAddressProof());
             customer.setCustomerAddressProof(base64AddressProof);
		}
		model.addAttribute("list",list);
		return "customerViewApprovedConnection";	
	}
	
	@GetMapping("/allApprovedConnection")
	public String allApprovedConnection(Model model)
	{ 
		List<Customer> list=userDAO.allApprovedConnection();
		for(Customer customer:list)
		{ 
			String base64AddressProof = Base64.getEncoder().encodeToString(customer.getAddressProof());
             customer.setCustomerAddressProof(base64AddressProof);
		}
		model.addAttribute("list",list);
		return "adminViewApprovedConnection";	
	}
		
	@GetMapping("/customerConnection")
	public String customerConnection(@RequestParam("serviceNumber")long serviceNumber,Model model,HttpSession session)
	{
		Customer customer=new Customer();
		customer.setServiceNumber(serviceNumber);
		userDAO.adminApproveConnection(customer);
		List<Customer> list=userDAO.readAllApplyConnection();
		model.addAttribute("list",list);
		return "approveConnection";					
	}
	
	@GetMapping("/searchConnection")
	public String searchConnection(@RequestParam("emailId")String emailId,Model model)
	{		
		List<Customer> list=userDAO.searchConnection(emailId);
		model.addAttribute("list",list);
		return "adminViewApprovedConnection";
	}
	
	@GetMapping("/searchAppliedConnection")
	public String searchAppliedConnection(@RequestParam("emailId")String emailId,Model model)
	{		
		List<Customer> list=userDAO.searchConnection(emailId);
		model.addAttribute("list",list);
		return "approveConnection";
	}
	
}
