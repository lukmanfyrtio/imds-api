package com.idx.imds.api.controller.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idx.imds.api.persistence.entity.LdapUser;
import com.idx.imds.api.persistence.repository.LdapUserRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/ldap")
public class LdapController {
	
	@Autowired 
	private LdapUserRepository ldapRepository;
	
	@GetMapping("/users")
	private Iterable<LdapUser> getLdapUsers() {
		return ldapRepository.findAll();
	}
}
