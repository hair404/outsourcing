package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class FileDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void insert(String url, Integer id) {
		String sql = ("update user set img = ? where id = ?");  
		jdbcTemplate.update(sql,url,id);
	}
}
