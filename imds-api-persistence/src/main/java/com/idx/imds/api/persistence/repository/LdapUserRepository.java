package com.idx.imds.api.persistence.repository;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import com.idx.imds.api.persistence.entity.LdapUser;

@Repository
public interface LdapUserRepository extends LdapRepository<LdapUser>{

	LdapUser findByUsername(String username);
	
}
