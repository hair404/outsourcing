package com.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.dao.BidRepository;
import com.dao.File_project_repository;
import com.dao.ProjectRepository;
import com.dao.UserRepository;
import com.model.Bid;
import com.model.File_project;
import com.model.Project;
import com.model.UserInfo;

@Service
public class ProjectService {
	@Autowired
	File_project_repository fpj;
	@Autowired
	BidRepository bidRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;

	// a project information(the bid system)
	public String get_info(Integer project_id, Integer company_id) {
		Project project = projectRepository.get_info(project_id);
		UserInfo company = userRepository.getInfoById(company_id);
		JSONObject project_info = new JSONObject(project);
		project_info.put("companyName",	company.getUsername());
	
		if (project.getState()==0) {
			JSONArray enroll = new JSONArray();
			enroll.add(0);
			List<Bid> rs = bidRepository.get_info(project_id);
			for (int i=0;i<rs.size();i++) {
			   Bid bid = rs.get(i);
			   Integer company_id_check = bid.getCompany_id();
				if(company_id_check==company_id) {
					   project_info.put("companyID", "self");
				   }
				Integer account_id = bid.getProject_id();
				UserInfo user = userRepository.getInfoById(account_id);
				String prjname = user.getUsername();
				JSONObject user_info = new JSONObject(user);
				
				user_info.remove("username");
				user_info.put("prjname", prjname);
				enroll.add(user_info);
				project_info.put("enroll", enroll);	
				System.out.println(user_info);
				System.out.println(user);
				}
		}

		return project_info.toString();
	}

	public void upload(MultipartFile file, int prj_id) {
		String fileName = file.getOriginalFilename();
		String filePath = "F://file//" + prj_id + "/";
		File dest = new File(filePath + fileName);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insert_upload(MultipartFile file, int prj_id) {
		String fileName = file.getOriginalFilename();
		String filePath = "F://file//" + prj_id + "/";
		File dest = new File(filePath + fileName);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			String url = "http://localhost:8080/upload_file/" + fileName;
			File_project prj = new File_project();
			prj.setUrl(url);
			prj.setPrj_id(prj_id);
			fpj.save(prj);
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
