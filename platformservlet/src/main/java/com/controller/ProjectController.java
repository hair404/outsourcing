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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.ProjectDao;
import com.dao.UserRepository;
import com.service.ProjectService;

@RestController
public class ProjectController {
	@Autowired
	ProjectService projectService;
	@Autowired
	ProjectDao projectDao;
	@Autowired
    UserRepository userRepository;
	
	@PostMapping("project_info")
	public String info(HttpServletRequest request, @RequestParam("id") Integer project_id){
//		HttpSession session = request.getSession();
//		Integer user_id = (Integer) session.getAttribute("id");
	Integer account_id = 1;
	userRepository.getInfoById(account_id);
		return projectService.get_info(project_id,account_id);
		
	}

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
		 String solr_id= UUID.randomUUID().toString();
			HttpSession session = request.getSession();
			Integer id = (Integer) session.getAttribute("id");
			id=1;
			if (file.isEmpty()) 
				return "null";
			else {
			if(id!=null)
			{
				String fileName = file.getOriginalFilename();
				String suffixName = fileName.substring(fileName.lastIndexOf("."));
				String filePath = "F://img//prj_img//";
				fileName = solr_id + suffixName;
				File dest = new File(filePath + fileName);
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				try {
					String img = "http://localhost:8080/prjimg/" + fileName;
					file.transferTo(dest);
					projectDao.insertPrj(name, tag, sub_tag, img, releaseTime, info, state, ifAd, deadline, price, id, solr_id, entity);
					return "success";
				} catch (IOException e) {
					e.printStackTrace();
				}
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