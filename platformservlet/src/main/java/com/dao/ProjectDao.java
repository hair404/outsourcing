package com.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.model.Project;

@Repository
public class ProjectDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ProjectRepository projectRepository;


	public List<Project> getCompletedProject(Integer id) {
		String sql = "select * from project where state=1 and (CompanyId=? or studioId=?)";
		List<Project> projectList = (List<Project>) jdbcTemplate.query(sql, new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
				Project project = new Project();
				project.setUuid(rs.getInt("id"));
				project.setTag(rs.getInt("tag"));
				project.setPrjname(rs.getString("prjname"));
				project.setSub_tag(rs.getInt("sub_tag"));
				project.setImg(rs.getString("img"));
				project.setPrice(rs.getFloat("price"));
				return project;
			}
		},id,id);
		return projectList;
	}
	
	public List<Project> getProjectById(Integer state,Integer id) {
		String sql = "select * from project where state=? and (CompanyId=? or studioId=?)";
		List<Project> projectList = (List<Project>) jdbcTemplate.query(sql, new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
				Project project = new Project();
				project.setUuid(rs.getInt("uuid"));
				project.setTag(rs.getInt("tag"));
				project.setPrjname(rs.getString("prjname"));
				project.setSub_tag(rs.getInt("sub_tag"));
				project.setImg(rs.getString("img"));
				project.setPrice(rs.getFloat("price"));
				return project;
			}
		},state,id,id);
		return projectList;
	}

	public void insertPrj(String name, Integer tag, Integer sub_tag, String img, Date releaseTime, String info,
			Integer state, Integer ifAd, Date deadline, float price, Integer companyId, String solr_id, String entity) {
		String sqlCom = "insert into project(prjname,tag,sub_tag,img,releaseTime,info,state,ifAd,deadline,price,companyId,solr_id,entity) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sqlCom, name, tag, sub_tag, img, releaseTime, info, state, ifAd, deadline, price,
				companyId,solr_id,entity);
	}
	
}
