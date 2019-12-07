package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.model.FileProject;

public interface FileProjectrepository extends JpaRepository<FileProject, Long>{
	
	@Query("select u.prj_id from com.model.FileProject u where u.prj_id =:prj_id")
	public Integer get_prj_id(@Param("prj_id") Integer prj_id);
	
	@Query("select u from com.model.FileProject u where u.prj_id =:prj_id")
	public FileProject get_file(@Param("prj_id") Integer prj_id);
	
	@Query("select u.url from com.model.FileProject u where u.prj_id =?1 and u.step_id=?2")
	String get_file(Integer prj_id,Integer step_id);
}
