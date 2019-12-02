package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.model.Tag;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface TagRepository extends CrudRepository<Tag, Integer> {

    List<Tag> findAllByUserId(Integer userId);

    List<Tag> findAllByTag(int tag);

    @Transactional
    void deleteAllByUserId(int userId);

}
