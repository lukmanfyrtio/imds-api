package com.idx.imds.api.persistence.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dtt.generic.persistence.repository.GenericRepository;
import com.idx.imds.api.persistence.entity.CustomerUser;

@Repository
public interface CustomerUserRepository extends GenericRepository<CustomerUser, Long>{

	@Query("SELECT obj FROM CustomerUser obj WHERE obj.status='L' and userType ='CUST' and userName = ?1  ")
	public CustomerUser getByUserName(String userName);

	
	@Modifying
	@Query("UPDATE from CustomerUser obj set obj.tokenConfluent = ?1, obj.tokenId = ?2, tokenCreatedTime =?4, tokenExpiredTime =?5, tokenMaxLifeTime = ?6  WHERE obj.status ='L' and userType='CUST' and userName = ?3 ")
	public void updateToken(String token, String tokenId, String username, Date createdTime, Date tokenExpiredTime, Date tokenMaxLifeTime );


	@Query("SELECT obj FROM CustomerUser obj WHERE obj.status='L' and userType ='CUST' and tokenId = ?1 and tokenConfluent = ?2 ")
	public CustomerUser getByTokenIdAndToken(String tokenId, String token);
}
