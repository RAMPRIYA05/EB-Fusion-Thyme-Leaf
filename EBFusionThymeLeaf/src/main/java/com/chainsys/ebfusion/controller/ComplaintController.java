package com.chainsys.ebfusion.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.ebfusion.dao.UserDAO;
import com.chainsys.ebfusion.model.Complaint;
import com.chainsys.ebfusion.service.ComplaintService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ComplaintController {

	@Autowired
   UserDAO userDAO;
	JdbcTemplate jdbcTemplate;
	@Autowired
	ComplaintService complaintService;
	
	@GetMapping("/applyComplaint")
	public String applyComplaint(@RequestParam("emailId")String emailId,@RequestParam("serviceNumber")long serviceNumber,@RequestParam("description")String description,@RequestParam("complaintStatus")String complaintStatus,Model model,HttpSession session) {
		Complaint complaint=new Complaint();
		
		complaint.setEmailId(emailId);
		complaint.setServiceNumber(serviceNumber);
		complaint.setDescription(description);
		complaint.setComplaintStatus(complaintStatus);
		userDAO.applyComplaint(complaint);
		
		String email=(String)session.getAttribute("UserEmailId");
		complaintService.viewComplaint(model,email);
		return "customerViewComplaint";
	}
	
	@RequestMapping("/ComplaintForm")
	public String complaintForm()
	{
		return "complaintForm";
	}
	
	
	@GetMapping("/UpdateComplaintStatus")
	public String updateComplaintStatus() {
	    return "updateComplaintStatus";
	}
	
	@GetMapping("/viewPendingComplaint")
	public String viewPendingComplaint(Model model,HttpSession session)
	{
		String email=(String)session.getAttribute("UserEmailId");
		complaintService.viewComplaint(model,email);
		return "customerViewComplaint";
	}
	
	@GetMapping("/adminViewComplaint")
	public String adminViewComplaint(Model model)
	{
		complaintService.viewPendingComplaint(model);
		return "adminViewComplaint";
	}
	
	@GetMapping("/updateComplaint")
	public String updateComplaint(@RequestParam("complaintStatus")String complaintStatus,@RequestParam("complaintId") int complaintId,Model model) {
		Complaint complaint=new Complaint();
		complaint.setComplaintId(complaintId);
		complaintService.updateComplaint(complaintStatus, complaintId);
		complaintService.viewPendingComplaint(model);
		return "adminViewComplaint";
	}
	
	
	@GetMapping("/viewRectifiedComplaint")
	public String viewRectifiedComplaint(Model model,HttpSession session)
	{
		String email=(String)session.getAttribute("UserEmailId");
		complaintService.rectifiedComplaint(model, email);
		return "rectifiedComplaint";
	}
	
	@GetMapping("/rectifiedComplaint")
	public String rectifiedComplaint(Model model)
	{
		complaintService.adminRectifiedComplaint(model);
		return "adminRectifiedComplaint";
	}
	
	@GetMapping("/searchPendingComplaint")
	public String searchAppliedConnection(@RequestParam("emailId")String emailId,Model model)
	{		
		complaintService.searchPending(model, emailId);
		return "adminViewComplaint";
	}
	
	
	
	
	
	
	
	@RequestMapping("/searchRectifiedComplaint")
	 public String searchRectifiedComplaint(@RequestParam("emailId")String emailId,Model model)
	 {
		List<Complaint> list=userDAO.searchRectified(emailId).stream()
				.filter(match->match.getEmailId().equalsIgnoreCase(emailId)||match.getEmailId().contains(emailId))
		.collect(Collectors.toList());
		model.addAttribute("list", list);
		return "adminRectifiedComplaint";
	 }
	
	
}
