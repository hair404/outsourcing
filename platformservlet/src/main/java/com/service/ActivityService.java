package com.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dao.ActivityRepository;
import com.model.Activity;

@Service
public class ActivityService {
	@Autowired
	ActivityRepository ar;
	public void register_activity(MultipartFile file, String url) {
		String fileName = file.getOriginalFilename();
		String filePath = "F:/img/activity_img/";
		File dest = new File(filePath + fileName);
		String img_url = "http://localhost:8080/activity_img/" + fileName;
		try {
			Activity ac = new Activity();
			ac.setUrl(url);
			ac.setImg(img_url);
			ar.save(ac);
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
}
