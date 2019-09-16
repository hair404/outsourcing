package com.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.model.Ad;

@Repository
public class AdDao {
	@Autowired
	AdRepository adPrjRepository;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Ad> queryAd() {
		Sort sort = new Sort(Sort.Direction.DESC, "weight");
		List<Ad> listAd = adPrjRepository.findAll(sort);
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