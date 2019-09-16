package com.service;

import java.sql.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dao.AdDao;
import com.dao.AdRepository;
import com.model.Ad;
import com.utils.JsonUtils;

@Service
public class AdService {
	
	@Autowired
    AdDao adDao;	
		
	public JSONArray recommend_ad(int first,int end) {
		JSONArray array = new JSONArray();
	     array.put(0);
	     List<Ad> ad = adDao.queryAd();
	for (int i = first; i <= end; i++) {
		JSONObject ob =new JSONObject(ad.get(i));
		array.put(ob);
	}
	return array;
	}
	public void insert( Date dueTime,
			 String prjName,
			 Integer tag,
			// Integer subTag,
			 float price,
			 float ad_price,
			 String img,
			 String info,
			 String tel,
			 String email) {
		adDao.insert(dueTime, prjName, tag, price, ad_price, img, info, tel, email);
	}
	
	
	public void insert(Date dueTime, String prjName, Integer tag,Integer subTag, float price, float ad_price, String img) {
		
		adDao.insert(dueTime, prjName, tag,subTag, price, ad_price, img);
	}
	
}
