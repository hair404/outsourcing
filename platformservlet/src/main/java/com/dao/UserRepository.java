package com.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Account;
import com.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	@Query("select u from com.model.Account u where u.id =:id")
	public Account getAccountById(@Param("id") Integer id); 
		
	@Query("select u from com.model.Account u where u.tel =:tel")
	public Account getAccountByTel(@Param("tel") String tel);
	
	@Query("select u from com.model.User u where u.tel =:tel")
	public User getInfoByTel(@Param("tel") String tel);
	
	@Query("select u from com.model.User u where u.account_id =:account_id")
	public User getInfoById(@Param("account_id") Integer account_id);
	
	@Query("select u from com.model.User u where u.type =?1")
	public List<User> getInfoByType(Integer type);
	
	@Query("select u.username from com.model.User u where u.account_id =?1")
	 String  findUsernameById(Integer id);

    public List<User> findByUsernameLike(String username);
    
    public float findCreditById(Integer id);    

    public List<User> findByTypeAndUsernameLike(Integer type, String username);

	@Transactional
	@Modifying   
	@Query("update com.model.User u set u.avatar=?1 where u.id=?2")    
	void updateAvatar(String avatar,Integer id);
	
	@Transactional
	@Modifying   
	@Query("update com.model.User u set u.img=?1 where u.id=?2")    
	void updateImg(String img,Integer id);
	
	
	
}
