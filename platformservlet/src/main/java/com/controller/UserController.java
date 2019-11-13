package com.controller;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.AccountRepository;
import com.dao.ProjectDao;
import com.dao.ProjectRepository;
import com.dao.TagDao;
import com.dao.UserDao;
import com.dao.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Account;
import com.model.UserInfo;
import com.service.TagService;
import com.service.UserService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

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
	private ProjectDao projectDao;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	AccountRepository  accountRepository;

	UserInfo user = new UserInfo();

	@RequestMapping("index")
	public String index() {
		return "success";
	}

	@PostMapping(value = "register")
	public String register(@RequestParam("name") String name, @RequestParam("phone") String tel,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("username") String username, @RequestParam("type") Integer type) {
		if (!userService.ifExsit(tel)) {
			userDao.insertAccount(password, tel);
			Integer account_id = accountRepository.get_id(tel);
			String solr_id = UUID.randomUUID().toString();
			String entity = null;
			if (type == 0)
				entity = "company";
			else if (type == 1)
				entity = "studio";
			else if (type == 2)
				entity = "manager";
		 userDao.insertInfo(solr_id, account_id, name, tel, email, username, type, entity);
			return "success";
		} else
			return "fail";
	}

	@RequestMapping("list")
	public java.util.List<UserInfo> list() {
		return userRepository.findAll();
	}

	@PostMapping(value = "login")
	public String login(@RequestParam("name") String tel, @RequestParam("password") String password,
			@RequestParam("type") Integer type, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Account account = userRepository.getAccountByTel(tel);
			if (account.getTel().equals(tel) && account.getPassword().equals(password))
				session.setMaxInactiveInterval(24 * 60 * 60);
			session.setAttribute("tel", tel);
			session.setAttribute("password", password);
			session.setAttribute("id", account.getId());
			session.setAttribute("type", type);
			return "success";
		} catch (Exception e) {
		}
		return "fail";
	}

	@RequestMapping("logoff")
	public String loginOff(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "success";
	}

	@RequestMapping("info")
	public String info(@RequestParam("type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("id");
		if (session.getId() != null) {
			String tel = (String) session.getAttribute("tel");
			UserInfo user = (UserInfo) userRepository.getInfoByTel(tel);
			ObjectMapper mapper = new ObjectMapper();
			String username = user.getUsername();
			String email = user.getEmail();
			String img_url = user.getImg();
			Integer user_type = user.getType();
			Integer id_check = user.getAccount_id();
			String info = user.getInfo();
			String name = user.getName();
			HashMap<String, Object> basic = new HashMap<>();
			basic.put("username", username);
			basic.put("email", email);
			basic.put("img", img_url);
			basic.put("type", user_type);
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
	public String edit(@RequestParam("name") String name, @RequestParam("tag") String tag,
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
	public String center(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("id");
		JSONObject json = new JSONObject();
		if (id != null) {
			UserInfo user = userRepository.getInfoById(id);
			JSONArray tag = tagDao.QueryTag(id);
			json.put("img", user.getImg());
			json.put("username", user.getUsername());
			json.put("name", user.getName());
			json.put("tag", tag);
			json.put("email", user.getEmail());
			json.put("phone", user.getTel());
			json.put("info", user.getInfo());
		}
		return json.toString();
	}

	@PostMapping("display_info")
	public String display(HttpServletRequest request,@RequestParam("id") String solr_id) {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("id");
		//Integer id=1;
		JSONObject json = new JSONObject();
		if (id != null) {
			UserInfo user = userRepository.getInfoById(id);
			JSONArray array = new JSONArray();
			array.put(0);
			JSONObject project  = new JSONObject(projectRepository.get_info_by_solr_id(solr_id));
			array.put(project);
			json.put("id", user.getSolr_id());
			json.put("type", user.getType());
			json.put("img", user.getImg());
			json.put("username", user.getUsername());
			json.put("email", user.getEmail());
			json.put("phone", user.getTel());
			json.put("info", user.getInfo());
			json.put("complete", array);
		}
		System.out.println(json);
		return json.toString();
		}
}
