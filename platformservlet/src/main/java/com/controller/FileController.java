 package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.File_project_repository;
import com.service.ProjectService;

@RestController
public class FileController {
	@Autowired
	File_project_repository fpj;
	
	@Autowired
	ProjectService ser;

	public static final String ROOT = "F:/file";

	@PostMapping("upload_file")
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("prjid") Integer prj_id,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		//Integer id = (Integer) session.getAttribute("id");// user's id;
		Integer id =1;
		if (file.isEmpty()) {
			return "null";
		} else {
			if (id != null) {
				Integer check = fpj.get_prj_id(prj_id);
				if (check == prj_id) 
					ser.upload(file, prj_id);        
				else 
					ser.insert_upload(file, prj_id);
				  return "success";
			}
		}

		return "false";
	}

	@PostMapping("examine")
	public String exmaine(@RequestParam("filename") String filename, @RequestParam("action") Integer action) {

		return "success";
	}
}
