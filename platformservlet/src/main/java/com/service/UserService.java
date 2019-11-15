package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.dao.UserRepository;
import com.model.UserInfo;

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
   
	public boolean isCompany(Integer id) {
		if(userRepository.getInfoById(id).getType()==0)
			return true;
		return false;
		
	}
	public void insertInfo(String solr_id,
			Integer account_id, 
			String name,
			String tel, 
			String email,
			String username,
			Integer type,
			String entity) {
		UserInfo user = new UserInfo();
		user.setAccount_id(account_id);
		user.setCredit((float) 0);
		user.setEmail(email);
		user.setName(name);
		user.setUsername(username);
		user.setSolr_id(solr_id);
		user.setTel(tel);
		user.setType(type);
		user.setEntity(entity);
	}



}