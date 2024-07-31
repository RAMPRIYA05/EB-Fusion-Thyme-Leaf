package com.chainsys.ebfusion.controller;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

import org.springframework.web.multipart.MultipartFile;
import com.chainsys.ebfusion.dao.UserDAO;
import com.chainsys.ebfusion.model.Bill;
import com.chainsys.ebfusion.model.Customer;
import com.chainsys.ebfusion.service.CustomerService;
import com.chainsys.ebfusion.validation.Validation;


import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
	@Autowired
    UserDAO userDAO;
	JdbcTemplate jdbcTemplate;
	@Autowired
	Validation validate;
	
	@Autowired
	CustomerService customerService;
	
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
		
		
		
		Bill latestBill = userDAO.findLatestBillByServiceNumber(serviceNumber);
        boolean canEnterBill = isBillEntryAllowed(latestBill);
        customer.setCanEnterBill(canEnterBill);
		
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
		customerService.readApplyConnection(email,model);
		return "applyConnectionTable";
	}
	
	
	
	
	@GetMapping("/ImageAddress")
	public String imageAddressProof(Model model,HttpSession session,String emailId,Long serviceNumber) {
       
		customerService.getImage(emailId,serviceNumber,model);
		
		return "addressProofPicture";	
	}
	
	
	@GetMapping("/readAllConnection")
	public String readAllAppliedConnection(Model model)
	{ 
		customerService.readAllApplyConnection(model);
		
		return "approveConnection";	
	}
	
	@GetMapping("/approvedConnection")
	public String approvedConnection(Model model,HttpSession session)
	{ 
		String email=(String)session.getAttribute("UserEmailId");
		customerService.readApprovedConnection(email,model);
		return "customerViewApprovedConnection";	
	}
	
	@GetMapping("/allApprovedConnection")
	public String allApprovedConnection(Model model, Long serviceNumber) {
		List<Customer> list = userDAO.allApprovedConnection();
		for (Customer customer : list) {
			String base64AddressProof = Base64.getEncoder().encodeToString(customer.getAddressProof());
			customer.setCustomerAddressProof(base64AddressProof);

			Bill latestBill = userDAO.findLatestBillByServiceNumber(customer.getServiceNumber());

			boolean isBillEntryAllowed = isBillEntryAllowed(latestBill);
			customer.setCanEnterBill(isBillEntryAllowed);

		}
		model.addAttribute("list", list);
		return "adminViewApprovedConnection";
	}

	private boolean isBillEntryAllowed(Bill latestBill) {
		if (latestBill == null) {
			return true;
		}

		LocalDate currentDate = LocalDate.now();
		LocalDate readingTakenDate = LocalDate.parse(latestBill.getReadingTakenDate());

		long daysDifference = ChronoUnit.DAYS.between(readingTakenDate, currentDate);
		
		
		if (daysDifference >= 60) {
	        return true;
	    } else {
	        return false; 
	    }
		
	}

	@GetMapping("/customerConnection")
	public String customerConnection(@RequestParam("serviceNumber")long serviceNumber,Model model,HttpSession session)
	{
		Customer customer=new Customer();
		customer.setServiceNumber(serviceNumber);
		customerService.adminApproveConnection(customer);
		customerService.readAllApplyConnection(model);
		
		return "approveConnection";					
	}
	
	@GetMapping("/searchConnection")
	public String searchConnection(@RequestParam("emailId")String emailId,Model model)
	{		
		customerService.searchConnection(emailId,model);
		
		return "adminViewApprovedConnection";
	}
	
	@GetMapping("/searchAppliedConnection")
	public String searchAppliedConnection(@RequestParam("emailId")String emailId,Model model)
	{		
		customerService.searchConnection(emailId,model);
		
		
		return "approveConnection";
	}
	
	
	
	
	@GetMapping("/storeServiceTypeCount")
    public String storeServiceTypeCount(HttpSession session) {
       
       
        return "redirect:/displayServiceTypeCount";
    }

    @GetMapping("/displayServiceTypeCount")
    public String displayServiceTypeCount(Model model, HttpSession session) {
        Integer count = (Integer) session.getAttribute("uniqueServiceTypeCount");
        if (count != null) {
            model.addAttribute("uniqueServiceTypeCount", count);
        } else {
            model.addAttribute("uniqueServiceTypeCount", "Not available");
        }
        return "userWelcomePage"; 
    }
}
