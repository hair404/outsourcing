package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.dao.AccountRepository;
import com.dao.AdminRepository;
import com.dao.MemberRepository;
import com.dao.ProjectRepository;
import com.dao.TagDao;
import com.dao.TagRepository;
import com.dao.UserDao;
import com.dao.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Account;
import com.model.Admin;
import com.model.Project;
import com.model.User;
import com.service.ProjectService;
import com.service.TagService;
import com.service.UserService;
import com.utils.Code;
import com.utils.MessageTools;
import com.utils.UuidUtils;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TagService tagService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TagDao tagDao;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	private TagRepository tagR;
	@Autowired
	ProjectService ps;
	@Autowired
	AdminRepository ar;
	@Autowired
	Code co;

	User user = new User();

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
			HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			String sessionCode = (String) session.getAttribute("code");
			if (type == 0 || type == 1) {
				Account account = userRepository.getAccountByTel(tel);
				if (account.getPassword().equals(password) && userService.checkCode(code,sessionCode)) {
					session.setMaxInactiveInterval(24 * 60 * 60);
					session.setAttribute("id", account.getId());
					session.setAttribute("tel", account.getTel());
					session.setAttribute("type", type);
					return "success";
				}
			} else {
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
	public String info(@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("id") != null) {
			Integer id = (Integer) session.getAttribute("id");
			String tel = (String) session.getAttribute("tel");
			User user = (User) userRepository.getInfoByTel(tel);
			ObjectMapper mapper = new ObjectMapper();
			String username = user.getUsername();
			String email = user.getEmail();
			String img_url = user.getImg();
			Integer user_type = user.getType();
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
			try {
				if (type.equals("all")) {
					if (id_check == id) {
						basic.put("tel", tel);
						basic.put("info", info);
						basic.put("name", name);
						basic.put("id", "self");
						return mapper.writeValueAsString(basic);
					} else {
						basic.put("tel", tel);
						basic.put("info", info);
						basic.put("name", name);
						basic.put("id", id);
						return mapper.writeValueAsString(basic);
					}
				}
				return mapper.writeValueAsString(basic);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return "NotLogin";
	}

	@PostMapping("edit")
	public String edit(@RequestParam("name") String name, @RequestParam(name = "tag", required = false) String tag,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("username") String username, @RequestParam("phone") String tel,
			@RequestParam("info") String info, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("id");
		String tel_bef = (String) session.getAttribute("tel");
		// Account account = userService.getAccountById("id");
		userDao.updateAccount(tel, password, tel_bef);
		userDao.updateInfo(name, email, username, tel, info, id);
		tagService.tagService(id, tag);
		return "succes";
	}

	@PostMapping("center")
	public JSONObject center(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("id");
		JSONObject json = new JSONObject();

		if (id != null) {
			User user = userRepository.getInfoById(id);
			json.put("img", user.getImg());
			json.put("username", user.getUsername());
			json.put("name", user.getName());

			if (!userService.isCompany(id))
				json.put("tag", tagDao.QueryTag(id));

			json.put("avatar", user.getAvatar());
			json.put("email", user.getEmail());
			json.put("tel", user.getTel());
			json.put("info", user.getInfo());
			json.put("isValid", user.getIsValid());
		}
		return json;
	}

	@PostMapping("display_info")
	public JSONObject display(HttpServletRequest request, @RequestParam("id") Integer id,
			@RequestParam("first") Integer first) {
		JSONObject json = new JSONObject();
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
		JSONArray array = JSONArray.parseArray(JSON.toJSONString(memberRepository.findByStudioid(id)));
		return array;
	}
}
