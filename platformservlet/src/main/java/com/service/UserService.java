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

	public void insertInfo(String name, String tel, String password, String email, String username, Integer type, String uuid, String entity) {
		userDao.insertInfo(name, tel, password, email, username, type, uuid, entity);
	}
	public void insertAccount(String password, String tel) {
		userDao.insertAccount(password, tel);
	}


}