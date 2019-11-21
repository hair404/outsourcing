package com.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.api.domain.AccountRecord;
import com.dao.AccountRepository;
import com.dao.ActivityRepository;
import com.dao.AdProjectRepository;
import com.dao.AdStudioRepository;
import com.dao.CtgRepository;
import com.dao.FundRepository;
import com.dao.UserRepository;
import com.service.AdService;

@RestController

public class ManagerController {
	@Autowired
	UserRepository ur;
	@Autowired
	AccountRepository ar;
	@Autowired
	AdService adService;
	@Autowired
	AdProjectRepository apr;
	@Autowired
	AdStudioRepository asr;
	@Autowired
	CtgRepository cr;
	@Autowired
	ActivityRepository acr;
	@Autowired
	FundRepository fr;

	@PostMapping("manager")
	public JSONArray manager(@RequestParam("state") Integer state,
			@RequestParam(name = "type", required = false) Integer type,
			@RequestParam(name = "text", required = false) String text) {
		if (state == 0) {
          if(type==0)
        	 return JSONArray.parseArray(JSON.toJSONString(ur.findAll()));
          if(type==1)
        	 return JSONArray.parseArray(JSON.toJSONString(ur.getInfoByType(0)));
          if(type==2)
        	 return JSONArray.parseArray(JSON.toJSONString(ur.getInfoByType(1)));  
		}
		if (state == 1) {

		}
		if (state == 2) {
			 return JSONArray.parseArray(JSON.toJSONString(fr.findAll()));  
		}
		if (state == 3) {
              return JSONArray.parseArray(JSON.toJSONString(acr.findAll())); 
		}
		if (state == 4) {

		}
		if (state == 5) {

		}
		return null;
	}

	@PostMapping("getCtg")
	public JSONArray getCtg() {
		return JSONArray.parseArray(JSON.toJSONString(cr.getOne((long) 1)));
	}

	@PostMapping("manager_action")
	public String managerAction(@RequestParam("state") Integer state,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "solr_id", required = false) String solr_id,
			@RequestParam(name = "do", required = false) Integer action,
			@RequestParam(name = "ctg", required = false) String ctg) {
		if (state == 0) {
			ur.deleteById(id);
			ar.deleteById(id);
		} else if (state == 1) {
			if (adService.isProject(solr_id)) {
				if (action == 1)
					apr.updateState(1, solr_id);
			} else {
				if (action == 1)
					asr.updateState(1, solr_id);
			}
		} else if (state == 2) {
		} else if (state == 3) {
			acr.deleteById((long) id);
		} else if (state == 4) {
			cr.updateCtg(ctg);
		} else if (state == 5) {

		}
		return "success";
	}
}
