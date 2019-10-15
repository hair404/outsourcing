package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.model.File_project;

public interface File_project_repository extends JpaRepository<File_project, Long>{
	
	@Query("select u.prj_id from com.model.File_project u where u.prj_id =:prj_id")
	public Integer get_prj_id(@Param("prj_id") Integer prj_id);
	
}
