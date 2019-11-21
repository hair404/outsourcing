package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	
   List<Member> findByStudioid(Integer studio_id);
}
