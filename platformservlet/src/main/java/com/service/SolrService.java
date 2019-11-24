package com.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
	//cron代表的是时间  如下代表（23点59分59秒）  
	@Scheduled(cron = "0 * 0/2 * * *")    
	public void timer() throws Exception{       
		LocalDateTime localDateTime =LocalDateTime.now();          
		System.out.println("当前时间为:" +                                 
		localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));        
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
			 System.out.println(items);         
			 document.addField("solr_id",items.getSolr_id());          
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
			 solrClient.add(document);          
			 }
		 List<User> list2 = ur.findAll();        
		 for (User items : list2) {            
			 SolrInputDocument document = new SolrInputDocument();        
			 System.out.println(items);         
			 document.addField("solr_id",items.getSolr_id());  
			 document.addField("account_id",items.getAccount_id());  
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
			 solrClient.add(document);          
			 }         
		 solrClient.commit();      
		 System.out.println("success");   
		 }
		 }
	 
	
	
	
	


