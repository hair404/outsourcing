package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.model.Account;


@Transactional
public interface AccountRepository extends JpaRepository<Account,Integer> {
	@Query("select u.id from com.model.Account u where u.tel =:tel")
	public Integer get_id (String tel);
	
	@Modifying
	@Query("update com.model.Account u set u.password =?1 where u.tel = ?2")
	 void changePassword(String password,String tel);
}
