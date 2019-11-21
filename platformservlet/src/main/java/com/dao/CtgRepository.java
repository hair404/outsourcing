package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.Ctg;

public interface CtgRepository extends JpaRepository<Ctg, Long>{
	@Modifying   
	@Query("update com.model.Ctg u set u.ctg=?1")    
	void updateCtg(String ctg);
}
