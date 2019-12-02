package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.model.AdProject;

public interface AdProjectRepository extends JpaRepository<AdProject, Integer> {

    @Query("select u from com.model.AdProject u where u.solrid =:solr_id")
    public AdProject findBySolrId(String solr_id);

    @Modifying
    @Query("update com.model.AdProject u set u.state=?1 where u.solrid=?2")
    void updateState(Integer state, String solr_id);

    public List<AdProject> findByPrjnameLike(String name);

    public float findWeightBySolrid(String solr_id);

    public Optional<AdProject> findByPrjId(int PrjId);



}
