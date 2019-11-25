package com.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dao.AdProjectRepository;
import com.dao.AdStudioRepository;
import com.dao.ProjectRepository;
import com.dao.UserRepository;
import com.model.Project;
import com.model.User;

@Service
public class SolrService {
	@Autowired

	private SolrClient solrClient;
	@Autowired
	ProjectRepository pr;
	@Autowired
	UserRepository ur;

	@Autowired
	AdProjectRepository apr;
	@Autowired
	AdStudioRepository asr;

	// cron代表的是时间 每两个小时
	@Scheduled(cron = "0 0 */2 * * ?")
	public void timer() throws Exception {
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		deleteDocument();
		Thread.sleep(5000);
		selectGoods();
	}

	public void deleteDocument() throws Exception {
		solrClient.deleteByQuery("*:*");
		solrClient.commit();
		System.out.println("delete successfully");
	}

	public void selectGoods() throws Exception {
		List<Project> list = pr.findAll();
		for (Project items : list) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("solr_id", items.getSolr_id());
			document.addField("prjname", items.getPrjname());
			document.addField("tag", items.getTag());
			document.addField("subtag", items.getSubtag());
			document.addField("img", items.getImg());
			document.addField("companyId", items.getCompanyID());
			document.addField("studioId", items.getStudioID());
			document.addField("price", items.getPrice());
			document.addField("info", items.getInfo());
			document.addField("state", items.getState());
			document.addField("ifAd", items.getIfAd());
			document.addField("entity", items.getEntity());
			document.addField("payinadvance", items.getPayinadvance());
			document.addField("weight", weight_studio(items.getCompanyID(),items.getSolr_id()));
			solrClient.add(document);
		}
		List<User> list2 = ur.findAll();
		for (User items : list2) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("solr_id", items.getSolr_id());
			document.addField("account_id", items.getAccount_id());
			document.addField("username", items.getUsername());
			document.addField("name", items.getName());
			document.addField("tel", items.getTel());
			document.addField("info", items.getInfo());
			document.addField("img", items.getImg());
			document.addField("type", items.getType());
			document.addField("email", items.getEmail());
			document.addField("alipay", items.getAlipay());
			document.addField("avatar", items.getAvatar());
			document.addField("entity", items.getEntity());
			document.addField("credit", items.getCredit());
			document.addField("weight", weight_studio(items.getCredit(), items.getSolr_id()));
			solrClient.add(document);
		}
		solrClient.commit();
		System.out.println("success");
	}

	public float weight_studio(float credit, String solr_id) {
		float weight = apr.findWeightBySolrid(solr_id);
		return (float) (weight * 0.6 + credit * 0.5);
	}
	
	public float weight_project(Integer companyId,String solr_id) {
		float weight = asr.findWeightBySolrid(solr_id);
		float credit = ur.findCreditById(companyId);
		return (float) (weight * 0.6 + credit * 0.5);
	}
	
}
