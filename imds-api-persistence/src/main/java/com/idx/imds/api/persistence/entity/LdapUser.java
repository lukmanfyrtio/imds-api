package com.idx.imds.api.persistence.entity;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Attribute.Type;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(base = "ou=People", objectClasses = {"inetOrgPerson"})
public class LdapUser {
	@Id
	private Name id;

	private @Attribute(name = "cn") String username;
	private @Attribute(name = "sn") String lastName;
	
	private @Attribute(name = "uid") String uid;
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	

	

	

	// standard getters/setters
	
	
}
