package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.model.File_project;

public interface File_project_repository extends JpaRepository<File_project, Long>{
	
	@Query("select u.prj_id from com.model.File_project u where u.prj_id =:prj_id")
	public Integer get_prj_id(@Param("prj_id") Integer prj_id);
	
	@Query("select u from com.model.File_project u where u.prj_id =:prj_id")
	public File_project get_file(@Param("prj_id") Integer prj_id);
	
	@Query("select u.url from com.model.File_project u where u.prj_id =?1 and u.step_id=?2")
	String get_file(Integer prj_id,Integer step_id);
}
