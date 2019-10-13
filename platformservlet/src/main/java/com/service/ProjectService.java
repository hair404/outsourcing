package com.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dao.File_project_repository;
import com.model.File_project;



@Service
public class ProjectService {
	@Autowired
	File_project_repository fpj;
	
	public void upload(MultipartFile file, int prj_id) {
		String fileName = file.getOriginalFilename();
		String filePath = "F://file//"+prj_id+"/";
		File dest = new File(filePath + fileName);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);  
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_upload(MultipartFile file, int prj_id) {
		String fileName = file.getOriginalFilename();
		String filePath = "F://file//"+prj_id+"/";
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
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

