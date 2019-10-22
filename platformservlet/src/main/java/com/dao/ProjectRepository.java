package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
   
	@Query("select u from com.model.Project u where u.id =:id")
	public Project get_info (@Param("id")Integer id);
}
