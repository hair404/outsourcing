package com.dao;

import java.util.List;

import com.alipay.api.domain.AdUser;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.model.AdStudio;

public interface AdStudioRepository extends JpaRepository<AdStudio, Integer> {
    @Modifying
    @Query("update com.model.AdStudio u set u.state=?1 where u.solrid=?2")
    void updateState(Integer state, String solrid);

    public List<AdStudio> findByUsernameLike(String name);

    float findWeightBySolrid(String solrid);

    AdStudio findById(int id);
}
