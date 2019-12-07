package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Admin;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAccount(String account);

    Admin findById(Integer id);

    List<Admin> findAllByType(int type);
}
