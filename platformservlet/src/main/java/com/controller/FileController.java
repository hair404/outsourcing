package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class FileController {
	
	public static final String ROOT = "F:/file";
	@PostMapping("upload_file")
		public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
			HttpSession session = request.getSession();
			Integer id = (Integer) session.getAttribute("id");
			if (file.isEmpty()) {
				return "null";
			}
			if(id!=null)
			{
				String fileName = file.getOriginalFilename();
				String filePath = "F://file//";
				File dest = new File(filePath + fileName);
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				try {
					String url = "http://localhost:8080/upload_file/" + fileName;
					file.transferTo(dest);
					return url;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				return "false";
		}




	@PostMapping("examine")
	public String exmaine(@RequestParam("filename") String filename, 
			@RequestParam("action") Integer action) {

     return "success";
}
}
