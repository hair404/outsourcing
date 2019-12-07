package com.dao;

import com.model.Verification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VerificationRepository extends CrudRepository<Verification, Integer> {

    List<Verification> findAllByUserId(int userId);
}
