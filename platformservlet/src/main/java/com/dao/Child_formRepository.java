package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.Child_form;

public interface Child_formRepository extends JpaRepository<Child_form, Long> {
	@Query("select u from com.model.Child_form u where u.project_id =:project_id")
	public List<Child_form> getChildForm(Integer project_id);
	
	@Modifying 
	@Query("update com.model.Child_form u set u.price=?1 where u.project_id=?2 and u.part=?3")    
	void updatePrice( Float price,Integer id,Integer part);
	
	@Modifying 
	@Query("update com.model.Child_form u set u.state=?1 where u.project_id=?2 and u.part=?3")    
	void updateState( Integer state,Integer project_id,Integer part);
}
