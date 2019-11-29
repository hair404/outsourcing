package com.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.model.Bid;

public interface BidRepository extends CrudRepository<Bid, Integer> {

    public List<Bid> findAllByProjectId(int projectId);

    public Optional<Bid> getByProjectIdAndStudioId(int projectId, int studioId);
}
