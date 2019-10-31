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
import com.dao.CancelReasonRepository;
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
	@Autowired
	CancelReasonRepository reasonRepository;

	// a project information(the bid system)
	public String get_info(Integer project_id, Integer user_id) {
		Project project = projectRepository.get_info(project_id);
		UserInfo company = userRepository.getInfoById( user_id);
		JSONObject project_info = new JSONObject(project);
		project_info.put("companyName",	company.getUsername());
	
		if (project.getState()==0) {
			JSONArray enroll = new JSONArray();
			enroll.add(0);
			List<Bid> rs = bidRepository.get_info(project_id);
			for (int i=0;i<rs.size();i++) {
			   Bid bid = rs.get(i);
			   Integer company_id_check = bid.getCompany_id();
				if(company_id_check==user_id) {
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
				}
		}
		else if(project.getState()==1) {
			File_project file= fpj.get_file(project_id);
			Integer user_id_check = project.getCompanyID();
			String entity = userRepository.getInfoById(user_id).getEntity();
			if(user_id_check==user_id&&entity.equals("company")) {
				   project_info.put("companyID", "self");
			   }
			else project_info.put("studioID", "self");
			String studio_name = userRepository.getInfoById(project.getStudioID()).getUsername();
			project_info.put("studioName", studio_name);
			JSONObject file_info = new JSONObject(file);
			file_info.put("name", file.getPrj_id());
			project_info.put("file", file_info);
		}
		else if(project.getState()==2||project.getState()==3) {
			String entity = userRepository.getInfoById(user_id).getEntity();
			Integer user_id_check = project.getCompanyID();
			if(user_id_check==user_id&&entity.equals("company")) {
				   project_info.put("companyID", "self");
			   }
			else project_info.put("studioID", "self");
			String studio_name = userRepository.getInfoById(project.getStudioID()).getUsername();
			project_info.put("studioName", studio_name);
			project_info.remove("companyhasPaid");
			project_info.remove("studiohasPaid");
			if(project.getState()==3) {
				String reason = reasonRepository.get_reason_studio_id(project.getStudioID(),project.getId()).getReason();
				project_info.put("reason", reason);
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
