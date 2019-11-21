package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.model.Account;

public interface AccountRepository extends JpaRepository<Account,Integer> {
	@Query("select u.id from com.model.Account u where u.tel =:tel")
	public Integer get_id (String tel);
}
