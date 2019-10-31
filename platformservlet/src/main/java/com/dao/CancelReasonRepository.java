package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Cancel_reason;

public interface CancelReasonRepository extends JpaRepository<Cancel_reason, Long> {
	@Query("select u from com.model.Cancel_reason u where u.studioid =:studioid and u.project_id =:project_id")
	public Cancel_reason get_reason_studio_id (@Param("studioid")Integer stu_id,@Param("project_id")Integer prj_id);	

}
