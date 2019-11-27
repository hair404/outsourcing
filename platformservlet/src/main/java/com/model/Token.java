package com.model;


import org.springframework.stereotype.Component;

@Component
public class Token {
   
	String token;
	public void setToken(String token) {
		this.token=token;
	}
	public String getToken() {
		return token;
	}
}
