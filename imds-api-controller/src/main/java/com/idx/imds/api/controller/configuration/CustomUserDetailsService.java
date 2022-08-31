package com.idx.imds.api.controller.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.idx.imds.api.persistence.entity.CustomerUser;
import com.idx.imds.api.persistence.repository.CustomerUserRepository;
import com.idx.imds.api.persistence.repository.LdapUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final Logger log = LogManager.getLogger(CustomUserDetailsService.class);
	@Autowired
	private CustomerUserRepository customerUserRepository;

	@Autowired 
	private LdapUserRepository ldapRepository;
	
	public Boolean authenticate(String u) {
	    return ldapRepository.findByUsername(u) != null;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Username access : "+username);
		CustomerUser user = customerUserRepository.getByUserName(username);
		if(user!=null&& authenticate(username)) {
			return buildUserForAuthentication(user, new ArrayList<>());
		}else {
	        throw new UsernameNotFoundException("User name not found");
	    }
	}
	/*
	 * private List<GrantedAuthority> getUserAuthority() { List<GrantedAuthority>
	 * authorities = new ArrayList<>(); authorities.add(new
	 * SimpleGrantedAuthority(AppConstant.ADMIN_AUTHORITY)); return authorities; }
	 */

	private UserDetails buildUserForAuthentication(CustomerUser user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);
	}
	
	
}
