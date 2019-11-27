package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Fund;
import com.model.User;

public interface FundRepository extends JpaRepository<Fund,Long>{
	public List<Fund> findByPrjnameLike(String prjname);
}
