package com.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.model.Ad_project;

@Repository
public class AdDao {
	@Autowired
	AdProjectRepository adPrjRepository;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Ad_project> queryAd() {
		Sort sort = new Sort(Sort.Direction.DESC, "weight");
		List<Ad_project> project = adPrjRepository.findAll(sort);
		List<Ad_project> listAd = new ArrayList<>();
		listAd.addAll(project);
		return listAd;
	}
	
	public void insert(	 
	 Date dueTime,
	 String prjName,
	 Integer tag,
	// Integer subTag,
	 float price,
	 float ad_price,
	 String img,
	 String info,
	 String tel,
	 String email) {
		String sqlCom = "insert into ad(due_time,prj_name,tag,price,ad_price,img,info,tel,email)"
				+ "values (?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sqlCom, dueTime,prjName,tag,price,ad_price,img,info,tel,email);
}

	public void insert(Date dueTime, String prjName, Integer tag, Integer subTag,float price, float ad_price, String img) {
		String sqlCom = "insert into ad(due_time,prj_name,tag,sub_tag,price,ad_price,img)"
				+ "values (?,?,?,?,?,?,?)";
		jdbcTemplate.update(sqlCom, dueTime,prjName,tag,subTag,price,ad_price,img);
		
	}
}