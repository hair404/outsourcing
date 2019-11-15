package com.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.AdService;

@RestController
public class AdController {
	@Autowired
	AdService adService;
	
	@PostMapping("ad_register_prj")
	public String establdish_ad2(
			@RequestParam("prjName") String prjName,
			@RequestParam("dueTime") Date dueTime,
			@RequestParam("ad_price") float ad_price, 
			@RequestParam("tag") Integer tag,		
			@RequestParam("subTag") Integer subTag,
			@RequestParam("price") float price,
			@RequestParam("img") String img
		) {
		adService.insert_ad_project(prjName, dueTime, ad_price, tag, subTag, price, img);
		   
		return "success";
	}

	@RequestMapping("recommend_project")
	public String recommend_pro(@RequestParam("first") Integer first) {
	return adService.recommend_ad_project(first);
	}
	@RequestMapping("recommend_studio")
	public String recommend_stu(@RequestParam("first") Integer first) {
	return adService.recommend_ad_studio(first);
	}
		
}
