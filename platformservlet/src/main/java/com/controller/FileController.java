package com.controller;

import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.File_projectRepository;
import com.service.ProjectService;

@RestController
public class FileController {
	@Autowired
	File_projectRepository fpj;
	@Autowired
	ResourceLoader resourceLoader;
	@Autowired
	ProjectService ser;

@GetMapping(value = "/file/{project_id:.+}/{step_id:.+}/{filename:.+}",produces ="application/octet-stream;charset = utf-8")
public ResponseEntity<?> get_user_img(@PathVariable String project_id,@PathVariable String step_id,@PathVariable String filename) {
	try {
		final String ROOT = "F://file//"+project_id+"//"+step_id;
		return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
	} catch (Exception e) {
		return ResponseEntity.notFound().build();
	}
}
}
