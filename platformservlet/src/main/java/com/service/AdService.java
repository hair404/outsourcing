package com.service;

import java.sql.Date;
import java.util.Dictionary;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import com.dao.AdProjectRepository;
import com.dao.AdStudioRepository;
import com.dao.UserRepository;
import com.model.Ad_project;
import com.model.Ad_studio;
import com.model.UserInfo;
import com.utils.JsonUtils;

@Service
public class AdService {

	@Autowired
	AdProjectRepository apr;
	@Autowired
	AdStudioRepository asr;
	@Autowired
	UserRepository ur;

	public String recommend_ad_project(int first) {
		Integer page = first/20;
        Sort sort= new Sort(Direction.DESC,"weight");
		final Pageable pageable=PageRequest.of(page, 20, sort);
	return JsonUtils.objectToJson(apr.findAll(pageable).getContent());
	}
	public String recommend_ad_studio(int first) {
		Integer page = first/20;
        Sort sort= new Sort(Direction.DESC,"weight");
		final Pageable pageable=PageRequest.of(page, 20, sort);
	return JsonUtils.objectToJson(asr.findAll(pageable).getContent());
	}

	public void insert_ad_project(String prjName, Date dueTime, float ad_price, Integer tag, Integer subTag,
			float price, String img) {
		Ad_project ap = new Ad_project();
		ap.setPrjname(prjName);
		ap.setDueTime(dueTime);
		ap.setAd_price(ad_price);
		ap.setTag(tag);
		ap.setSubtag(subTag);
		ap.setPrice(price);
		ap.setImg(img);
		apr.save(ap);
	}

	public void insert_ad_studio(float ad_price, Integer tag, String img, Integer account_id) {
		Ad_studio as = new Ad_studio();
		UserInfo ui = ur.getInfoById(account_id);
		float weight = 1;// todo
		as.setAccount_id(account_id);
		as.setAvatar(ui.getAvatar());
		as.setAd_price(ad_price);
		as.setTag(tag);
		as.setEmail(ui.getEmail());
		as.setWeight(weight);
		as.setTel(ui.getTel());
		as.setImg(img);
		as.setUsername(ui.getUsername());
		asr.save(as);
	}

}
