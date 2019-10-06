package com.controller;

import java.io.File;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.service.ImgService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ImgController {
	@Autowired
	ImgService imgService;
	@Autowired
	ResourceLoader resourceLoader;
	public static final String ROOT = "F:/img/user_img";

	@PostMapping("upload_userimg" )
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("id");
		if (file.isEmpty()) {
			return "null";
		}
		if(id!=null)
		{
			String fileName = file.getOriginalFilename();
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String filePath = "F://img//user_img//";
			fileName = UUID.randomUUID() + suffixName;
			File dest = new File(filePath + fileName);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			try {
				String url = "http://localhost:8080/userimg/" + fileName;
				file.transferTo(dest);
				imgService.insert(url, id);
				return url;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
			}
			return "false";
	}

	@GetMapping(value = "/userimg/{filename:.+}",produces ="application/octet-stream;charset = utf-8")
	public ResponseEntity<?> getFile(@PathVariable String filename) {
		try {
			return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
