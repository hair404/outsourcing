package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Account;
import com.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo,Long>{
	@Query("select u from com.model.Account u where u.id =:id")
	public Account getAccountById(@Param("id") Integer id); 
		
	@Query("select u from com.model.Account u where u.tel =:tel")
	public Account getAccountByTel(@Param("tel") String tel);
	
	@Query("select u from com.model.UserInfo u where u.tel =:tel")
	public UserInfo getInfoByTel(@Param("tel") String tel);
	
	@Query("select u from com.model.UserInfo u where u.account_id =:account_id")
	public UserInfo getInfoById(@Param("account_id") Integer account_id);
	
}
