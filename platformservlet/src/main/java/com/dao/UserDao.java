package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.model.UserInfo;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	UserRepository userRepository;

//select	
	

	public List<UserInfo> getInfoByTag(Integer tag) {
		String sql = "select * from user";
		List<UserInfo> userList = (List<UserInfo>) jdbcTemplate.query(sql, new RowMapper<UserInfo>() {
			@Override
			public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserInfo user = new UserInfo();
				user.setAccount_id(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setTel(rs.getString("tel"));
				user.setEmail(rs.getString("email"));
				user.setInfo(rs.getString("Info"));
				return user;
			}
		});
		return userList;
	}
	
//insert	
		public void insertAccount(String password, String tel) {
		String sql = "insert into account(password,tel) values (?,?)";
		jdbcTemplate.update(sql, password, tel);
	}

	public void insertInfo(String solr_id,Integer account_id, String name, String tel,  String email, String username, Integer type, String entity) {
		String sqlCom = "insert into user(solr_id, account_id, name, tel, email, username, type, entity) values (?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sqlCom,solr_id,account_id, name, tel, email, username, type,entity);	
	}
	
//update
	
	public void updateInfo(String name, String email, String username,String tel, String info, Integer id) {
		String sql = "update user set name=?,email=?,username=?,tel=?,info=? where user_id=?)";
		jdbcTemplate.update(sql, name, email, username, tel, info ,id);
		
	}
	public void updateAccount(String tel,String tel_bef, String password) {
		String sql = "update account set tel=?,password=? where tel=?";
		jdbcTemplate.update(sql,tel,password,tel_bef);
	}

}
