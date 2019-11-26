package com.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Project;


public interface ProjectRepository extends JpaRepository<Project, Long> {
   
	@Query("select u from com.model.Project u where u.id =:id")
	public Project get_info (@Param("id")Integer id);
	
	@Query("select u from com.model.Project u where u.solr_id =:solr_id")
	public Project getInfoBySolrId(@Param("solr_id")String solr_id);
	
	@Query("select u.studioID from com.model.Project u where u.id =:id")
	public Integer get_studioid(@Param("id")Integer id);
	
	@Query("select u.companyID from com.model.Project u where u.id =:id")
	public Integer get_companyid(@Param("id")Integer id);
	
	@Query("select u.companyName from com.model.Project u where u.id =:id")
	public String findCompanyNameById(Integer id);
	
	@Query("select u from com.model.Project u where u.state=?1 and (u.companyID=?2 or u.studioID=?2)")
	public List<Project> getProjectById(Integer state,Integer id);
	
	@Query("select u from com.model.Project u where u.state=?1 and (u.companyID=?2 or u.studioID=?2)")
	public List<Project> getProjectById(Integer state,Integer id,Pageable pageable);
	
	@Query("select u from com.model.Project u where u.state=?1 and u.companyID=?2")
	public List<Project> findByStateAndCompanyID(Integer state,Integer companyID);
	
	@Query("select u from com.model.Project u where u.companyID=?1 or u.studioID=?1")
	public List<Project> findByCompanyIDOrStudioID(Integer id,Pageable pageable);
	
	@Query("select u from com.model.Project u where u.companyID=?1 or u.studioID=?1")
	public List<Project> findByCompanyIDOrStudioID(Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.studioID=?1 where u.id=?2")    
	void update_studioid(Integer studioid,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.state=?1 where u.id=?2")    
	void update_state(Integer state,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.isconfirm=?1 where u.id=?2")    
	void updateIsconfirm(Integer isconfirm,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.hasPaid=?1 where u.id=?2")    
	void updateHasPaid(Integer hasPaid,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.studioRate=?1 where u.id=?2")    
	void updateStudioRate(Double studioRate,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.companyRate=?1 where u.id=?2")    
	void updateCompanyRate(Float companyRate,Integer id);
	
	
	@Modifying   
	@Query("update com.model.Project u set u.isform=?1 where u.id=?2")    
	
	void updateIsform(Integer isform ,Integer id);
	@Modifying   
	@Query("update com.model.Project u set u.isdeposit=?1 where u.id=?2")    
	void updateIsdeposit(Integer isdeposit,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.ispia=?1 where u.id=?2")    
	void updateIspia(Integer ispia,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.issetprice=?1 where u.id=?2")    
	void updateIssetprice(Integer issetprice,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.current=?1 where u.id=?2")    
	void updateCurrent(Integer current,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.payDeadline=?1 where u.id=?2")    
	void updatePayDeadline(Date date,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.startTime=?1 where u.id=?2")    
	void updateStartTime(Date date,Integer id);
	
	@Modifying   
	@Query("update com.model.Project u set u.countdown=?1 where u.id=?2")    
	void updateCountdown(Date date,Integer id);

//	@Modifying   
//	@Query("update com.model.Project u set u.studiohasPaid=?1, where u.id=?2")    
//	void update_studioPaidState(Integer state,Integer id);
}
