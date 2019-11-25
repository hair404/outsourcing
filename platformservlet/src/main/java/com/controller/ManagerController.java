package com.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dao.AccountRepository;
import com.dao.ActivityRepository;
import com.dao.AdProjectRepository;
import com.dao.AdStudioRepository;
import com.dao.CtgRepository;
import com.dao.FundRepository;
import com.dao.RefundRepository;
import com.dao.UserRepository;
import com.model.Ctg;
import com.service.AdService;
import com.utils.JsonUtils;

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
	@Autowired
	RefundRepository rfr;
	@Autowired
	JsonUtils js;

	@PostMapping("manager")
	public JSONArray manager(@RequestParam("state") Integer state,
			@RequestParam(name = "type", required = false) Integer type,
			@RequestParam(name = "text", required = false) String text,
			@RequestParam(name = "isVerified",required = false) Integer isVerified) {
		if (text != null)
			text = '%' + text + '%';
		if (state == 0) {
			if (type == 0) {
				if (text != null)
					return JSONArray.parseArray(JSON.toJSONString(ur.findByUsernameLike(text)));
				return JSONArray.parseArray(JSON.toJSONString(ur.findAll()));
			} else if (type == 1) {
				if (text != null)
					return JSONArray.parseArray(JSON.toJSONString(ur.findByTypeAndUsernameLike(0, text)));
				return JSONArray.parseArray(JSON.toJSONString(ur.getInfoByType(0)));
			} else if (type == 2) {
				if (text != null)
					return JSONArray.parseArray(JSON.toJSONString(ur.findByTypeAndUsernameLike(1, text)));
				return JSONArray.parseArray(JSON.toJSONString(ur.getInfoByType(1)));
			}
		} else if (state == 1) {
			if (type == null)
				return adService.ad();
			else if (type == 0) {
				if (text != null)
					return adService.adP(text);
				return adService.adP();
			} else if (type == 1) {
				if (text != null)
					return adService.adS(text);
				return adService.adS();
			}
		} else if (state == 2) {
			if (text != null)
				return JSONArray.parseArray(JSON.toJSONString(fr.findByPrjnameLike(text)));
			return JSONArray.parseArray(JSON.toJSONString(fr.findAll()));
		} else if (state == 3) {
			return JSONArray.parseArray(JSON.toJSONString(acr.findAll()));
		} else if (state == 4) {
		} else if (state == 5) {
			if (type == 0) {
				if (text != null)
					return JSONArray.parseArray(JSON.toJSONString(rfr.findByTypeAndNameLike(type, text)));
				return JSONArray.parseArray(JSON.toJSONString(rfr.findAll()));
			} else if (type == 1) {
				if (text != null)
					return JSONArray.parseArray(JSON.toJSONString(ur.findByTypeAndUsernameLike(1, text)));
				return JSONArray.parseArray(JSON.toJSONString(rfr.findByType(type)));
			} else if (type == 2) {
				if (text != null)
					return JSONArray.parseArray(JSON.toJSONString(ur.findByTypeAndUsernameLike(2, text)));
				return JSONArray.parseArray(JSON.toJSONString(rfr.findByType(type)));
			}
		}
		return null;
	}

	@PostMapping("getCtg")
	public String getCtg() {
		return cr.getOne((long)1).getCtg();
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
