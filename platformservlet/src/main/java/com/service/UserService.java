package com.service;

import com.dao.*;
import com.model.*;
import com.type.*;
import com.utils.OSSTools;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @Resource
    private VerificationRepository verificationRepository;


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

    public boolean isStudent(int userId) {
        Optional<User> op = userRepository.findById(userId);
        return op.map(User::isStudent).orElse(false);
    }

    public String uploadVerification(int userId, VerificationType type, MultipartFile file) throws IOException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return "NotFound";
        }
        User user = optionalUser.get();
        user.setValidState(UserValidState.PENDING);

        String uuid = UUID.randomUUID().toString();
        Verification verification = new Verification();
        verification.setImage("user/verification/image/" + userId + "/" + uuid + ".jpg");
        verification.setState(VerificationState.PENDING);
        verification.setUserId(userId);
        verification.setType(type);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream()).scale(1.0f).outputFormat("jpeg").toOutputStream(byteArrayOutputStream);


        List<Verification> verificationList = verificationRepository.findAllByUserId(userId);
        verificationList.forEach(it -> it.setState(VerificationState.CANCEL));

        OSSTools.uploadFile("user/verification/image/" + userId + "/" + uuid + ".jpg", byteArrayOutputStream.toByteArray());
        verificationRepository.saveAll(verificationList);
        verificationRepository.save(verification);
        userRepository.save(user);
        return "success";
    }

    public List<Verification> getAllVerification() {
        return (List<Verification>) verificationRepository.findAll();
    }

    /**
     * 保存身份认证审核结果
     *
     * @param vid    审核ID
     * @param result 审核结果
     * @return
     */
    public String judgeVerification(int vid, boolean result) {
        Optional<Verification> optionalVerification = verificationRepository.findById(vid);
        if (!optionalVerification.isPresent()) {
            return "NotFound";
        }
        Verification verification = optionalVerification.get();

        Optional<User> optionalUser = userRepository.findById(verification.getUserId());
        if (!optionalUser.isPresent()) {
            return "NotFound";
        }
        User user = optionalUser.get();

        //判断状态是否为等待
        if (verification.getState() != VerificationState.PENDING) {
            return "CanNotJudge";
        }

        //设置Verification状态
        verification.setState(result ? VerificationState.PASS : VerificationState.REJECT);
        //设置User中的认证状态
        user.setValidState(result ? UserValidState.PASS : UserValidState.REJECT);

        //保存数据
        verificationRepository.save(verification);
        userRepository.save(user);

        return "success";
    }

    public String deleteUser(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return "NotFound";
        }
        User user = optionalUser.get();

        //设置账户状态为删除
        user.setDeleted(true);

        //保存数据
        userRepository.save(user);

        return "success";
    }
}