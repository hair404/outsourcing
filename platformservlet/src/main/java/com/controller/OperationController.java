package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.PaymentRepository;
import com.dao.ProjectRepository;
import com.dao.CancelReasonRepository;
import com.dao.ComplainReasonRepository;
import com.model.Bid;
import com.model.Payment;
import com.model.Cancel_reason;
import com.model.Complain_reason;

@RestController
public class OperationController {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	CancelReasonRepository cancelr;
	@Autowired
	ComplainReasonRepository complainr;
	@Autowired
	PaymentRepository pr;
	
	@PostMapping("company_action")
	 public String company_action(@RequestParam("id") Integer id,@RequestParam("action") String action,
			 @RequestParam(value = "studioid" ,required = false) Integer studioid,
			 @RequestParam(value = "reason" ,required = false) String reason,
			 @RequestParam(value = "isfirst" ,required = false) Integer isfirst,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer company_id = (Integer) session.getAttribute("id");
		if (action.equals("select")) {
           projectRepository.update_studioid(studioid, id);
           
		}
		else if (action.equals("cancel")) {
			Cancel_reason r = new Cancel_reason();
			r.setProject_id(id);
			r.setReason(reason);
			r.setStudioid(studioid);
			cancelr.save(r);
			return "success";
		}else if(action.equals("pay")) {
			if(isfirst==0) {
				Payment p = new Payment();
				Integer studio_id = projectRepository.get_studioid(id);
		         p.setCompany_id(company_id);
		         p.setProject_id(id);
		         p.setState(1);
		         p.setStudio_id(studio_id);
		         pr.save(p);
			}
			else {
				pr.update_state(1, id);
			}
			projectRepository.update_companyPaidState(1, id);
			return "success";
			}
		
		return "false";
	}
	@PostMapping("studio_action")
	 public String studio_action(@RequestParam("id") Integer id,@RequestParam("action") String action,
			 @RequestParam(value = "reason" ,required = false) String reason,
			 @RequestParam(value = "isfirst" ,required = false) Integer isfirst,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer studio_id = (Integer) session.getAttribute("id");
		Integer company_id = projectRepository.get_companyid(id);
		if (action.equals("tender")) {
			Bid bid = new Bid();
			bid.setCompany_id(company_id);
			bid.setProject_id(id);
			bid.setState(0);
			bid.setStudio_id(studio_id);    
			bid.setCompany_id(company_id);
		}
		else if (action.equals("complain")) {
			Complain_reason r = new Complain_reason();
			r.setEntity(2);
			r.setReason(reason);
		    r.setProject_id(id);
			complainr.save(r);
			return "success";
		}else if(action.equals("pay")) {
			projectRepository.update_studioPaidState(1, id);
			return "success";
			}
		
		return "false";
	}
		
}
