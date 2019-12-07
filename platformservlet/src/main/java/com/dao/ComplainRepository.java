package com.dao;

import java.util.List;

import com.model.Complain;
import org.springframework.data.jpa.repository.JpaRepository;

import com.model.User;

public interface ComplainRepository extends JpaRepository<Complain, Long> {

    List<Complain> findByType(Integer type);

    List<Complain> findByTypeAndNameLike(Integer type, String name);

    int countByExpert(int expert);

    List<Complain> findAllByExpert(int expert);
}
