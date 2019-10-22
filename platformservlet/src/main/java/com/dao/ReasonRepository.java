package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Reason;

public interface ReasonRepository extends JpaRepository<Reason, Long> {
	@Query("select u from com.model.Reason u where u.studioid =:studioid and u.project_id =:project_id")
	public Reason get_reason_studio_id (@Param("studioid")Integer stu_id,@Param("project_id")Integer prj_id);
}
