package com.chainsys.ebfusion.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
 

import com.chainsys.ebfusion.dao.UserDAO;

import com.chainsys.ebfusion.model.Payment;
import com.chainsys.ebfusion.service.PaymentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
    
	@Autowired
	UserDAO userDAO;
	JdbcTemplate jdbcTemplate;
	public static int paymentId;
     @Autowired
     PaymentService  paymentService;
	@PostMapping("/payBill")
	public String payBill(@RequestParam("emailId") String emailId, @RequestParam("serviceNumber") long serviceNumber,
			@RequestParam("amount") double amount, @RequestParam("accountNumber") long accountNumber,
			@RequestParam("paymentDate") String paymentDate, @RequestParam("totalAmount") double totalAmount,
			@RequestParam("payedAmount") double payedAmount,@RequestParam("readingTakenDate")String readingTakenDate,@RequestParam("ifsc")String ifsc, Model model, HttpSession session) {

		Payment payment = new Payment();

		payment.setEmailId(emailId);
		payment.setServiceNumber(serviceNumber);
		payment.setAmount(amount);
		payment.setAccountNumber(accountNumber);
		payment.setPaymentDate(paymentDate);
		payment.setTotalAmount(totalAmount);
		payment.setPayedAmount(payedAmount);
        payment.setIfsc(ifsc);
        
        paymentService.processPayment(payment, serviceNumber);
		String email = (String) session.getAttribute("UserEmailId");	 
		 paymentService.checkPayment(email,serviceNumber,paymentDate,amount,model);
		 model.addAttribute("readingTakenDate", readingTakenDate);
		return "viewPaidBill";
	}

	@GetMapping("/viewPaidStatus")
	public String viewPaidStatus(Model model, HttpSession session,String readingTakenDate) {
		String email = (String) session.getAttribute("UserEmailId");
		Payment payment = new Payment();
		paymentService.checkPaymentAll(model, email);
		model.addAttribute("readingTakenDate", readingTakenDate);
		return "paymentHistory";
	}

	@GetMapping("/adminViewPaidStatus")
	public String adminViewPaidStatus(Model model) {
		paymentService.viewPayment(model);
		return "customerPaidBills";
	}
	
	
	@GetMapping("/searchPaidBills")
	public String searchPaidBills(@RequestParam("emailId")String emailId,Model model)
	{		
		paymentService.searchPaid(model, emailId);
		return "customerPaidBills";
	}
	
	 @GetMapping("/PaymentForm") 
	  public String paymentForm() 
	  {
	  return "paymentForm";
	  }
	
}
