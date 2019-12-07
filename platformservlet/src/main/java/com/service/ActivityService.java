package com.service;

import java.io.File;
import java.io.IOException;

import com.common.PictureCommon;
import com.type.PictureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dao.ActivityRepository;
import com.model.Activity;

import javax.annotation.Resource;

@Service
public class ActivityService {
	@Resource
	private ActivityRepository activityRepository;
	public void registerActivity(MultipartFile file, String url) throws IOException {
		File dest = PictureCommon.saveImage(file, PictureType.ACTIVITY);
		String img_url = "/activity_img/" + dest.getName();
		Activity activity = new Activity();
		activity.setUrl(url);
		activity.setImg(img_url);
		activityRepository.save(activity);
	}
}
