package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.model.Ad_project;

public interface AdProjectRepository extends JpaRepository<Ad_project,Long> {

	@Query("select u.prj_id from com.model.Ad_project u where u.id =:id")
	public Integer getProjectIdById(Integer id);
	
}
