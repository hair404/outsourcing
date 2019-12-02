package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.ChildForm;

import javax.persistence.Transient;
import javax.transaction.Transactional;

public interface ChildFormRepository extends JpaRepository<ChildForm, Integer> {
    @Query("select u from com.model.ChildForm u where u.projectId =:project_id")
    public List<ChildForm> getChildForm(Integer project_id);

    @Modifying
    @Query("update com.model.ChildForm u set u.price=?1 where u.projectId=?2 and u.part=?3")
    void updatePrice(Float price, Integer id, Integer part);

    @Modifying
    @Transactional
    @Query("update com.model.ChildForm u set u.state=?1 where u.projectId=?2 and u.part=?3")
    void updateState(Integer state, Integer project_id, Integer part);

    Optional<ChildForm> findByProjectIdAndPart(int projectId, int part);
}
