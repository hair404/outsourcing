package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.model.Ad_studio;

public interface AdStudioRepository extends JpaRepository<Ad_studio,Long> {
	@Modifying   
	@Query("update com.model.Ad_studio u set u.state=?1 where u.solrid=?2")    
	void updateState(Integer state,String solrid);

	 public List<Ad_studio> findByUsernameLike(String name);

	float findWeightBySolrid(String solrid);
}
