package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Fund;

public interface FundRepository extends JpaRepository<Fund,Long>{

}
