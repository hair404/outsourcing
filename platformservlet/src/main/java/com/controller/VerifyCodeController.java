package com.controller;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.exceptions.ClientException;
import com.utils.Code;
import com.utils.MessageTools;

@RestController
public class VerifyCodeController {
	@Autowired
	Code code;

	@PostMapping("getverifycode")
	public void getVerifyCode( HttpServletRequest request,@RequestParam("tel") String phoneNumber) throws ClientException {
	HttpSession session = request.getSession();
		session.setAttribute("tel", phoneNumber);
	String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
	session.setAttribute("verifyCode", verifyCode);
	MessageTools.sendVerifyCode(phoneNumber, verifyCode);
	}
	@PostMapping("checkverifycode")
	public String checkVerifyCode( HttpServletRequest request,@RequestParam("verifyCode") String verifyCode) {
		HttpSession session = request.getSession();
		String sessionVerifyCode = (String) session.getAttribute("verifyCode");
       if(verifyCode!=null&&sessionVerifyCode!=null&&verifyCode.equals(sessionVerifyCode)) 
    	   return "success";
       else 
    	   return "false";
	}
	
	@GetMapping("code")
	public void code(HttpServletResponse response,HttpServletRequest request) throws IOException {
		HttpSession session  = request.getSession();
		 ImageIO.write(code.buildCode(), "jpg", response.getOutputStream());
		  session.setAttribute("code", code.getCode());
	}
}
