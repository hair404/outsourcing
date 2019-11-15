package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.BidRepository;
import com.service.UserService;

@Controller
public class BidController {
	@Autowired
	UserService  us;
	@Autowired
	BidRepository br;
	@PostMapping("bid")
	public String bid(@RequestParam("state")Integer state,HttpServletRequest request) {
	   HttpSession session = request.getSession();
	   Integer id = (Integer) session.getAttribute("id");
//	   if(us.isCompany(id))
//		 br.get
		return null;
	}
}
