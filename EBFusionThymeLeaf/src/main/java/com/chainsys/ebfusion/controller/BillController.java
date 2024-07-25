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
import com.chainsys.ebfusion.model.Bill;

import jakarta.servlet.http.HttpSession;

@Controller
public class BillController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@PostMapping("/Bill")
	public String bill(@RequestParam("emailId")String emailId,@RequestParam("serviceNumber")long serviceNumber,@RequestParam("serviceType")String serviceType,@RequestParam("address")String address,@RequestParam("readingUnits")double readindUnits,@RequestParam("readingTakenDate")String readingTakenDate,@RequestParam("dueDate")String dueDate,@RequestParam("amount")double amount,Model model,HttpSession session)
	{
		Bill bill=new Bill();
		session.setAttribute("ServiceNumber",serviceNumber);
		
		bill.setEmailId(emailId);
		bill.setServiceNumber(serviceNumber);
		bill.setServiceType(serviceType);
		bill.setAddress(address);
		bill.setReadingUnits(readindUnits);
		bill.setReadingTakenDate(readingTakenDate);
		bill.setDueDate(dueDate);
		bill.setAmount(amount);
		bill.setBillStatus("Not Paid");
		userDAO.enterBill(bill);	
		 userDAO.updateReadingUnit(); 
		List<Bill> list=userDAO.viewBill();
		model.addAttribute("list",list);
		
		return "customerBill";
		
	}
	
	@GetMapping("/readAllBill")
	public String readAllBill(Model model)
	{ 	
		List<Bill> list=userDAO.viewBill();
		model.addAttribute("list",list);
		return "customerBill";
	}
	
	
	@RequestMapping("/BillForm")
	public String billForm(HttpSession session) {	
		return "billForm";
	}
	@GetMapping("/readParticularBill")
	public String readParticularBill(Model model,HttpSession session)
	{ 		
		String email=(String)session.getAttribute("UserEmailId");		
		List<Bill> list=userDAO.readBill(email);
		model.addAttribute("list",list);
		return "viewBill";
	}
	
	@GetMapping("/readPaidBill")
	public String readPaidBill(Model model,HttpSession session)
	{ 	
		String email=(String)session.getAttribute("UserEmailId");	
		List<Bill> list=userDAO.readPaidBill(email);
		model.addAttribute("list",list);
		return "paidBill";
	}
	
	@GetMapping("/searchUnpaidBills")
	public String searchUnpaidBills(@RequestParam("emailId")String emailId,Model model)
	{		
		List<Bill> list=userDAO.searchUnpaid(emailId);
		model.addAttribute("list",list);
		return "customerBill";
	}
	
}

