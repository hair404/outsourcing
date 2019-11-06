package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity,Long>{

}
