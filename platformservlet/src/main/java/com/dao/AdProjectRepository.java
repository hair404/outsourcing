package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.model.Ad_project;

public interface AdProjectRepository extends JpaRepository<Ad_project,Long> {

	@Query("select u.prj_id from com.model.Ad_project u where u.id =:id")
	public Integer getProjectIdById(Integer id);
	
	@Query("select u from com.model.Ad_project u where u.solrid =:solr_id")
	public Ad_project findBySolrId(String solr_id);	
	
	@Modifying   
	@Query("update com.model.Ad_project u set u.state=?1 where u.solrid=?2")    
	void updateState(Integer state,String solr_id);
	
	 public List<Ad_project> findByPrjnameLike(String name);
	 
	 public float findWeightBySolrid(String solr_id);
	 

}
