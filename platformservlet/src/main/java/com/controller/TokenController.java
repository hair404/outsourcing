package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Token;

@RestController
public class TokenController {
	@Autowired
	Token to;
	@PostMapping("token")
	public String token(@RequestParam("token") String token) {
		to.setToken(token);
		return "success";
	}
}
