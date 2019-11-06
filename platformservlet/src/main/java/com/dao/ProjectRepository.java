package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Project;


public interface ProjectRepository extends JpaRepository<Project, Long> {
   
	@Query("select u from com.model.Project u where u.id =:id")
	public Project get_info (@Param("id")Integer id);
	
	@Query("select u from com.model.Project u where u.solr_id =:solr_id")
	public Project get_info_by_solr_id(@Param("solr_id")String solr_id);
	
	@Query("select u.studioID from com.model.Project u where u.id =:id")
	public Integer get_studioid(@Param("id")Integer id);
	
	@Query("select u.companyID from com.model.Project u where u.id =:id")
	public Integer get_companyid(@Param("id")Integer id);
		
	@Modifying   
	@Query("update com.model.Project u set u.studioID=?1 where u.id=?2")    
	void update_studioid(Integer studioid,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.companyhasPaid=?1 where u.id=?2")    
	void update_companyPaidState(Integer state,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.studiohasPaid=?1 where u.id=?2")    
	void update_studioPaidState(Integer state,Integer id);
}
