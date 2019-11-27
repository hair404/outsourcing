package com.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findByUserId(Integer user_id);
}
