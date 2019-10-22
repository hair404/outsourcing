package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.model.UserInfo;

@Repository
public class ImgDao{
	@Autowired
	JdbcTemplate jdbcTemplate;
	UserInfo userInfo;
	
	@Query("select i from com.model.UserImg i where i.userId =:userId")
	public String getUserImg(@Param("id") Integer id) {
		return userInfo.getImg();
	}
	public void insert(String url, Integer id) {
		String sql = ("update user set img = ? where id = ?");  
		jdbcTemplate.update(sql,url,id);
	}
}
