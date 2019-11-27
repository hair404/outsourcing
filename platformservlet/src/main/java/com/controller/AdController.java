package com.controller;


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
	public String establish_ad1(@RequestParam("ad_price") float ad_price, @RequestParam("id") Integer id) {
		adService.insert_ad_project(ad_price, id);
		return "success";
	}

	@PostMapping("ad_register_stu")
	public String establish_ad2(@RequestParam("ad_price") float ad_price, @RequestParam("id") Integer id) {
		adService.insert_ad_project(ad_price, id);
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
