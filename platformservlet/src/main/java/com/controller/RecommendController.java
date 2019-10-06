package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.service.AdService;

@RestController
public class RecommendController {

	@Autowired
	AdService adService;

	@RequestMapping(value = "recommend",produces = "application/json")
	public String recommend(@RequestParam("first") Integer first, @RequestParam("end") Integer end) {
	return adService.recommend_ad(first, end).toString();
	}
}
