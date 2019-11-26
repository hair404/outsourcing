package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.MemberRepository;
import com.dao.UserRepository;
import com.model.Member;
import com.model.User;


import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private ProjectRepository projectRepository;
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

    /**
     * 计算某类所有用户的主观打分
     * @param type UserType 用户类型
     * @return
     */
    public Map<Integer, Double> getUsersScore(UserType type) {
        Map<Integer, Double> total = new HashMap<>();
        Map<Integer, Integer> cnt = new HashMap<>();
        switch (type) {
            case COMPANY:
                projectRepository.findAll().forEach(it -> {
                    total.put(it.getCompanyID(), total.getOrDefault(it.getCompanyID(), 0.0) + it.getCompanyRate());
                    cnt.put(it.getCompanyID(), cnt.getOrDefault(it.getCompanyID(), 0) + 1);
                });
                break;
            case STUDIO:
                projectRepository.findAll().forEach(it -> {
                    total.put(it.getStudioID(), total.getOrDefault(it.getStudioID(), 0.0) + it.getStudioRate());
                    cnt.put(it.getStudioID(), cnt.getOrDefault(it.getStudioID(), 0) + 1);
                });
                break;
            default:
                return new HashMap<>();
        }
        Map<Integer, Double> scores = new HashMap<>();
        total.forEach((id, score) -> scores.put(id, score / cnt.getOrDefault(id, 1)));
        return scores;
    }

	public Boolean checkCode(String code,String sessionCode) {
		if (code!=null&&sessionCode!=null&&code.toUpperCase().equals(sessionCode)) 	
			return true;
		else 
			return false;
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