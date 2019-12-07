package com.dao;

import java.util.List;
import java.util.Optional;

import com.alipay.api.domain.AdUser;
import com.model.Advertisement;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Integer> {

    List<Advertisement> findAllByType(int type);

    Optional<Advertisement> findByTypeAndTypeId(int type, int typeId);
}
