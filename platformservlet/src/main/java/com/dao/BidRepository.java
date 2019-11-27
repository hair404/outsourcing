package com.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
	@Query("select u from com.model.Bid u where u.project_id =:project_id")
	public List<Bid> get_info(@Param("project_id") Integer project_id);
	@Query("select u from com.model.Bid u where u.company_id =:company_id")
	public List<Integer> getPrjIdByCom(Integer company_id);
	@Modifying 
	@Query("update com.model.Bid u set u.state=?1 where u.project_id=?2 and u.studio_id=?3")    
	void updateState( Integer state,Integer project_id,Integer studio_id);
	@Modifying 
	@Query("update com.model.Bid u set u.state=?1 where u.project_id=?2")    
	void updateAllState( Integer state,Integer project_id);
	
}
