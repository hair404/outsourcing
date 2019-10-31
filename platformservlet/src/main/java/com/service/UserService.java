package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.dao.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRepository userRepository;

	public boolean ifExsit(String tel) {
		try {
			if (userRepository.getAccountByTel(tel).getTel().equals(tel))
				return true;
		} catch (Exception e) {
			System.out.println("无数据");
		}
		return false;
	}




}