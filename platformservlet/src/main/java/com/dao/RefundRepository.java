package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.Refund;
import com.model.User;

public interface RefundRepository extends JpaRepository<Refund, Long> {

	public List<User> findByType(Integer type);
	
	public List<User> findByTypeAndNameLike(Integer type,String name);
	
	
}
