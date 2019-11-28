package com.service;

import com.dao.ProjectRepository;
import com.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.MemberRepository;
import com.dao.UserRepository;
import com.model.Member;
import com.model.User;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
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
        return userRepository.getInfoById(id).getType() == UserType.COMPANY;

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
        user.setType(UserType.fromId(type));
        user.setEntity(entity);
        user.setIsValid(0);
        user.setStudent(false);
        user.setImg("/userimg/default.jpg");
        user.setAvatar("/avatar/default.jpg");
        userRepository.save(user);
    }

    /**
     * 计算某类所有用户的主观打分
     *
     * @param type UserType 用户类型
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

    public Boolean checkCode(String code, String sessionCode) {
        return code != null && code.toUpperCase().equals(sessionCode);
    }

    /**
     * 改变用户背景图
     *
     * @param userId 用户id
     * @param url    图片地址
     */
    public void updateBackground(int userId, String url) {
        Optional<User> op = userRepository.findById(userId);
        if (op.isPresent()) {
            User user = op.get();
            user.setImg(url);
            userRepository.save(user);
        }
    }

    /**
     * 改变用户头像
     *
     * @param userId 用户id
     * @param url 图片地址
     */
    public void updateAvatar(int userId, String url) {
        Optional<User> op = Optional.ofNullable(userRepository.getInfoById(userId));
        if (op.isPresent()) {
            User user = op.get();
            user.setAvatar(url);
            userRepository.save(user);
        }
    }

    public void addMember(String name, String tel, String email, String info, Integer studio_id) {
        Member member = new Member();
        member.setName(name);
        member.setTel(tel);
        member.setEmail(email);
        member.setInfo(info);
        member.setStudioid(studio_id);
        mr.save(member);
    }
}