package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.Ad_studio;

public interface AdStudioRepository extends JpaRepository<Ad_studio,Long> {
	@Modifying   
	@Query("update com.model.Ad_studio u set u.state=?1 where u.solr_id=?2")    
	void updateState(Integer state,String solr_id);
}
