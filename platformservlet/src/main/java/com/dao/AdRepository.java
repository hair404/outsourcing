package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Ad;

public interface AdRepository extends JpaRepository<Ad,Long> {

}
