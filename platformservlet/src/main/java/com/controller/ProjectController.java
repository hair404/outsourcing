package com.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.ProjectDao;
import com.model.UserInfo;
import com.service.AdService;
import com.service.ProjectService;

@RestController
public class ProjectController {
	@Autowired
	ProjectService projectService;
	@Autowired
	ProjectDao projectDao;

	@PostMapping("register_prj")
	public String register(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("name") String name,
			@RequestParam("tag") Integer tag,
			@RequestParam("sub_tag") Integer sub_tag,
			@RequestParam("file") MultipartFile file,
			@RequestParam("releaseTime") Date releaseTime,
			@RequestParam("info") String info,
			@RequestParam("deadline") Date deadline, 
			@RequestParam("price") float price
			) {
		 Integer ifAd = 0;
		 Integer state = 0;
		 String entity = "project";
		 String uuid = UUID.randomUUID().toString();
			HttpSession session = request.getSession();
			Integer id = (Integer) session.getAttribute("id");
			id=1;
			if (file.isEmpty()) {
				return "null";
			}
			if(id!=null)
			{
				String fileName = file.getOriginalFilename();
				String suffixName = fileName.substring(fileName.lastIndexOf("."));
				String filePath = "F://img//prj_img//";
				fileName = uuid + suffixName;
				File dest = new File(filePath + fileName);
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				try {
					String img = "http://localhost:8080/prj_img/" + fileName;
					file.transferTo(dest);
					projectDao.insertPrj(name, tag, sub_tag, img, releaseTime, info, state, ifAd, deadline, price, id, uuid, entity);
					return "success";
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
				return "false";
}
   @PostMapping("my_prj")
    public JSONArray myPrj(@RequestParam("state") Integer state,HttpServletRequest request) {
	   HttpSession session =request.getSession();
	   Integer id = (Integer) session.getAttribute("id");
	   //Integer id =1;
	   JSONArray array = new JSONArray();
	   if(id!=null) {
		   array.put(0);
		   array.put(projectDao.getProjectById(state, id));
	   }
	   System.out.println(array);
	   return array;
   }
}