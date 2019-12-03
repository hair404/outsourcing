package com.service;

import com.dao.*;
import com.model.Notification;
import com.model.Tag;
import com.type.ActionType;
import com.type.UserType;
import com.utils.GoEasyNotification;
import org.springframework.stereotype.Service;
import com.model.Member;
import com.model.User;


import javax.annotation.Resource;
import java.util.*;

@Service
public class UserService {
    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private MemberRepository mr;

    @Resource
    private NotificationRepository notificationRepository;

    @Resource
    private TagRepository tagRepository;


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
        user.setId(account_id);
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

    public String getCompanyName(int companyId) {
        Optional<User> op = userRepository.findById(companyId);
        if (op.isPresent()) {
            User user = op.get();
            if (user.getType() == UserType.COMPANY) {
                return user.getUsername();
            }
        }
        return "";
    }

    /**
     * 改变用户头像
     *
     * @param userId 用户id
     * @param url    图片地址
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

    /**
     * 设置推送token
     *
     * @param userId 用户id
     * @param token  token
     */
    public void setToken(int userId, String token) {
        Optional<User> op = userRepository.findById(userId);
        if (op.isPresent()) {
            User user = op.get();
            user.setToken(token);
            userRepository.save(user);
        }
    }

    /**
     * 获取推送token
     *
     * @param userId 用户id
     * @return
     */
    public String getToken(int userId) {
        Optional<User> op = userRepository.findById(userId);
        if (op.isPresent()) {
            User user = op.get();
            return user.getToken();
        }
        return "";
    }

    /**
     * 获取通知
     *
     * @param userId 用户id
     * @return
     */
    public List<Notification> getNotifications(int userId) {
        return notificationRepository.findAllByUid(userId);
    }

    public void edit(int userId, String name, List<Integer> tags, String email, String username, String info) {
        Optional<User> op = userRepository.findById(userId);
        if (!op.isPresent()) {
            return;
        }
        User user = op.get();
        user.setName(name);
        user.setEmail(email);
        user.setUsername(username);
        user.setInfo(info);
        userRepository.save(user);

        tagRepository.deleteAllByUserId(userId);
        List<Tag> tagSave = new ArrayList<>();
        tags.forEach(it -> {
            Tag tag = new Tag();
            tag.setTag(it);
            tag.setUserId(userId);
            tagSave.add(tag);
        });
        tagRepository.saveAll(tagSave);
    }

    public List<Tag> getTags(int userId) {
        return tagRepository.findAllByUserId(userId);
    }

    public List<Integer> getTagIntList(int userId) {
        List<Integer> ids = new ArrayList<>();
        getTags(userId).forEach(it -> ids.add(it.getTag()));
        return ids;
    }

    public Optional<User> getUser(int userId) {
        return userRepository.findById(userId);
    }
}