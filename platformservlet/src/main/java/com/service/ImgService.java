package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.ImgDao;

@Service
public class ImgService {

	@Autowired
	ImgDao imgDao;
	
	public void insert( String url, Integer id) {
			imgDao.insert(url,id);
	}
	public String query(Integer userId) {
		return imgDao.getUserImg(userId);
	}
}
