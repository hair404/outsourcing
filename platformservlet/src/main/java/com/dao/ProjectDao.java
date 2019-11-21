package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.Project;

@Repository
public class ProjectDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ProjectRepository projectRepository;

	// user's project information
	public List<Project> getCompletedProject(Integer id) {
		String sql = "select * from project where state=1 and (companyId=? or studioId=?)";
		List<Project> projectList = (List<Project>) jdbcTemplate.query(sql, new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTag(rs.getInt("tag"));
				project.setPrjname(rs.getString("prjname"));
				project.setSubtag(rs.getInt("subtag"));
				project.setImg(rs.getString("img"));
				project.setPrice(rs.getFloat("price"));
				return project;
			}
		}, id, id);
		return projectList;
	}



}
