package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.MemberRepository;
import com.dao.UserDao;
import com.dao.UserRepository;
import com.model.Member;
import com.model.User;
import com.utils.Code;

import net.bytebuddy.asm.MemberRemoval;

@Service
public class UserService {	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	MemberRepository mr;

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
		if (userRepository.getInfoById(id).getType() == 0)
			return true;
		return false;

	}

	public void insertInfo(String solr_id, Integer account_id, String name, String tel, String email, String username,
			Integer type, String entity) {
		User user = new User();
		user.setAccount_id(account_id);
		user.setCredit((float) 0);
		user.setEmail(email);
		user.setName(name);
		user.setUsername(username);
		user.setSolr_id(solr_id);
		user.setTel(tel);
		user.setType(type);
		user.setEntity(entity);
		userRepository.save(user);
	}

	public Boolean checkCode(String code) {
		String check = code.toUpperCase();
		if (check.equals(Code.getCode())) {
			System.out.println(Code.getCode());
			System.out.println(check);
			Code.setCode();
			return true;
		}
		else {
			Code.setCode();
			return false;
		}
	}
	
	public void addMember(String name,String tel,String email, String info,Integer studio_id) {
		Member member  =  new Member();
		member.setName(name);
		member.setTel(tel);
		member.setEmail(email);
		member.setInfo(info);
		member.setStudioid(studio_id);
		mr.save(member);
	}
	
}