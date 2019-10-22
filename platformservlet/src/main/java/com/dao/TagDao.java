package com.dao;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class TagDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public JSONArray queryUserId(Integer tag) {
		String sql = String.format("select user_id from tag where tag = '%d'", tag);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		JSONArray json = new JSONArray();
		while (rs.next()) {
			json.put(rs.getInt("user_id"));
		}
		return json;
	}

	public void InsertTag(Integer id, Integer tag) {
		String sql = "Insert into tag (tag) where user_id=? values (?)";
		jdbcTemplate.update(sql, id, tag);
	}

	public void DeleteTag(Integer id) {
		String sql = "delete from tag where user_id=?";
		jdbcTemplate.update(sql, id);
	}

	public JSONArray QueryTag(Integer id) {
		String sql = String.format("select tag from tag where user_id = '%d'", id);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		JSONArray json = new JSONArray();
		while (rs.next()) {
			json.put(rs.getInt("tag"));
		}
		return json;
	}
}
