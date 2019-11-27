package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Cancel_reason;
import com.model.Complain_reason;

public interface ComplainReasonRepository extends JpaRepository<Complain_reason, Long> {
//	@Query("select u from com.model.Complain_reason u where u.studio_id =:studioid and u.project_id =:project_id")
//	public Cancel_reason get_reason_studio_id (@Param("studioid")Integer stu_id,@Param("project_id")Integer prj_id);	

}
