package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Long>{
	@Modifying   
	@Query("update com.model.Payment u set u.state=?2 where u.project_id=?1")    
	void update_state(Integer state,Integer project_id);
}
