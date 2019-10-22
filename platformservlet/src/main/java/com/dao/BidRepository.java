package com.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
	@Query("select u from com.model.Bid u where u.project_id =:project_id")
	public List<Bid> get_info(@Param("project_id") Integer project_id);
}
