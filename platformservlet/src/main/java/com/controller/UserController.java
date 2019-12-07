package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Environment;
import com.dao.*;
import com.dp.NotificationDataProcessor;
import com.dp.UserDataProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Account;
import com.model.Admin;
import com.model.User;
import com.service.ProjectService;
import com.service.TagService;
import com.service.UserService;
import com.type.UserType;
import com.type.VerificationType;
import com.utils.Code;
import com.utils.JsonUtils;
import com.utils.UuidUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserDao userDao;
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    AccountRepository accountRepository;
    @Resource
    MemberRepository memberRepository;
    @Resource
    ProjectService ps;
    @Resource
    AdminRepository ar;
    @Resource
    Code co;

    @Resource
    private NotificationDataProcessor notificationDataProcessor;

    @Resource
    private UserDataProcessor userDataProcessor;

    @PostMapping("register")
    public String register(@RequestParam("name") String name, @RequestParam("phone") String tel,
                           @RequestParam("password") String password, @RequestParam("email") String email,
                           @RequestParam("username") String username, @RequestParam("type") Integer type) {
        if (!userService.ifExsit(tel)) {
            userDao.insertAccount(password, tel);
            Integer account_id = accountRepository.get_id(tel);
            String solr_id = UuidUtils.generateShortUuid();
            String entity = null;
            if (type == 0)
                entity = "company";
            else if (type == 1)
                entity = "studio";
            else if (type == 2)
                entity = "manager";
            userService.insertInfo(solr_id, account_id, name, tel, email, username, type, entity);
            return "success";
        } else
            return "fail";
    }

    @RequestMapping("list")
    public java.util.List<User> list() {
        return userRepository.findAll();
    }

    @PostMapping(value = "login")
    public String login(@RequestParam("name") String tel, @RequestParam("password") String password,
                        @RequestParam(name = "code", required = false) String code, @RequestParam("type") Integer type,
                        HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            String sessionCode = (String) session.getAttribute("code");
            if (type == UserType.COMPANY.getId() || type == UserType.STUDIO.getId()) {
                Account account = userRepository.getAccountByTel(tel);
                if (account == null) {
                    return "fail";
                }
                if (!Environment.DEBUG && !userService.checkCode(code, sessionCode)) {
                    return "CodeError";
                }
                if (account.getPassword().equals(password)) {
                    session.setMaxInactiveInterval(24 * 60 * 60);
                    session.setAttribute("id", account.getId());
                    session.setAttribute("tel", account.getTel());
                    session.setAttribute("type", type);
                    return "success";
                }
            } else if (type == UserType.ADMIN.getId() || type == UserType.EXPERT.getId()) {
                Admin admin = ar.findByAccount(tel);
                if (admin.getPassword().equals(password)) {
                    session.setMaxInactiveInterval(24 * 60 * 60);
                    session.setAttribute("id", admin.getId());
                    session.setAttribute("type", type);
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping("logoff")
    public String loginOff(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "success";
    }


    @PostMapping("changepassword")
    public String login(@RequestParam("password") String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String tel = (String) session.getAttribute("tel");
        accountRepository.changePassword(password, tel);
        return "success";
    }

    @RequestMapping("info")
    public String info(@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        Integer userType = (Integer) session.getAttribute("type");
        if (id != null) {
            if (userType == 0 || userType == 1) {
                String tel = (String) session.getAttribute("tel");
                User user = userRepository.getInfoByTel(tel);
                if (user == null) {
                    return "NotLogin";
                }
                ObjectMapper mapper = new ObjectMapper();
                String username = user.getUsername();
                String email = user.getEmail();
                String img_url = user.getImg();
                Integer user_type = user.getType().getId();
                Integer id_check = user.getAccount_id();
                String info = user.getInfo();
                String name = user.getName();
                String avatar = user.getAvatar();
                HashMap<String, Object> basic = new HashMap<>();
                basic.put("username", username);
                basic.put("email", email);
                basic.put("img", img_url);
                basic.put("type", user_type);
                basic.put("avatar", avatar);
                basic.put("tel", tel);
                basic.put("info", info);
                basic.put("name", name);
                basic.put("id", id);
                return mapper.writeValueAsString(basic);
            } else {
                return JSON.toJSONString(ar.findById(id));
            }
        }
        return "NotLogin";
    }

    @PostMapping("edit")
    public String edit(@RequestParam("name") String name,
                       @RequestParam(name = "tag") String tag,
                       @RequestParam("email") String email,
                       @RequestParam("username") String username,
                       @RequestParam("info") String info,
                       HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "NotLogin";
        }

        userService.edit(id, name, JsonUtils.jsonToList(tag, Integer.class), email, username, info);

        return "success";
    }

    @PostMapping("center")
    public Object center(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        JSONObject json = new JSONObject();

        if (id != null) {
            return userDataProcessor.getCenter(id);
        }
        return json;
    }

    @PostMapping("display_info")
    public JSONObject display(HttpServletRequest request, @RequestParam("id") Integer id,
                              @RequestParam("first") Integer first) {
        JSONObject json;
        json = JSONObject.parseObject(JSON.toJSONString(userRepository.getInfoById(id)));
        if (userService.isCompany(id))
            json.put("bid", projectRepository.findByStateAndCompanyID(2, id));
        json.put("complete", ps.displayPrj(id, first));
        return json;
    }

    @PostMapping("addMember")
    public String addMember(HttpServletRequest request, @RequestParam("email") String email,
                            @RequestParam("name") String name, @RequestParam("tel") String tel, @RequestParam("info") String info) {
        HttpSession session = request.getSession();
        Integer studio_id = (Integer) session.getAttribute("id");
        userService.addMember(name, tel, email, info, studio_id);
        return "success";
    }

    @PostMapping("delMember")
    public String delMember(@RequestParam("id") JSONArray id) {
        for (int i = 0; i < id.size(); i++)
            memberRepository.deleteById(id.getInteger(i));
        return "success";
    }

    @PostMapping("member")
    public JSONArray member(HttpServletRequest request, @RequestParam("id") Integer id) {
        HttpSession session = request.getSession();
        if (id == null)
            id = (Integer) session.getAttribute("id");
        return JSONArray.parseArray(JSON.toJSONString(memberRepository.findByStudioid(id)));
    }

    @GetMapping("notify")
    public Object notify(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            return "NotLogin";
        }
        int id = (int) session.getAttribute("id");
        return notificationDataProcessor.getNotifications(id);
    }

    @PostMapping("verify")
    public String verify(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("type") int typeId) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            return "NoPermission";
        }

        int id = (int) session.getAttribute("id");

        VerificationType verificationType = VerificationType.fromId(typeId);

        if (verificationType == VerificationType.UNKNOWN) {
            return "UnsupportedType";
        }

        return userService.uploadVerification(id, verificationType, file);
    }
}
