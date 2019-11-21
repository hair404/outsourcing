package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.dao.AdProjectRepository;
import com.dao.AdStudioRepository;
import com.dao.ProjectRepository;
import com.dao.TagDao;
import com.dao.UserRepository;
import com.model.Ad_project;
import com.model.Ad_studio;
import com.model.Project;
import com.model.User;
import com.utils.JsonUtils;

@Service
public class AdService {

	@Autowired
	AdProjectRepository apr;
	@Autowired
	AdStudioRepository asr;
	@Autowired
	UserRepository ur;
	@Autowired
	ProjectRepository pr;
	@Autowired
	TagDao tagDao;

	public String recommend_ad_project(int first) {
		Integer page = first / 20;
		Sort sort = new Sort(Direction.DESC, "weight");
		final Pageable pageable = PageRequest.of(page, 20, sort);
		return JsonUtils.objectToJson(apr.findAll(pageable).getContent());
	}

	public String recommend_ad_studio(int first) {
		Integer page = first / 20;
		Sort sort = new Sort(Direction.DESC, "weight");
		final Pageable pageable = PageRequest.of(page, 20, sort);
		return JsonUtils.objectToJson(asr.findAll(pageable).getContent());
	}

	public void insert_ad_project(float ad_price, Integer prj_id) {
		Project prj = pr.get_info(prj_id);
		Ad_project ap = new Ad_project();
		ap.setPrjname(prj.getPrjname());
		ap.setDueTime(prj.getDeadline());
		ap.setAd_price(ad_price);
		ap.setTag(prj.getTag());
		ap.setSubtag(prj.getSubtag());
		ap.setPrice(prj.getPrice());
		ap.setImg(prj.getImg());
		ap.setWeight(weight(ad_price));
		ap.setPrj_id(prj_id);
		ap.setSolr_id(prj.getSolr_id());
		ap.setState(0);
		apr.save(ap);
	}

	public Float weight(Float ad_price) {
		return ad_price / 1000;
	}

	public void insert_ad_studio(float ad_price, Integer account_id) {
		Ad_studio as = new Ad_studio();
		User ui = ur.getInfoById(account_id);
		as.setAccount_id(account_id);
		as.setAvatar(ui.getAvatar());
		as.setAd_price(ad_price);
		as.setTag(tagDao.QueryTag(account_id));
		as.setEmail(ui.getEmail());
		as.setWeight(weight(ad_price));
		as.setTel(ui.getTel());
		as.setImg(ui.getImg());
		as.setUsername(ui.getUsername());
		as.setSolr_id(ui.getSolr_id());
		as.setCredit(ui.getCredit());
		as.setState(0);
		asr.save(as);
	}

	public Boolean isProject(String solr_id) {
		if (apr.findBySolrId(solr_id) != null)
			return true;
		else
			return false;
	}

}
