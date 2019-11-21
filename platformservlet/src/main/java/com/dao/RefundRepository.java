package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.Refund;
import com.model.User;

public interface RefundRepository extends JpaRepository<Refund, Long> {
	@Query("select u from com.model.Refund u where u.type =?1")
	public List<User> getInfoByType(Integer type);
}
